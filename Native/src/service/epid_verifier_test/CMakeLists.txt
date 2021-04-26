# Copyright 2021 Intel Corporation
# SPDX-License-Identifier: Apache 2.0

include_directories(headers)
include_directories(${base_path}/epid_verifier/headers)

file(GLOB_RECURSE epid_verifier_test_source_files cpp/*.cpp)

add_executable(epid_verifier_test ${epid_verifier_test_source_files})

target_link_libraries(epid_verifier_test epid_verifier_static)

target_link_libraries(epid_verifier_test
            "-Wl,-wrap,EpidVerifierCreate"
            "-Wl,-wrap,EpidVerifierSetHashAlg"
            "-Wl,-wrap,EpidVerifierSetSigRl"
            "-Wl,-wrap,EpidVerifierSetBasename"
            "-Wl,-wrap,EpidVerify"
            "-Wl,-wrap,EpidVerifierSetPrivRl"
            "-Wl,-wrap,Epid11VerifierCreate"
            "-Wl,-wrap,Epid11VerifierSetSigRl"
            "-Wl,-wrap,Epid11VerifierSetBasename"
            "-Wl,-wrap,Epid11Verify"
            "-Wl,-wrap,Epid11VerifierSetPrivRl")

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

add_test(NAME epid_verifier_test_suite COMMAND epid_verifier_test)

resolve_dependencies(epid_verifier_test)

target_link_libraries(epid_verifier_test pthread)
