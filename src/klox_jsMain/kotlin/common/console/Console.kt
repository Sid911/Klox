package common.console

/**
 * Console shared code declaration. Class is used to output something on screen.
 */
actual class Console : IConsole {
    actual override fun println(s: String) {
        println(s)
    }

}