package mpdev.springboot.aoc2020.solutions.day18

class ParserPart2: Parser() {

    override fun getPart1Or2() = 2

    override fun calculate(expression: List<Token>): Long {
        accumulator = 0
        index = -1
        stack.clear()
        this.expression = expression
        parseExpression()
        return accumulator
    }

    /**
     * <expression> ::= <term> [ <multop> <term> ] *
     * <term> ::= <factor> [ <multop> <factor> ] *
     * <factor> ::= <integer> | <parenthesised expression>
     * <parenthesised expression> ::= <left paren> <expression> <right paren>
     */
    private fun parseExpression() {
        parseTerm()
        while (lookAhead().encToken == Kwd.MULT) {
            stack.push(accumulator)
            when (lookAhead().encToken) {
                Kwd.MULT -> multiply()
                else -> expected("multiply operator")
            }
        }
    }

    private fun parseTerm() {
        parseFactor()
        while (lookAhead().encToken == Kwd.ADD) {
            stack.push(accumulator)
            when (lookAhead().encToken) {
                Kwd.ADD -> add()
                else -> expected("add operator")
            }
        }
    }

    private fun parseFactor() {
        when (lookAhead().type) {
            TokType.NUMBER -> parseNumber()
            TokType.PAREN -> parseParenthesisedExpression()
            else -> expected("number or left parenthesis")
        }
    }

    private fun parseNumber() {
        val token = match(Kwd.NUMBER)
        accumulator = token.value.digitToInt().toLong()
    }

    private fun parseParenthesisedExpression() {
        match(Kwd.LEFT_PAREN)
        parseExpression()
        match(Kwd.RIGHT_PAREN)
    }

    private fun add() {
        match(Kwd.ADD)
        parseTerm()
        accumulator += stack.pop()
    }

    private fun multiply() {
        match(Kwd.MULT)
        parseTerm()
        accumulator *= stack.pop()
    }
}