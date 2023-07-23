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
        println()
        msgValidator.rulesGraph.getNodes().map { it.value.nodeId }.forEach { println(it) }
        assertThat(msgValidator.rulesGraph.getNodes().size).isEqualTo(7)
    }

    @Test
    @Order(4)
    fun `Solves Part 1`() {
        val res = puzzleSolver.solvePart1()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("26457")
    }

    @Test
    @Order(8)
    fun `Solves Part 2`() {
        val res = puzzleSolver.solvePart2()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("expected")
    }
}
