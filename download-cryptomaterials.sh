#!/bin/bash

# Copyright 2021 Intel Corporation
# SPDX-License-Identifier: Apache 2.0

# Download cryptomaterials tar ball using one of the URLs below:
# http://verify.epid-sbx.trustedservices.intel.com/v1/epid/cryptomaterials/download
# https://verify.epid-sbx.trustedservices.intel.com/v1/epid/cryptomaterials/download

# To swtich server or HTTP mode for download, update URL variable.

BASE_URL="http://verifier.fdorv.com"
EP_CRYPTOMATERIALS_DOWNLOAD="/v1/epid/cryptomaterials/download"
EP_CRYPTOMATERIALS_SIGNATURE="/v1/epid/cryptomaterials/signature"

URL_CRYPTOMATERIALS_DOWNLOAD=${BASE_URL}${EP_CRYPTOMATERIALS_DOWNLOAD}
URL_CRYPTOMATERIALS_SIGNATURE=${BASE_URL}${EP_CRYPTOMATERIALS_SIGNATURE}

# In case output_filename is updated, update the same in demo folder files
# and in epidVerificationService.sh file.

OUTPUT_FILENAME="cryptoMaterial"
CRYPTOMATERIALS_FILENAME="${OUTPUT_FILENAME}.tgz"
CRYPTOMATERIALS_SIGNATURE_FILENAME="${OUTPUT_FILENAME}.tgz.sig"

# Download cryptomaterials tar ball and its signature file
echo "Downloading cryptomaterials tar ball..."
curl -k ${URL_CRYPTOMATERIALS_DOWNLOAD} -o ${CRYPTOMATERIALS_FILENAME}
echo "Downloading cryptomaterials tar ball signature..."
curl -k ${URL_CRYPTOMATERIALS_SIGNATURE} -o ${CRYPTOMATERIALS_SIGNATURE_FILENAME}
