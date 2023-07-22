package mpdev.springboot.aoc2020.solutions.day18

import mpdev.springboot.aoc2020.utils.AocException
import java.util.*

abstract class Parser {

    protected lateinit var expression: List<Token>
    protected var accumulator: Long = 0L
    protected val stack: Stack<Long> = Stack()

    abstract fun getPart1Or2(): Int

    abstract fun calculate(expression: List<Token>): Long

    ////// expression scanner

    protected var index: Int = -1

    protected fun lookAhead(): Token = if (index < expression.size-1) expression[index+1] else Token(encToken = Kwd.END, type = TokType.END)

    protected fun match(encValue: Kwd): Token {
        val nextToken = lookAhead()
        if (nextToken.encToken == encValue)
            ++index
        else
            throw AocException("expected $encValue found ${nextToken.encToken}")
        return nextToken
    }

    protected fun expected(msg: String) {
        System.err.println("expected $msg found ${lookAhead().value}")
    }
}