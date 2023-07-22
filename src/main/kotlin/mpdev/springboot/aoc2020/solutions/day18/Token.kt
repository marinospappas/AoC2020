package mpdev.springboot.aoc2020.solutions.day18

data class Token(val value: Char = ' ',
            val encToken: Kwd = Kwd.NONE,
            val type: TokType = TokType.NONE
)

enum class TokType {
    NUMBER,
    OPERATOR,
    PAREN,
    END,
    NONE
}

enum class Kwd {
    ADD,
    MULT,
    NUMBER,
    LEFT_PAREN,
    RIGHT_PAREN,
    END,
    NONE
}