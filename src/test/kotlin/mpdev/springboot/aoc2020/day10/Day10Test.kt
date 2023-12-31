package mpdev.springboot.aoc2020.day10

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day10.Day10
import mpdev.springboot.aoc2020.solutions.day10.JoltAdapters
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day10Test {

    private val day = 10                                    ///////// Update this for a new dayN test
    private val puzzleSolver = Day10()                      ///////// Update this for a new dayN test
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
    fun `Reads Input and Sets up Graph correctly`() {
        val adapters = JoltAdapters(inputLines)
        println(adapters.data)
        println(adapters)
        assertThat(inputLines.size).isEqualTo(31)
    }

    @Test
    @Order(3)
    fun `Calculates Jolt differences`() {
        puzzleSolver.inputData = File("src/test/resources/inputdata/input10-1.txt").readLines()
        puzzleSolver.initSolver()
        val res = puzzleSolver.solvePart1().result.toInt()
        assertThat(res).isEqualTo(35)
    }

    @Test
    @Order(4)
    fun `Solves Part 1`() {
        val res = puzzleSolver.solvePart1()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("220")
    }

    @Test
    @Order(5)
    fun `Calculates Various Adapter Combinations (paths)`() {
        val adapters = JoltAdapters(File("src/test/resources/inputdata/input10-1.txt").readLines())
        println(adapters.data)
        println(adapters)
        val res = adapters.getAllPaths(0,22)
        res.forEach { println(it) }
        assertThat(res.size).isEqualTo(8)
        val res1 = adapters.getAllCombinations()
        assertThat(res1).isEqualTo(8)
    }

    @Test
    @Order(7)
    fun `Solves Part 2`() {
        val res = puzzleSolver.solvePart2()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("19208")
    }

}
