// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.exceptions;

public class ResourceAccessRestrictedException extends Exception {
  public ResourceAccessRestrictedException(String reason) {
    super(reason);
  }
}
