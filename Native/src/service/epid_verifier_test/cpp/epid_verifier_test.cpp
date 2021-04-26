// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

#include "epid_verifier_test.h"
#include "epid_sdk_mocks.h"
#include "external_mocks.h"

TEST(EpidVerifier, verify_signature_positive)
{
    g_behavior = BEHAVIOR_OK;

    int result = verify_signature(groupa_member0_sig, sizeof(groupa_member0_sig), groupa_pubkey, TEST_MESSAGE, sizeof(TEST_MESSAGE) - 1, groupa_sig_rl,
                                  sizeof(groupa_sig_rl), groupa_priv_rl, sizeof(groupa_priv_rl), TEST_BASENAME, sizeof(TEST_BASENAME) - 1);

    ASSERT_EQ(SIG_OK, result);

    ASSERT_EQ(groupa_pubkey, (byte_t *)g_pub_key);
    ASSERT_EQ(NULL, g_precomp);
    ASSERT_EQ(kSha256, g_hash_alg);
    ASSERT_EQ(groupa_sig_rl, (byte_t *)g_sig_rl);
    ASSERT_EQ(sizeof(groupa_sig_rl), g_sig_rl_size);
    ASSERT_EQ(groupa_member0_sig, (byte_t *)g_sig);
    ASSERT_EQ(sizeof(groupa_member0_sig), g_sig_len);
    ASSERT_EQ(TEST_MESSAGE, (byte_t *)g_msg);
    ASSERT_EQ(sizeof(TEST_MESSAGE) - 1, g_msg_len);
    ASSERT_EQ(TEST_BASENAME, g_basename);
    ASSERT_EQ(sizeof(TEST_BASENAME) - 1, g_basename_len);
    ASSERT_EQ(groupa_priv_rl, (byte_t *)g_priv_rl);
    ASSERT_EQ(sizeof(groupa_priv_rl), g_priv_rl_size);
}

TEST(EpidVerifier, verify_signature_sig_invalid)
{
    g_behavior = BEHAVIOR_SIG_INVALID;

    byte_t invalid_sig[sizeof(groupa_member0_sig)];
    memcpy(invalid_sig, groupa_member0_sig, sizeof(groupa_member0_sig));
    invalid_sig[0] = 0x42;

    int result = verify_signature(invalid_sig, sizeof(invalid_sig), groupa_pubkey, TEST_MESSAGE, sizeof(TEST_MESSAGE) - 1, groupa_sig_rl, sizeof(groupa_sig_rl),
                                  groupa_priv_rl, sizeof(groupa_priv_rl), TEST_BASENAME, sizeof(TEST_BASENAME) - 1);

    ASSERT_EQ(SIG_INVALID, result);
}

TEST(EpidVerifier, verify_signature_sigrl_revoked)
{
    g_behavior = BEHAVIOR_SIG_REVOKED_IN_SIGRL;

    int result = verify_signature(groupa_sigrevokedmember0_sig, sizeof(groupa_sigrevokedmember0_sig), groupa_pubkey, TEST_MESSAGE, sizeof(TEST_MESSAGE) - 1,
                                  groupa_sig_rl, sizeof(groupa_sig_rl), groupa_priv_rl, sizeof(groupa_priv_rl), TEST_BASENAME, sizeof(TEST_BASENAME) - 1);

    ASSERT_EQ(SIG_REVOKED_IN_SIGRL, result);
}

TEST(EpidVerifier, verify_verifier_create_fail)
{
    g_behavior = BEHAVIOR_VERIFIER_CREATE_FAIL;

    int result = verify_signature(groupa_member0_sig, sizeof(groupa_member0_sig), groupa_pubkey, TEST_MESSAGE, sizeof(TEST_MESSAGE) - 1, groupa_sig_rl,
                                  sizeof(groupa_sig_rl), groupa_priv_rl, sizeof(groupa_priv_rl), TEST_BASENAME, sizeof(TEST_BASENAME) - 1);

    ASSERT_EQ(VERIFIER_CREATE_ERROR, result);
}


TEST(EpidVerifier, verify_verifier_set_sig_rl_fail)
{
    g_behavior = BEHAVIOR_VERIFIER_SET_SIG_RL_FAIL;

    int result = verify_signature(groupa_member0_sig, sizeof(groupa_member0_sig), groupa_pubkey, TEST_MESSAGE, sizeof(TEST_MESSAGE) - 1, groupa_sig_rl,
                                  sizeof(groupa_sig_rl), groupa_priv_rl, sizeof(groupa_priv_rl), TEST_BASENAME, sizeof(TEST_BASENAME) - 1);

    ASSERT_EQ(INVALID_SIGRL_FORMAT, result);
}

TEST(EpidVerifier, verify_verify_fail)
{
    g_behavior = BEHAVIOR_VERIFY_FAIL;

    int result = verify_signature(groupa_member0_sig, sizeof(groupa_member0_sig), groupa_pubkey, TEST_MESSAGE, sizeof(TEST_MESSAGE) - 1, groupa_sig_rl,
                                  sizeof(groupa_sig_rl), groupa_priv_rl, sizeof(groupa_priv_rl), TEST_BASENAME, sizeof(TEST_BASENAME) - 1);

    ASSERT_EQ(SDK_ERR_kEpidErr, result);
}

TEST(EpidVerifier, verify_signature_privrl_revoked)
{
    g_behavior = BEHAVIOR_SIG_REVOKED_IN_PRIVRL;

    int result = verify_signature(groupa_privrevokedmember0_sig, sizeof(groupa_privrevokedmember0_sig), groupa_pubkey, TEST_MESSAGE, sizeof(TEST_MESSAGE) - 1,
                                  groupa_sig_rl, sizeof(groupa_sig_rl), groupa_priv_rl, sizeof(groupa_priv_rl), TEST_BASENAME, sizeof(TEST_BASENAME) - 1);

    ASSERT_EQ(PRIVKEY_REVOKED_IN_PRIVRL, result);
}

TEST(EpidVerifier, verify_verifier_set_priv_rl_fail)
{
    g_behavior = BEHAVIOR_VERIFIER_SET_PRIV_RL_FAIL;

    int result = verify_signature(groupa_privrevokedmember0_sig, sizeof(groupa_privrevokedmember0_sig), groupa_pubkey, TEST_MESSAGE, sizeof(TEST_MESSAGE) - 1,
                                  groupa_sig_rl, sizeof(groupa_sig_rl), groupa_priv_rl, sizeof(groupa_priv_rl), TEST_BASENAME, sizeof(TEST_BASENAME) - 1);

    ASSERT_EQ(INVALID_PRIVRL_FORMAT, result);
}
