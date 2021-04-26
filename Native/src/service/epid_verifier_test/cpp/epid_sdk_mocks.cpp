// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

#include "external_mocks.h"
#include "epid_sdk_mocks.h"

int g_behavior;

GroupPubKey *g_pub_key;
VerifierPrecomp *g_precomp;
Epid11GroupPubKey *g_epid11_pub_key;
Epid11VerifierPrecomp *g_epid11_precomp;
HashAlg g_hash_alg;
SigRl *g_sig_rl;
size_t g_sig_rl_size;
Epid11SigRl *g_epid11_sig_rl;
size_t g_epid11_sig_rl_size;
EpidSignature *g_sig;
size_t g_sig_len;
void *g_msg;
size_t g_msg_len;
Epid11Signature *g_epid11_sig;
size_t g_epid11_sig_len;
void *g_epid11_msg;
size_t g_epid11_msg_len;
void *g_basename;
size_t g_basename_len;
void *g_epid11_basename;
size_t g_epid11_basename_len;
PrivRl *g_priv_rl;
size_t g_priv_rl_size;
Epid11PrivRl *g_epid11_priv_rl;
size_t g_epid11_priv_rl_size;

CFUNC EpidStatus __wrap_EpidVerifierCreate(GroupPubKey *pub_key, VerifierPrecomp *precomp, VerifierCtx **ctx)
{
    g_pub_key = pub_key;
    g_precomp = precomp;

    if (g_behavior == BEHAVIOR_VERIFIER_CREATE_FAIL)
        return kEpidErr;

    return kEpidNoErr;
}

CFUNC EpidStatus __wrap_Epid11VerifierCreate(Epid11GroupPubKey *pub_key, Epid11VerifierPrecomp *precomp, Epid11VerifierCtx **ctx)
{
    g_epid11_pub_key = pub_key;
    g_epid11_precomp = precomp;

    if (g_behavior == BEHAVIOR_VERIFIER_CREATE_FAIL)
        return kEpidErr;

    return kEpidNoErr;
}

CFUNC EpidStatus __wrap_EpidVerifierSetSigRl(VerifierCtx *ctx, SigRl *sig_rl, size_t sig_rl_size)
{
    g_sig_rl = sig_rl;
    g_sig_rl_size = sig_rl_size;

    if (g_behavior == BEHAVIOR_VERIFIER_SET_SIG_RL_FAIL)
        return kEpidErr;

    return kEpidNoErr;
}

CFUNC EpidStatus __wrap_Epid11VerifierSetSigRl(Epid11VerifierCtx *ctx, Epid11SigRl *sig_rl, size_t sig_rl_size)
{
    g_epid11_sig_rl = sig_rl;
    g_epid11_sig_rl_size = sig_rl_size;

    if (g_behavior == BEHAVIOR_VERIFIER_SET_SIG_RL_FAIL)
        return kEpidErr;

    return kEpidNoErr;
}

CFUNC EpidStatus __wrap_EpidVerify(VerifierCtx *ctx, EpidSignature *sig, size_t sig_len, void *msg, size_t msg_len)
{
    g_sig = sig;
    g_sig_len = sig_len;
    g_msg = msg;
    g_msg_len = msg_len;

    switch (g_behavior)
    {
    case BEHAVIOR_SIG_INVALID:
        return kEpidSigInvalid;
    case BEHAVIOR_SIG_REVOKED_IN_SIGRL:
        return kEpidSigRevokedInSigRl;
    case BEHAVIOR_SIG_REVOKED_IN_PRIVRL:
        return kEpidSigRevokedInPrivRl;
    case BEHAVIOR_VERIFY_FAIL:
        return kEpidErr;
    }

    return kEpidSigValid;
}

CFUNC EpidStatus __wrap_Epid11Verify(Epid11VerifierCtx *ctx, Epid11Signature *sig, size_t sig_len, void *msg, size_t msg_len)
{
    g_epid11_sig = sig;
    g_epid11_sig_len = sig_len;
    g_epid11_msg = msg;
    g_epid11_msg_len = msg_len;

    switch (g_behavior)
    {
    case BEHAVIOR_SIG_INVALID:
        return kEpidSigInvalid;
    case BEHAVIOR_SIG_REVOKED_IN_SIGRL:
        return kEpidSigRevokedInSigRl;
    case BEHAVIOR_SIG_REVOKED_IN_PRIVRL:
        return kEpidSigRevokedInPrivRl;
    case BEHAVIOR_VERIFY_FAIL:
        return kEpidErr;
    }

    return kEpidSigValid;
}

CFUNC EpidStatus __wrap_EpidVerifierSetPrivRl(VerifierCtx *ctx, PrivRl *priv_rl, size_t priv_rl_size)
{
    g_priv_rl = priv_rl;
    g_priv_rl_size = priv_rl_size;
    if (g_behavior == BEHAVIOR_VERIFIER_SET_PRIV_RL_FAIL)
        return kEpidErr;

    return kEpidNoErr;
}

CFUNC EpidStatus __wrap_Epid11VerifierSetPrivRl(Epid11VerifierCtx *ctx, Epid11PrivRl *priv_rl, size_t priv_rl_size)
{
    g_epid11_priv_rl = priv_rl;
    g_epid11_priv_rl_size = priv_rl_size;
    if (g_behavior == BEHAVIOR_VERIFIER_SET_PRIV_RL_FAIL)
        return kEpidErr;

    return kEpidNoErr;
}

CFUNC EpidStatus __wrap_EpidVerifierSetBasename(VerifierCtx *ctx, void* basename, size_t basename_len)
{
    g_basename = basename;
    g_basename_len = basename_len;
    if (g_behavior == BEHAVIOR_VERIFIER_SET_BASENAME_FAIL)
        return kEpidErr;

    return kEpidNoErr;
}

CFUNC EpidStatus __wrap_Epid11VerifierSetBasename(Epid11VerifierCtx *ctx, void* basename, size_t basename_len)
{
    g_epid11_basename = basename;
    g_epid11_basename_len = basename_len;
    if (g_behavior == BEHAVIOR_VERIFIER_SET_BASENAME_FAIL)
        return kEpidErr;

    return kEpidNoErr;
}

