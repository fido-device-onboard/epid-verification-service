// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.http;

import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.fidoalliance.fdo.epid.verification.logging.IpManager;
import org.fidoalliance.fdo.epid.verification.logging.TransactionManager;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class RequestInitializerInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws NoSuchAlgorithmException {
    IpManager.retrieveIpFromRequest(request);
    TransactionManager.startTransaction();

    return true;
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      @Nullable Exception ex)
      throws Exception {
    IpManager.removeIP();
    TransactionManager.removeTransactionId();
  }
}
