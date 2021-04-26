// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.exceptions;

public class ServiceDataSerializationException extends EpidServicesException {
  public ServiceDataSerializationException(String objectName, Throwable t) {
    super(String.format("Error occurred during serializing %s", objectName), t);
  }
}
