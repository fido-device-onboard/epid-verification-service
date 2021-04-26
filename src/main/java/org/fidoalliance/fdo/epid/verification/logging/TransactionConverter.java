// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.logging;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.slf4j.MDC;

public class TransactionConverter extends ClassicConverter {

  private static final String NO_XID = "NO XID";

  @Override
  public String convert(ILoggingEvent event) {
    String xid = MDC.get(TransactionManager.XID_ATTRIBUTE_NAME);

    if (xid == null) {
      return NO_XID;
    }

    return xid;
  }
}
