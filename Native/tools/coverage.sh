#!/bin/bash

# Copyright 2021 Intel Corporation
# SPDX-License-Identifier: Apache 2.0

BULLSEYE_PATH="/opt/BullseyeCoverage/bin"
COVERAGE_SCRIPT_DIR=$(cd `dirname "${BASH_SOURCE[0]}"` && pwd)

RUN_MODE=$1 #client or service

COVERAGE_FILE="epid_$RUN_MODE.cov"
COVERAGE_DIR="$COVERAGE_SCRIPT_DIR/../build/coverage"

function activate_coverage() {
  if [ "$COVERAGE" = false ]; then
    return 1
  fi

  # Activate BullseyeCoverage
  rm -rf "$COVERAGE_DIR"
  export PATH="$BULLSEYE_PATH:$PATH"
  export COVFILE="$COVERAGE_SCRIPT_DIR/../$COVERAGE_FILE"
  cov01 --on --no-banner

  # Exclude some components from measurement
  covselect --quiet --deleteAll
  covselect --quiet --add '!build/' # build artifacts
  covselect --quiet --add '!src/service/dependencies/' # project dependencies
  covselect --quiet --add '!src/service/epid_verifier_wrap/'
  covselect --quiet --add '!src/service/epid_verifier_test/'
  covselect --quiet --add '!src/service/epid_verifier_1_1_wrap/'
  covselect --quiet --add '!src/service/epid_verifier_1_1_test/'

  echo "Current exclusion settings:"
  covselect --list --verbose
}

function deactivate_coverage() {
  if [ "$COVERAGE" = false ]; then
    return 1
  fi

  cov01 --off --no-banner
}

function generate_coverage_report() {
  if [ "$COVERAGE" = false ]; then
    return 1
  fi

  covsrc
  covhtml --no-banner "$COVERAGE_DIR"
  covxml --file $COVFILE --output $COVERAGE_DIR/CoverageBullseye.xml
  mv $COVFILE "$COVERAGE_DIR"
}
