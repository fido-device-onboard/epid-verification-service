// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.exceptions;

public class InvalidEpidResourceTypeException extends Exception {

  public InvalidEpidResourceTypeException(String epidResourceName) {
    super(String.format("Resource type (%s) is not supported", epidResourceName));
  }
}
