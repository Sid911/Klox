import common.KToken

sealed class Expression{
       class Assign(val name: KToken, val value: Expression): Expression()
       class Binary(val left: Expression, val op: KToken, val right: Expression): Expression()
       class Call(val callee: Expression, val paren: KToken, val arguments: List<Expression>): Expression()
       class Get(val obj: Expression, val name: KToken): Expression()
       class Grouping(val expr: Expression): Expression()
       class Literal(val value: Any?): Expression()
       class Logical(val left: Expression, val operator: KToken, val right: Expression): Expression()
       class Unary(val op: KToken, val expr: Expression): Expression()
       class Variable(val name: KToken): Expression()
       class Set(val obj: Expression, val name: KToken, val value: Expression): Expression()
       class This(val keyword: KToken): Expression()
   }
