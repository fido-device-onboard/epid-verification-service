// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification;

import org.fidoalliance.fdo.epid.verification.conf.CustomJettyContainerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import(CustomJettyContainerConfiguration.class)
@EnableAutoConfiguration(
    exclude = {
      JtaAutoConfiguration.class,
      JmxAutoConfiguration.class,
      JacksonAutoConfiguration.class,
      DataSourceTransactionManagerAutoConfiguration.class,
      ErrorMvcAutoConfiguration.class,
      MultipartAutoConfiguration.class,
      HypermediaAutoConfiguration.class,
      DataSourceAutoConfiguration.class,
      WebSocketServletAutoConfiguration.class,
      PersistenceExceptionTranslationAutoConfiguration.class,
      CacheAutoConfiguration.class,
      RepositoryRestMvcAutoConfiguration.class
    })
@ComponentScan(basePackages = {"org.fidoalliance.fdo.epid"})
public class UVerificationServiceApplication {

  /**
   * Starts verification service spring application.
   * @param args runtime arguments
   */
  public static void main(String[] args) {
    System.loadLibrary("epid_verifier");
    System.loadLibrary("epid_verifier_wrap");
    SpringApplication.run(UVerificationServiceApplication.class, args);
  }
}
