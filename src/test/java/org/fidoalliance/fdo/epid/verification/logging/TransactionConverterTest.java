// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.logging;

import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.slf4j.MDC;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@PrepareForTest(MDC.class)
public class TransactionConverterTest extends PowerMockTestCase {

  @BeforeMethod
  private void beforeMethod() {
    MockitoAnnotations.initMocks(this);
    PowerMockito.mockStatic(MDC.class);
  }

  @Test
  public void testConvertNoPropertiesInMdc() {
    // given
    PowerMockito.when(MDC.get("xid")).thenReturn(null);

    // when
    TransactionConverter converter = new TransactionConverter();

    // then
    Assert.assertEquals("NO XID", converter.convert(null));
  }

  @Test
  public void testConvertMdcPropertiesFilled() {
    // given
    PowerMockito.when(MDC.get("xid")).thenReturn("XXXXXXX");

    // when
    TransactionConverter converter = new TransactionConverter();
    String actualXid = converter.convert(null);

    // then
    Assert.assertEquals("XXXXXXX", actualXid);
  }
}
