// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.controllers;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.fidoalliance.fdo.epid.verification.conf.InfoConfig;
import org.fidoalliance.fdo.epid.verification.logging.IpManager;
import org.fidoalliance.fdo.epid.verification.logging.MethodCallLogged;
import org.fidoalliance.fdo.epid.verification.model.responses.VerificationHealthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HealthController {

  @Autowired private InfoConfig infoConfig;

  /**
   * Returns service health status response.
   * @return service health status
   */
  @RequestMapping(value = "/health", method = RequestMethod.GET)
  @MethodCallLogged
  public ResponseEntity checkHealth() {
    VerificationHealthResponse hr = new VerificationHealthResponse(infoConfig.getVersion());
    return new ResponseEntity<>(hr, HttpStatus.OK);
  }
}
