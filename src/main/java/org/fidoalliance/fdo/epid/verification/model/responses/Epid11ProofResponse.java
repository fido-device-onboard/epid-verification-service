// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.model.responses;

public class Epid11ProofResponse extends ProofResponse {
  public Epid11ProofResponse(byte[] groupId, byte[] msg) {
    super(groupId, msg);
  }
}
