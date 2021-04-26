// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.exceptions;

import javax.xml.bind.DatatypeConverter;

public class SigrlVersionSignatureVersionMismatchException extends Exception {

  /**
   * Exception for handling requests where the version in signature doesn't match the version in
   * sigrl.
   *
   * @param signatureVersion signature version
   * @param sigrlVersion sigrl version
   */
  public SigrlVersionSignatureVersionMismatchException(
      byte[] signatureVersion, byte[] sigrlVersion) {
    super(
        String.format(
            "Sigrl version is |%s| Signature version is |%s|",
            DatatypeConverter.printHexBinary(sigrlVersion),
            DatatypeConverter.printHexBinary(signatureVersion)));
  }
}
