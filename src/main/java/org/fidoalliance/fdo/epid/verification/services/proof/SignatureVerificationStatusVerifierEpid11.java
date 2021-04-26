// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.services.proof;

import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component
class SignatureVerificationStatusVerifierEpid11 extends SignatureVerificationStatusVerifier {

  private static final int SIGRL_VERSION_OFFSET = 4;
  private static final int SIGNATURE_VERSION_OFFSET = 565;
  private static final int VERSION_SIZE = 4;

  @Override
  byte[] getSigrlVersion(byte[] sigrl) {
    return Arrays.copyOfRange(sigrl, SIGRL_VERSION_OFFSET, SIGRL_VERSION_OFFSET + VERSION_SIZE);
  }

  @Override
  byte[] getSignatureVersion(byte[] signature) {
    return Arrays.copyOfRange(
        signature, SIGNATURE_VERSION_OFFSET, SIGNATURE_VERSION_OFFSET + VERSION_SIZE);
  }
}
