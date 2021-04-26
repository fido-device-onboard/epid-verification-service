// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.services.proof;

import org.fidoalliance.fdo.epid.verification.model.requests.ProofRequest;
import org.fidoalliance.fdo.epid.verification.model.responses.Epid11ProofResponse;
import org.fidoalliance.fdo.epid.verification.model.responses.ProofResponse;
import org.springframework.stereotype.Service;

@Service
public class ResponseServiceEpid11 extends ResponseService {
  @Override
  public ProofResponse create(ProofRequest proofRequest) {
    return new Epid11ProofResponse(proofRequest.getGroupId(), proofRequest.getMsg());
  }
}
