package mpdev.springboot.aoc2020.day17

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day17.ConwayCubes
import mpdev.springboot.aoc2020.solutions.day17.Cube
import mpdev.springboot.aoc2020.solutions.day17.Day17
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day17Test {

    private val day = 17                                    ///////// Update this for a new dayN test
    private val puzzleSolver = Day17()                      ///////// Update this for a new dayN test
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
    fun `Reads Input and Sets up Cubes initial state`() {
        val cubes = ConwayCubes(inputLines)
        cubes.printCubes()
        cubes.grid.getDimensions().forEach { print("$it  ") }
        println()
        println(cubes.grid.getData())
        assertThat(cubes.grid.getData().size).isEqualTo(9)
        assertThat(cubes.countOf(Cube.ACTIVE)).isEqualTo(5)
    }

    @Test
    @Order(3)
    fun `Runs Cycles and Activates and Deactivates Cubes`() {
        val cubes = ConwayCubes(inputLines)
        cubes.printCubes()
        val expected = listOf(11,21,38)
        repeat(3) {
            cubes.runCycle()
            println("\ncycle ${it+1}")
            cubes.printCubes()
            assertThat(cubes.countOf(Cube.ACTIVE)).isEqualTo(expected[it])
        }
    }

    @Test
    @Order(4)
    fun `Solves Part 1`() {
        val res = puzzleSolver.solvePart1()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("112")
    }

    @Test
    @Order(5)
    fun `Reads Input and Sets up Cubes initial state Part 2`() {
        val cubes = ConwayCubes(inputLines, 2)
        cubes.printCubes()
        cubes.grid.getDimensions().forEach { print("$it  ") }
        println(cubes.grid.getData())
        assertThat(cubes.grid.getData().size).isEqualTo(9)
        assertThat(cubes.countOf(Cube.ACTIVE)).isEqualTo(5)
    }

    @Test
    @Order(3)
    fun `Runs Cycles and Activates and Deactivates Cubes Part 2`() {
        val cubes = ConwayCubes(inputLines, 2)
        cubes.printCubes()
        val expected = listOf(29,60)
        repeat(2) {
            cubes.runCycle()
            println("\ncycle ${it+1}")
            cubes.printCubes()
            assertThat(cubes.countOf(Cube.ACTIVE)).isEqualTo(expected[it])
        }
    }

    @Test
    @Order(8)
    fun `Solves Part 2`() {
        val res = puzzleSolver.solvePart2()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("848")
    }
}
