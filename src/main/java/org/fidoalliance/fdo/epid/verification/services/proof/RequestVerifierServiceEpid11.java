// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.services.proof;

import lombok.RequiredArgsConstructor;
import org.fidoalliance.fdo.epid.verification.exceptions.GenericVerificationException;
import org.fidoalliance.fdo.epid.verification.exceptions.InvalidSignatureException;
import org.fidoalliance.fdo.epid.verification.exceptions.SigrlVersionSignatureVersionMismatchException;
import org.fidoalliance.fdo.epid.verification.generated.VerifySignatureStatus;
import org.fidoalliance.fdo.epid.verification.generated.epid_verifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestVerifierServiceEpid11 extends RequestVerifierService {

  private final SignatureVerificationStatusVerifierEpid11 signatureVerificationStatusVerifier;

  @Override
  public void verifyEpidSignature(
      byte[] signature,
      byte[] msg,
      byte[] groupPubKey,
      byte[] sigRl,
      byte[] privRl,
      byte[] basename)
      throws InvalidSignatureException, SigrlVersionSignatureVersionMismatchException,
          GenericVerificationException {
    if (basename == null) {
      basename = new byte[0];
    }
    VerifySignatureStatus status =
        epid_verifier.verify_signature_1_1(signature, groupPubKey, msg, sigRl, privRl, basename);

    signatureVerificationStatusVerifier.verifyStatus(status, signature, sigRl);
  }
}
