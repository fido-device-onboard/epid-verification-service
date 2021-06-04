node('ccode'){

  stage('Clone epid-verification-service'){
    cleanWs()
    checkout scm
  }

  stage('Build EPID SDK'){
    dir('Native/src/service/dependencies'){
      sh '''
        export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
        git clone -b v7.0.1 https://github.com/Intel-EPID-SDK/epid-sdk
        cd epid-sdk
        chmod +x configure
        ./configure
        make all
        make check
        make install
      '''
    }
  }

  stage('Build GoogleTest'){
    dir('Native/src/service/dependencies'){
      sh '''
        export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
        git clone -b release-1.7.0 https://github.com/google/googletest
        cd googletest/make
        make
      '''
    }
  }

  stage('Build EPID Verification Service'){
    sh '''
      export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
      mvn clean install
    '''
    print "Archive the artifacts"
    sh 'tar -czvf demo.tar.gz demo/'
    archiveArtifacts artifacts: 'demo.tar.gz', fingerprint: true, allowEmptyArchive: false
  }

}