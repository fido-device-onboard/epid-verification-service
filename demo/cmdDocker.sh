#!/bin/sh
# Copyright 2021 Intel Corporation
# SPDX-License-Identifier: Apache 2.0

# Unzip cryptomaterial tar ball
CRYPTO_DIR="/home/verifier/cryptoMaterial/static"
mkdir -p -v ${CRYPTO_DIR}
if [ $SPRING_PROFILES_ACTIVE = "development" ]
then
    # copying test cryptomaterial:
    tar -zxf cryptoMaterial.tgz -C $CRYPTO_DIR cryptoMaterial/static/dev --strip-components=2
else
    # copying production cryptomaterial:
    tar -zxf cryptoMaterial.tgz -C $CRYPTO_DIR cryptoMaterial/static/prod --strip-components=3
fi

SSL_KEY_STORE="verification-service-keystore.p12"
JAVA_SSL_PARAMS="-Dserver.ssl.key-store=/home/verifier/certs/$SSL_KEY_STORE -Dserver.ssl.key-store-password=$SSL_KEY_STORE_PASSWORD"

LD_LIBRARY_PATH=/home/verifier java  ${JAVA_SSL_PARAMS} -jar epid-verification-service-*.jar
