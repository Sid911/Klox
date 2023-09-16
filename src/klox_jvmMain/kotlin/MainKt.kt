import common.KToken
import common.Math
import common.console.Console
import common.error.IErrorReporter
import common.error.LogReporter
import java.io.File

import lexer.KloxLexer
import kotlin.system.exitProcess


fun main(vararg args: String) {
    println("Klox interpreter : \n")
    if (args.size > 1) {
        println("Usage: jlox [script]");
        exitProcess(64);
    } else if (args.size == 1) {
        val res = args[0]
        runFile(args[0]);
    } else {
        runPrompt();
    }
}

fun runPrompt() {
    val reporter = LogReporter()
    while (true) {
        print("> ")
        val input = readlnOrNull()
        if (input.isNullOrEmpty()) break

        run(input, reporter)
    }
}

fun runFile(path: String) {
    val bufferedReader = File(path).bufferedReader()
    val inputString = bufferedReader.readText()

    val reporter = LogReporter()
    run(inputString, reporter)
}

fun run(source: String, reporter: IErrorReporter){
    // Example Shared class usage
    val clazz = SharedClass(console = Console(), math = Math())


    val lexer: KloxLexer = KloxLexer(source,reporter)
    val tokens: List<KToken> = lexer.scanTokens()

    for (token in tokens){
        println(token)
    }
}
