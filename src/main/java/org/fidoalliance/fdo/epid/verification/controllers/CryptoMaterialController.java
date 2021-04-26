// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import lombok.extern.slf4j.Slf4j;
import org.fidoalliance.fdo.epid.verification.logging.MethodExecutionLogged;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("v1")
public class CryptoMaterialController {

  @Value("${crypto-material.tarball.path}")
  private String cryptomaterialTarballPath;

  @Value("${crypto-material.tarball.sig.path}")
  private String cryptomaterialTarballSigPath;

  private static final String CRYPTOMATERIAL_TAR_GZ_NAME = "cryptoMaterial.tgz";
  private static final String CRYPTOMATERIAL_TAR_GZ_SIG_NAME = "cryptoMaterial.tgz.sig";

  public CryptoMaterialController() {
  }

  /**
   * REST interface that returns cryptoMaterial.tgz file.
   *
   * @return requested cryptomaterial
   */
  @GetMapping(
      value = "/epid/cryptomaterials/download",
      produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  @MethodExecutionLogged
  public Callable<ResponseEntity<ByteArrayResource>> getCryptoMaterial() throws IOException {
    return () -> {
      try {
        final ByteArrayResource cryptoMaterial =
            new ByteArrayResource(Files.readAllBytes(Paths.get(cryptomaterialTarballPath)));
        final ResponseEntity<ByteArrayResource> responseEntity =
            ResponseEntity.ok()
                .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + CRYPTOMATERIAL_TAR_GZ_NAME + "\"")
                .body(cryptoMaterial);
        return responseEntity;
      } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    };
  }

  /**
   * REST interface that returns cryptoMaterial.tgz.sig file.
   *
   * @return Cryptomaterial signature
   */
  @GetMapping(
      value = "/epid/cryptomaterials/signature",
      produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  @MethodExecutionLogged
  public Callable<ResponseEntity<ByteArrayResource>> getCryptoMaterialSig() throws IOException {
    return () -> {
      try {
        final ByteArrayResource cryptoMaterial =
            new ByteArrayResource(Files.readAllBytes(Paths.get(cryptomaterialTarballSigPath)));
        final ResponseEntity<ByteArrayResource> responseEntity =
            ResponseEntity.ok()
                .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + CRYPTOMATERIAL_TAR_GZ_SIG_NAME + "\"")
                .body(cryptoMaterial);
        return responseEntity;
      } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    };
  }
}
