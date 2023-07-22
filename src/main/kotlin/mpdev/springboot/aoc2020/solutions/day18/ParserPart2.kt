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
     * <term> ::= <integer> | <parenthesised expression>
     * <parenthesised expression> ::= <left paren> <expression> <right peren>
     * <operator> ::= <addop> | <mulop>
     */
    private fun parseExpression() {
        parseTerm()
        while (lookAhead().type == TokType.OPERATOR) {
            stack.push(accumulator)
            when (lookAhead().encToken) {
                Kwd.ADD -> add()
                Kwd.MULT -> multiply()
                else -> expected("add or multiply operator")
            }
        }
    }

    private fun parseTerm() {
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