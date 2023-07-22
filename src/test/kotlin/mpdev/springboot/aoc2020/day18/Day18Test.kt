package mpdev.springboot.aoc2020.day18

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day18.Day18
import mpdev.springboot.aoc2020.solutions.day18.Math
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day18Test {

    private val day = 18                                    ///////// Update this for a new dayN test
    private val puzzleSolver = Day18()                      ///////// Update this for a new dayN test
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
    fun `Reads Input and Sets up Expressions`() {
        val math = Math(inputLines)
        inputLines.indices.forEach {
            println(inputLines[it])
            println(math.expressionList[it])
        }
        assertThat(math.expressionList.size).isEqualTo(6)
    }

    @Test
    @Order(2)
    fun `Parses and Calculates Expressions`() {
        val math = Math(inputLines)
        val expected = listOf<Long>(71, 51, 26, 437, 12240, 13632)
        inputLines.indices.forEach {
            println(inputLines[it])
            val res = math.calculate(math.expressionList[it])
            println(res)
            assertThat(res).isEqualTo(expected[it])
        }
    }

    @Test
    @Order(4)
    fun `Solves Part 1`() {
        val res = puzzleSolver.solvePart1()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("26457")
    }

    @Test
    @Order(5)
    fun `Parses and Calculates Expressions Part 2`() {
        val math = Math(inputLines, 2)
        val expected = listOf<Long>(231, 51, 46, 1445, 669060, 23340)
        inputLines.indices.forEach {
            println(inputLines[it])
            val res = math.calculate(math.expressionList[it])
            println(res)
            assertThat(res).isEqualTo(expected[it])
        }
    }

    @Test
    @Order(8)
    fun `Solves Part 2`() {
        val res = puzzleSolver.solvePart2()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("694173")
    }
}
