//package parser
//
//import Expression
//import common.KToken
//import common.KTokenType
//import common.KTokenType.*
//import common.error.IErrorReporter
//
//
//class KloxParser (private val tokens: List<KToken>, private val errorReporter: IErrorReporter) {
//    private var current = 0
//
//    private fun expression(): Expression {
//        return equality()
//    }
//
//    // Binary
//    private fun equality(): Expression {
//        return parseBinaryLA(arrayOf(BANG_EQUAL, BANG), ::comparison)
//    }
//
//    private fun comparison(): Expression {
//        return parseBinaryLA(arrayOf(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL), ::term)
//    }
//
//    private fun term(): Expression{
//        return parseBinaryLA(arrayOf(MINUS, PLUS), ::factor)
//    }
//
//    private fun factor(): Expression {
//        return parseBinaryLA(arrayOf(SLASH, STAR), ::unary)
//    }
//
//    /**
//     * Parses a binary expression with left-associative operators.
//     *
//     * This function is used to parse binary expressions with operators that have left-associativity, such as addition and subtraction.
//     *
//     * @param tokenList An array of token types representing the operators to parse.
//     * @param opMethod A function that returns the left-hand operand of the Expression.Binary.
//     * @return The parsed binary Expression.
//     */
//    private fun parseBinaryLA(tokenList: Array<KTokenType>, opMethod: () -> Expression): Expression {
//        var expr = opMethod() // term
//
//        while (match(*tokenList)){  // GREATER, GREATER_EQUAL, LESS, LESS_EQUAL
//            val op: KToken = previous()
//            val right: Expression = opMethod() // term
//            expr = Expression.Binary(expr, op, right)
//        }
//        return expr
//    }
//
//
//    // Unary
//
//    private fun unary(): Expression {
//        if (match(BANG, MINUS)){
//            val op: KToken = previous()
//            val right = unary()
//            return Expression.Unary(op,right)
//        }
//
//        return primary()
//    }
//
//    private fun primary(): Expression {
//        when{
//            match(FALSE) -> return Expression.Literal(false)
//            match(TRUE) -> return Expression.Literal(true)
//            match(NIL) -> return Expression.Literal("nil")
//            match(NUMBER, STRING) -> return Expression.Literal(previous().literal)
//            match(LEFT_PAREN) -> {
//                val expr = expression()
//                consume(RIGHT_PAREN, "Expect ')' after expression.")
//                return Expression.Grouping(expr)
//            }
//        }
//    }
//    private fun consume(type: KTokenType, message: String): KToken {
//        if (check(type)) return advance()
//        throw error(peek(),message)
//    }
//
//    private fun error(token: KToken, message: String): ParseError {
//        errorReporter.error(token, message)
//        return ParseError()
//    }
//
//    private fun match(vararg types: KTokenType): Boolean {
//        for (type in types) {
//            if (check(type)) {
//                advance()
//                return true
//            }
//        }
//        return false
//    }
//
//    private fun check(type: KTokenType): Boolean{
//        if (isAtEnd()) return false
//        return peek().type == type;
//    }
//
//    private fun advance(): KToken {
//        if (!isAtEnd()) current++
//        return previous()
//    }
//
//    private fun isAtEnd(): Boolean {
//        return peek().type === EOF
//    }
//
//    private fun peek(): KToken {
//        return tokens[current]
//    }
//
//    private fun previous(): KToken {
//        return tokens[current - 1]
//    }
//
//}