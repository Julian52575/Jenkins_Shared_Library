def call(Map config = [:] ) {
    sh 'test Makefile'
    sh 'make'
    sh "test -x ${config.name}"
    sh "echo '${config.name} has been correctly compiled !' "
    //readFile
}
