import common.KToken

sealed class Statement{
       class Block(val statements: List<Statement>): Statement()
       class ExpStatement(val expr: Expression): Statement()
       class Menu(val name: KToken, val fields: List<Statement>): Statement()
       class Recipe(val name: KToken, val params: List<KToken>, val body: List<Statement>): Statement()
       class Taste(val condition: Expression, val thenBranch: Statement, val elseifBranches: Statement?, val elseBranch: Statement?): Statement()
       class Serve(val value: Expression): Statement()
       class Bill(val keyword: KToken, val value: Expression?): Statement()
       class Ingredient(val name: KToken, val kind: KToken, val initializer: Expression?): Statement()
       class Stir(val condition: Expression, val body: Statement): Statement()
       class GiveUp(): Statement()
       class Favourite(val condition: Expression, val cases: Statement): Statement()
   }