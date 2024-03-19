// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.fidoalliance.fdo.epid.verification.enums.EpidResource;
import org.fidoalliance.fdo.epid.verification.enums.EpidVersion;
import org.fidoalliance.fdo.epid.verification.exceptions.GenericVerificationException;
import org.fidoalliance.fdo.epid.verification.exceptions.InvalidSignatureException;
import org.fidoalliance.fdo.epid.verification.exceptions.SigrlVersionSignatureVersionMismatchException;
import org.fidoalliance.fdo.epid.verification.logging.MethodExecutionLogged;
import org.fidoalliance.fdo.epid.verification.model.requests.ProofRequest;
import org.fidoalliance.fdo.epid.verification.model.responses.Epid20ProofResponse;
import org.fidoalliance.fdo.epid.verification.model.responses.ProofResponse;
import org.fidoalliance.fdo.epid.verification.services.proof.RequestVerifierService;
import org.fidoalliance.fdo.epid.verification.services.proof.ResponseService;
import org.fidoalliance.fdo.epid.verification.services.proof.ServiceBeansFactory;
import org.fidoalliance.fdo.epid.verification.services.staticresources.EpidStaticResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/v1/")
public class ProofController {

  @Autowired private ServiceBeansFactory serviceBeansFactory;

  @Autowired private EpidStaticResourceLoader epidStaticResourceLoader;

  /**
   * Returns EPID 20 proof request response.
   *
   * @param streamInput requestbyte array
   * @return EPID 20 proof request response
   * @throws IOException if an I/O error occurs
   * @throws InvalidSignatureException is thrown when the signature supplied by device to server for
   *     verification, is invalid
   * @throws SigrlVersionSignatureVersionMismatchException is thrown when the version mismatches in
   *     sigrl and signature
   * @throws GenericVerificationException for handling generic exceptions during signature
   *     verification
   */
  @PostMapping(
      value = "epid20/proof",
      consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE,
      produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  @MethodExecutionLogged
  public ResponseEntity<byte[]> createProofEpid20(@RequestBody byte[] streamInput)
      throws IOException, InvalidSignatureException, SigrlVersionSignatureVersionMismatchException,
          GenericVerificationException {
    Epid20ProofResponse response =
        (Epid20ProofResponse) handleByteRequest(EpidVersion.EPID20, streamInput);
    return new ResponseEntity<>(response.toByteArray(), HttpStatus.OK);
  }

  @PostMapping(
      value = "epid20/proof",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @MethodExecutionLogged
  public ResponseEntity<ProofResponse> createProofEpid20(
      @RequestBody ProofRequest proofRequest)
      throws IOException, InvalidSignatureException, SigrlVersionSignatureVersionMismatchException,
          GenericVerificationException {
    ProofResponse proofResponse = handleJsonRequest(EpidVersion.EPID20, proofRequest);
    return new ResponseEntity<>(proofResponse, HttpStatus.OK);
  }

  @PostMapping(
      value = "epid11/proof",
      consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE,
      produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  @MethodExecutionLogged
  public ResponseEntity<byte[]> createProofEpid11(@RequestBody byte[] streamInput)
      throws IOException, InvalidSignatureException, SigrlVersionSignatureVersionMismatchException,
          GenericVerificationException {
    ProofResponse response = handleByteRequest(EpidVersion.EPID11, streamInput);
    return new ResponseEntity<>(response.toByteArray(), HttpStatus.OK);
  }

  @PostMapping(
      value = "epid11/proof",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @MethodExecutionLogged
  public ResponseEntity<ProofResponse> createProofEpid11(
      @RequestBody ProofRequest proofRequest)
      throws IOException, InvalidSignatureException, SigrlVersionSignatureVersionMismatchException,
          GenericVerificationException {
    ProofResponse proofResponse = handleJsonRequest(EpidVersion.EPID11, proofRequest);
    return new ResponseEntity<>(proofResponse, HttpStatus.OK);
  }

  private ProofResponse handleByteRequest(EpidVersion epidVersion, byte[] streamInput)
      throws IOException, InvalidSignatureException, SigrlVersionSignatureVersionMismatchException,
          GenericVerificationException {
    ProofRequest proofRequest = new ProofRequest(streamInput, epidVersion);
    RequestVerifierService requestVerifierService =
        serviceBeansFactory.getRequestVerifierService(epidVersion);

    requestVerifierService.verifyMessage(proofRequest.getMsg());
    try {
      requestVerifierService.verifyEpidSignature(
          proofRequest.getEpidSignature(),
          proofRequest.getMsg(),
          epidStaticResourceLoader.getResource(
              EpidResource.PUBKEY, epidVersion, proofRequest.getGroupId()),
          epidStaticResourceLoader.getResource(
              EpidResource.SIGRL, epidVersion, proofRequest.getGroupId()),
          epidStaticResourceLoader.getResource(
              EpidResource.PRIVRL, epidVersion, proofRequest.getGroupId()),
          proofRequest.getBasename());
    } catch (FileNotFoundException e) {
      throw new GenericVerificationException(
          "Requested "
              + epidVersion.toLowerCaseString()
              + " group ("
              + DatatypeConverter.printHexBinary(proofRequest.getGroupId())
              + ") does not exist");
    }

    ResponseService responseService = serviceBeansFactory.getResponseService(epidVersion);

    return responseService.create(proofRequest);
  }

  private ProofResponse handleJsonRequest(EpidVersion epidVersion, ProofRequest proofRequest)
      throws IOException, InvalidSignatureException, SigrlVersionSignatureVersionMismatchException,
          GenericVerificationException {
    RequestVerifierService requestVerifierService =
        serviceBeansFactory.getRequestVerifierService(epidVersion);
    requestVerifierService.verifyMessage(proofRequest.getMsg());

    requestVerifierService.verifyEpidSignature(
        proofRequest.getEpidSignature(),
        proofRequest.getMsg(),
        epidStaticResourceLoader.getResource(
            EpidResource.PUBKEY, epidVersion, proofRequest.getGroupId()),
        epidStaticResourceLoader.getResource(
            EpidResource.SIGRL, epidVersion, proofRequest.getGroupId()),
        epidStaticResourceLoader.getResource(
            EpidResource.PRIVRL, epidVersion, proofRequest.getGroupId()),
        proofRequest.getBasename());

    ResponseService responseService = serviceBeansFactory.getResponseService(epidVersion);

    return responseService.create(proofRequest);
  }
}
