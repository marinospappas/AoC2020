package mpdev.springboot.aoc2020.day12

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day12.Day12
import mpdev.springboot.aoc2020.solutions.day12.Direction
import mpdev.springboot.aoc2020.solutions.day12.Navigation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day12Test {

    private val day = 12                                    ///////// Update this for a new dayN test
    private val puzzleSolver = Day12()                      ///////// Update this for a new dayN test
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
    fun `Reads Input and Sets up Navigation Data correctly`() {
        val navigation = Navigation(inputLines)
        println(navigation.data)
        navigation.instructions.forEach { println(it) }
        assertThat(navigation.instructions.size).isEqualTo(8)
    }

    @Test
    @Order(3)
    fun `Navigates Following Instructions for Part 1`() {
        val navigation = Navigation(inputLines)
        println(navigation.data)
        navigation.instructions.forEach { println(it) }
        navigation.navigate()
        println(navigation.data)
        assertThat(navigation.data.position.x).isEqualTo(17)
        assertThat(navigation.data.position.y).isEqualTo(-8)
        assertThat(navigation.data.direction).isEqualTo(Direction.SOUTH)
        assertThat(navigation.data.distanceCovered()).isEqualTo(25)
    }

    @Test
    @Order(4)
    fun `Solves Part 1`() {
        val res = puzzleSolver.solvePart1()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("25")
    }

    @Test
    @Order(4)
    fun `Navigates Following Instructions for Part 2`() {
        val navigation = Navigation(inputLines, 2)
        println(navigation.data)
        navigation.instructions.forEach { println(it) }
        navigation.navigate()
        println(navigation.data)
        assertThat(navigation.data.position.x).isEqualTo(214)
        assertThat(navigation.data.position.y).isEqualTo(-72)
        assertThat(navigation.data.distanceCovered()).isEqualTo(286)
    }

    @Test
    @Order(7)
    fun `Solves Part 2`() {
        val res = puzzleSolver.solvePart2()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("286")
    }

}
