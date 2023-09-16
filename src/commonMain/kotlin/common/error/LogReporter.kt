package common.error

import common.KToken
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging

class LogReporter() : IErrorReporter {
    override var hadError: Boolean = false

//    private val logger: KLogger = KotlinLogging.logger("Lexer logger")
    override fun error(line: Int, message: String){
        println(
            "line : $line \nError : $message"
        )
//        logger.error {
//            "line : $line \nError : $message"
//        }
        hadError = true
    }

    override fun error(token: KToken, message: String) {

        println(
            "Token : $token \nError : $message"
        )
    }

    override fun warn(line: Int, message: String) {
        println(
            "line : $line \nWarning : $message"
        )
//        logger.warn {
//            "line : $line \nWarning : $message"
//        }
        hadError = true
    }
}