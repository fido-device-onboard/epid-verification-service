// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.model.responses;

import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.fidoalliance.fdo.epid.verification.model.RawSerializable;
import org.fidoalliance.fdo.epid.verification.utils.ArrayByteBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public abstract class ProofResponse implements RawSerializable {

  private static final int ECDSA_PROOF_SIZE = 64;

  protected byte[] groupId;
  protected byte[] msg;

  @Override
  public byte[] toByteArray() throws IOException {

    return new ArrayByteBuilder().append(groupId).append((short) msg.length).append(msg).build();
  }
}
