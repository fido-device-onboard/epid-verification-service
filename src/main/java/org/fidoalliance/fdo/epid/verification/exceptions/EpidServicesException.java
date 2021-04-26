// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.exceptions;

public class EpidServicesException extends Exception {

  public EpidServicesException() {
    super();
  }

  public EpidServicesException(String message) {
    super(message);
  }

  public EpidServicesException(Throwable throwable) {
    super(throwable);
  }

  public EpidServicesException(String message, Throwable t) {
    super(message, t);
  }
}
