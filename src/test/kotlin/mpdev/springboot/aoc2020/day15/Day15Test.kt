package mpdev.springboot.aoc2020.day15

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day15.Day15
import mpdev.springboot.aoc2020.solutions.day15.MemoryGame
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day15Test {

    private val day = 15                                    ///////// Update this for a new dayN test
    private val puzzleSolver = Day15()                      ///////// Update this for a new dayN test
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
    fun `Reads Input and Sets up Memory Game`() {
        val memoryGame = MemoryGame(inputLines)
        println(memoryGame.startSequence)
        println(memoryGame.numbersIndex)
        assertThat(memoryGame.startSequence).isEqualTo(listOf(0,3,6))
    }

    @Test
    @Order(3)
    fun `Identifies Numbers to Speak based on Rules`() {
        val memoryGame = MemoryGame(inputLines)
        repeat(9) { print("${memoryGame.playRound()}  ") }
        val last = memoryGame.playRound()
        println(last)
        assertThat(last).isEqualTo(0)
    }

    @ParameterizedTest
    @Order(4)
    @MethodSource("part1And2Params")
    fun `Solves Part 1`(input: List<String>, expected: String) {
        puzzleSolver.inputData = input
        puzzleSolver.initSolver()
        val res = puzzleSolver.solvePart1()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo(expected)
    }

    @ParameterizedTest
    @Order(8)
    @MethodSource("part1And2Params")
    fun `Solves Part 2`(input: List<String>, _ignore: Any, expected: String) {
        puzzleSolver.inputData = input
        puzzleSolver.initSolver()
        val res = puzzleSolver.solvePart2()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo(expected)
    }

    private fun part1And2Params() = Stream.of(
        Arguments.of(inputLines, "436", "175594"),
        Arguments.of(listOf("1,3,2"), "1", "2578"),
        Arguments.of(listOf("2,1,3"), "10", "3544142"),
        Arguments.of(listOf("1,2,3"), "27", "261214"),
        Arguments.of(listOf("2,3,1"), "78", "6895259"),
        Arguments.of(listOf("3,2,1"), "438", "18"),
        Arguments.of(listOf("3,1,2"), "1836", "362"),
    )
}
