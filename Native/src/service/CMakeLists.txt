# Copyright 2021 Intel Corporation
# SPDX-License-Identifier: Apache 2.0

cmake_minimum_required(VERSION 2.8)
project(EPIDServicesNative)

enable_testing()
set(base_path ${CMAKE_SOURCE_DIR})
set(arch "x64")

if(DEFINED ENV{BUILD_TYPE})
    set(CMAKE_BUILD_TYPE $ENV{BUILD_TYPE})
endif(DEFINED ENV{BUILD_TYPE})   

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wextra -Wall -Werror -DWITH_POSIX -fstack-protector -fPIE -fPIC -D_FORTIFY_SOURCE=2 -Wformat -Wformat-security")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wno-write-strings")
set(SECURITY_LINKER_FLAGS "-Wl,-z,noexecstack -Wl,-z,relro -Wl,-z,now")

include(../../dependency_resolver.txt)

set(CMAKE_SKIP_BUILD_RPATH TRUE)

add_subdirectory(epid_verifier)
add_subdirectory(epid_verifier_test)
add_subdirectory(epid_verifier_wrap)
