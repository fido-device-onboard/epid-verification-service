// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

#include <epid/verifier/api.h>
#include <epid/verifier/1.1/api.h>

extern GroupPubKey *g_pub_key;
extern VerifierPrecomp *g_precomp;
extern Epid11GroupPubKey *g_epid11_pub_key;
extern Epid11VerifierPrecomp *g_epid11_precomp;
extern HashAlg g_hash_alg;
extern SigRl *g_sig_rl;
extern size_t g_sig_rl_size;
extern Epid11SigRl *g_epid11_sig_rl;
extern size_t g_epid11_sig_rl_size;
extern EpidSignature *g_sig;
extern size_t g_sig_len;
extern void *g_msg;
extern size_t g_msg_len;
extern Epid11Signature *g_epid11_sig;
extern size_t g_epid11_sig_len;
extern void *g_epid11_msg;
extern size_t g_epid11_msg_len;
extern void *g_basename;
extern size_t g_basename_len;
extern void *g_epid11_basename;
extern size_t g_epid11_basename_len;
extern PrivRl *g_priv_rl;
extern size_t g_priv_rl_size;
extern Epid11PrivRl *g_epid11_priv_rl;
extern size_t g_epid11_priv_rl_size;
