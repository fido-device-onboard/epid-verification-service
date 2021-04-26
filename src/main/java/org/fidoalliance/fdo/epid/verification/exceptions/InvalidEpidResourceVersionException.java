// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.exceptions;

public class InvalidEpidResourceVersionException extends Exception {

  public InvalidEpidResourceVersionException(String epidVersion) {
    super(String.format("Requested resource EPID version (%s) is invalid", epidVersion));
  }
}
