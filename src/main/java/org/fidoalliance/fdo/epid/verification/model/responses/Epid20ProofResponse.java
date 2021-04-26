// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.model.responses;

import java.io.IOException;
import lombok.Getter;
import org.fidoalliance.fdo.epid.verification.utils.ArrayByteBuilder;

@Getter
public class Epid20ProofResponse extends ProofResponse {

  private byte[] pseudonym;

  public Epid20ProofResponse(byte[] groupId, byte[] pseudonym, byte[] msg) {
    super(groupId, msg);
    this.pseudonym = pseudonym;
  }

  @Override
  public byte[] toByteArray() throws IOException {

    return new ArrayByteBuilder()
        .append(groupId)
        .append(pseudonym)
        .append((short) msg.length)
        .append(msg)
        .build();
  }
}
