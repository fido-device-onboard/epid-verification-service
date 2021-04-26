// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.model.epid;

import java.io.IOException;
import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;
import org.fidoalliance.fdo.epid.verification.utils.ByteConversionUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EpidSignatureTest {

  private static final byte[] TEST_SIGNATURE =
      DatatypeConverter.parseHexBinary(
          "c2f41e50d4047b983bb401d76d4fc5bd5588dd551807896192d5291c1b38654d6055691cefdebc9b520"
              + "6b6462285dedea9ccc32dd742c538dcdfd95e372fae8d0e19af8568cee25917258545723f5c"
              + "8ec39b10e04c7e2365197f1fe44cf8304e97c1ce19a48d706e70a321bf1c1b90efdb5c83913"
              + "b9d95ab0e1b8b26138c9bd79190ac7c979136967170936bb78ad5d2a86b864eb09703e00e56"
              + "3261f6f856da2ba4f4be0f4f04d5c5bacf5b654e3194ca6afbfd14d0e77e5c991df111c08e7"
              + "4a91e19c0698d6747a12f108d5ec6ca5ef69ecc17dc712a42f51c2db41d2a2d571b56c7bfaf"
              + "53a79eb7f6c32ec12eec0b539e77ab24749164b181cdc769d6b416fa5e9c8b091443f088f1b"
              + "60aeca092f461a611e4d95523a7296bee9d297ffb2c78786ad2ad206901566b9ccb9e1f1e05"
              + "35ee498ab32fd5b5b26cac12038f273fc5f4e1f08a215696a27a316960b2143d1cd46674ecd"
              + "7de5a0c9b323ab4a7ba8e0000000300000003e87a78272128542385a806e4fc2cd35d59f394"
              + "ddf1651782186b8f57408d25fcf1241a190472371323675b6d4565c93bd859d2e2f637025b4"
              + "04a3872e0adfc3ade24e54ee0622daaca6e8459b2038d52aa93811e2e3c5034487c90ad4931"
              + "e9c1dbff602d274bfe027d5873c871eec4f105ff9ef62d214ffdc552e77e96fb13a21c6e4ad"
              + "8ae8411dc75f1a6675ac2cc227dc45b4f95bac4e855848a68467565e98828879ab6fdbff331"
              + "224d5471d2315585906bd53d52259a7610dddbccc861e34683f99008a5a6c26fad6acffaaea"
              + "039f8cf96254cf0d524d79c7ca854b82e2512f51c74ea76921603d2fe3c3b3b6dc401126522"
              + "d88c4640cc6e4b05058170d7a197d195ba78d26e7ebc4181c0215e9383f381319f8b38a509a"
              + "c83d6b44fb3ba224ca2d0b704492368fb1bf0eab441b5fc18be0ef57b2adb368db263eff25a"
              + "b505c9f4dfe5af62037dfe08c0ffef7c1342977a26fdae979168fcdb962a9ad4e40be21d23d"
              + "98c73c4e25d8283f66c1d4d3527c0a0b396c1561b0b89b91ebf65a8377a70f9d1dacd0cdfbb"
              + "44838178a3c7f89ea3fcdc91ccf5712023c2382d2ff7d5b2377c14e719da37eff31cc86ff5d"
              + "2f9808db6fb607306d6664f1ab4e8eb2856e7fe493ac36bdb17e833c7c7d673f96c8675c58e"
              + "c984ab5b197d12020540ad");
  private static final byte[] TEST_B =
      DatatypeConverter.parseHexBinary(
          "c2f41e50d4047b983bb401d76d4fc5bd5588dd551807896192d5291c1b38654d60"
              + "55691cefdebc9b5206b6462285dedea9ccc32dd742c538dcdfd95e372fae8d");
  private static final byte[] TEST_K =
      DatatypeConverter.parseHexBinary(
          "0e19af8568cee25917258545723f5c8ec39b10e04c7e2365197f1fe44cf8304e97"
              + "c1ce19a48d706e70a321bf1c1b90efdb5c83913b9d95ab0e1b8b26138c9bd7");
  private static final byte[] TEST_T =
      DatatypeConverter.parseHexBinary(
          "9190ac7c979136967170936bb78ad5d2a86b864eb09703e00e563261f6f856da2b"
              + "a4f4be0f4f04d5c5bacf5b654e3194ca6afbfd14d0e77e5c991df111c08e74");
  private static final byte[] TEST_C =
      DatatypeConverter.parseHexBinary(
          "a91e19c0698d6747a12f108d5ec6ca5ef69ecc17dc712a42f51c2db41d2a2d57");
  private static final byte[] TEST_SX =
      DatatypeConverter.parseHexBinary(
          "1b56c7bfaf53a79eb7f6c32ec12eec0b539e77ab24749164b181cdc769d6b416");
  private static final byte[] TEST_SF =
      DatatypeConverter.parseHexBinary(
          "fa5e9c8b091443f088f1b60aeca092f461a611e4d95523a7296bee9d297ffb2c");
  private static final byte[] TEST_SA =
      DatatypeConverter.parseHexBinary(
          "78786ad2ad206901566b9ccb9e1f1e0535ee498ab32fd5b5b26cac12038f273f");
  private static final byte[] TEST_SB =
      DatatypeConverter.parseHexBinary(
          "c5f4e1f08a215696a27a316960b2143d1cd46674ecd7de5a0c9b323ab4a7ba8e");
  private static final int TEST_SIGRL_VERSION = 3;
  private static final int TEST_SIGRL_ENTRIES = 3;

  @Test
  public void testEpidSignatureConstruction() throws Exception {
    EpidSignature epidSignature = new EpidSignature(TEST_SIGNATURE);

    Assert.assertEquals(epidSignature.getG1B(), TEST_B);
    Assert.assertEquals(
        epidSignature.getPseudonym(), ByteConversionUtils.concatByteArrays(TEST_B, TEST_K));
    Assert.assertEquals(epidSignature.getG1K(), TEST_K);
    Assert.assertEquals(epidSignature.getG1T(), TEST_T);
    Assert.assertEquals(epidSignature.getG1C(), TEST_C);
    Assert.assertEquals(epidSignature.getSx(), TEST_SX);
    Assert.assertEquals(epidSignature.getSf(), TEST_SF);
    Assert.assertEquals(epidSignature.getSa(), TEST_SA);
    Assert.assertEquals(epidSignature.getSb(), TEST_SB);
    Assert.assertEquals(epidSignature.getSigrlVersion(), TEST_SIGRL_VERSION);
    Assert.assertEquals(epidSignature.getSigrlEntries(), TEST_SIGRL_ENTRIES);
  }

  @Test(expectedExceptions = IOException.class)
  public void testEpidSignatureTooShort() throws Exception {
    new EpidSignature(Arrays.copyOf(TEST_SIGNATURE, 50));
  }
}
