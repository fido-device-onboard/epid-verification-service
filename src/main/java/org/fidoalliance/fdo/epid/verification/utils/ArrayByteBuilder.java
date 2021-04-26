// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class ArrayByteBuilder {

  private final int integerSize = 4;

  private ByteArrayOutputStream output;

  public ArrayByteBuilder() {
    this.output = new ByteArrayOutputStream();
  }

  public ArrayByteBuilder append(byte[] input) throws IOException {
    output.write(input);
    return this;
  }

  public ArrayByteBuilder append(byte input) throws IOException {
    output.write(ByteConversionUtils.byteToByteArray(input));
    return this;
  }

  public ArrayByteBuilder append(short input) throws IOException {
    output.write(ByteConversionUtils.shortToByteArray(input));
    return this;
  }

  public ArrayByteBuilder append(int input) throws IOException {
    output.write(ByteConversionUtils.intToByteArray(input));
    return this;
  }

  /**
   * Converts int to ArrayByteBuilder.
   * @param input int to be converted ArrayByteBuilder
   * @param numberOfBytes number of bytes
   * @return ArrayByteBuilder
   * @throws IOException if I/O exception occurs
   */
  public ArrayByteBuilder append(int input, int numberOfBytes) throws IOException {
    output.write(
        Arrays.copyOfRange(
            ByteConversionUtils.intToByteArray(input), integerSize - numberOfBytes, integerSize));
    return this;
  }

  public byte[] build() {
    return output.toByteArray();
  }
}
