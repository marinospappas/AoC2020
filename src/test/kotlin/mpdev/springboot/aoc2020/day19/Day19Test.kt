package mpdev.springboot.aoc2020.day19

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day19.Day19
import mpdev.springboot.aoc2020.solutions.day19.MessageValidator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day19Test {

    private val day = 19                                    ///////// Update this for a new dayN test
    private val puzzleSolver = Day19()                      ///////// Update this for a new dayN test
    private val inputDataReader = InputDataReader("src/test/resources/inputdata/input")
    private var inputLines: List<String> = inputDataReader.read(day)

    @BeforeEach
    fun setup() {
        puzzleSolver.setDay()
        puzzleSolver.inputData = inputLines
        puzzleSolver.initSolver()
    }

    @Test
    @Order(1)
    fun `Sets Day correctly`() {
        assertThat(puzzleSolver.day).isEqualTo(day)
    }

    @Test
    @Order(2)
    fun `Reads Input and Sets up Matching Rules`() {
        val msgValidator = MessageValidator(inputLines)
        msgValidator.rules.forEach{println(it)}
        println()
        msgValidator.messages.forEach{println(it)}
        assertThat(msgValidator.rules.size).isEqualTo(6)
        assertThat(msgValidator.messages.size).isEqualTo(5)
    }

    @Test
    @Order(3)
    fun `Successfully validates messages against rules`() {
        val msgValidator = MessageValidator(inputLines)
        val expected = listOf("", null, "", null, "b")
        msgValidator.messages.indices.forEach{
            println("${msgValidator.messages[it]} -> ${msgValidator.match(msgValidator.messages[it],0)}")
            assertThat(msgValidator.match(msgValidator.messages[it],0)).isEqualTo(expected[it])
        }
    }

    @Test
    @Order(4)
    fun `Solves Part 1`() {
        val res = puzzleSolver.solvePart1()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("2")
    }

    @Test
    @Order(5)
    fun `Solves Part 1 for Input-2`() {
        puzzleSolver.inputData = input2()
        puzzleSolver.initSolver()
        val res = puzzleSolver.solvePart1()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("3")
    }

    @Test
    @Order(6)
    fun `Successfully validates messages against repeated rules`() {
        val msgValidator = MessageValidator(input2())
        val expected = listOf(false,true,true,true,true,true,true,true,true,true,true,false,true,false,true)
        msgValidator.messages.indices.forEach{
            println("${msgValidator.messages[it]} -> ${msgValidator.match2(msgValidator.messages[it], 42, 31)}")
            assertThat(msgValidator.match2(msgValidator.messages[it], 42, 31)).isEqualTo(expected[it])
        }
    }

    @Test
    @Order(8)
    fun `Solves Part 2`() {
        puzzleSolver.inputData = input2()
        puzzleSolver.initSolver()
        val res = puzzleSolver.solvePart2()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("12")
    }

    fun input2() = listOf(
        "42: 9 14 | 10 1",
        "9: 14 27 | 1 26",
        "10: 23 14 | 28 1",
        "1: \"a\"",
        "11: 42 31",
        "5: 1 14 | 15 1",
        "19: 14 1 | 14 14",
        "12: 24 14 | 19 1",
        "16: 15 1 | 14 14",
        "31: 14 17 | 1 13",
        "6: 14 14 | 1 14",
        "2: 1 24 | 14 4",
        "0: 8 11",
        "13: 14 3 | 1 12",
        "15: 1 | 14",
        "17: 14 2 | 1 7",
        "23: 25 1 | 22 14",
        "28: 16 1",
        "4: 1 1",
        "20: 14 14 | 1 15",
        "3: 5 14 | 16 1",
        "27: 1 6 | 14 18",
        "14: \"b\"",
        "21: 14 1 | 1 14",
        "25: 1 1 | 1 14",
        "22: 14 14",
        "8: 42",
        "26: 14 22 | 1 20",
        "18: 15 15",
        "7: 14 5 | 1 21",
        "24: 14 1",
        "",
        "abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa",
        "bbabbbbaabaabba",
        "babbbbaabbbbbabbbbbbaabaaabaaa",
        "aaabbbbbbaaaabaababaabababbabaaabbababababaaa",
        "bbbbbbbaaaabbbbaaabbabaaa",
        "bbbababbbbaaaaaaaabbababaaababaabab",
        "ababaaaaaabaaab",
        "ababaaaaabbbaba",
        "baabbaaaabbaaaababbaababb",
        "abbbbabbbbaaaababbbbbbaaaababb",
        "aaaaabbaabaaaaababaa",
        "aaaabbaaaabbaaa",
        "aaaabbaabbaaaaaaabbbabbbaaabbaabaaa",
        "babaaabbbaaabaababbaabababaaab",
        "aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba"
    )
}
