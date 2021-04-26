# Copyright 2021 Intel Corporation
# SPDX-License-Identifier: Apache 2.0

#!/usr/bin bash

# Add appropriate path to cryptomaterials by updating argument crypto-material.path.

# To run the service in HTTP mode remove arguments:
# server.ssl.key-store and server.ssl.key-store-password.

java -Dserver.port=1180 -Dcrypto-material.path=./cryptomaterial \
     -Djava.library.path=./Native/build/epid_verifier:./Native/build/epid_verifier_wrap \
     -Dserver.ssl.key-store=./certs/verification-service-keystore.p12 -Dserver.ssl.key-store-password=ver!f!c@t!0n \
     -Dspring.profiles.active=production -jar ./target/epid-verification-service-*.jar
