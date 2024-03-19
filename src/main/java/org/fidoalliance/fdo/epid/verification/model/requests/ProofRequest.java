// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.model.requests;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fidoalliance.fdo.epid.verification.enums.EpidVersion;
import org.fidoalliance.fdo.epid.verification.model.RawSerializable;
import org.fidoalliance.fdo.epid.verification.utils.ArrayByteBuilder;
import org.fidoalliance.fdo.epid.verification.utils.ByteBufferInputStream;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProofRequest implements RawSerializable {

  private byte[] groupId;

  private byte[] msg;

  private byte[] basename;

  private byte[] epidSignature;

  /**
   * Parameterized constructor.
   * @param frame message body
   * @param epidVersion EPID version
   * @throws IOException if I/O error occurs
   */
  public ProofRequest(byte[] frame, EpidVersion epidVersion) throws IOException {
    InputStream inputStream = new ByteArrayInputStream(frame);
    ByteBufferInputStream buf = new ByteBufferInputStream(inputStream);

    groupId = buf.getBytes(epidVersion.getGroupIdLength());
    msg = buf.getBytes(buf.getUShortAsInt());
    basename = buf.getBytes(buf.getUShortAsInt());
    epidSignature =
        buf.getBytes(
            frame.length
                - (epidVersion.getGroupIdLength()
                    + Short.BYTES
                    + msg.length
                    + Short.BYTES
                    + basename.length));
  }

  @Override
  public byte[] toByteArray() throws IOException {
    return new ArrayByteBuilder()
        .append(groupId)
        .append((short) msg.length)
        .append(msg)
        .append((short) basename.length)
        .append(basename)
        .append(epidSignature)
        .build();
  }
}
