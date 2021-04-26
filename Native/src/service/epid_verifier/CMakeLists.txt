# Copyright 2021 Intel Corporation
# SPDX-License-Identifier: Apache 2.0

include_directories(headers)

file(GLOB_RECURSE epid_verifier_source_files c/*.c)

add_library(epid_verifier_static STATIC ${epid_verifier_source_files})
add_library(epid_verifier SHARED ${epid_verifier_source_files})

if(DEFINED ENV{BUILD_NUMBER})
    set(EPID_VERIFIER_VERSION $ENV{BUILD_NUMBER})
else()
    set(EPID_VERIFIER_VERSION "9.99.999")
endif()
message(STATUS "EPID Verifier Version: " ${EPID_VERIFIER_VERSION})

configure_file(
    "${PROJECT_SOURCE_DIR}/epid_verifier/headers/get_verifier_version.h.in"
    "${PROJECT_BINARY_DIR}/get_verifier_version.h"
)
include_directories("${PROJECT_BINARY_DIR}")

resolve_dependencies(epid_verifier_static)
resolve_dependencies(epid_verifier)

target_link_libraries(epid_verifier ${SECURITY_LINKER_FLAGS})
