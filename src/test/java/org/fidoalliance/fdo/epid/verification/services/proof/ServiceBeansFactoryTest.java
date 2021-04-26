// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.services.proof;

import java.util.ArrayList;
import java.util.List;
import org.fidoalliance.fdo.epid.verification.enums.EpidVersion;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ServiceBeansFactoryTest {

  private ServiceBeansFactory serviceBeansFactory;

  private List<RequestVerifierService> requestVerifierServices;

  private List<ResponseService> responseServices;

  /**
   * Setup before running tests.
   */
  @BeforeMethod
  public void beforeMethod() {
    requestVerifierServices = new ArrayList<>();
    responseServices = new ArrayList<>();
    serviceBeansFactory = new ServiceBeansFactory(requestVerifierServices, responseServices);
    MockitoAnnotations.initMocks(this);
  }

  @Mock private SignatureVerificationStatusVerifierEpid11 signatureVerificationStatusVerifierEpid11;

  @Mock private SignatureVerificationStatusVerifierEpid20 signatureVerificationStatusVerifierEpid20;

  @Test
  public void testGetRequestVerifierServiceEpid20Positive() {
    requestVerifierServices.add(
        new RequestVerifierServiceEpid11(signatureVerificationStatusVerifierEpid11));
    requestVerifierServices.add(
        new RequestVerifierServiceEpid20(signatureVerificationStatusVerifierEpid20));

    RequestVerifierService actualService =
        serviceBeansFactory.getRequestVerifierService(EpidVersion.EPID20);
    Assert.assertEquals(
        actualService.getClass().getSimpleName(),
        RequestVerifierServiceEpid20.class.getSimpleName());
  }

  @Test
  public void testGetRequestVerifierServiceEpid11Positive() {
    requestVerifierServices.add(
        new RequestVerifierServiceEpid11(signatureVerificationStatusVerifierEpid11));
    requestVerifierServices.add(
        new RequestVerifierServiceEpid20(signatureVerificationStatusVerifierEpid20));

    RequestVerifierService actualService =
        serviceBeansFactory.getRequestVerifierService(EpidVersion.EPID11);
    Assert.assertEquals(
        actualService.getClass().getSimpleName(),
        RequestVerifierServiceEpid11.class.getSimpleName());
  }

  @Test(
      expectedExceptions = ServiceBeansFactory.ServiceBeanFactoryException.class,
      expectedExceptionsMessageRegExp = "Multiple definition of RequestVerifierService(.*)")
  public void testGetRequestVerifierServiceEpid11MultipleServiceDefinitionNegative() {
    requestVerifierServices.add(
        new RequestVerifierServiceEpid11(signatureVerificationStatusVerifierEpid11));
    requestVerifierServices.add(
        new RequestVerifierServiceEpid11(signatureVerificationStatusVerifierEpid11));
    requestVerifierServices.add(
        new RequestVerifierServiceEpid20(signatureVerificationStatusVerifierEpid20));

    serviceBeansFactory.getRequestVerifierService(EpidVersion.EPID11);
  }

  @Test(
      expectedExceptions = ServiceBeansFactory.ServiceBeanFactoryException.class,
      expectedExceptionsMessageRegExp = "Not found definition of RequestVerifierService(.*)")
  public void testGetRequestVerifierServiceEpid11NoSuchServiceDefinitionNegative() {
    requestVerifierServices.add(
        new RequestVerifierServiceEpid20(signatureVerificationStatusVerifierEpid20));
    serviceBeansFactory.getRequestVerifierService(EpidVersion.EPID11);
  }

  @Test
  public void testGetResponseServiceEpid20Positive() {
    responseServices.add(new ResponseServiceEpid11());
    responseServices.add(new ResponseServiceEpid20());

    ResponseService actualService = serviceBeansFactory.getResponseService(EpidVersion.EPID20);
    Assert.assertEquals(
        actualService.getClass().getSimpleName(), ResponseServiceEpid20.class.getSimpleName());
  }

  @Test
  public void testGetResponseServiceEpid11Positive() {
    responseServices.add(new ResponseServiceEpid11());
    responseServices.add(new ResponseServiceEpid20());

    ResponseService actualService = serviceBeansFactory.getResponseService(EpidVersion.EPID11);
    Assert.assertEquals(
        actualService.getClass().getSimpleName(), ResponseServiceEpid11.class.getSimpleName());
  }

  @Test(
      expectedExceptions = ServiceBeansFactory.ServiceBeanFactoryException.class,
      expectedExceptionsMessageRegExp = "Multiple definition of ResponseService(.*)")
  public void testGetResponseServiceEpid11MultipleServiceDefinitionNegative() {
    responseServices.add(new ResponseServiceEpid11());
    responseServices.add(new ResponseServiceEpid11());
    responseServices.add(new ResponseServiceEpid20());

    serviceBeansFactory.getResponseService(EpidVersion.EPID11);
  }

  @Test(
      expectedExceptions = ServiceBeansFactory.ServiceBeanFactoryException.class,
      expectedExceptionsMessageRegExp = "Not found definition of ResponseService(.*)")
  public void testGetResponseServiceEpid11NoSuchServiceDefinitionNegative() {
    responseServices.add(new ResponseServiceEpid20());
    serviceBeansFactory.getResponseService(EpidVersion.EPID11);
  }
}
