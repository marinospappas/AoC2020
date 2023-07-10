package mpdev.springboot.aoc2020.solutions.day02

import mpdev.springboot.aoc2020.utils.AocException

class PasswordRules(inputLines: List<String>) {

    private var rules: MutableList<PwdRule> = mutableListOf()

    init {
        inputLines.forEach { line -> rules.add(PwdRule.fromString(line)) }
    }

    fun countValidPwds1() = rules.count { it.apply1() }
    fun countValidPwds2() = rules.count { it.apply2() }
}

class PwdRule(var c: Char, var indx1: Int, var indx2: Int,var password: CharArray) {
    companion object {
        fun fromString(input: String): PwdRule {
            val match = Regex("""(\d+)-(\d+) ([a-zA-Z]+): (.+)""").find(input)
            try {
                val (min, max, c, p) = match!!.destructured
                return PwdRule(c.first(), min.toInt(), max.toInt(), p.toCharArray())
            } catch (e: Exception) {
                throw AocException("bad input line $input")
            }
        }
    }
    fun apply1(): Boolean {
        val countOfC = password.count { it == c }
        return countOfC in indx1..indx2
    }

    fun apply2(): Boolean {
        val c1 = password[indx1-1]
        val c2 = password[indx2-1]
        return c1 == c && c2 != c || c1 != c && c2 == c
    }
}