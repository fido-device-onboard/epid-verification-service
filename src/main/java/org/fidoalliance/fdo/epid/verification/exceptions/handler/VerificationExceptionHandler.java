// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.exceptions.handler;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.fidoalliance.fdo.epid.verification.exceptions.GenericVerificationException;
import org.fidoalliance.fdo.epid.verification.exceptions.InvalidEpidGroupIdException;
import org.fidoalliance.fdo.epid.verification.exceptions.InvalidEpidGroupLengthException;
import org.fidoalliance.fdo.epid.verification.exceptions.InvalidEpidResourceTypeException;
import org.fidoalliance.fdo.epid.verification.exceptions.InvalidEpidResourceVersionException;
import org.fidoalliance.fdo.epid.verification.exceptions.InvalidSignatureException;
import org.fidoalliance.fdo.epid.verification.exceptions.ResourceAccessRestrictedException;
import org.fidoalliance.fdo.epid.verification.exceptions.SigrlVersionSignatureVersionMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class VerificationExceptionHandler {

  @ExceptionHandler({ResourceAccessRestrictedException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void resourceAccessRestricted(ResourceAccessRestrictedException exception) {
    log.info(exception.getMessage());
  }

  @ExceptionHandler({InvalidEpidGroupIdException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void invalidEpidGroupId(InvalidEpidGroupIdException exception) {
    log.info(exception.getMessage());
  }

  @ExceptionHandler({InvalidEpidResourceTypeException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void invalidEpidResourceType(InvalidEpidResourceTypeException exception) {
    log.info(exception.getMessage());
  }

  @ExceptionHandler({InvalidEpidGroupLengthException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void invalidEpidGroupLength(InvalidEpidGroupLengthException exception) {
    log.info(exception.getMessage());
  }

  @ExceptionHandler({InvalidEpidResourceVersionException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void invalidResource(InvalidEpidResourceVersionException exception) {
    log.info(exception.getMessage());
  }

  @ExceptionHandler({IOException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public void invalidRequest(IOException exception) {
    log.info(exception.getMessage(), exception);
  }

  @ExceptionHandler({Exception.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public void unexpectedException(Exception exception) {
    log.info(exception.getMessage(), exception);
  }

  @ExceptionHandler({GenericVerificationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public void genericError(GenericVerificationException exception) {
    log.info(exception.getMessage());
  }

  @ExceptionHandler({InvalidSignatureException.class})
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public void invalidSignature(InvalidSignatureException exception) {
    log.info(exception.getMessage());
  }

  @ExceptionHandler({SigrlVersionSignatureVersionMismatchException.class})
  @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
  public void sigrlVersionMismatched(SigrlVersionSignatureVersionMismatchException exception) {
    log.info(exception.getMessage());
  }
}
