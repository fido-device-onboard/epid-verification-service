// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.conf;

import org.fidoalliance.fdo.epid.verification.exceptions.handler.VerificationExceptionHandler;
import org.fidoalliance.fdo.epid.verification.http.RequestInitializerInterceptor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableCaching
public class VerificationAppConfig implements WebMvcConfigurer {

  @Bean(name = "cacheManagerCM")
  public CacheManager cacheManager() {
    return new ConcurrentMapCacheManager(
        "requestVerifierService", "responseService", "serviceClassesSuffix");
  }

  @Bean
  public VerificationExceptionHandler verificationExceptionHandler() {
    return new VerificationExceptionHandler();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new RequestInitializerInterceptor());
  }
}
