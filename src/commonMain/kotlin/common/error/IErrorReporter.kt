package common.error

import common.KToken

interface IErrorReporter {
    var hadError: Boolean

    fun error(line: Int, message: String)
    fun error(token: KToken, message: String)
    fun warn(line: Int, message: String)
}