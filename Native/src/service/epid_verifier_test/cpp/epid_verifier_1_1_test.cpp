// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

#include "epid_verifier_1_1_test.h"
#include "epid_sdk_mocks.h"
#include "external_mocks.h"

TEST(Epid11Verifier, verify_signature_positive)
{
    g_behavior = BEHAVIOR_OK;

    int result = verify_signature_1_1(group5_member_sig, sizeof(group5_member_sig), group5_pubkey, TEST_MESSAGE, sizeof(TEST_MESSAGE)-1, group5_sig_rl,
    								sizeof(group5_sig_rl), group5_priv_rl, sizeof(group5_priv_rl), NULL, 0);

    ASSERT_EQ(SIG_OK, result);

    ASSERT_EQ(group5_pubkey, (byte_t *)g_epid11_pub_key);
    ASSERT_EQ(NULL, g_epid11_precomp);
    ASSERT_EQ(group5_sig_rl, (byte_t *)g_epid11_sig_rl);
    ASSERT_EQ(sizeof(group5_sig_rl), g_epid11_sig_rl_size);
    ASSERT_EQ(group5_member_sig, (byte_t *)g_epid11_sig);
    ASSERT_EQ(sizeof(group5_member_sig), g_epid11_sig_len);
    ASSERT_EQ(TEST_MESSAGE, (byte_t *)g_epid11_msg);
    ASSERT_EQ(sizeof(TEST_MESSAGE) - 1, g_epid11_msg_len);
    ASSERT_EQ(NULL, g_epid11_basename);
    ASSERT_EQ(0, g_epid11_basename_len);
    ASSERT_EQ(group5_priv_rl, (byte_t *)g_epid11_priv_rl);
    ASSERT_EQ(sizeof(group5_priv_rl), g_epid11_priv_rl_size);
}

TEST(Epid11Verifier, verify_signature_sig_invalid)
{
    g_behavior = BEHAVIOR_SIG_INVALID;

    byte_t invalid_sig[sizeof(group5_member_sig)];
    memcpy(invalid_sig, group5_member_sig, sizeof(group5_member_sig));
    invalid_sig[0] = 0x42;

    int result = verify_signature_1_1(invalid_sig, sizeof(invalid_sig), group5_pubkey, TEST_MESSAGE, sizeof(TEST_MESSAGE) - 1, group5_sig_rl, sizeof(group5_sig_rl),
                                  group5_priv_rl, sizeof(group5_priv_rl), NULL, 0);

    ASSERT_EQ(SIG_INVALID, result);
}

TEST(Epid11Verifier, verify_signature_sigrl_revoked)
{
    g_behavior = BEHAVIOR_SIG_REVOKED_IN_SIGRL;

    int result = verify_signature_1_1(group5_sig_revoked, sizeof(group5_sig_revoked), group5_pubkey, TEST_MESSAGE, sizeof(TEST_MESSAGE) - 1,
                                  group5_sig_rl, sizeof(group5_sig_rl), group5_priv_rl, sizeof(group5_priv_rl), NULL, 0);

    ASSERT_EQ(SIG_REVOKED_IN_SIGRL, result);
}

TEST(Epid11Verifier, verify_verifier_create_fail)
{
    g_behavior = BEHAVIOR_VERIFIER_CREATE_FAIL;

    int result = verify_signature_1_1(group5_member_sig, sizeof(group5_member_sig), group5_pubkey, TEST_MESSAGE, sizeof(TEST_MESSAGE)-1, group5_sig_rl,
        								sizeof(group5_sig_rl), group5_priv_rl, sizeof(group5_priv_rl), NULL, 0);

    ASSERT_EQ(VERIFIER_CREATE_ERROR, result);
}

TEST(Epid11Verifier, verify_verifier_set_sig_rl_fail)
{
    g_behavior = BEHAVIOR_VERIFIER_SET_SIG_RL_FAIL;

    int result = verify_signature_1_1(group5_member_sig, sizeof(group5_member_sig), group5_pubkey, TEST_MESSAGE, sizeof(TEST_MESSAGE)-1, group5_sig_rl,
        								sizeof(group5_sig_rl), group5_priv_rl, sizeof(group5_priv_rl), NULL, 0);

    ASSERT_EQ(INVALID_SIGRL_FORMAT, result);
}

TEST(Epid11Verifier, verify_verify_fail)
{
    g_behavior = BEHAVIOR_VERIFY_FAIL;

    int result = verify_signature_1_1(group5_member_sig, sizeof(group5_member_sig), group5_pubkey, TEST_MESSAGE, sizeof(TEST_MESSAGE)-1, group5_sig_rl,
        								sizeof(group5_sig_rl), group5_priv_rl, sizeof(group5_priv_rl), NULL, 0);

    ASSERT_EQ(SDK_ERR_kEpidErr, result);
}

TEST(Epid11Verifier, verify_signature_privrl_revoked)
{
    g_behavior = BEHAVIOR_SIG_REVOKED_IN_PRIVRL;

    int result = verify_signature_1_1(group5_member_sig, sizeof(group5_member_sig), group5_pubkey, TEST_MESSAGE, sizeof(TEST_MESSAGE)-1, group5_sig_rl,
        								sizeof(group5_sig_rl), group5_priv_rl, sizeof(group5_priv_rl), NULL, 0);

    ASSERT_EQ(PRIVKEY_REVOKED_IN_PRIVRL, result);
}

TEST(Epid11Verifier, verify_verifier_set_priv_rl_fail)
{
    g_behavior = BEHAVIOR_VERIFIER_SET_PRIV_RL_FAIL;

    int result = verify_signature_1_1(group5_member_sig, sizeof(group5_member_sig), group5_pubkey, TEST_MESSAGE, sizeof(TEST_MESSAGE)-1, group5_sig_rl,
        								sizeof(group5_sig_rl), group5_priv_rl, sizeof(group5_priv_rl), NULL, 0);

    ASSERT_EQ(INVALID_PRIVRL_FORMAT, result);
}
