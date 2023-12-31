
    //name -> 1+1
    //cmd -> ./math 1 1 +
    //expOutput -> Your result is 2.
    //expReturnValue -> 0
    //logName -> new_mouli.log
    //depthName -> InDepth.log


def call(Map config = [:]) {
    def statusOK = true
    def outputOK = true
    //Printing Name
    sh "echo -n ${config.name}: >> ${config.logName}"
    sh "echo -n '\t\t|\t' >> ${config.logName}"
    //
    //
    //Run CMD and fill Output && statusCode
    //
    def output = " "
    def statusCode = 0
    timeout(time: 2, unit: 'MINUTES') {
        statusCode = sh (
            script: "${config.cmd} > cmdOutput.txt",
            returnStatus: true
        )
    }
    output = sh (
                script: "cat cmdOutput.txt",
                returnStdout: true
            )
    output = output.trim()
    //
    if ( output == config.expOutput ) {
        printOK(
           logName: "${config.logName}"
        )
    } else {
        printKO(
            logName: "${config.logName}"
        )
        outputOK = false
    }
    sh "echo -n '\t\t|\t' >> ${config.logName}"
    //
    if ( statusCode == config.expReturnValue ) {
        printOK(
            logName: "${config.logName}"
        )
    } else {
        printKO(
            logName: "${config.logName}"
        )
        statusOK = false
    }
    //
    //Print cmd in log
    sh "echo -n '\t\t|\t' >> ${config.logName}"
    sh "echo -n ${config.cmd} >> ${config.logName}"
    sh "echo '\t|' >> ${config.logName}"
    //
    //Print result in next line
    if ( statusOK == false ) {
        sh "echo 'ReturnValue: ${statusCode}.' >> ${config.logName}"
    }
    if ( outputOK == false ) {
        sh "echo 'ReturnOutput:\n[[${output}]]' >> ${config.logName}"
        printTableEnd(
            logName: "${config.logName}"
        )
    }
    doInDepthLog(
        cmd: "${config.cmd}",
        output: "${output}",
        statusCode: "${statusCode}",
        depthName: "${config.depthName}"
    ) 
}
