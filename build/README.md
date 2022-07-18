
# About

Docker/Podman Script for Building EPID-Verification-Service repository. Using this script you can build the local copy of the repository as well as the latest upstream of the repository.

## Prerequisites

- Operating system: **Ubuntu (22.04, 20.04) / RHEL 8.4.**

- Docker engine : **20.10.X** / Podman engine (For RHEL).

- Docker-compose : **1.23.2** / Podman-compose: **0.1.5** (For RHEL).



## Usage

NOTE:  `sudo` can be removed for docker commands to instill the principle of least privilege by adding a user to docker group in Ubuntu [REFER](https://docs.docker.com/engine/install/linux-postinstall/#manage-docker-as-a-non-root-user).

####  Docker Commands
When you want to build a local copy of the repository.

``` sudo docker-compose up --build ```

When you want to build the latest upstream of the repository.

``` sudo use_remote=1 docker-compose up --build ```

#### Podman Commands
When you want to build a local copy of the repository.

``` podman-compose up --build ```

When you want to build the latest upstream of the repository.

``` use_remote=1 podman-compose up --build ```
You also have the option to change the remote repository address as well as the remote repository branch in build.sh file.

    REMOTE_URL=link-to-your-fork
    REMOTE_BRANCH=branch-name

## Expected Outcome
As the docker/podman script finishes its execution successfully, the ```.jar``` file of the EPID-Verification-Service will be present in ```<epid-verification-service>/demo/``` folder.

## Updating Proxy Info (Optional )
If you are working behind a proxy network, ensure that both http and https proxy variables are set.

    export http_proxy=http-proxy-host:http-proxy-port
    export https_proxy=https-proxy-host:https-proxy-port
