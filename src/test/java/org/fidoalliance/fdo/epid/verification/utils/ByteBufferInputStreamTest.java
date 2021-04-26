// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.DatatypeConverter;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ByteBufferInputStreamTest {

  private final byte[] frame = DatatypeConverter.parseHexBinary("11111122333344444444556677");

  @Test
  public void testByteBufferInputStreamPositive() throws Exception {
    InputStream inputStream = new ByteArrayInputStream(frame);
    ByteBufferInputStream buf = new ByteBufferInputStream(inputStream);
    byte[] val1 = buf.getBytes(3);
    byte val2 = buf.getByte();
    short val3 = buf.getShort();
    int val4 = buf.getUint();
    byte[] val5 = buf.getRemainingBytes();
    Assert.assertEquals(DatatypeConverter.printHexBinary(val1), "111111");
    Assert.assertEquals(val2, (byte) 0x22);
    Assert.assertEquals(val3, (short) 13107);
    Assert.assertEquals(val4, 1145324612);
    Assert.assertEquals(DatatypeConverter.printHexBinary(val5), "556677");
  }

  @Test(expectedExceptions = IOException.class)
  public void testByteBufferInputStreamNegative() throws Exception {
    InputStream inputStream = new ByteArrayInputStream(frame);
    ByteBufferInputStream buf = new ByteBufferInputStream(inputStream);
    buf.getBytes(20);
  }

  @Test
  public void testGetUint() throws Exception {
    ByteArrayInputStream stream =
        new ByteArrayInputStream(DatatypeConverter.parseHexBinary("1122334455667788"));
    ByteBufferInputStream buf = new ByteBufferInputStream(stream);
    Assert.assertEquals(buf.getUint(), 0x11223344);
    Assert.assertEquals(buf.getUint(), 0x55667788);
  }

  @Test(expectedExceptions = IOException.class)
  public void testGetUintNoData() throws Exception {
    ByteArrayInputStream stream =
        new ByteArrayInputStream(DatatypeConverter.parseHexBinary("112233"));
    ByteBufferInputStream buf = new ByteBufferInputStream(stream);
    buf.getUint();
  }

  @Test
  public void testGetShort() throws Exception {
    ByteArrayInputStream stream =
        new ByteArrayInputStream(DatatypeConverter.parseHexBinary("11223344"));
    ByteBufferInputStream buf = new ByteBufferInputStream(stream);
    Assert.assertEquals(buf.getShort(), 0x1122);
    Assert.assertEquals(buf.getShort(), 0x3344);
  }

  @Test(expectedExceptions = IOException.class)
  public void testGetShortNoData() throws Exception {
    ByteArrayInputStream stream = new ByteArrayInputStream(DatatypeConverter.parseHexBinary("11"));
    ByteBufferInputStream buf = new ByteBufferInputStream(stream);
    buf.getShort();
  }

  @Test
  public void testGetBytes() throws Exception {
    ByteArrayInputStream stream =
        new ByteArrayInputStream(DatatypeConverter.parseHexBinary("112233445566777F"));
    ByteBufferInputStream buf = new ByteBufferInputStream(stream);
    Assert.assertEquals(buf.getBytes(5), new byte[] {0x11, 0x22, 0x33, 0x44, 0x55});
    Assert.assertEquals(buf.getBytes(3), new byte[] {0x66, 0x77, 0x7F});
  }

  @Test
  public void testGetBytesZeroBytes() throws Exception {
    ByteArrayInputStream stream = new ByteArrayInputStream(DatatypeConverter.parseHexBinary("11"));
    ByteBufferInputStream buf = new ByteBufferInputStream(stream);
    Assert.assertEquals(buf.getBytes(0), new byte[0]);
  }

  @Test(expectedExceptions = IOException.class)
  public void testGetBytesNoData() throws Exception {
    ByteArrayInputStream stream =
        new ByteArrayInputStream(DatatypeConverter.parseHexBinary("11223344"));
    ByteBufferInputStream buf = new ByteBufferInputStream(stream);
    buf.getBytes(666);
  }

  @Test
  public void testGetUShortAsInt() throws Exception {
    ByteArrayInputStream stream =
        new ByteArrayInputStream(DatatypeConverter.parseHexBinary("0011"));
    ByteBufferInputStream buf = new ByteBufferInputStream(stream);
    Assert.assertEquals(buf.getUShortAsInt(), 0x00000011);

    stream = new ByteArrayInputStream(DatatypeConverter.parseHexBinary("11ff"));
    buf = new ByteBufferInputStream(stream);
    Assert.assertEquals(buf.getUShortAsInt(), 0x000011ff);

    // negative short
    stream = new ByteArrayInputStream(DatatypeConverter.parseHexBinary("ffff"));
    buf = new ByteBufferInputStream(stream);
    Assert.assertEquals(buf.getUShortAsInt(), 0x0000ffff);

    // more than 2 values
    stream = new ByteArrayInputStream(DatatypeConverter.parseHexBinary("ff00eeee"));
    buf = new ByteBufferInputStream(stream);
    Assert.assertEquals(buf.getUShortAsInt(), 0x0000ff00);
  }
}
