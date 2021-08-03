# Table of Contents
1. [System Requirements](#system-requirements)
1. [Starting EPID Verification service in Docker](#starting-epid-verification-service-in-docker)
    * [Docker Dependent Files](#docker-dependent-files)
    * [Cryptomaterials Signature Verification](#cryptomaterials-signature-verification)
    * [Create Java Keystore Files](#create-java-keystore-files)
    * [Docker-compose Configuration](#docker-compose-configuration)
1. [Docker Commands](#docker-commands)
    * [Start the Docker Container](#start-the-docker-container)
    * [Docker Container Login](#docker-container-login)
    * [Stop the Docker Container](#stop-the-docker-container)
    * [Clean up Containers](#clean-up-containers)

***NOTE***: The demo is provided solely to demonstrate the operation of the FIDO Device Onboard (FDO) EPID Verification service with an example database and configuration. This demo is not recommended for use in any production capacity. Appropriate security measures with respect to keystore management and configuration management should be considered while performing production deployment.

# System Requirements

* Operating system: Ubuntu 20.04.

*  Linux packages:<br/><br/>
`Docker engine (minimum version 18.09)`<br/><br/>
`Docker-compose (minimum version 1.21.2)`<br/>

# Starting EPID Verification service in Docker

## Docker Dependent Files

The EPID Verification service requires the Cryptomaterials and the EPID SDK library files during runtime. These files are copied to the demo folder while building the source code.

## Cryptomaterials Signature Verification

Follow this step to perform signature verification of `cryptoMaterial.tgz` using `cryptoMaterial.tgz.sig`

```
$ openssl smime -verify -in cryptoMaterial.tgz.sig -inform der -content cryptoMaterial.tgz -noverify > /dev/null
```
Expected response: Verification successful

## Create Java Keystore Files
See instructions in the [FIDO Device Onboard EPID Verification service README](https://github.com/secure-device-onboard/epid-verification-service#generate-keystores). Once the keystore file is created, update docker-compose.yml to reflect the file name, path and password. The default configured keystore is '/certs/verification-service-keystore.p12' with default password as 'ver!f!c@t!0n'.

The EPID Verification service will run in HTTP mode if the keystore is not provided.

***IMPORTANT***:

-  This is an example implementation using simplified credentials. This must be changed while performing production deployment

## Docker-compose Configuration
Review the docker-compose.yml file and follow instructions in the file to customize for your environment.

# Docker Commands

## Start the Docker Container
* Use the following command to start the Docker container.
```
$ sudo docker-compose up -d --build
```
* Your Docker container is now ready to support TO0 & TO1 protocol operations.

## Docker Container Login
* Use the following command to login to Docker container.
```
$ sudo docker exec -it <container-name> bash
  OR
$ sudo docker exec -it <container-id> bash
```
* Your container should be in running state for login.

## Stop the Docker Container

* Use the following command to stop all running Docker containers.
```
$ sudo docker stop $(sudo docker ps -a -q)
```

## Clean up Containers

* Use the following command to delete all the Docker artifacts. (Note: Docker containers must be stopped before deleting them)
```
$ sudo docker system prune -a
```
