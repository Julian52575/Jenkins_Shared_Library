// config.cmd -> command to run
// config.expOutput -> expected output to stdin
// config.expStatus -> expected return status
// config.logPath -> logFile to write result into (if any)
// config.errorPath -> file to log errors into

def call(Map config = [:]) {
    if ( config.cmd == "" )
        return 84

    sh "echo Executing ${config.cmd}..."/////////////////////////
    def expOutput = config.expOutput
    def expStatus = config.expStatus
    def logPath = config.logPath
    def errorPath = config.errorPath
    def status = 0
    def stdOutput = ""
    def process = null
    //Run command thanks to java.lang.Process
    try {
        def process = "${config.cmd}".execute()
        if (process.isAlive()) {
            process.waitFor()
        }
    } catch (Exception e) {
        // Log or print the exception details for debugging
        echo "!!! Exception: ${e.message}"
    }

    
    //if ( process.isAlive() == true )
        //process.waitFor() //check return value for timeout ?


    
    stdOutput = process.text
    status = process.exitValue()
    sh "echo ${status}: _${stdOutput}_"//////////////////////////

    if ( status != 0 )
        return status
    if ( ${config.logname} == "" ) {
      sh "echo 'Result: _${stdOutput}_' "
    } else {
      sh "echo 'Ok _${stdOutput}_' >> ${logPath}"
    }
    return 0
}
