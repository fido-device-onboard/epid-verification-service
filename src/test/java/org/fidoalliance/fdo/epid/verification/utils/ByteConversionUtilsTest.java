// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.utils;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import javax.xml.bind.DatatypeConverter;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ByteConversionUtilsTest {

  private static final long MAX_UINT_32_AS_LONG = (long) (Integer.MAX_VALUE) * 2 + 1;

  @Test
  public void testConcatByteArrays() throws Exception {
    byte[] ary1 = new byte[] {(byte) 0x80};

    byte[] result = ByteConversionUtils.concatByteArrays();
    Assert.assertEquals(result.length, 0);

    result = ByteConversionUtils.concatByteArrays(ary1);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "80");

    byte[] ary2 = new byte[] {0x11, 0x22};
    result = ByteConversionUtils.concatByteArrays(ary1, ary2);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "801122");

    byte[] ary0 = new byte[0];
    result = ByteConversionUtils.concatByteArrays(ary1, ary0, ary2);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "801122");

    result = ByteConversionUtils.concatByteArrays(ary2, ary2, ary2);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "112211221122");
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testConcatByteArraysNull() throws Exception {
    ByteConversionUtils.concatByteArrays(new byte[] {0x00, 0x00}, null, new byte[] {0x01});
  }

  @Test
  public void testByteToByteArray() {
    byte[] result = ByteConversionUtils.byteToByteArray((byte) 0x80);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "80");

    result = ByteConversionUtils.byteToByteArray((byte) 0x7F);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "7F");

    result = ByteConversionUtils.byteToByteArray((byte) 0x33);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "33");

    result = ByteConversionUtils.byteToByteArray((byte) 0xFFFFFF44);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "44");
  }

  @Test
  public void testShortToByteArray() {
    byte[] result = ByteConversionUtils.shortToByteArray((short) -32768);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "8000");

    result = ByteConversionUtils.shortToByteArray((short) 32767);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "7FFF");

    result = ByteConversionUtils.shortToByteArray((short) 0x8000);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "8000");

    result = ByteConversionUtils.shortToByteArray((short) 0xFFFF);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "FFFF");

    result = ByteConversionUtils.shortToByteArray((short) 0x01FFF0);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "FFF0");
  }

  @Test
  public void testIntToByteArray() {
    byte[] result = ByteConversionUtils.intToByteArray(-559038737);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "DEADBEEF");

    result = ByteConversionUtils.intToByteArray(256);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "00000100");

    result = ByteConversionUtils.intToByteArray(322122551);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "13333337");
  }

  @Test
  public void testIntAsShortToByteArray() {
    byte[] result = ByteConversionUtils.intAsShortToByteArray(0x3344);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "3344");

    result = ByteConversionUtils.intAsShortToByteArray(0xFF3355);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "3355");

    result = ByteConversionUtils.intAsShortToByteArray(0xFFFF3366);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "3366");

    result = ByteConversionUtils.intAsShortToByteArray(0xFFFF8044);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "8044");

    result = ByteConversionUtils.intAsShortToByteArray(0x7FFF0000);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "0000");

    result = ByteConversionUtils.intAsShortToByteArray((short) 0x0011);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "0011");

    result = ByteConversionUtils.intAsShortToByteArray((byte) 0x22);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "0022");
  }

  @Test
  void testIntToByteArrayOfThree() {
    byte[] result = ByteConversionUtils.intToByteArrayOfThree(0x00112233);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "112233");

    result = ByteConversionUtils.intToByteArrayOfThree(0xFF112233);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "112233");

    result = ByteConversionUtils.intToByteArrayOfThree(0xFFFF2233);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "FF2233");

    result = ByteConversionUtils.intToByteArrayOfThree(0xFF112233);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "112233");

    result = ByteConversionUtils.intToByteArrayOfThree(0x2233);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "002233");

    result = ByteConversionUtils.intToByteArrayOfThree((byte) 0x11);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "000011");
  }

  @Test
  public void testLongToByteArray() {
    byte[] result = ByteConversionUtils.longToByteArray(9223372036854775807L);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "7FFFFFFFFFFFFFFF");

    result = ByteConversionUtils.longToByteArray(17592186048256L);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "0000100000000F00");

    result = ByteConversionUtils.longToByteArray(-9155554159905214208L);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "80F0EFFFF0FFF100");

    result = ByteConversionUtils.longToByteArray((byte) -1);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "FFFFFFFFFFFFFFFF");
  }

  @Test
  public void testLongAsByteToByteArray() {
    byte[] result = ByteConversionUtils.longAsByteToByteArray(9223372036854775807L);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "FF");

    result = ByteConversionUtils.longAsByteToByteArray(17592186048256L);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "00");

    result = ByteConversionUtils.longAsByteToByteArray(-9155554159905214208L);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "00");

    result = ByteConversionUtils.longAsByteToByteArray((byte) -1);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "FF");

    result = ByteConversionUtils.longAsByteToByteArray((short) 0x14);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "14");

    result = ByteConversionUtils.longAsByteToByteArray(0x5533L);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "33");
  }

  @Test
  public void testLongAsShortToByteArray() {
    byte[] result = ByteConversionUtils.longAsShortToByteArray(9223372036854775807L);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "FFFF");

    result = ByteConversionUtils.longAsShortToByteArray(17592186048256L);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "0F00");

    result = ByteConversionUtils.longAsShortToByteArray(-9155554159905214208L);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "F100");

    result = ByteConversionUtils.longAsShortToByteArray((byte) -1);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "FFFF");

    result = ByteConversionUtils.longAsShortToByteArray((short) 0x14);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "0014");

    result = ByteConversionUtils.longAsShortToByteArray(0x5533L);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "5533");
  }

  @Test
  public void testLongToByteArrayOfThree() {
    byte[] result = ByteConversionUtils.longToByteArrayOfThree(9223372036854775807L);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "FFFFFF");

    result = ByteConversionUtils.longToByteArrayOfThree(17592186048256L);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "000F00");

    result = ByteConversionUtils.longToByteArrayOfThree(-9155554159905214208L);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "FFF100");

    result = ByteConversionUtils.longToByteArrayOfThree((byte) -1);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "FFFFFF");

    result = ByteConversionUtils.longToByteArrayOfThree(0x5533L);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "005533");

    result = ByteConversionUtils.longToByteArrayOfThree((short) 0x14);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "000014");

    result = ByteConversionUtils.longToByteArrayOfThree(0x33L);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "000033");
  }

  @Test
  public void testLongAsIntToByteArray() {
    byte[] result = ByteConversionUtils.longAsIntToByteArray(9223372036854775807L);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "FFFFFFFF");

    result = ByteConversionUtils.longAsIntToByteArray(17592186048256L);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "00000F00");

    result = ByteConversionUtils.longAsIntToByteArray(-9155554159905214208L);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "F0FFF100");

    result = ByteConversionUtils.longAsIntToByteArray((byte) -1);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "FFFFFFFF");

    result = ByteConversionUtils.longAsIntToByteArray(0x5533L);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "00005533");

    result = ByteConversionUtils.longAsIntToByteArray((short) 0x14);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "00000014");

    result = ByteConversionUtils.longAsIntToByteArray(0xAAAAAAAA);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "AAAAAAAA");

    result = ByteConversionUtils.longAsIntToByteArray(0x33L);
    Assert.assertEquals(DatatypeConverter.printHexBinary(result), "00000033");
  }

  @Test
  public void testByteArrayToShort() {
    byte[] arr = new byte[] {0x7F, (byte) 0xFF};
    Assert.assertEquals(ByteConversionUtils.byteArrayToShort(arr), (short) 0x7FFF);

    arr = new byte[] {(byte) 0x80, 0x00};
    Assert.assertEquals(ByteConversionUtils.byteArrayToShort(arr), (short) 0x8000);

    arr = new byte[] {0x13, 0x37};
    Assert.assertEquals(ByteConversionUtils.byteArrayToShort(arr), (short) 0x1337);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testByteArrayToShort_null() {
    ByteConversionUtils.byteArrayToShort(null);
  }

  @Test(expectedExceptions = BufferUnderflowException.class)
  public void testByteArrayToShort_shortArr() {
    ByteConversionUtils.byteArrayToShort(new byte[] {0x01});
  }

  @Test
  public void testByteArrayToShort_longArr() {
    byte[] arr = new byte[] {0x01, 0x02, 0x03};
    Assert.assertEquals(ByteConversionUtils.byteArrayToShort(arr), (short) 0x0102);
  }

  @Test
  public void testByteArrayToShortAsInt() throws Exception {
    byte[] arr = DatatypeConverter.parseHexBinary("0011");
    Assert.assertEquals(ByteConversionUtils.byteArrayToShortAsInt(arr), 0x00000011);

    arr = DatatypeConverter.parseHexBinary("2211ff");
    Assert.assertEquals(ByteConversionUtils.byteArrayToShortAsInt(arr), 0x00002211);

    arr = DatatypeConverter.parseHexBinary("ffff");
    Assert.assertEquals(ByteConversionUtils.byteArrayToShortAsInt(arr), 0x0000ffff);

    arr = DatatypeConverter.parseHexBinary("ff00eeee");
    Assert.assertEquals(ByteConversionUtils.byteArrayToShortAsInt(arr), 0x0000ff00);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testByteArrayToShortAsInt_null() {
    ByteConversionUtils.byteArrayToShortAsInt(null);
  }

  @Test(expectedExceptions = BufferUnderflowException.class)
  public void testByteArrayToShortAsInt_shortArr() {
    ByteConversionUtils.byteArrayToShortAsInt(new byte[] {0x01});
  }

  @Test
  public void testByteArrayToShortAsInt_longArr() {
    byte[] arr = new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};
    Assert.assertEquals(ByteConversionUtils.byteArrayToShort(arr), 0x0102);
  }

  @Test
  public void testByteArrayToInt() {
    byte[] arr = new byte[] {0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
    Assert.assertEquals(ByteConversionUtils.byteArrayToInt(arr), 2147483647);

    arr = new byte[] {(byte) 0x80, 0x00, 0x00, 0x00};
    Assert.assertEquals(ByteConversionUtils.byteArrayToInt(arr), -2147483648);

    arr = new byte[] {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
    Assert.assertEquals(ByteConversionUtils.byteArrayToInt(arr), -1);

    arr = new byte[] {0x00, 0x00, 0x13, 0x37};
    Assert.assertEquals(ByteConversionUtils.byteArrayToInt(arr), 4919);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testByteArrayToInt_null() {
    ByteConversionUtils.byteArrayToInt(null);
  }

  @Test(expectedExceptions = BufferUnderflowException.class)
  public void testByteArrayToInt_shortArr() {
    ByteConversionUtils.byteArrayToInt(new byte[] {0x01, 0x02, 0x03});
  }

  @Test
  public void testByteArrayToInt_longArr() {
    byte[] arr = new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};
    Assert.assertEquals(ByteConversionUtils.byteArrayToInt(arr), 0x01020304);
  }

  @Test
  public void testByteArrayToIntAsLong() {
    byte[] arr = new byte[] {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};

    Assert.assertEquals(ByteConversionUtils.byteArrayToIntAsLong(arr), 0x00000000FFFFFFFFL);
    Assert.assertEquals(ByteConversionUtils.byteArrayToIntAsLong(arr), MAX_UINT_32_AS_LONG);
    Assert.assertEquals(
        ByteConversionUtils.byteArrayToIntAsLong(arr), (long) (Integer.MAX_VALUE) * 2 + 1);

    arr = new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x10, (byte) 0x00};
    Assert.assertEquals(ByteConversionUtils.byteArrayToIntAsLong(arr), 0x0000000000001000L);

    arr = new byte[] {(byte) 0x80, (byte) 0xF0, (byte) 0xEF, (byte) 0xFF};
    Assert.assertEquals(ByteConversionUtils.byteArrayToIntAsLong(arr), 0x0000000080F0EFFFL);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testByteArrayToIntAsLong_null() {
    ByteConversionUtils.byteArrayToIntAsLong(null);
  }

  @Test(expectedExceptions = BufferUnderflowException.class)
  public void testByteArrayToIntAsLong_shortArr() {
    ByteConversionUtils.byteArrayToIntAsLong(new byte[] {0x01, 0x02, 0x03});
  }

  @Test
  public void testByteArrayToLong_longArr() {
    byte[] arr = new byte[] {0x01, 0x02, 0x03, 0x04, 0x05};
    Assert.assertEquals(ByteConversionUtils.byteArrayToIntAsLong(arr), 0x0000000001020304L);
  }

  @Test
  public void testByteArrayToLong() {
    byte[] arr =
        new byte[] {
          (byte) 0x7F,
          (byte) 0xFF,
          (byte) 0xFF,
          (byte) 0xFF,
          (byte) 0xFF,
          (byte) 0xFF,
          (byte) 0xFF,
          (byte) 0xFF
        };

    Assert.assertEquals(ByteConversionUtils.byteArrayToLong(arr), 9223372036854775807L);

    arr =
        new byte[] {
          (byte) 0x00,
          (byte) 0x00,
          (byte) 0x10,
          (byte) 0x00,
          (byte) 0x00,
          (byte) 0x00,
          (byte) 0x0F,
          (byte) 0x00
        };
    Assert.assertEquals(ByteConversionUtils.byteArrayToLong(arr), 17592186048256L);

    arr =
        new byte[] {
          (byte) 0x80,
          (byte) 0xF0,
          (byte) 0xEF,
          (byte) 0xFF,
          (byte) 0xF0,
          (byte) 0xFF,
          (byte) 0xF1,
          (byte) 0x00
        };
    Assert.assertEquals(ByteConversionUtils.byteArrayToLong(arr), -9155554159905214208L);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testByteArrayToLong_null() {
    ByteConversionUtils.byteArrayToLong(null);
  }

  @Test(expectedExceptions = BufferUnderflowException.class)
  public void testByteArrayToLong_shortArr() {
    ByteConversionUtils.byteArrayToLong(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07});
  }

  @Test
  public void testByteArrayToIntAsLong_longArr() {
    byte[] arr = new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09};
    Assert.assertEquals(ByteConversionUtils.byteArrayToLong(arr), 0x0102030405060708L);
  }

  @Test
  public void byteArrayOfThreeToIntTest() throws IOException {
    byte[] arr = new byte[] {0x7F, (byte) 0xFF, (byte) 0xFF};
    Assert.assertEquals(ByteConversionUtils.byteArrayOfThreeToInt(arr), 0x7FFFFF);

    arr = new byte[] {(byte) 0x80, 0x00, 0x00};
    Assert.assertEquals(ByteConversionUtils.byteArrayOfThreeToInt(arr), 0x800000);

    arr = new byte[] {0x00, 0x13, 0x37};
    Assert.assertEquals(ByteConversionUtils.byteArrayOfThreeToInt(arr), 0x001337);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testByteArrayOfThreeToInt_null() throws Exception {
    ByteConversionUtils.byteArrayOfThreeToInt(null);
  }

  @Test(expectedExceptions = BufferUnderflowException.class)
  public void testByteArrayOfThreeToInt_shortArr() throws Exception {
    ByteConversionUtils.byteArrayOfThreeToInt(new byte[] {0x01, 0x02});
  }

  @Test
  public void testByteArrayOfThreeToInt_longArr() throws Exception {
    byte[] arr = new byte[] {0x01, 0x02, 0x03};
    Assert.assertEquals(ByteConversionUtils.byteArrayOfThreeToInt(arr), 0x010203L);
  }
}
