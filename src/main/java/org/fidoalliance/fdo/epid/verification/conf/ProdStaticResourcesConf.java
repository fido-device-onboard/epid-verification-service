// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.conf;

import org.fidoalliance.fdo.epid.verification.enums.EpidResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Profile("production")
@Configuration
public class ProdStaticResourcesConf implements WebMvcConfigurer {

  @Value("${crypto-material.path}")
  String cryptoMaterialPath;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    String resourceLocation = "file:" + cryptoMaterialPath + "/static/prod/";
    registry
        .addResourceHandler(
            "/**/" + EpidResource.PRIVRL.toLowerCaseString(),
            "/**/" + EpidResource.PUBKEY.toLowerCaseString(),
            "/**/" + EpidResource.SIGRL.toLowerCaseString(),
            "/**/" + EpidResource.PUBKEY_CRT.toLowerCaseString(),
            "/**/" + EpidResource.PUBKEY_CRT_BIN.toLowerCaseString())
        .addResourceLocations(resourceLocation);
  }
}
