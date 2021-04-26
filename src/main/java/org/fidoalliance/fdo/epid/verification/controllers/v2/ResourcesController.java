// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.controllers.v2;

import java.io.IOException;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.fidoalliance.fdo.epid.verification.enums.EpidResource;
import org.fidoalliance.fdo.epid.verification.enums.EpidVersion;
import org.fidoalliance.fdo.epid.verification.exceptions.InvalidEpidGroupIdException;
import org.fidoalliance.fdo.epid.verification.exceptions.InvalidEpidGroupLengthException;
import org.fidoalliance.fdo.epid.verification.exceptions.InvalidEpidResourceTypeException;
import org.fidoalliance.fdo.epid.verification.exceptions.InvalidEpidResourceVersionException;
import org.fidoalliance.fdo.epid.verification.exceptions.ResourceAccessRestrictedException;
import org.fidoalliance.fdo.epid.verification.logging.LogGetResourceInput;
import org.fidoalliance.fdo.epid.verification.logging.MethodExecutionLogged;
import org.fidoalliance.fdo.epid.verification.services.staticresources.EpidStaticResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/v2/")
public class ResourcesController {

  private final EpidStaticResourceLoader epidStaticResourceLoader;

  @Autowired
  public ResourcesController(EpidStaticResourceLoader epidStaticResourceLoader) {
    this.epidStaticResourceLoader = epidStaticResourceLoader;
  }

  /**
   * Returns EPID resource.
   *
   * @param epidVersionName EPID version
   * @param groupId Group ID
   * @param resourceId Resource ID
   * @return EPID resource
   * @throws InvalidEpidResourceVersionException is thrown when the EPID version is invalid for the
   *     requested resource
   * @throws InvalidEpidGroupLengthException is thrown when the provided groupId length is invalid
   * @throws InvalidEpidResourceTypeException is thrown when the requested resource type is not
   *     supported by the service
   * @throws IOException if an I/O error occurs
   * @throws InvalidEpidGroupIdException is thrown when the EPID group doesn't exist
   * @throws ResourceAccessRestrictedException is thrown when the request tries to access a
   *     restricted resource
   */
  @GetMapping(
      value = "{epid_version}/{group_id}/{resource_id:.+}",
      produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE, "application/x-x509-ca-cert"})
  @MethodExecutionLogged
  @LogGetResourceInput
  public ResponseEntity<byte[]> getResource(
      @PathVariable("epid_version") String epidVersionName,
      @PathVariable("group_id") String groupId,
      @PathVariable("resource_id") String resourceId)
      throws InvalidEpidResourceVersionException, InvalidEpidGroupLengthException,
          InvalidEpidResourceTypeException, IOException, InvalidEpidGroupIdException,
          ResourceAccessRestrictedException {

    EpidVersion epidVersion = EpidVersion.getEpidVersionByName(epidVersionName);

    byte[] binaryGroupId = DatatypeConverter.parseHexBinary(groupId);

    if (epidVersion.getGroupIdLength() != binaryGroupId.length) {
      throw new InvalidEpidGroupLengthException(epidVersion, binaryGroupId.length);
    }

    EpidResource epidResource = EpidResource.getEpidResourceByName(resourceId);

    log.info("Resource type is " + epidResource.toLowerCaseString());

    if (!epidStaticResourceLoader.checkIfGroupExists(epidVersionName, groupId)) {
      throw new InvalidEpidGroupIdException(epidVersion, groupId);
    }

    return new ResponseEntity<>(getEpidResource(epidVersion, groupId, epidResource), HttpStatus.OK);
  }

  @MethodExecutionLogged
  @LogGetResourceInput
  private byte[] getEpidResource(EpidVersion epidVersion, String groupId, EpidResource epidResource)
      throws IOException, ResourceAccessRestrictedException {

    byte[] binaryGroupId = DatatypeConverter.parseHexBinary(groupId);

    if (epidResource == EpidResource.SIGRL_SIGNED) {
      throw new ResourceAccessRestrictedException(
          "Signed version of sigrl is only accessible as sigrl resource");
    }

    if (epidResource == EpidResource.SIGRL) {
      log.info("Sigrl requested but Sigrl.signed will be returned");
      try {
        return epidStaticResourceLoader.getResource(
            EpidResource.SIGRL_SIGNED, epidVersion, binaryGroupId);
      } catch (IOException e) {
        log.info(String.format("Signed sigrl not found for EPID group (%s)", groupId));
        return new byte[] {};
      }
    }

    return epidStaticResourceLoader.getResource(epidResource, epidVersion, binaryGroupId);
  }
}
