#!/bin/bash

# Copyright 2020 Intel Corporation
# SPDX-License-Identifier: Apache 2.0

export http_proxy_host=$(echo $http_proxy | awk -F':' {'print $2'} | tr -d '/')
export http_proxy_port=$(echo $http_proxy | awk -F':' {'print $3'} | tr -d '/')

export https_proxy_host=$(echo $https_proxy | awk -F':' {'print $2'} | tr -d '/')
export https_proxy_port=$(echo $https_proxy | awk -F':' {'print $3'} | tr -d '/')

export _JAVA_OPTIONS="-Dhttp.proxyHost=$http_proxy_host -Dhttp.proxyPort=$http_proxy_port -Dhttps.proxyHost=$https_proxy_host -Dhttps.proxyPort=$https_proxy_port"
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64

REMOTE_URL=https://github.com/secure-device-onboard/epid-verification-service.git
REMOTE_BRANCH=master

if [ "$use_remote" = "1" ]; then
    echo "Building $REMOTE_URL : $REMOTE_BRANCH"
    cd /tmp/epid-verification-service/
    git clone $REMOTE_URL .
    git checkout $REMOTE_BRANCH

    # Building Dependencies:

    cd /tmp/epid-verification-service/Native/src/service
    mkdir -p /tmp/epid-verification-service/Native/src/service/dependencies
    cd /tmp/epid-verification-service/Native/src/service/dependencies

    git clone https://github.com/Intel-EPID-SDK/epid-sdk.git
    cd /tmp/epid-verification-service/Native/src/service/dependencies/epid-sdk
    git checkout v7.0.1
    chmod +x configure
    ./configure
    make all
    make check
    make install

    cd /tmp/epid-verification-service/Native/src/service/dependencies
    git clone https://github.com/google/googletest.git
    cd /tmp/epid-verification-service/Native/src/service/dependencies/googletest
    git checkout release-1.7.0
    cd /tmp/epid-verification-service/Native/src/service/dependencies/googletest/make
    make

    # Build service
    cd /tmp/epid-verification-service
    mvn clean install

    # Copy build artifacts to demo folder
    mkdir -p /home/epiduser/epid-verification-service/demo

    cd /tmp/epid-verification-service/target && find . -name \*.jar -exec cp --parents {} /home/epiduser/epid-verification-service/demo \;
    cd /tmp/epid-verification-service/Native/build/epid_verifier && find . -name \*.so -exec cp --parents {} /home/epiduser/epid-verification-service/demo \;
    cd /tmp/epid-verification-service/Native/build/epid_verifier_wrap && find . -name \*.so -exec cp --parents {} /home/epiduser/epid-verification-service/demo \;
    cd /tmp/epid-verification-service && find . -name \*.tgz -exec cp --parents {} /home/epiduser/epid-verification-service/demo \;
    cd /tmp/epid-verification-service && find . -name \*.tgz.sig -exec cp --parents {} /home/epiduser/epid-verification-service/demo \;
    cd /tmp/epid-verification-service/demo && cp -r certs/ /home/sdouser/epid-verification-service/demo

else
  echo "Building local copy"
  mvn clean install
fi
