// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.utils;

import javax.xml.bind.DatatypeConverter;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ArrayByteBuilderTest {

  @Test
  public void testArrayByteBuilder() throws Exception {
    ArrayByteBuilder builder = new ArrayByteBuilder();
    builder.append(new byte[] {0x11, 0x11, 0x11});
    builder.append((byte) 0x22);
    builder.append((short) 13107);
    builder.append(1145324612);
    builder.append(5592405, 3);
    Assert.assertEquals(
        DatatypeConverter.printHexBinary(builder.build()), "11111122333344444444555555");
  }
}
