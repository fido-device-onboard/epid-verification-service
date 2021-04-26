# Copyright 2021 Intel Corporation
# SPDX-License-Identifier: Apache 2.0

execute_process(COMMAND swig -java -package org.fidoalliance.fdo.epid.verification.generated ${CMAKE_CURRENT_SOURCE_DIR}/epid_verifier.i
                RESULT_VARIABLE swig_result
                OUTPUT_QUIET)

if(NOT swig_result EQUAL 0)
    message(FATAL_ERROR "Failed to run swig")
endif()

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wno-pointer-sign")

if(DEFINED ENV{JAVA_HOME})
    set(java_home $ENV{JAVA_HOME})
else()
    set(java_home /usr/lib/jvm/java-8-openjdk-amd64)
endif()

include_directories(${java_home}/include)
include_directories(${java_home}/include/linux)
include_directories(${base_path}/epid_verifier/headers)

file(GLOB_RECURSE epid_verifier_wrap_source_files *.c)

add_library(epid_verifier_wrap SHARED ${epid_verifier_wrap_source_files})

target_link_libraries(epid_verifier_wrap epid_verifier)
