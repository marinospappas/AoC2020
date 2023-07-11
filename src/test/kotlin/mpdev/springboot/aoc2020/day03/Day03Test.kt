package mpdev.springboot.aoc2020.day03

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day03.Day03
import mpdev.springboot.aoc2020.solutions.day03.Slope
import mpdev.springboot.aoc2020.solutions.day03.SlopePoint
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.awt.Point
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day03Test {

    private val day = 3                                     ///////// Update this for a new dayN test
    private val puzzleSolver = Day03()                        ///////// Update this for a new dayN test
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
    fun `Reads Input and Sets up slope correctly`() {
        val slope = Slope(inputLines)
        slope.printSlope()
        assertThat(slope.maxX).isEqualTo(11)
        assertThat(slope.maxY).isEqualTo(11)
        assertThat(slope.countOf(SlopePoint.TREE)).isEqualTo(37)
    }

    @Test
    @Order(3)
    fun `Calculates Number of Trees for Part 1`() {
        val slope = Slope(inputLines)
        val res = slope.traverse(Point(3,1))
        println("encountered $res")
        assertThat(res).isEqualTo(7)
    }

    @ParameterizedTest
    @MethodSource("calculatePart2Parameters")
    @Order(4)
    fun `Calculates Number of Trees for Part 2`(slopeStep: Point, expected: Int) {
        val slope = Slope(inputLines)
        val res = slope.traverse(slopeStep)
        println("encountered $res")
        assertThat(res).isEqualTo(expected)
    }

    private fun calculatePart2Parameters() =
        Stream.of(
            Arguments.of(Point(1,1), 2),
            Arguments.of(Point(5,1), 3),
            Arguments.of(Point(7,1), 4),
            Arguments.of(Point(1,2), 2)
        )

    @Test
    @Order(5)
    fun `Solves Part 1`() {
        assertThat(puzzleSolver.solvePart1().result).isEqualTo("7")
    }

    @Test
    @Order(6)
    fun `Solves Part 2`() {
        assertThat(puzzleSolver.solvePart2().result).isEqualTo("336")
    }
}
