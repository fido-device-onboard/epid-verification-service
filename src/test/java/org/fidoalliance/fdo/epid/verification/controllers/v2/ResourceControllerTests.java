// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.controllers.v2;

import static javax.xml.bind.DatatypeConverter.parseHexBinary;

import java.io.IOException;
import org.fidoalliance.fdo.epid.verification.enums.EpidResource;
import org.fidoalliance.fdo.epid.verification.enums.EpidVersion;
import org.fidoalliance.fdo.epid.verification.exceptions.InvalidEpidGroupIdException;
import org.fidoalliance.fdo.epid.verification.exceptions.InvalidEpidGroupLengthException;
import org.fidoalliance.fdo.epid.verification.exceptions.InvalidEpidResourceTypeException;
import org.fidoalliance.fdo.epid.verification.exceptions.InvalidEpidResourceVersionException;
import org.fidoalliance.fdo.epid.verification.exceptions.ResourceAccessRestrictedException;
import org.fidoalliance.fdo.epid.verification.logging.TransactionManager;
import org.fidoalliance.fdo.epid.verification.services.staticresources.EpidStaticResourceLoader;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*",
        "javax.management.*", "org.fidoalliance.fdo.epid.verification.enums.*"})
@PrepareForTest({TransactionManager.class})
public class ResourceControllerTests extends PowerMockTestCase {

  @Mock private EpidStaticResourceLoader epidStaticResourceLoader;

  private ResourcesController resourcesController;

  private final String invalidEpidId = "not_epid_id";
  private final String epid11GroupIdWithInvalidLength = "FFFFFF00AA";
  private final String epid11GroupIdWithInvalidCharacter = "FFFFFF00SS";
  private final String epid11GroupIdThatNotExist = "AAAAAA00";
  private final String validEpid11GroupId = "FFFFFF00";
  private final String validEpid20GroupId = "0000000DDDDDCCCC00000000EEEEEE00";
  private final String validEpid11GroupIdWithoutSignedSigrl = "BBBBBB00";
  private final String invalidResourceName = "bkFamilyPhoto";
  private final String epid11Name = "epid11";
  private final String epid20Name = "epid20";
  private final String resourcePrivrlName = "privrl";
  private final String resourceSignedSigrlName = "sigrl.signed";
  private final String resourceUnsignedSigrlName = "sigrl";

  private final byte[] signedSigrl = new byte[] {0x1, 0x2, 0x3};

  /**
   * Setup before running tests.
   * @throws IOException for unhandled IO exceptions
   */
  @BeforeMethod
  public void setUp() throws IOException {
    MockitoAnnotations.initMocks(this);
    PowerMockito.mockStatic(TransactionManager.class);

    Mockito.when(
            epidStaticResourceLoader.getResource(
                EpidResource.PRIVRL, EpidVersion.EPID11, parseHexBinary(validEpid11GroupId)))
        .thenReturn(new byte[] {});
    Mockito.when(
            epidStaticResourceLoader.getResource(
                EpidResource.SIGRL_SIGNED, EpidVersion.EPID20, parseHexBinary(validEpid20GroupId)))
        .thenReturn(signedSigrl);
    Mockito.when(
            epidStaticResourceLoader.getResource(
                EpidResource.SIGRL,
                EpidVersion.EPID11,
                parseHexBinary(validEpid11GroupIdWithoutSignedSigrl)))
        .thenThrow(new IOException());

    Mockito.when(epidStaticResourceLoader.checkIfGroupExists(epid11Name, epid11GroupIdThatNotExist))
        .thenReturn(false);
    Mockito.when(epidStaticResourceLoader.checkIfGroupExists(epid11Name, validEpid11GroupId))
        .thenReturn(true);
    Mockito.when(
            epidStaticResourceLoader.checkIfGroupExists(
                epid11Name, validEpid11GroupIdWithoutSignedSigrl))
        .thenReturn(true);
    Mockito.when(epidStaticResourceLoader.checkIfGroupExists(epid20Name, validEpid20GroupId))
        .thenReturn(true);

    resourcesController = new ResourcesController(epidStaticResourceLoader);
  }

  @Test(expectedExceptions = {InvalidEpidResourceVersionException.class})
  public void shouldThrowOnInvalidEpidVersionId()
      throws InvalidEpidResourceVersionException, InvalidEpidGroupIdException, IOException,
          InvalidEpidGroupLengthException, ResourceAccessRestrictedException,
          InvalidEpidResourceTypeException {
    resourcesController.getResource(invalidEpidId, validEpid11GroupId, resourcePrivrlName);
  }

  @Test(expectedExceptions = {InvalidEpidGroupLengthException.class})
  public void shouldThrowOnInvalidEpidGroupIdLength()
      throws InvalidEpidResourceVersionException, InvalidEpidGroupIdException, IOException,
          InvalidEpidGroupLengthException, ResourceAccessRestrictedException,
          InvalidEpidResourceTypeException {
    resourcesController.getResource(epid11Name, epid11GroupIdWithInvalidLength, resourcePrivrlName);
  }

  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void shouldThrowOnInvalidEpidGroupName()
      throws InvalidEpidResourceVersionException, InvalidEpidGroupIdException, IOException,
          InvalidEpidGroupLengthException, ResourceAccessRestrictedException,
          InvalidEpidResourceTypeException {
    resourcesController.getResource(
        epid11Name, epid11GroupIdWithInvalidCharacter, resourcePrivrlName);
  }

  @Test(expectedExceptions = {InvalidEpidResourceTypeException.class})
  public void shouldThrowOnInvalidInvalidResourceName()
      throws InvalidEpidResourceVersionException, InvalidEpidGroupIdException, IOException,
          InvalidEpidGroupLengthException, ResourceAccessRestrictedException,
          InvalidEpidResourceTypeException {
    resourcesController.getResource(epid11Name, validEpid11GroupId, invalidResourceName);
  }

  @Test(expectedExceptions = {InvalidEpidGroupIdException.class})
  public void shouldThrowOnNotExistingEpidGroupId()
      throws InvalidEpidResourceVersionException, InvalidEpidGroupIdException, IOException,
          InvalidEpidGroupLengthException, ResourceAccessRestrictedException,
          InvalidEpidResourceTypeException {
    resourcesController.getResource(epid11Name, epid11GroupIdThatNotExist, resourcePrivrlName);
  }

  @Test(expectedExceptions = {ResourceAccessRestrictedException.class})
  public void shouldThrowOnAccessingSignedPrivrlOnEpid11()
      throws InvalidEpidResourceVersionException, InvalidEpidGroupIdException, IOException,
          InvalidEpidGroupLengthException, ResourceAccessRestrictedException,
          InvalidEpidResourceTypeException {
    resourcesController.getResource(epid11Name, validEpid11GroupId, resourceSignedSigrlName);
  }

  @Test(expectedExceptions = {ResourceAccessRestrictedException.class})
  public void shouldThrowOnAccessingSignedPrivrlOnEpid22()
      throws InvalidEpidResourceVersionException, InvalidEpidGroupIdException, IOException,
          InvalidEpidGroupLengthException, ResourceAccessRestrictedException,
          InvalidEpidResourceTypeException {
    resourcesController.getResource(epid20Name, validEpid20GroupId, resourceSignedSigrlName);
  }

  @Test
  public void testIfSignedSigrlIsAccessibleWhenAvailableForGroup()
      throws InvalidEpidResourceVersionException, InvalidEpidGroupIdException, IOException,
          InvalidEpidGroupLengthException, ResourceAccessRestrictedException,
          InvalidEpidResourceTypeException {
    ResponseEntity<byte[]> response =
        resourcesController.getResource(epid20Name, validEpid20GroupId, resourceUnsignedSigrlName);

    Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    Assert.assertEquals(response.getBody(), signedSigrl);
  }

  @Test
  public void testIfResponseIsOkWhenSignedSigrlIsNotAvailableForGroup()
      throws InvalidEpidResourceVersionException, InvalidEpidGroupIdException, IOException,
          InvalidEpidGroupLengthException, ResourceAccessRestrictedException,
          InvalidEpidResourceTypeException {
    ResponseEntity<byte[]> response =
        resourcesController.getResource(
            epid11Name, validEpid11GroupIdWithoutSignedSigrl, resourceUnsignedSigrlName);

    Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    Assert.assertEquals(response.getBody(), null);
  }
}
