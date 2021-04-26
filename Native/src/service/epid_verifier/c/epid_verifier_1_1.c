// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

#include <epid/verifier/1.1/api.h>
#include <stdio.h>
#include "epid_verifier_1_1.h"
#include "verifier_utils.h"

EpidStatus verify_1_1(Epid11Signature const *sig, size_t sig_len, Epid11GroupPubKey const *pubkey, void const *msg, size_t msg_len, Epid11SigRl const *sig_rl, size_t sig_rl_size,
                  Epid11PrivRl const *priv_rl, size_t priv_rl_size, byte_t *basename, size_t basename_size)
{
	EpidStatus result = kEpidErr;
    Epid11VerifierCtx *ctx = NULL;

    if (basename_size == 0 && basename) {
        basename = NULL;
    }

    result = Epid11VerifierCreate(pubkey, NULL, &ctx);
    if (kEpidNoErr != result) {
        result = CUSTOM_ERR_VERIFIER_CREATE;
        goto VERIFY_1_1_CLEANUP;
    }

    result = Epid11VerifierSetBasename(ctx, basename, basename_size);
	if (kEpidNoErr != result) {
        result = CUSTOM_ERR_SET_BASENAME;
        goto VERIFY_1_1_CLEANUP;
    }

    result = Epid11VerifierSetPrivRl(ctx, priv_rl, priv_rl_size);
    if (kEpidNoErr != result) {
        result = CUSTOM_ERR_INVALID_PRIVRL_FORMAT;
        goto VERIFY_1_1_CLEANUP;
    }

    result = Epid11VerifierSetSigRl(ctx, sig_rl, sig_rl_size);
    if (kEpidNoErr != result) {
        result = CUSTOM_ERR_INVALID_SIGRL_FORMAT;
        goto VERIFY_1_1_CLEANUP;
    }

    result = Epid11Verify(ctx, sig, sig_len, msg, msg_len);

VERIFY_1_1_CLEANUP:
    Epid11VerifierDelete(&ctx);

    return result;
}

VerifySignatureStatus verify_signature_1_1(byte_t *signature, size_t signature_size, byte_t *pubkey, byte_t *msg, size_t msg_size, byte_t *sig_rl,
                                       size_t sig_rl_size, byte_t *priv_rl, size_t priv_rl_size, byte_t *basename, size_t basename_size)
{
    // Cast operation performed internally to make EPID SDK types opaque for clients
    EpidStatus status = verify_1_1((Epid11Signature *)signature, signature_size, (Epid11GroupPubKey *)pubkey, msg, msg_size, (Epid11SigRl *)sig_rl, sig_rl_size,
                               (Epid11PrivRl *)priv_rl, priv_rl_size, basename, basename_size);

    return epid_status_to_return_code(status);
}
