// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.utils;

import org.fidoalliance.fdo.epid.verification.enums.EpidResource;
import org.fidoalliance.fdo.epid.verification.enums.EpidVersion;
import org.fidoalliance.fdo.epid.verification.services.staticresources.EpidStaticResourceLoader;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ResourceLoader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EpidStaticResourceLoaderTest {

  private static final byte[] EPID11_GROUP5_GID = {0x00, 0x00, 0x00, 0x05};
  private static final byte[] EPID11_GROUP5_SIGRL = {0x12, 0x23, 0x34, 0x45};

  @InjectMocks private EpidStaticResourceLoader epidStaticResourceLoader;

  @Mock private ResourceLoader resourceLoader;

  @Mock private Environment environment;

  @BeforeMethod
  private void beforeMethod() throws Exception {
    MockitoAnnotations.initMocks(this);
    epidStaticResourceLoader =
        new EpidStaticResourceLoader("cryptoMaterialPath", resourceLoader, environment);
  }

  @Test
  public void testGetSigrlEpid11DevelopmentPositive() throws Exception {
    String epid11Group5SigrlDevPath = "file:cryptoMaterialPath/static/dev/epid11/00000005/sigrl";
    Mockito.when(resourceLoader.getResource(epid11Group5SigrlDevPath))
        .thenReturn(new ByteArrayResource(EPID11_GROUP5_SIGRL));
    Mockito.when(environment.getActiveProfiles()).thenReturn(new String[] {"development"});
    byte[] returnedSigrl =
        epidStaticResourceLoader.getResource(
            EpidResource.SIGRL, EpidVersion.EPID11, EPID11_GROUP5_GID);
    Assert.assertEquals(returnedSigrl, EPID11_GROUP5_SIGRL);
  }

  @Test
  public void testGetSigrlEpid11ProductionPositive() throws Exception {
    String epid11Group5SigrlDevPath = "file:cryptoMaterialPath/static/prod/epid11/00000005/sigrl";
    Mockito.when(resourceLoader.getResource(epid11Group5SigrlDevPath))
        .thenReturn(new ByteArrayResource(EPID11_GROUP5_SIGRL));
    Mockito.when(environment.getActiveProfiles()).thenReturn(new String[] {"production"});
    byte[] returnedSigrl =
        epidStaticResourceLoader.getResource(
            EpidResource.SIGRL, EpidVersion.EPID11, EPID11_GROUP5_GID);
    Assert.assertEquals(returnedSigrl, EPID11_GROUP5_SIGRL);
  }

  @Test(expectedExceptions = IllegalStateException.class)
  public void testGetSigrlEpid11UnknownProfileExpectExceptionPositive() throws Exception {
    Mockito.when(environment.getActiveProfiles()).thenReturn(new String[] {"unknown_profile"});
    epidStaticResourceLoader.getResource(EpidResource.SIGRL, EpidVersion.EPID11, EPID11_GROUP5_GID);
  }
}
