// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

package org.fidoalliance.fdo.epid.verification.model;

import java.io.IOException;
import org.fidoalliance.fdo.epid.verification.exceptions.ServiceDataSerializationException;

public interface RawSerializable {

  byte[] toByteArray() throws IOException, ServiceDataSerializationException;
}
