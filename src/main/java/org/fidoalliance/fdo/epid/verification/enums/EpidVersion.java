// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.enums;

import org.fidoalliance.fdo.epid.verification.exceptions.InvalidEpidResourceVersionException;

public enum EpidVersion {
  EPID11(4),
  EPID20(16);

  private int groupIdLength;

  EpidVersion(int groupIdLength) {
    this.groupIdLength = groupIdLength;
  }

  /**
   * Returns EPID version.
   *
   * @param epidVersion EPID version
   * @return EPID version
   * @throws InvalidEpidResourceVersionException is thrown when the EPID version is invalid for the
   *     requested resource
   */
  public static EpidVersion getEpidVersionByName(String epidVersion)
      throws InvalidEpidResourceVersionException {
    for (EpidVersion supportedEpidVersion : EpidVersion.values()) {
      if (supportedEpidVersion.toLowerCaseString().equalsIgnoreCase(epidVersion)) {
        return supportedEpidVersion;
      }
    }

    throw new InvalidEpidResourceVersionException(epidVersion);
  }

  public int getGroupIdLength() {
    return groupIdLength;
  }

  public String toLowerCaseString() {
    return name().toLowerCase();
  }
}
