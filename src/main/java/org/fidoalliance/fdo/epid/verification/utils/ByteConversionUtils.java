// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ByteConversionUtils {

  private static final int INTEGER_SIZE = 4;
  private static final int SHORT_SIZE = 2;
  private static final int LONG_SIZE = 8;
  private static final byte[] ZERO_BYTE = new byte[] {0x00};

  /**
   * Concatenates byte arrays.
   *
   * @param args byte arrays to be concatenated
   * @return concatenated byte array
   * @throws IOException if I/O error occurs
   */
  public static byte[] concatByteArrays(byte[]... args) throws IOException {
    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      for (byte[] ary : args) {
        outputStream.write(ary);
      }
      return outputStream.toByteArray();
    }
  }

  public static byte[] byteToByteArray(byte byteValue) {
    return new byte[] {byteValue};
  }

  public static byte[] shortToByteArray(short shortValue) {
    return ByteBuffer.allocate(SHORT_SIZE).order(ByteOrder.BIG_ENDIAN).putShort(shortValue).array();
  }

  public static byte[] intToByteArray(int intValue) {
    return ByteBuffer.allocate(INTEGER_SIZE).order(ByteOrder.BIG_ENDIAN).putInt(intValue).array();
  }

  public static byte[] intAsShortToByteArray(int intValue) {
    return shortToByteArray((short) (intValue & 0xFFFF));
  }

  /**
   * Converts int to byte array.
   * @param intValue int value
   * @return byte array
   */
  public static byte[] intToByteArrayOfThree(int intValue) {
    return Arrays.copyOfRange(
        ByteBuffer.allocate(INTEGER_SIZE).order(ByteOrder.BIG_ENDIAN).putInt(intValue).array(),
        1,
        4);
  }

  public static byte[] longToByteArray(long longValue) {
    return ByteBuffer.allocate(LONG_SIZE).order(ByteOrder.BIG_ENDIAN).putLong(longValue).array();
  }

  /**
   * Long value as byte to byte array.
   *
   * @param longValue long value to be converted to byte array
   * @return byte array
   */
  public static byte[] longAsByteToByteArray(long longValue) {
    return Arrays.copyOfRange(
        ByteBuffer.allocate(LONG_SIZE).order(ByteOrder.BIG_ENDIAN).putLong((int) longValue).array(),
        7,
        8);
  }

  /**
   * Long value as short to byte array.
   *
   * @param longValue long value to be converted to byte array
   * @return byte array
   */
  public static byte[] longAsShortToByteArray(long longValue) {
    return Arrays.copyOfRange(
        ByteBuffer.allocate(LONG_SIZE).order(ByteOrder.BIG_ENDIAN).putLong((int) longValue).array(),
        6,
        8);
  }

  public static byte[] longToByteArrayOfThree(long longValue) {
    return intToByteArrayOfThree((int) longValue);
  }

  /**
   * Long value as int to byte array.
   *
   * @param longValue long value to be converted to byte array
   * @return byte array
   */
  public static byte[] longAsIntToByteArray(long longValue) {
    return Arrays.copyOfRange(
        ByteBuffer.allocate(LONG_SIZE).order(ByteOrder.BIG_ENDIAN).putLong((int) longValue).array(),
        4,
        8);
  }

  public static short byteArrayToShort(byte[] byteArray) {
    return ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).getShort();
  }

  public static int byteArrayToShortAsInt(byte[] byteArray) {
    return ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).getShort() & 0x0000FFFF;
  }

  public static int byteArrayToInt(byte[] byteArray) {
    return ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).getInt();
  }

  public static long byteArrayToIntAsLong(byte[] byteArray) {
    return ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).getInt() & 0x00000000FFFFFFFFL;
  }

  public static long byteArrayToLong(byte[] byteArray) {
    return ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).getLong();
  }

  /**
   * Byte array to int.
   *
   * @param byteArray byte array to be converted to int.
   * @return int
   * @throws IOException for unhandled IO Exceptions.
   */
  public static int byteArrayOfThreeToInt(byte[] byteArray) throws IOException {
    return ByteBuffer.wrap(concatByteArrays(ZERO_BYTE, byteArray))
        .order(ByteOrder.BIG_ENDIAN)
        .getInt();
  }
}
