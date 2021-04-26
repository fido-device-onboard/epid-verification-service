// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.logging;

import static org.mockito.ArgumentMatchers.anyString;

import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.slf4j.MDC;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
@PrepareForTest(MDC.class)
public class TransactionManagerTest extends PowerMockTestCase {

  @BeforeMethod
  private void beforeMethod() {
    MockitoAnnotations.initMocks(this);
    PowerMockito.mockStatic(MDC.class);
  }

  @Test
  public void testGetTransactionIdNull() {
    // given
    PowerMockito.when(MDC.get(anyString())).thenReturn(null);

    // when
    byte[] actualTransactionId = TransactionManager.getTransactionId();

    // then
    Assert.assertEquals(null, actualTransactionId);
  }

  @Test
  public void testGetTransactionIdNotNull() {
    // given
    PowerMockito.when(MDC.get(anyString())).thenReturn("0102");

    // when
    byte[] actualTransactionId = TransactionManager.getTransactionId();
    // then
    Assert.assertEquals(new byte[] {0x01, 0x02}, actualTransactionId);
  }
}
