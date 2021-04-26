// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.exceptions;

import org.fidoalliance.fdo.epid.verification.enums.EpidVersion;

public class InvalidEpidGroupIdException extends Exception {

  /**
   * Exception for handling requests in which the specified GroupId is invalid.
   * @param epidVersion EPID version
   * @param groupId EPID Group ID
   */
  public InvalidEpidGroupIdException(EpidVersion epidVersion, String groupId) {
    super(
        String.format(
            "Requested (%s) group (%s) does not exist", epidVersion.toLowerCaseString(), groupId));
  }
}
