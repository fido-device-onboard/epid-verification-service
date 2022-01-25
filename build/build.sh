#!/bin/bash

# Copyright 2020 Intel Corporation
# SPDX-License-Identifier: Apache 2.0

export http_proxy_host=$(echo $http_proxy | awk -F':' {'print $2'} | tr -d '/')
export http_proxy_port=$(echo $http_proxy | awk -F':' {'print $3'} | tr -d '/')

export https_proxy_host=$(echo $https_proxy | awk -F':' {'print $2'} | tr -d '/')
export https_proxy_port=$(echo $https_proxy | awk -F':' {'print $3'} | tr -d '/')

export _JAVA_OPTIONS="-Dhttp.proxyHost=$http_proxy_host -Dhttp.proxyPort=$http_proxy_port -Dhttps.proxyHost=$https_proxy_host -Dhttps.proxyPort=$https_proxy_port"
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64

build_source()
{
  # Arg1: Base directory
  basedir=$1

  cd $basedir/Native/src/service
  mkdir -p $basedir/Native/src/service/dependencies
  cd $basedir/Native/src/service/dependencies

  rm -rf $basedir/Native/src/service/dependencies/epid-sdk
  git clone https://github.com/Intel-EPID-SDK/epid-sdk.git
  cd $basedir/Native/src/service/dependencies/epid-sdk
  git checkout v7.0.1
  chmod +x configure
  ./configure
  make all
  make check
  make install

  rm -rf $basedir/Native/src/service/dependencies/googletest
  cd $basedir/Native/src/service/dependencies
  git clone https://github.com/google/googletest.git
  cd $basedir/Native/src/service/dependencies/googletest
  git checkout release-1.7.0
  cd $basedir/Native/src/service/dependencies/googletest/make
  make

  # Build service
  cd $basedir
  mvn clean install
}

REMOTE_URL=https://github.com/secure-device-onboard/epid-verification-service.git
REMOTE_BRANCH=1.0-rel

if [ "$use_remote" = "1" ]; then
  echo "Building $REMOTE_URL : $REMOTE_BRANCH"
  git clone -b $REMOTE_BRANCH $REMOTE_URL /tmp/epid-verification-service

  # Build source code
  build_source /tmp/epid-verification-service

  # Replace the demo folder in local copy
  rm -rf /home/fdouser/epid-verification-service/demo/*
  cp -arfv /tmp/epid-verification-service/demo/* /home/fdouser/epid-verification-service/demo/
else
  echo "Building local copy"
  build_source `pwd`
fi
