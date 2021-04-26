// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

#include <epid/verifier/api.h>
#include "epid_verifier.h"
#include "verifier_utils.h"

EpidStatus verify(EpidSignature const *sig, size_t sig_len, GroupPubKey const *pubkey, void const *msg, size_t msg_len, SigRl const *sig_rl, size_t sig_rl_size,
                  PrivRl const *priv_rl, size_t priv_rl_size, byte_t *basename, size_t basename_size)
{
    EpidStatus result = kEpidErr;
    VerifierCtx *ctx = NULL;

    if (basename_size == 0 && basename)
            basename = NULL;

    result = EpidVerifierCreate(pubkey, NULL, &ctx);
    if (kEpidNoErr != result) {
        result = CUSTOM_ERR_VERIFIER_CREATE;
        goto VERIFY_CLEANUP;
    }

    result = EpidVerifierSetBasename(ctx, basename, basename_size);
	if (kEpidNoErr != result) {
        result = CUSTOM_ERR_SET_BASENAME;
        goto VERIFY_CLEANUP;
    }

    result = EpidVerifierSetPrivRl(ctx, priv_rl, priv_rl_size);
    if (kEpidNoErr != result) {
        result = CUSTOM_ERR_INVALID_PRIVRL_FORMAT;
        goto VERIFY_CLEANUP;
    }

    result = EpidVerifierSetSigRl(ctx, sig_rl, sig_rl_size);
    if (kEpidNoErr != result) {
        result = CUSTOM_ERR_INVALID_SIGRL_FORMAT;
        goto VERIFY_CLEANUP;
    }

    result = EpidVerify(ctx, sig, sig_len, msg, msg_len);

VERIFY_CLEANUP:
    EpidVerifierDelete(&ctx);

    return result;
}

VerifySignatureStatus verify_signature(byte_t *signature, size_t signature_size, byte_t *pubkey, byte_t *msg, size_t msg_size, byte_t *sig_rl,
                                       size_t sig_rl_size, byte_t *priv_rl, size_t priv_rl_size, byte_t *basename, size_t basename_size)
{
    // Cast operation performed internally to make EPID SDK types opaque for clients
    EpidStatus status = verify((EpidSignature *)signature, signature_size, (GroupPubKey *)pubkey, msg, msg_size, (SigRl *)sig_rl, sig_rl_size,
                               (PrivRl *)priv_rl, priv_rl_size, basename, basename_size);

    return epid_status_to_return_code(status);
}
