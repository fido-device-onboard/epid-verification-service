#!/bin/bash

# Copyright 2021 Intel Corporation
# SPDX-License-Identifier: Apache 2.0

function usage() {
  echo "Usage: $0 [OPTIONS]"
  echo "Options:"
  echo "-h | --help                  - show this message"
  echo "-v | --verbose               - produce more output during execution of cmake and make"
  echo "-d | --debug                 - alias for verbose"
  echo "-c | --clean                 - clean temporary files and build artifacts"
  echo "--coverage                   - measure unit test coverage"
  echo "--skip-unit-tests            - skip running unit tests after build"
  echo "--eclipse                    - generate Eclipse project files instead of running build"
  echo "--format                     - enforce code formatting rules"
  exit 1
}

GETOPT_RESULT=`getopt -o hvdcu:p: --long help,verbose,debug,clean,user:,password:,coverage,skip-unit-tests,eclipse,format -- "$@"`

if [ $? != 0 ]; then
  echo "getopt failed! Aborting!"
  exit 1
fi

eval set -- "$GETOPT_RESULT"

CLEAN=false
VERBOSE=false
BUILD_VERSION="9.99.999"
SKIP_TESTS=false
COVERAGE=false
ECLIPSE=false
FORMAT=false

while true; do
  case "$1" in
    -c | --clean)
      CLEAN=true
      shift;;
    -v | --verbose | -d | --debug)
      VERBOSE=true
      shift;;
    --skip-unit-tests)
      SKIP_TESTS=true
      shift;;
    --coverage)
      COVERAGE=true
      shift;;
    --eclipse)
      ECLIPSE=true
      shift;;
    --format)
      FORMAT=true
      shift;;
    -h | --help)
      usage;;
    --)
      shift
      break;;
    *)
      break;;
  esac
done

SCRIPT_DIR=$(cd `dirname "${BASH_SOURCE[0]}"` && pwd)
source "$SCRIPT_DIR/tools/coverage.sh" service

CMAKE="cmake"
CTEST="ctest"

if [ "$COVERAGE" = true ] && [ "$SKIP_TESTS" = true ]; then
  echo "--coverage conflicts with --skip-unit-tests. I don't know what to do. Aborting!"
  exit 1
fi

BUILD_DIR="$SCRIPT_DIR/build"
mkdir -p "$BUILD_DIR"

if [ "$ECLIPSE" = true ]; then
  cd "$BUILD_DIR"
  "$CMAKE" -G"Eclipse CDT4 - Unix Makefiles" -D CMAKE_BUILD_TYPE=Debug ../src/service
  ERROR_CODE="$?"
  if [ "$ERROR_CODE" != "0" ]; then
    echo "CMake failed! Aborting!"
    exit 1
  fi

  # Get rid of extern "C" syntax errors
  sed -i "/__cplusplus/d" .cproject
  mv .cproject .project ../src/service
  exit 0
fi

if [ "$COVERAGE" = true ]; then
  echo "Coverage forced cleaning of cmake/make caches"
  rm -rf "$BUILD_DIR"
  mkdir -p "$BUILD_DIR"
fi

if [ "$CLEAN" = true ]; then
  echo "Cleaning temporary files and build artifacts"
  rm -rf "$BUILD_DIR"
  mkdir -p "$BUILD_DIR"
  rm -rf "$SCRIPT_DIR/src/service/dependencies"
  rm -rf "$SCRIPT_DIR"/src/service/epid_verifier_wrap/*.java "$SCRIPT_DIR"/src/service/epid_verifier_wrap/*.c
  exit 0
fi

if [ "$MEM_CHECKS" = true ]; then
  echo "Memory checks will be performed. This forces build in Debug mode."
  export BUILD_TYPE="Debug"
fi

if [ "$FORMAT" = true ]; then
  echo "Running clang-format to enforce code formatting rules"
  find src -type f -name '*.c' -o -name '*.cpp' -o -name '*.h' | xargs -I % clang-format-3.6 -style=file -i %
fi

CMAKE_OPTS=""
BUILD_OPTS=""

if [ "$VERBOSE" = true ]; then
  CMAKE_OPTS="$CMAKE_OPTS --debug-output"
  BUILD_OPTS="$BUILD_OPTS VERBOSE=1"
fi

cd "$BUILD_DIR"

activate_coverage
"$CMAKE" $CMAKE_OPTS ../src/service

make $BUILD_OPTS
ERROR_CODE="$?"

if [ "$ERROR_CODE" != "0" ]; then
  echo "Build failed with error code $ERROR_CODE! Aborting!"
  deactivate_coverage
  exit 1
fi

if [ "$SKIP_TESTS" = false ]; then
  REPORT_LINK="file://$BUILD_DIR/Testing/Temporary/LastTest.log"
  if [ "$OSTYPE" = "cygwin" ]; then
    "$CTEST" -C Release
  else
    "$CTEST"
  fi

  ERROR_CODE="$?"
  if [ "$ERROR_CODE" != "0" ]; then
    echo "Unit tests failed with error code $ERROR_CODE! Aborting!"
    echo "Check $REPORT_LINK for detailed report."
    deactivate_coverage
    exit 1
  fi

  echo "Unit tests passed. Detailed report: $REPORT_LINK"
  generate_coverage_report
  deactivate_coverage
else
  echo "Unit tests skipped."
fi

DESTINATION="$(dirname "$SCRIPT_DIR")/src/main/java/org/fidoalliance/fdo/epid/verification/generated"
mkdir -p $DESTINATION
cp ${SCRIPT_DIR}/src/service/epid_verifier_wrap/*.java $DESTINATION
echo "JNI java classes copy complete"

exit 0
