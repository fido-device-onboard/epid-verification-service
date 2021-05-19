FROM ubuntu:20.04

RUN apt-get -y  update && apt-get -y upgrade

RUN bash -c 'echo "deb http://dk.archive.ubuntu.com/ubuntu/ xenial main" >> /etc/apt/sources.list'
RUN bash -c 'echo "deb http://dk.archive.ubuntu.com/ubuntu/ xenial universe" >> /etc/apt/sources.list'
RUN apt update

RUN apt -y install g++-4.9
RUN apt -y install g++-4.9-multilib
RUN bash -c 'head -n -2 /etc/apt/sources.list > intermediate_file;mv -f intermediate_file /etc/apt/sources.list'
RUN update-alternatives --install /usr/bin/gcc gcc /usr/bin/gcc-4.9 10
RUN update-alternatives --install /usr/bin/g++ g++ /usr/bin/g++-4.9 10
RUN update-alternatives --install /usr/bin/cc cc /usr/bin/gcc 30
RUN update-alternatives --set cc /usr/bin/gcc
RUN update-alternatives --install /usr/bin/c++ c++ /usr/bin/g++ 30
RUN update-alternatives --set c++ /usr/bin/g++

RUN apt-get install -y software-properties-common && add-apt-repository -y ppa:ubuntu-toolchain-r/test
RUN apt-get install -y openjdk-11-jdk maven make git curl swig cmake unzip

# Create a user 'epiduser'. If the user name is updated, please update the same in docker-compose.yaml.
RUN useradd -ms /bin/bash epiduser
RUN mkdir -p /home/epiduser/epid-verification-service/ ; chown -R epiduser:epiduser /home/epiduser/epid-verification-service/
USER epiduser

WORKDIR /home/epiduser/epid-verification-service/
ENTRYPOINT ./build_service/build.sh
