## Table of Contents
1. [About](#about)
1. [System Requirements](#system-requirements)
1. [Build EPID Verification service Jar File](#build-epid-verification-service-jar-file)
1. [Generate keystores](#generate-keystores)
1. [Run EPID Verification service](#run-epid-verification-service)
    * [EPID Verification service settings](#epid-verification-service-settings)
    * [Proxy settings](#proxy-settings)
    * [Run EPID Verification service](#run-epid-verification-service)

### About
This document can be used as a quick guide to build and run the FIDO Device Onboard (FDO) EPID Verification service.
FDO EPID Verification service is a software service that assists FDO Rendezvous service and FDO Owner service to perform device signature verification for devices using EPID based device attestation.

### System Requirements

* **Ubuntu 20.04**.
* **Maven**.
* **Java 11**.
* **Curl**.
* **GCC 4.9**.
* **Cmake**.
* **Make**.
* **Swig**.
* **Unzip**.
* **Docker Engine 18.09**. (Optional)
* **Docker-compose 1.21.2**. (Optional)

### Source Layout

For the instructions in this document, <epid-verification-service> refers to the path of the EPID Verification service source folder `epid-verification-service`.

EPID Verification service source code is organized into the following sub-folders.

* `Jenkins` : It contains files for building service and running smoke test on the repo.

* `Native` : It contains JNI implementation for utilizing epid-sdk for performing EPID signature verification.

* `certs` : It contains sample key, certificate and keystore for running the service.

* `src` : It contains the springboot application for EPID Verification service.

### Build EPID Verification Service Jar File

#### Setup JAVA HOME

```
export JAVA_HOME=<path-to-java-11-jdk>
```

#### Pre-requisites for Building Dependencies

The external dependencies are built in \<epid-verification-service\>/Native/src/service/dependencies folder. This folder needs to be created if it doesn't exist.

#### Build EPID SDK

EPID Verification service uses [EPID SDK](https://github.com/Intel-EPID-SDK/epid-sdk) for performing signature verification of EPID based devices. Switch to `<epid-verification-service>/Native/src/service/dependencies` and run following commands to build EPID SDK.

***NOTE***: The latest version of EPID SDK compatible with EPID Verification service is `v7.0.1`.

```
$ git clone -b v7.0.1 https://github.com/Intel-EPID-SDK/epid-sdk
$ cd epid-sdk
$ chmod +x configure
$ ./configure
$ make all
$ make check
$ make install
```

#### Build Google Test

EPID Verification service uses [Google Test framework](https://github.com/google/googletest/) for performing sanity test of EPID JNI framework. Switch to `\<epid-verification-service\>/Native/src/service/dependencies` and run following commands to build Google Test framework.

***NOTE***: The latest version of Google Test framework compatible with epid-verification-service is `release-1.7.0`.
```
$ git clone -b release-1.7.0 https://github.com/google/googletest
$ cd googletest/make
$ make
```

#### Build EPID Verification Service

To build EPID verification service, execute the following command.

```
$ mvn install
```

***NOTE*** Maven build triggers the build script for JNI Native library.

To build only the JNI Native library, execute the following command.
```
$ cd <epid-verification-service>/Native
$ ./build.sh
```

To clean files generated by previous build, execute the following command.

```
$ mvn clean
```

To clean the files generated in previous build in the Native JNI library, execute the following command.

```
$ cd <epid-verification-service>/Native
$ ./build.sh --clean
```

To generate unit tests metrics, execute the following command.

```
$ mvn clean verify
```
The code coverage report is stored in the directory ./target/site/jacoco/test/html

### Generate Keystores

Keystore is used to store SSL certificates in the Java* programming language.

*The example of keystore can be found in the directory 'certs'*
```
keystore - "verification-service-keystore.p12"
```
Default passwords for both: ver!f!c@t!0n

Visit [page][1] for instructions on how to generate keystore and truststore.

***Important***:
- The keystore provided in this repository is for demonstration purpose only. This must be changed while performing production deployment.

### Run EPID Verification Service

#### EPID Verification Service Settings
JVM options can be set to configure EPID Verification service:

| Java Option | Description |
| --- | --- |
| **Hosts** | |
| server.port | EPID Verification service host port (default: 1180).
| **Keystores** | You can use default keystore or you can generate your own, please review section [keystores](#generate-keystores) |
| server.ssl.key-store | Keystore file (default: verification-service-keystore.p12)|
| server.ssl.key-store-password | Keystore password (default: ver!f!c@t!0n)|
| **Miscellaneous**| |
| crypto-material.path | Path to EPID Cryptomaterials |
| java.library.path | Location of JNI **.so** files <br/> (default: ./Native/build/epid_verifier:./Native/build/epid_verifier_wrap |
| spring.profiles.active | Spring profile for EPID Verification service (values: production, development) |


#### Proxy Settings

* To use external Verification service from behind proxy set the following JVM flags, more info [here][2]:
```
https.proxyPort
https.proxyHost
http.proxyPort
http.proxyHost
```

#### Run EPID Verification Service

To run the EPID Verification service, you can use `epidVerificationService.sh`.
```
$ bash epidVerificationService.sh
```

To check whether the EPID Verification service is working properly run the following command:
```
$ curl --cacert ./certs/ca.cert.pem https://localhost:1180/health
```

Expected result:
```
{
	"version": "1.0.2-SNAPSHOT"
}
```

[1]: https://docs.oracle.com/cd/E19509-01/820-3503/6nf1il6er/index.html
[2]: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/net/doc-files/net-properties.html
[3]: https://docs.oracle.com/en/java/javase/11/tools/jar.html#GUID-51C11B76-D9F6-4BC2-A805-3C847E857867
