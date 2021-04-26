// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

#ifndef EPIDVERIFIER_EPID_VERIFIER_H
#define EPIDVERIFIER_EPID_VERIFIER_H

#ifdef __cplusplus
extern "C" {
#endif

#include "verifier_utils.h"
#include <stddef.h>

typedef unsigned char byte_t;

VerifySignatureStatus verify_signature(byte_t *signature, size_t signature_size, byte_t *pubkey, byte_t *msg, size_t msg_size, byte_t *sig_rl,
                                       size_t sig_rl_size, byte_t *priv_rl, size_t priv_rl_size, byte_t *basename, size_t basename_size);

#ifdef __cplusplus
}
#endif

#endif // EPIDVERIFIER_EPID_VERIFIER_H
