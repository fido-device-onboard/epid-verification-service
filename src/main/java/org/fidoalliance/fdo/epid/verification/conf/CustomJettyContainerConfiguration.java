// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.conf;

import java.util.Arrays;
import java.util.Optional;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.ServerConnector;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

public class CustomJettyContainerConfiguration {
  
  /**
   * Returns WebServerFactoryCustomizer.
   * @return WebServerFactoryCustomizer
   */
  @Bean
  public WebServerFactoryCustomizer customizer() {
    return containerCustomizer ->
        Optional.of(containerCustomizer)
            .filter(container -> container instanceof JettyServletWebServerFactory)
            .map(JettyServletWebServerFactory.class::cast)
            .ifPresent(
                container ->
                    container.addServerCustomizers(
                        server ->
                            Arrays.asList(server.getConnectors()).stream()
                                .filter(connector -> connector instanceof ServerConnector)
                                .forEach(
                                    connector -> {
                                      HttpConnectionFactory connectionFactory =
                                          connector.getConnectionFactory(
                                              HttpConnectionFactory.class);
                                      connectionFactory
                                          .getHttpConfiguration()
                                          .setSendServerVersion(false);
                                    })));
  }
}
