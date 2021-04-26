// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.utils;

import static javax.xml.bind.DatatypeConverter.printHexBinary;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.security.SecureRandom;
import java.util.Arrays;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
@PrepareForTest({SecureRandom.class, RandomBytesUtils.class})
public class RandomBytesUtilsTest extends PowerMockTestCase {

  @Test
  public void testCreateRandomBytes() throws Exception {
    SecureRandom secureRandom = mock(SecureRandom.class);
    PowerMockito.whenNew(SecureRandom.class).withNoArguments().thenReturn(secureRandom);

    Mockito.doAnswer(
      invocation -> {
        byte[] ary = invocation.getArgument(0);
        Arrays.fill(ary, (byte) 0x77);
        return null;
      }).when(secureRandom).nextBytes(any());

    Assert.assertEquals(printHexBinary(RandomBytesUtils.createRandomBytes(3)), "777777");
  }
}
