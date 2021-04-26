// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidSignatureException extends Exception {

  public InvalidSignatureException(String msg) {
    super(msg);
  }

  public InvalidSignatureException(String s, Throwable throwable) {
    super(s, throwable);
  }
}
