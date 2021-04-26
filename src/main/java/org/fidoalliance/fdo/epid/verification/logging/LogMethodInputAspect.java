// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogMethodInputAspect {

  /**
   * Logs EPID version, Group ID and Resource ID.
   * @param jp JoinPoint of Spring AOP
   * @param annotation resource input log
   */
  @Before("@annotation(annotation)")
  public void getResponseLogger(JoinPoint jp, LogGetResourceInput annotation) {
    Object[] arguments = jp.getArgs();
    String epidVersionName = (String) arguments[0];
    String groupId = (String) arguments[1];
    String resourceId = (String) arguments[2];

    String message =
        ("Epid version name " + epidVersionName)
            + " Group id "
            + groupId
            + " Resource id "
            + resourceId;

    log.info(message);
  }
}
