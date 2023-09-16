package lexer

import common.KToken
import common.KTokenType
import common.KTokenType.*
import common.error.IErrorReporter

class KloxLexer(private val source: String, private val errorReporter: IErrorReporter) {
    private var tokens: MutableList<KToken> = mutableListOf()
    private var start = 0
    private var current = 0
    private var line = 1

    private val keywords = mapOf(
        "and" to AND,
        "class" to CLASS,
        "else" to ELSE,
        "false" to FALSE,
        "for" to FOR,
        "fun" to FUN,
        "if" to IF,
        "nil" to NIL,
        "or" to OR,
        "print" to PRINT,
        "return" to RETURN,
        "super" to SUPER,
        "this" to THIS,
        "var" to VAR,
        "while" to WHILE,
    )

    fun scanTokens(): List<KToken> {
        while (!isAtEnd()) {
            start = current
            scanToken()
        }

        tokens.add(KToken(EOF, "", null, line))
        return tokens
    }

    private fun scanToken() {
        val c: Char = advance()
        when (c) {
            '(' -> addToken(LEFT_PAREN)
            ')' -> addToken(RIGHT_PAREN)
            '{' -> addToken(LEFT_BRACE)
            '}' -> addToken(RIGHT_BRACE)
            ',' -> addToken(COMMA)
            '.' -> addToken(DOT)
            '-' -> addToken(MINUS)
            '+' -> addToken(PLUS)
            ';' -> addToken(SEMICOLON)
            '*' -> addToken(STAR)
            '!' -> addToken(if (match('=')) BANG_EQUAL else BANG)
            '=' -> addToken(if (match('=')) EQUAL_EQUAL else EQUAL)
            '<' -> addToken(if (match('=')) LESS_EQUAL else LESS)
            '>' -> addToken(if (match('=')) GREATER_EQUAL else GREATER)
            '/' -> {
                if (match('/')) {
                    while (peek() != '\n' && !isAtEnd()) advance()
                } else addToken(SLASH)
            }

            ' ', '\t', '\r' -> {}
            '\n' -> line++
            '"' -> string()
            in '0'..'9' -> number()
            else -> {
                if (isAlpha(c))
                    identifier()
                else
                errorReporter.error(line, "Parser: Unexpected char \"$c\"")
            }
        }
    }

    private fun identifier() {
        while (isAlphaNumeric(peek())) advance()
        val text = source.subSequence(start, current)
        val type = keywords[text] ?: IDENTIFIER
        addToken(type)
    }

    private fun number() {
        while (isDigit(peek())) advance()

        if (peek() == '.' && isDigit(peekNext())) {
            advance()
            while (isDigit(peek())) advance()
        }

        addToken(NUMBER, source.substring(start, current).toDouble())
    }

    private fun string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++
            advance()
        }

        if (isAtEnd()) {
            errorReporter.error(line, "Unterminated string.")
            return
        }
        advance()
        // Trim the surrounding quotes.
        val value = source.subSequence(start + 1, current - 1)
        addToken(STRING, value)
    }

    private fun isAlphaNumeric(c: Char): Boolean {
        return when (c) {
            '_' -> true
            in '0'..'9' -> true
            in 'a'..'z' -> true
            in 'A'..'Z' -> true
            else -> false
        }
    }

    private fun isAlpha(c: Char): Boolean {
        return when (c) {
            '_' -> true
            in 'a'..'z' -> true
            in 'A'..'Z' -> true
            else -> false
        }
    }

    private fun isDigit(c: Char): Boolean {
        return c in '0'..'9'
    }

    private fun peek(): Char {
        if (isAtEnd()) return '\u0000'
        return source.elementAt(current)
    }


    private fun peek(offset: Int): Char {
        if (isAtEnd()) return '\u0000'
        return source.elementAt(current + offset)
    }

    private fun peekNext(): Char {
        if (current + 1 >= source.length) return '\u0000'
        return source.elementAt(current + 1)
    }

    private fun match(expected: Char): Boolean {
        if (isAtEnd()) return false
        if (source.elementAt(current) != expected) return false

        current++
        return true
    }

    private fun addToken(type: KTokenType) {
        addToken(type, null)
    }

    private fun addToken(type: KTokenType, literal: Any?) {
        val text = source.subSequence(start, current) // Todo: Maybe current + 1
        tokens.add(KToken(type, text, literal, line))
    }

    private fun advance(): Char {
        return source.elementAt(current++)
    }

    private fun isAtEnd(): Boolean {
        return current >= source.length
    }


}