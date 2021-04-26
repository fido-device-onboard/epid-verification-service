// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.services.proof;

import org.powermock.modules.testng.PowerMockTestCase;

public class RequestVerifierServiceBaseTest extends PowerMockTestCase {

  byte[] dummySignature = new byte[1];
  byte[] dummyMessage = new byte[1];
  byte[] dummyGroupPubkey = new byte[1];
  byte[] dummySigrl = new byte[1];
  byte[] dummyPrivrl = new byte[1];
  byte[] dummyBasename = new byte[0];
  byte[] nullBasename = null;
}
