// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.logging;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.slf4j.MDC;

public class IpConverter extends ClassicConverter {

  private static final String NO_IP = "NO IP";

  @Override
  public String convert(ILoggingEvent event) {
    String ipAddress = MDC.get(IpManager.IP_ATTRIBUTE_NAME);

    if (ipAddress == null) {
      return NO_IP;
    }

    return ipAddress;
  }
}
