// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.logging;

import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.testng.PowerMockTestCase;
import org.slf4j.MDC;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class IpManagerTest extends PowerMockTestCase {

  private static final String HEADER_NAME = "X-Forwarded-For";
  private static final String IP_ATTRIBUTE_NAME = "ip";
  private static final String ADDRESS_1 = "test1";
  private static final String ADDRESS_2 = "test2";

  @Mock private HttpServletRequest request;

  @BeforeMethod
  private void setUp() {
    request = Mockito.mock(HttpServletRequest.class);
  }

  @AfterMethod
  private void tearDown() {
    MDC.clear();
  }

  @Test
  public void retrieveUrlWhenNoXForwardedForHeaderFoundTest() {
    // given
    when(request.getHeader(HEADER_NAME)).thenReturn(null);
    when(request.getRemoteAddr()).thenReturn(ADDRESS_1);

    // when
    IpManager.retrieveIpFromRequest(request);

    // then
    String retrievedIpAddress = MDC.get(IP_ATTRIBUTE_NAME);
    Assert.assertEquals(ADDRESS_1, retrievedIpAddress);
  }

  @Test
  public void retrieveUrlWhenTwoAddressesInHeaderTest() {
    // given
    when(request.getHeader(HEADER_NAME)).thenReturn(ADDRESS_1 + ", " + ADDRESS_2);

    // when
    IpManager.retrieveIpFromRequest(request);

    // then
    String retrievedIpAddress = MDC.get(IP_ATTRIBUTE_NAME);
    Assert.assertEquals(ADDRESS_2, retrievedIpAddress);
  }

  @Test
  public void getRequestIpTest() {
    // given
    when(request.getHeader(HEADER_NAME)).thenReturn(ADDRESS_1 + ", " + ADDRESS_2);

    // when
    IpManager.retrieveIpFromRequest(request);
    String retrievedIpAddress = IpManager.getRequestIP();

    // then
    Assert.assertEquals(ADDRESS_2, retrievedIpAddress);
  }

  @Test
  public void removeIpTest() {
    // given
    when(request.getHeader(HEADER_NAME)).thenReturn(ADDRESS_1 + ", " + ADDRESS_2);

    // when
    IpManager.retrieveIpFromRequest(request);
    IpManager.removeIP();

    // then
    String retrievedIpAddress = IpManager.getRequestIP();
    Assert.assertEquals(null, retrievedIpAddress);
  }
}
