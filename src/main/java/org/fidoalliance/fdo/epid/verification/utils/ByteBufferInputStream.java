// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ByteBufferInputStream {

  @Getter private InputStream stream;

  public ByteBufferInputStream(InputStream stream) {
    this.stream = stream;
  }

  private byte[] readBytes(int numBytes) throws IOException {
    if (numBytes == 0) {
      return new byte[0];
    }

    byte[] ary = new byte[numBytes];
    if (stream.read(ary, 0, numBytes) != numBytes) {
      throw new IOException("No more bytes in underlying stream.");
    }
    return ary;
  }

  public int getUint() throws IOException {
    // FIXME: IMO this does not work ;), see get UShortAsInt
    return ByteBuffer.wrap(readBytes(4)).order(ByteOrder.BIG_ENDIAN).getInt();
  }

  public short getShort() throws IOException {
    // Remember - this is not unsigned value
    return ByteBuffer.wrap(readBytes(2)).order(ByteOrder.BIG_ENDIAN).getShort();
  }

  public int getUShortAsInt() throws IOException {
    short signed = ByteConversionUtils.byteArrayToShort(readBytes(2));
    return signed & 0x0000ffff;
  }

  public byte getByte() throws IOException {
    return readBytes(1)[0];
  }

  public byte[] getBytes(int numBytes) throws IOException {
    return readBytes(numBytes);
  }

  public byte[] getRemainingBytes() throws IOException {
    return readBytes(this.stream.available());
  }
}
