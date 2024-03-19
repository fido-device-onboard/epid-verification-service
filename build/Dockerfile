FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get clean && apt-get -y update && apt-get -y upgrade && apt-get install -y openjdk-17-jdk maven make git curl swig cmake unzip software-properties-common --no-install-recommends

RUN bash -c 'echo "deb http://dk.archive.ubuntu.com/ubuntu/ xenial main" >> /etc/apt/sources.list'
RUN bash -c 'echo "deb http://dk.archive.ubuntu.com/ubuntu/ xenial universe" >> /etc/apt/sources.list'

RUN apt-get -y update && apt-get -y install g++-4.9 g++-4.9-multilib --no-install-recommends
RUN bash -c 'head -n -2 /etc/apt/sources.list > intermediate_file;mv -f intermediate_file /etc/apt/sources.list'
#RUN apt-get -y update
RUN update-alternatives --install /usr/bin/gcc gcc /usr/bin/gcc-4.9 10
RUN update-alternatives --install /usr/bin/g++ g++ /usr/bin/g++-4.9 10
RUN update-alternatives --install /usr/bin/cc cc /usr/bin/gcc 30
RUN update-alternatives --set cc /usr/bin/gcc
RUN update-alternatives --install /usr/bin/c++ c++ /usr/bin/g++ 30
RUN update-alternatives --set c++ /usr/bin/g++

# Create a user 'fdouser'. If the user name is updated, please update the same in docker-compose.yaml.
RUN useradd -ms /bin/bash fdouser
RUN mkdir -p /home/fdouser/epid-verification-service/ ; chown -R fdouser:fdouser /home/fdouser/epid-verification-service/
RUN mkdir -p /home/fdouser/.m2/; chown -R fdouser:fdouser /home/fdouser/.m2
USER fdouser

WORKDIR /home/fdouser/epid-verification-service/
ENTRYPOINT ./build/build.sh
