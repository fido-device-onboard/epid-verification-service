/*****************************************************************************
 *                         [INTEL CONFIDENTIAL]                              *
 *                                                                           *
 *                 Copyright 2016-2019 Intel Corporation                     *
 *                                                                           *
 * This software and the related documents are Intel copyrighted materials,  *
 * and your use of them is governed by the express license under which they  *
 * were provided to you (License). Unless the License provides otherwise,    *
 * you may not use, modify, copy, publish, distribute, disclose or transmit  *
 * this software or the related documents without Intel's prior written      *
 * permission.                                                               *
 *                                                                           *
 * This software and the related documents are provided as is, with no       *
 * express or implied warranties, other than those that are expressly stated *
 * in the License.                                                           *
 *****************************************************************************/

package org.fidoalliance.fdo.epid.verification.services.proof;

import org.fidoalliance.fdo.epid.verification.exceptions.GenericVerificationException;
import org.fidoalliance.fdo.epid.verification.exceptions.InvalidSignatureException;
import org.fidoalliance.fdo.epid.verification.exceptions.SigrlVersionSignatureVersionMismatchException;

public abstract class RequestVerifierService {

  public abstract void verifyEpidSignature(
      byte[] signature,
      byte[] msg,
      byte[] groupPubKey,
      byte[] sigRl,
      byte[] privRl,
      byte[] basename)
      throws InvalidSignatureException, SigrlVersionSignatureVersionMismatchException,
          GenericVerificationException;

  /**
   * Verifies that the message body contains the signature to be verified.
   *
   * @param message request message bytes
   * @throws InvalidSignatureException is thrown when the signature supplied by device for
   *     verification is invalid
   */
  public void verifyMessage(byte[] message) throws InvalidSignatureException {
    if (message.length == 0) {
      throw new InvalidSignatureException("Cannot verify signature of an empty message!");
    }
  }
}
