// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.services.proof;

import org.fidoalliance.fdo.epid.verification.exceptions.GenericVerificationException;
import org.fidoalliance.fdo.epid.verification.exceptions.InvalidSignatureException;
import org.fidoalliance.fdo.epid.verification.generated.VerifySignatureStatus;
import org.fidoalliance.fdo.epid.verification.generated.epid_verifier;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@PrepareForTest(epid_verifier.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*",
        "javax.management.*", "org.fidoalliance.fdo.epid.enums.*"})
public class RequestVerifierServiceEpid11Test extends RequestVerifierServiceBaseTest {

  @InjectMocks private RequestVerifierServiceEpid11 requestVerifierService;

  @BeforeMethod
  private void beforeMethod() {
    requestVerifierService =
        new RequestVerifierServiceEpid11(new SignatureVerificationStatusVerifierEpid11());
    PowerMockito.mockStatic(epid_verifier.class);
  }

  @Test
  public void testVerifyEpidSignatureNoErrorNullBasename() throws Exception {
    PowerMockito.when(
            epid_verifier.verify_signature_1_1(
                dummySignature,
                dummyMessage,
                dummyGroupPubkey,
                dummySigrl,
                dummyPrivrl,
                new byte[0]))
        .thenReturn(VerifySignatureStatus.SIG_OK);
    requestVerifierService.verifyEpidSignature(
        dummySignature, dummyMessage, dummyGroupPubkey, dummySigrl, dummyPrivrl, nullBasename);
  }

  @Test
  public void testVerifyEpidSignatureNoError() throws Exception {
    PowerMockito.when(
            epid_verifier.verify_signature_1_1(
                dummySignature,
                dummyMessage,
                dummyGroupPubkey,
                dummySigrl,
                dummyPrivrl,
                dummyBasename))
        .thenReturn(VerifySignatureStatus.SIG_OK);
    requestVerifierService.verifyEpidSignature(
        dummySignature, dummyMessage, dummyGroupPubkey, dummySigrl, dummyPrivrl, dummyBasename);
  }

  @Test(expectedExceptions = InvalidSignatureException.class)
  public void testVerifyEpidSignatureSigInvalid() throws Exception {
    PowerMockito.when(
            epid_verifier.verify_signature_1_1(
                dummySignature,
                dummyMessage,
                dummyGroupPubkey,
                dummySigrl,
                dummyPrivrl,
                dummyBasename))
        .thenReturn(VerifySignatureStatus.SIG_INVALID);
    requestVerifierService.verifyEpidSignature(
        dummySignature, dummyMessage, dummyGroupPubkey, dummySigrl, dummyPrivrl, dummyBasename);
  }

  @Test(expectedExceptions = GenericVerificationException.class)
  public void testVerifyEpidSignatureSigRevokedInSigrl() throws Exception {
    PowerMockito.when(
            epid_verifier.verify_signature_1_1(
                dummySignature,
                dummyMessage,
                dummyGroupPubkey,
                dummySigrl,
                dummyPrivrl,
                dummyBasename))
        .thenReturn(VerifySignatureStatus.PRIVKEY_REVOKED_IN_PRIVRL);
    requestVerifierService.verifyEpidSignature(
        dummySignature, dummyMessage, dummyGroupPubkey, dummySigrl, dummyPrivrl, dummyBasename);
  }
}
