// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.logging;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.slf4j.MDC;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@PrepareForTest(MDC.class)
public class IpConverterTest extends PowerMockTestCase {

  @BeforeMethod
  private void setUp() {
    PowerMockito.mockStatic(MDC.class);
  }

  @Test
  private void convertNoIpInAttributesTest() throws Exception {
    IpConverter converter = new IpConverter();
    Assert.assertEquals("NO IP", converter.convert(null));
  }

  @Test
  public void convertPositiveTest() throws Exception {
    IpConverter converter = new IpConverter();
    PowerMockito.when(MDC.get("ip")).thenReturn("test_string");
    Assert.assertEquals("test_string", converter.convert(null));
  }
}
