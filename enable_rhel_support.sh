# Copyright 2021 Intel Corporation
#
# Summary:
# This script is used to add the required changes to enable EPID Verification Service support on RHEL. 
# It will replace the dockerfile build to Podmanfile in docker-compose.yml

#build/
sed -i 's/Dockerfile/Podmanfile/' build/docker-compose.yml
sed -i '/- fdo-m2:\/home\/fdouser\/.m2:rw/d' build/docker-compose.yml
sed -i 's/:rw/:Z/' build/docker-compose.yml
grep -q 'fdo-m2: {}' build/docker-compose.yml || sed -i 's/fdo-m2:/fdo-m2: {}/' build/docker-compose.yml
sed -i 's/java-11-openjdk-amd64/java-11-openjdk/' build/build.sh

#demo/
sed -i 's/Dockerfile-epid-verification/Podmanfile-epid-verification/' demo/docker-compose.yml

#Native/src/service/
grep -q 'D_FORTIFY_SOURCE=2 -O2' Native/src/service/CMakeLists.txt || sed -i 's/-D_FORTIFY_SOURCE=2/-D_FORTIFY_SOURCE=2 -O2/' Native/src/service/CMakeLists.txt
