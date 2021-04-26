// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.services.proof;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.fidoalliance.fdo.epid.verification.model.requests.ProofRequest;
import org.fidoalliance.fdo.epid.verification.model.responses.ProofResponse;

@Slf4j
public abstract class ResponseService {

  public abstract ProofResponse create(ProofRequest proofRequestEpid20) throws IOException;
}
