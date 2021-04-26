// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.logging;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class IpManager {

  public static final String IP_ATTRIBUTE_NAME = "ip";
  private static final String NO_IP_ADDRESS = "";

  /**
   * Sets IP address from servlet request.
   * @param request HTTP servlet request
   */
  public static void retrieveIpFromRequest(HttpServletRequest request) {

    String xforwardedHeader = request.getHeader("X-Forwarded-For");
    String ipAddress = NO_IP_ADDRESS;
    if (xforwardedHeader != null) {
      ipAddress = retrieveClientIpAddressFromXForwardedHeader(xforwardedHeader);
    }
    if (ipAddress.equals(NO_IP_ADDRESS)) {
      ipAddress = request.getRemoteAddr();
    }
    MDC.put(IP_ATTRIBUTE_NAME, ipAddress);
  }

  public static String getRequestIP() {
    return MDC.get(IP_ATTRIBUTE_NAME);
  }

  public static void removeIP() {
    MDC.remove(IP_ATTRIBUTE_NAME);
  }

  private static String retrieveClientIpAddressFromXForwardedHeader(String xforwardedHeader) {
    String[] forwardedAddresses = xforwardedHeader.split(",");
    if (forwardedAddresses.length > 0) {
      return forwardedAddresses[forwardedAddresses.length - 1].trim();
    }
    return NO_IP_ADDRESS;
  }
}
