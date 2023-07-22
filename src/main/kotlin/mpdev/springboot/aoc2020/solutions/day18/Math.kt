package mpdev.springboot.aoc2020.solutions.day18

import mpdev.springboot.aoc2020.utils.AocException

class Math(input: List<String>, val part1Or2: Int = 1) {

    val expressionList = mutableListOf<List<Token>>()
    private val parsers = listOf(ParserPart1(), ParserPart2())

    init {
        processInput(input)
    }

    private fun getParser(part1Or2: Int): Parser =
        parsers.first { parser -> parser.getPart1Or2() == part1Or2 }

    fun calculate(expression: List<Token>): Long {
        val parser = getParser(part1Or2)
        return parser.calculate(expression)
    }

    ////////////////////////////////////////////////////////

    private fun processInput(input: List<String>) {
        input.forEach { line -> expressionList.add(expressionFromString(line)) }
    }

    private fun expressionFromString(s: String): List<Token> {
        val expression = mutableListOf<Token>()
        s.filter { c -> c != ' ' }.forEach { c ->
            expression.add(
                when (c) {
                    '+' -> Token(c, Kwd.ADD, TokType.OPERATOR)
                    '*' -> Token(c, Kwd.MULT, TokType.OPERATOR)
                    in '0'..'9' -> Token(c, Kwd.NUMBER, TokType.NUMBER)
                    '(' -> Token(c, Kwd.LEFT_PAREN, TokType.PAREN)
                    ')' -> Token(c, Kwd.RIGHT_PAREN, TokType.PAREN)
                    else -> throw AocException("invalid input $s")
                }
            )
        }
        return expression
    }
}
