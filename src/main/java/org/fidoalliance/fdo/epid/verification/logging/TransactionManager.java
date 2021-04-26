// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.logging;

import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.fidoalliance.fdo.epid.verification.utils.RandomBytesUtils;
import org.slf4j.MDC;

@Slf4j
public class TransactionManager {

  public static final int XID_SIZE = 16;
  public static final String XID_ATTRIBUTE_NAME = "xid";

  /**
   * Starts transaction, generates and sets transaction ID.
   * @return transaction ID
   */
  public static byte[] startTransaction() {
    byte[] xid = RandomBytesUtils.createRandomBytes(XID_SIZE);
    setTransactionId(xid);
    log.debug("Started new transaction");

    return xid;
  }

  public static void setTransactionId(byte[] xid) {
    MDC.put(XID_ATTRIBUTE_NAME, DatatypeConverter.printHexBinary(xid));
  }

  /**
   * Gets transaction ID.
   * @return transaction ID
   */
  public static byte[] getTransactionId() {
    String xidStr = MDC.get(XID_ATTRIBUTE_NAME);
    if (xidStr == null) {
      return null;
    }

    return DatatypeConverter.parseHexBinary(xidStr);
  }

  public static void removeTransactionId() {
    MDC.remove(XID_ATTRIBUTE_NAME);
  }
}
