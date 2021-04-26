// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.model.epid;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fidoalliance.fdo.epid.verification.utils.ByteBufferInputStream;
import org.fidoalliance.fdo.epid.verification.utils.ByteConversionUtils;

@NoArgsConstructor
@Getter
@Setter
public class EpidSignature {

  private static final int G1_ELEMENT_SIZE = 64;
  private static final int FP_ELEMENT_SIZE = 32;

  private byte[] g1B; // /< an element in G1
  private byte[] g1K; // /< an element in G1
  private byte[] g1T; // /< an element in G1
  private byte[] g1C; // /< an integer between [0, p-1]
  private byte[] sx; // /< an integer between [0, p-1]
  private byte[] sf; // /< an integer between [0, p-1]
  private byte[] sa; // /< an integer between [0, p-1]
  private byte[] sb; // /< an integer between [0, p-1]
  private int sigrlVersion; // /< revocation list version number
  private int sigrlEntries; // /< number of entries in SigRL

  /**
   * Parameterized constructor.
   * @param epidSignature EPID signature
   * @throws IOException if an I/O error occurs
   */
  public EpidSignature(byte[] epidSignature) throws IOException {
    InputStream inputStream = new ByteArrayInputStream(epidSignature);
    ByteBufferInputStream buf = new ByteBufferInputStream(inputStream);

    g1B = buf.getBytes(G1_ELEMENT_SIZE);
    g1K = buf.getBytes(G1_ELEMENT_SIZE);
    g1T = buf.getBytes(G1_ELEMENT_SIZE);
    g1C = buf.getBytes(FP_ELEMENT_SIZE);
    sx = buf.getBytes(FP_ELEMENT_SIZE);
    sf = buf.getBytes(FP_ELEMENT_SIZE);
    sa = buf.getBytes(FP_ELEMENT_SIZE);
    sb = buf.getBytes(FP_ELEMENT_SIZE);
    sigrlVersion = buf.getUint();
    sigrlEntries = buf.getUint();
  }

  // Alias for getB()
  public byte[] getPseudonym() throws IOException {
    return ByteConversionUtils.concatByteArrays(g1B, g1K);
  }
}
