// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.services.staticresources;

import com.google.common.base.Joiner;
import com.google.common.io.ByteStreams;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.fidoalliance.fdo.epid.verification.enums.EpidResource;
import org.fidoalliance.fdo.epid.verification.enums.EpidVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EpidStaticResourceLoader {

  private static final String PRODUCTION_DIR = "";
  private static final String DEVELOPMENT_DIR = "/dev";

  private final String cryptoMaterialPath;
  private final ResourceLoader resourceLoader;
  private final Environment environment;

  /**
   * Parameterized constructor.
   * @param cryptoMaterialPath cryptomaterial path
   * @param resourceLoader resource loader 
   * @param environment spring profiles
   */
  @Autowired
  public EpidStaticResourceLoader(
      @Value("${crypto-material.path}") String cryptoMaterialPath,
      ResourceLoader resourceLoader,
      Environment environment) {
    this.cryptoMaterialPath = cryptoMaterialPath;
    this.resourceLoader = resourceLoader;
    this.environment = environment;
  }

  private String getResourcesDirBasedOnProfiles() {
    List<String> activeProfilesList = Arrays.asList(environment.getActiveProfiles());
    if (activeProfilesList.contains("production")) {
      return PRODUCTION_DIR;
    } else if (activeProfilesList.contains("development")) {
      return DEVELOPMENT_DIR;
    } else {
      throw new IllegalStateException("Unknown profile! Can't resolve static resources path.");
    }
  }

  /**
   * Retrieves resource from cryptomaterial.
   * @param epidResource EPID resource
   * @param epidVersion EPID version
   * @param groupId Group ID
   * @return byte array of resource from cryptomaterial
   * @throws IOException if I/O error occurs
   */
  public byte[] getResource(EpidResource epidResource, EpidVersion epidVersion, byte[] groupId)
      throws IOException {
    return getResourceAsByteStream(
        epidVersion.toLowerCaseString(),
        DatatypeConverter.printHexBinary(groupId),
        epidResource.toLowerCaseString());
  }

  /**
   * Determines if EPID group exists.
   * @param epidVersion EPID version
   * @param group EPID group
   * @return boolean value determining group existance in EPID cryptomaterials
   */
  public boolean checkIfGroupExists(String epidVersion, String group) {
    String classPathStaticDir = cryptoMaterialPath + "/static";
    String groupPath =
        Joiner.on(File.separator)
            .join(classPathStaticDir + getResourcesDirBasedOnProfiles(), epidVersion, group);

    return new File(groupPath).isDirectory();
  }

  private byte[] getResourceAsByteStream(String epidVersion, String groupId, String epidResource)
      throws IOException {
    String classPathStaticDir = "file:" + cryptoMaterialPath + "/static";
    String resourcePath =
        Joiner.on(File.separator)
            .join(
                classPathStaticDir + getResourcesDirBasedOnProfiles(),
                epidVersion,
                groupId,
                epidResource);

    log.info("Loading resource " + resourcePath);

    Resource resource = resourceLoader.getResource(resourcePath);

    return ByteStreams.toByteArray(resource.getInputStream());
  }
}
