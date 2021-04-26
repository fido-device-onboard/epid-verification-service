// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

#ifndef EPIDVERIFIER_VERIFIER_UTILS_H
#define EPIDVERIFIER_VERIFIER_UTILS_H

typedef enum
{
    SIG_OK,

    // Basic SDK errors (1 to 5)
    SIG_INVALID, // SDK 1
    SIG_REVOKED_IN_SIGRL, // SDK 3
    PRIVKEY_REVOKED_IN_PRIVRL, // SDK 4
    GENERIC_ERROR, // SDK 5

    // Errors -999 to -987 from epid SDK
    SDK_ERR_kEpidErr,
    SDK_ERR_kEpidNotImpl,
    SDK_ERR_kEpidBadArgErr,
    SDK_ERR_kEpidNoMemErr,
    SDK_ERR_kEpidMemAllocErr,
    SDK_ERR_kEpidMathErr,
    SDK_ERR_kEpidDivByZeroErr,
    SDK_ERR_kEpidUnderflowErr,
    SDK_ERR_kEpidHashAlgorithmNotSupported,
    SDK_ERR_kEpidRandMaxIterErr,
    SDK_ERR_kEpidDuplicateErr,
    SDK_ERR_kEpidInconsistentBasenameSetErr,
    SDK_ERR_kEpidMathQuadraticNonResidueError,

    //Custom errors
    INVALID_PRIVRL_FORMAT,
    INVALID_SIGRL_FORMAT,
    VERIFIER_CREATE_ERROR,
    SET_BASENAME_ERROR,
    SET_HASH_ALG_ERROR,
} VerifySignatureStatus;

// During wrapper execution, we can distinguish more errors than epid SDK. In some cases we can override value returned by SDK.
// Values used should not affect mapping of SDK<->JAVA error codes, so this proxy states are used to bring less confusion.
// Range <-101 ; -199> is safe to use at the moment of writing this code.
typedef enum {
    CUSTOM_ERR_INVALID_PRIVRL_FORMAT = -199,
    CUSTOM_ERR_INVALID_SIGRL_FORMAT,
    CUSTOM_ERR_VERIFIER_CREATE,
    CUSTOM_ERR_SET_BASENAME,
    CUSTOM_ERR_SET_HASH_ALG
} EpidStatusCustomCodes;

VerifySignatureStatus epid_status_to_return_code(int status);

#endif // EPIDVERIFIER_VERIFIER_UTILS_H
