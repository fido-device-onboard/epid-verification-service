
# Table of Contents
1. [System Requirements](#system-requirements)
1. [Starting EPID Verification Service in Docker/Podman](#starting-epid-verification-service-in-docker)
    * [Docker/Podman Dependent Files](#dockerpodman-dependent-files)
    * [Cryptomaterials Signature Verification](#cryptomaterials-signature-verification)
    * [Create Java Keystore Files](#create-java-keystore-files)
    * [Docker-Compose Configuration](#docker-compose-configuration)
1. [Docker Commands](#docker-commands)
    * [Start the Docker Container](#start-the-docker-container)
    * [Docker Container Login](#docker-container-login)
    * [Stop the Docker Container](#stop-the-docker-container)
    * [Clean up Docker Containers](#clean-up-docker-containers)
1. [Podman Commands](#podman-commands)
    * [Start the Podman Container](#start-the-podman-container)
    * [Podman Container Login](#podman-container-login)
    * [Stop the Podman Container](#stop-the-podman-container)
    * [Clean up Podman Containers](#clean-up-podman-containers)


***NOTE***: The demo is provided solely to demonstrate the operation of the FIDO Device Onboard (FDO) EPID Verification service with an example database and configuration. This demo is not recommended for use in any production capacity. Appropriate security measures with respect to keystore management and configuration management should be considered while performing production deployment.

# System Requirements

* Operating system: Ubuntu (22.04, 20.04) / RHEL 8.4.

*  Linux packages:<br/><br/>
`Docker engine (minimum version 20.10.X) / Podman engine (For RHEL)`<br/><br/>
`Docker-compose (minimum version 1.21.2) / Podman-compose (v0.1.5) (For RHEL)`<br/>

# Starting EPID Verification Service in Docker

## Docker/Podman Dependent Files

The EPID Verification Service requires the Cryptomaterials and the EPID SDK library files during runtime. These files are copied to the demo folder while building the source code.

## Cryptomaterials Signature Verification

Follow this step to perform signature verification of `cryptoMaterial.tgz` using `cryptoMaterial.tgz.sig`

```
$ openssl smime -verify -in cryptoMaterial.tgz.sig -inform der -content cryptoMaterial.tgz -noverify > /dev/null
```
Expected response: Verification successful

## Create Java Keystore Files
See instructions in the [FIDO Device Onboard EPID Verification service README](https://github.com/secure-device-onboard/epid-verification-service#generate-keystores). Once the keystore file is created, update docker-compose.yml to reflect the file name, path and password. The default configured keystore is '/certs/verification-service-keystore.p12' with default password as 'ver!f!c@t!0n'.

The EPID Verification Service will run in HTTP mode if the keystore is not provided.

***IMPORTANT***:

-  This is an example implementation using simplified credentials. This must be changed while performing production deployment

## Docker-compose Configuration
Review the docker-compose.yml file and follow instructions in the file to customize for your environment.

# Docker Commands

NOTE:  `sudo` can be removed for docker commands to instill the principle of least privilege by adding a user to docker group in Ubuntu [REFER](https://docs.docker.com/engine/install/linux-postinstall/#manage-docker-as-a-non-root-user).

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

## Clean up Docker Containers

* Use the following command to delete all the Docker artifacts. (Note: Docker containers must be stopped before deleting them)
```
$ sudo docker system prune -a
```
# Podman Commands

## Start the Podman Container
* Use the following command to start the Podman container.
```
$ podman-compose up -d --build
```
* Your Podman container is now ready to support TO0 & TO1 protocol operations.

## Podman Container Login
* Use the following command to login to Podman container.
```
$ podman exec -it <container-name> bash
  OR
$ podman exec -it <container-id> bash
```
* In order to login, your container should be in the running state.

## Stop the Podman Container

* Use the following command to stop all running Podman containers.
```
$ podman stop -a
```

## Clean up Podman Containers

* Use the following command to delete all the Podman artifacts. (Note: Podman containers must be stopped before deleting them)
```
$ podman system prune -a
```
