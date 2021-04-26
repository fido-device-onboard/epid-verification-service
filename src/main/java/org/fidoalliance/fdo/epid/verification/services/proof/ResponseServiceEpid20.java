// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.services.proof;

import java.io.IOException;
import org.fidoalliance.fdo.epid.verification.model.epid.EpidSignature;
import org.fidoalliance.fdo.epid.verification.model.requests.ProofRequest;
import org.fidoalliance.fdo.epid.verification.model.responses.Epid20ProofResponse;
import org.fidoalliance.fdo.epid.verification.model.responses.ProofResponse;
import org.springframework.stereotype.Service;

@Service
public class ResponseServiceEpid20 extends ResponseService {

  @Override
  public ProofResponse create(ProofRequest proofRequest) throws IOException {

    EpidSignature epidSignature = new EpidSignature(proofRequest.getEpidSignature());

    return new Epid20ProofResponse(
        proofRequest.getGroupId(), epidSignature.getPseudonym(), proofRequest.getMsg());
  }
}
