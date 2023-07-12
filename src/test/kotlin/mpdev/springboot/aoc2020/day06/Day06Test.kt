package mpdev.springboot.aoc2020.day06

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day06.Day06
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day06Test {

    private val day = 6                                     ///////// Update this for a new dayN test
    private val puzzleSolver = Day06()                        ///////// Update this for a new dayN test
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
    fun `Reads Input and Sets up Seats List correctly`() {
        puzzleSolver.data.forEach { println(it) }
        assertThat(puzzleSolver.data.size).isEqualTo(5)
    }

    @Test
    @Order(3)
    fun `Solves Part 1`() {
        assertThat(puzzleSolver.solvePart1().result).isEqualTo("11")
    }

    @Test
    @Order(4)
    fun `Solves Part 2`() {
        val initialSet = mutableSetOf<Char>().also { set -> ('a'..'z').forEach { char -> set.add(char) } }
        puzzleSolver.data.sumOf {
            println("> $it")
            println(">>folded ${it.fold(initialSet) { acc: MutableSet<Char>, chars -> (acc intersect chars.toSet()).toMutableSet() }}")
            it.fold(initialSet) { acc: MutableSet<Char>, chars -> (acc intersect chars.toSet()).toMutableSet() }.size
        }
        assertThat(puzzleSolver.solvePart2().result).isEqualTo("6")
    }
}
