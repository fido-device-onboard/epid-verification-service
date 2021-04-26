// Copyright 2021 Intel Corporation
// SPDX-License-Identifier: Apache 2.0

%module epid_verifier
%{
    #include "epid_verifier.h"
    #include "epid_verifier_1_1.h"
%}

// Type map to pass byte[] as pointer to data and size
%typemap(in) (byte_t *bytes, size_t bytes_size) {
	$1 = JCALL2(GetByteArrayElements, jenv, $input, NULL);
	$2 = JCALL1(GetArrayLength, jenv, $input);
}

%typemap(freearg) (byte_t *bytes, size_t bytes_size) {
	JCALL3(ReleaseByteArrayElements, jenv, $input, $1, JNI_ABORT); 
}

%typemap(jtype) (byte_t *bytes, size_t bytes_size) "byte[]"
%typemap(jstype) (byte_t *bytes, size_t bytes_size) "byte[]"
%typemap(jni) (byte_t *bytes, size_t bytes_size) "jbyteArray"
%typemap(javain) (byte_t *bytes, size_t bytes_size) "$javainput"

// Type map to pass byte[] as pointer to data (read-only)
%typemap(in) (byte_t *bytes) {
	$1 = JCALL2(GetByteArrayElements, jenv, $input, NULL);
}

%typemap(freearg) (byte_t *bytes) {
	JCALL3(ReleaseByteArrayElements, jenv, $input, $1, JNI_ABORT); 
}

%typemap(jtype) (byte_t *bytes) "byte[]"
%typemap(jstype) (byte_t *bytes) "byte[]"
%typemap(jni) (byte_t *bytes) "jbyteArray"
%typemap(javain) (byte_t *bytes) "$javainput"

// Type map to pass byte[] as pointer to data (read-write)
%typemap(in) (byte_t *rw_bytes) {
	$1 = JCALL2(GetByteArrayElements, jenv, $input, NULL);
}

%typemap(freearg) (byte_t *rw_bytes) {
	JCALL3(ReleaseByteArrayElements, jenv, $input, $1, 0); 
}

%typemap(jtype) (byte_t *rw_bytes) "byte[]"
%typemap(jstype) (byte_t *rw_bytes) "byte[]"
%typemap(jni) (byte_t *rw_bytes) "jbyteArray"
%typemap(javain) (byte_t *rw_bytes) "$javainput"

// Apply type maps
%apply (byte_t *bytes, size_t bytes_size)	{ (byte_t* signature, size_t signature_size) };
%apply (byte_t *bytes)						{ byte_t* pubkey };
%apply (byte_t *bytes, size_t bytes_size)	{ (byte_t* msg, size_t msg_size) };
%apply (byte_t *bytes, size_t bytes_size)	{ (byte_t* sig_rl, size_t sig_rl_size) };
%apply (byte_t *bytes, size_t bytes_size)	{ (byte_t* priv_rl, size_t priv_rl_size) };
%apply (byte_t *bytes, size_t bytes_size)	{ (byte_t* basename, size_t basename_size) };

VerifySignatureStatus verify_signature(byte_t* signature, size_t signature_size, byte_t* pubkey, byte_t* msg, size_t msg_size,
		             	 	 	 	   byte_t* sig_rl, size_t sig_rl_size, byte_t *priv_rl, size_t priv_rl_size,
									   byte_t* basename, size_t basename_size);
									   
VerifySignatureStatus verify_signature_1_1(byte_t *signature, size_t signature_size, byte_t *pubkey, byte_t *msg, size_t msg_size, 
										   byte_t *sig_rl, size_t sig_rl_size, byte_t *priv_rl, size_t priv_rl_size, 
										   byte_t *basename, size_t basename_size);

%include "enums.swg"
%javaconst(1);
typedef enum {
    SIG_OK,
    SIG_INVALID,
    SIG_REVOKED_IN_SIGRL,
    PRIVKEY_REVOKED_IN_PRIVRL,
    GENERIC_ERROR,
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
    INVALID_PRIVRL_FORMAT,
    INVALID_SIGRL_FORMAT,
    VERIFIER_CREATE_ERROR,
    SET_BASENAME_ERROR,
    SET_HASH_ALG_ERROR,
} VerifySignatureStatus;