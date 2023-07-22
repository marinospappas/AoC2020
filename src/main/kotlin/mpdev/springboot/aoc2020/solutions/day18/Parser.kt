package mpdev.springboot.aoc2020.solutions.day18

import mpdev.springboot.aoc2020.utils.AocException
import java.util.*

class Parser(val expression: List<Token>) {

    private var accumulator = 0
    private val stack = Stack<Int>()

    fun parseExpression(): Int {
        accumulator = parseTerm()
        while (lookAhead().type == TokType.OPERATOR) {
            stack.push(accumulator)
            when (lookAhead().encToken) {
                Kwd.ADD -> add()
                Kwd.MULT -> multiply()
                else -> expected("add or multiply operator")
            }
        }
        return accumulator
    }

    fun parseParenthesisedExpression(): Int {
        return 0
    }

    fun parseTerm(): Int {

    }

    fun add(): Int {

    }

    fun multiply(): Int {

    }

    fun expected(msg: String) {
        System.err.println("$msg found ${lookAhead().value}")
    }

    ////// expression scanner

    var index = 0

    fun lookAhead(): Token = if (index < expression.size) expression[index+1] else Token(encToken = Kwd.END, type = TokType.END)

    fun match(encValue: Kwd) {
        val nextToken = lookAhead()
        if (nextToken.encToken == encValue)
            ++index
        else
            throw AocException("expected $encValue found ${nextToken.encToken}")
    }
}