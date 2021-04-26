// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.utils;

import java.security.SecureRandom;

public class RandomBytesUtils {

  /**
   * Gives random bytes of specified length.
   * @param length length of random bytes required
   * @return random byte array of specified length
   */
  public static byte[] createRandomBytes(int length) {
    byte[] randomData = new byte[length];
    SecureRandom secureRandom = new SecureRandom();
    secureRandom.nextBytes(randomData);
    return randomData;
  }
}
