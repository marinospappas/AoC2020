package mpdev.springboot.aoc2020.day09

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day09.Day09
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day09Test {

    private val day = 9                                     ///////// Update this for a new dayN test
    private val puzzleSolver = Day09()                      ///////// Update this for a new dayN test
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
    fun `Reads Input and Sets up Sequence correctly`() {
        inputLines.forEach { println(it) }
        assertThat(inputLines.size).isEqualTo(20)
        assertTrue(inputLines.all { it.toInt() > 0 })
    }

    @Test
    @Order(3)
    fun `Finds First Invalid Number`() {
        val data = inputLines.map { it.toLong() }.toLongArray()
        val preambleSize = 5
        assertThat(puzzleSolver.getFirstInvalidNumber(data, preambleSize).first).isEqualTo(127)
        assertThat(puzzleSolver.getFirstInvalidNumber(data, preambleSize).second).isEqualTo(14)
    }

    @Test
    @Order(4)
    fun `Solves Part 1`() {
        puzzleSolver.preambleSize = 5
        val res = puzzleSolver.solvePart1()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("127")
    }

    @Test
    @Order(5)
    fun `Finds Consecutive Numbers that sum up to the Invalid Number`() {
        val data = inputLines.map { it.toLong() }.toLongArray()
        val preambleSize = 5
        val (invalidNumber, invIndex) = puzzleSolver.getFirstInvalidNumber(data, preambleSize)
        val res = puzzleSolver.getConsecutiveNumbersThatAddToInvNumber(data, preambleSize, invalidNumber, invIndex)
            .let { it.first() + it.last() }
        assertThat(res).isEqualTo(62)
    }

    @Test
    @Order(6)
    fun `Solves Part 2`() {
        puzzleSolver.preambleSize = 5
        val res = puzzleSolver.solvePart2()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("62")
    }

}
