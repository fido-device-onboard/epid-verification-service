// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.conf.logging;

import java.net.InetSocketAddress;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import net.logstash.logback.stacktrace.ShortenedThrowableConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "logging.logstash.enabled", havingValue = "true")
public class LoggingBeans {

  private LogstashConfig logstashConfig;

  @Autowired
  public LoggingBeans(LogstashConfig logstashConfig) {
    this.logstashConfig = logstashConfig;
  }

  @Bean
  public LogstashTcpSocketAppender getLogbackLoggerContextListener() {
    return new LogstashTcpSocketAppender();
  }

  /**
   * Returns logstash encoder.
   * @return Logstash encoder
   */
  @Bean
  public LogstashEncoder getLogstashEncoder() {
    LogstashEncoder logstashEncoder = new LogstashEncoder();
    String customApplicationNameField =
        "{\"application_name\":\"" + logstashConfig.getApplicationName() + "\"}";
    logstashEncoder.setCustomFields(customApplicationNameField);
    return logstashEncoder;
  }

  @Bean
  public ShortenedThrowableConverter getShortenedThrowableConverter() {
    return new ShortenedThrowableConverter();
  }

  @Bean
  public InetSocketAddress getInetSocketAddress() {
    return new InetSocketAddress(logstashConfig.getHost(), logstashConfig.getPort());
  }
}
