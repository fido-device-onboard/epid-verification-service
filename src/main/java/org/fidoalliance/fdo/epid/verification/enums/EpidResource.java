// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.enums;

import org.fidoalliance.fdo.epid.verification.exceptions.InvalidEpidResourceTypeException;

public enum EpidResource {
  SIGRL,
  PRIVRL,
  PUBKEY,
  SIGRL_SIGNED,
  PUBKEY_CRT,
  PUBKEY_CRT_BIN;

  /**
   * Fetches EPID resource.
   *
   * @param epidResourceName EPID resource name
   * @return EpidResource
   * @throws InvalidEpidResourceTypeException is thrown when the request tries to access a
   *     restricted resource
   */
  public static EpidResource getEpidResourceByName(String epidResourceName)
      throws InvalidEpidResourceTypeException {
    for (EpidResource epidResource : EpidResource.values()) {
      if (epidResource.toLowerCaseString().equalsIgnoreCase(epidResourceName)) {
        return epidResource;
      }
    }

    throw new InvalidEpidResourceTypeException(epidResourceName);
  }

  public String toLowerCaseString() {
    return name().toLowerCase().replace("_", ".");
  }
}
