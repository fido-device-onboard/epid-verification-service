// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class MethodCallLoggingAspect {

  @Before("@annotation(annotation)")
  public void methodCallLogger(JoinPoint pjp, MethodCallLogged annotation) throws Throwable {
    String signature = getSignature(pjp);
    log.debug("Method '{}' called", signature);
  }

  /**
   * Calculates and logs method execution time.
   * @param pjp ProceedingJoinPoint of Spring AOP
   * @param annotation method execution log
   * @return Spring AOP ProceedingJoinPoint method invocation object
   * @throws Throwable for unhandled exceptions
   */
  @Around("@annotation(annotation)")
  public Object methodExecutionLogger(ProceedingJoinPoint pjp, MethodExecutionLogged annotation)
      throws Throwable {
    String signature = getSignature(pjp);

    log.debug("Method '{}' called", signature);

    long startTime = System.currentTimeMillis();
    Object retValue = pjp.proceed();
    long endTime = System.currentTimeMillis();
    long executionTime = endTime - startTime;

    log.debug("Method '{}' execution time {} ms", signature, executionTime);
    return retValue;
  }

  private String getSignature(JoinPoint jp) {
    String className = jp.getTarget().getClass().getSimpleName();
    String methodName = jp.getSignature().getName();
    return className + "." + methodName;
  }
}
