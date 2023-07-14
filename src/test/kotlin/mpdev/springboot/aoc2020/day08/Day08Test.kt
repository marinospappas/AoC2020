package mpdev.springboot.aoc2020.day08

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day08.Day08
import mpdev.springboot.aoc2020.solutions.day08.GameConsole
import mpdev.springboot.aoc2020.solutions.day08.GameConsole.Companion.CODE_ERROR
import mpdev.springboot.aoc2020.solutions.day08.GameConsole.Companion.CODE_SUCCESS
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day08Test {

    private val day = 8                                     ///////// Update this for a new dayN test
    private val puzzleSolver = Day08()                        ///////// Update this for a new dayN test
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
    fun `Reads Input and Sets up Boot Program correctly`() {
        val gameConsole = GameConsole(inputLines)
        gameConsole.bootProgram.forEach { println("${it.first.name} ${it.second}") }
        assertThat(gameConsole.bootProgram.size).isEqualTo(9)
    }

    @Test
    @Order(3)
    fun `Detects Loop and Reports Accumulator`() {
        val gameConsole = GameConsole(inputLines)
        val exitCode = gameConsole.runBootProg()
        println("Acc: ${gameConsole.getAcc()}")
        assertThat(exitCode).isEqualTo(CODE_ERROR)
        assertThat(gameConsole.getAcc()).isEqualTo(5)
        assertThat(gameConsole.getPc()).isEqualTo(1)
    }

    @Test
    @Order(4)
    fun `Solves Part 1`() {
        assertThat(puzzleSolver.solvePart1().result).isEqualTo("5")
    }

    @Test
    @Order(5)
    fun `Repairs Program and Terminates Successfully`() {
        val gameConsole = GameConsole(inputLines)
        val exitCode = gameConsole.repairBoorProg()
        println("Acc: ${gameConsole.getAcc()}")
        assertThat(exitCode).isEqualTo(CODE_SUCCESS)
        assertThat(gameConsole.getAcc()).isEqualTo(8)
        assertThat(gameConsole.getPc()).isEqualTo(9)
    }

    @Test
    @Order(6)
    fun `Solves Part 2`() {
        assertThat(puzzleSolver.solvePart2().result).isEqualTo("8")
    }

}
