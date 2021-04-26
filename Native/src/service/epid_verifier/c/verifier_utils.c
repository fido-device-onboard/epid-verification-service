// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

#include "verifier_utils.h"

 VerifySignatureStatus epid_status_to_return_code(int status)
{
    VerifySignatureStatus result;

    // Refer epid/common/errors.h for SDK codes
    // and verifier_utils.h (EpidStatusCustomCodes) for custom codes
    switch (status)
    {
        case 0:					//kEpidSigValid
            result = SIG_OK;
            break;
        case 1:					//kEpidSigInvalid
            result = SIG_INVALID;
            break;
        case 3:					//kEpidSigRevokedInPrivRl
            result = PRIVKEY_REVOKED_IN_PRIVRL;
            break;
        case 4:					//kEpidSigRevokedInSigRl
            result = SIG_REVOKED_IN_SIGRL;
            break;
        case CUSTOM_ERR_INVALID_PRIVRL_FORMAT:
            result = INVALID_PRIVRL_FORMAT;
            break;
        case CUSTOM_ERR_INVALID_SIGRL_FORMAT:
            result = INVALID_SIGRL_FORMAT;
            break;
        case CUSTOM_ERR_SET_BASENAME:
            result = SET_BASENAME_ERROR;
            break;
        case CUSTOM_ERR_SET_HASH_ALG:
            result = SET_HASH_ALG_ERROR;
            break;
        case CUSTOM_ERR_VERIFIER_CREATE:
            result = VERIFIER_CREATE_ERROR;
            break;

        case -999:
            result = SDK_ERR_kEpidErr;
            break;
        case -998:
            result = SDK_ERR_kEpidNotImpl;
            break;
        case -997:
            result = SDK_ERR_kEpidBadArgErr;
            break;
        case -996:
            result = SDK_ERR_kEpidNoMemErr;
            break;
        case -995:
            result = SDK_ERR_kEpidMemAllocErr;
            break;
        case -994:
            result = SDK_ERR_kEpidMathErr;
            break;
        case -993:
            result = SDK_ERR_kEpidDivByZeroErr;
            break;
        case -992:
            result = SDK_ERR_kEpidUnderflowErr;
            break;
        case -991:
            result = SDK_ERR_kEpidHashAlgorithmNotSupported;
            break;
        case -990:
            result = SDK_ERR_kEpidRandMaxIterErr;
            break;
        case -989:
            result = SDK_ERR_kEpidDuplicateErr;
            break;
        case -988:
            result = SDK_ERR_kEpidInconsistentBasenameSetErr;
            break;
        case -987:
            result = SDK_ERR_kEpidMathQuadraticNonResidueError;
            break;

        default:
            result = GENERIC_ERROR;
            break;
    }

    return result;
}
