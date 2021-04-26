// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.model.requests;

import java.io.IOException;
import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.fidoalliance.fdo.epid.verification.enums.EpidVersion;
import org.testng.Assert;
import org.testng.annotations.Test;

@Slf4j
public class ProofRequestEpid20Test {

  private static final String TEST_SIGNATURE =
      "c2f41e50d4047b983bb401d76d4fc5bd5588dd551807896192d5291c1b38654d6055691cefdebc9b5206b6462"
          + "285dedea9ccc32dd742c538dcdfd95e372fae8d0e19af8568cee25917258545723f5c8ec39b10e0"
          + "4c7e2365197f1fe44cf8304e97c1ce19a48d706e70a321bf1c1b90efdb5c83913b9d95ab0e1b8b2"
          + "6138c9bd79190ac7c979136967170936bb78ad5d2a86b864eb09703e00e563261f6f856da2ba4f4"
          + "be0f4f04d5c5bacf5b654e3194ca6afbfd14d0e77e5c991df111c08e74a91e19c0698d6747a12f1"
          + "08d5ec6ca5ef69ecc17dc712a42f51c2db41d2a2d571b56c7bfaf53a79eb7f6c32ec12eec0b539e"
          + "77ab24749164b181cdc769d6b416fa5e9c8b091443f088f1b60aeca092f461a611e4d95523a7296"
          + "bee9d297ffb2c78786ad2ad206901566b9ccb9e1f1e0535ee498ab32fd5b5b26cac12038f273fc5"
          + "f4e1f08a215696a27a316960b2143d1cd46674ecd7de5a0c9b323ab4a7ba8e0000000300000003e"
          + "87a78272128542385a806e4fc2cd35d59f394ddf1651782186b8f57408d25fcf1241a1904723713"
          + "23675b6d4565c93bd859d2e2f637025b404a3872e0adfc3ade24e54ee0622daaca6e8459b2038d5"
          + "2aa93811e2e3c5034487c90ad4931e9c1dbff602d274bfe027d5873c871eec4f105ff9ef62d214f"
          + "fdc552e77e96fb13a21c6e4ad8ae8411dc75f1a6675ac2cc227dc45b4f95bac4e855848a6846756"
          + "5e98828879ab6fdbff331224d5471d2315585906bd53d52259a7610dddbccc861e34683f99008a5"
          + "a6c26fad6acffaaea039f8cf96254cf0d524d79c7ca854b82e2512f51c74ea76921603d2fe3c3b3"
          + "b6dc401126522d88c4640cc6e4b05058170d7a197d195ba78d26e7ebc4181c0215e9383f381319f"
          + "8b38a509ac83d6b44fb3ba224ca2d0b704492368fb1bf0eab441b5fc18be0ef57b2adb368db263e"
          + "ff25ab505c9f4dfe5af62037dfe08c0ffef7c1342977a26fdae979168fcdb962a9ad4e40be21d23"
          + "d98c73c4e25d8283f66c1d4d3527c0a0b396c1561b0b89b91ebf65a8377a70f9d1dacd0cdfbb448"
          + "38178a3c7f89ea3fcdc91ccf5712023c2382d2ff7d5b2377c14e719da37eff31cc86ff5d2f9808d"
          + "b6fb607306d6664f1ab4e8eb2856e7fe493ac36bdb17e833c7c7d673f96c8675c58ec984ab5b197"
          + "d12020540ad";
  private static final String TEST_GROUPID = "00000000000000000000000000001234";
  private static final String TEST_MSGSIZE = "0040";
  private static final String TEST_SVCNONCE =
      "4141414141414141414141414141414141414141414141414141414141414141";
  private static final String TEST_DEVNONCE =
      "4242424242424242424242424242424242424242424242424242424242424242";
  private static final String TEST_PREAMBLE =
      TEST_GROUPID + TEST_MSGSIZE + TEST_SVCNONCE + TEST_DEVNONCE;
  private static final String TEST_EMPTY_BASENAME = "0000";
  private static final String TEST_BASENAME = "000c416e64727a656a2044756461";
  private static final byte[] TEST_FRAME_NO_BASENAME =
      DatatypeConverter.parseHexBinary(TEST_PREAMBLE + TEST_EMPTY_BASENAME + TEST_SIGNATURE);
  private static final byte[] TEST_FRAME_WITH_BASENAME =
      DatatypeConverter.parseHexBinary(TEST_PREAMBLE + TEST_BASENAME + TEST_SIGNATURE);

  @Test
  public void testConstructionWithoutBasename() throws Exception {
    ProofRequest proofRequestEpid20 = new ProofRequest(TEST_FRAME_NO_BASENAME, EpidVersion.EPID20);

    Assert.assertEquals(
        proofRequestEpid20.getGroupId(), DatatypeConverter.parseHexBinary(TEST_GROUPID));
    Assert.assertEquals(
        proofRequestEpid20.getMsg(),
        DatatypeConverter.parseHexBinary(TEST_SVCNONCE + TEST_DEVNONCE));
    Assert.assertEquals(proofRequestEpid20.getBasename(), new byte[0]);
    Assert.assertEquals(
        proofRequestEpid20.getEpidSignature(), DatatypeConverter.parseHexBinary(TEST_SIGNATURE));
  }

  @Test
  public void testConstructionWithBasename() throws Exception {
    ProofRequest proofRequestEpid20 =
        new ProofRequest(TEST_FRAME_WITH_BASENAME, EpidVersion.EPID20);

    Assert.assertEquals(
        proofRequestEpid20.getGroupId(), DatatypeConverter.parseHexBinary(TEST_GROUPID));
    Assert.assertEquals(
        proofRequestEpid20.getMsg(),
        DatatypeConverter.parseHexBinary(TEST_SVCNONCE + TEST_DEVNONCE));
    Assert.assertEquals(
        proofRequestEpid20.getBasename(),
        DatatypeConverter.parseHexBinary(TEST_BASENAME.substring(4)));
    Assert.assertEquals(
        proofRequestEpid20.getEpidSignature(), DatatypeConverter.parseHexBinary(TEST_SIGNATURE));
  }

  @Test(expectedExceptions = IOException.class)
  public void testConstructionFrameTooShort() throws Exception {
    new ProofRequest(Arrays.copyOf(TEST_FRAME_WITH_BASENAME, 30), EpidVersion.EPID20);
  }
}
