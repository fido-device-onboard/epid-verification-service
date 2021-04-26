// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.exceptions;

import org.fidoalliance.fdo.epid.verification.enums.EpidVersion;

public class InvalidEpidGroupLengthException extends Exception {

  /**
   * Exception for handling requests in which the length of GroupId is invalid.
   * @param epidVersion EPID version
   * @param currentLength group length
   */
  public InvalidEpidGroupLengthException(EpidVersion epidVersion, int currentLength) {
    super(
        String.format(
            "Provided groupId length for %s is invalid. Expected %d but got %d",
            epidVersion.toLowerCaseString(), epidVersion.getGroupIdLength(), currentLength));
  }
}
