// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.services.proof;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.fidoalliance.fdo.epid.verification.exceptions.GenericVerificationException;
import org.fidoalliance.fdo.epid.verification.exceptions.InvalidSignatureException;
import org.fidoalliance.fdo.epid.verification.exceptions.SigrlVersionSignatureVersionMismatchException;
import org.fidoalliance.fdo.epid.verification.generated.VerifySignatureStatus;

@Slf4j
abstract class SignatureVerificationStatusVerifier {

  void verifyStatus(VerifySignatureStatus status, byte[] signature, byte[] sigRl)
      throws InvalidSignatureException, SigrlVersionSignatureVersionMismatchException,
          GenericVerificationException {
    String message;

    switch (status) {
      case SIG_OK:
        log.info("Signature verification succeed");
        break;
      case SIG_INVALID:
        message = String.format("Signature verification failed with error: %s.", status);
        throw new InvalidSignatureException(message);
      case SDK_ERR_kEpidErr:
        // TODO: expect there other error after adding outdatedSigRLError to EPID SDK
        if (!hasSameSigrlVersion(sigRl, signature)) {
          throw new SigrlVersionSignatureVersionMismatchException(
              getSignatureVersion(signature), getSigrlVersion(sigRl));
        } else {
          message = String.format("Signature verification failed with error: %s.", status);
          throw new GenericVerificationException(message);
        }
      default:
        message = String.format("Signature verification failed with error: %s.", status);
        throw new GenericVerificationException(message);
    }
  }

  abstract byte[] getSigrlVersion(byte[] sigrl);

  abstract byte[] getSignatureVersion(byte[] signature);

  private boolean hasSameSigrlVersion(byte[] sigRl, byte[] signature) {
    return Arrays.equals(getSigrlVersion(sigRl), getSignatureVersion(signature));
  }
}
