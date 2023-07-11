package mpdev.springboot.aoc2020.day04

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day04.Day04
import mpdev.springboot.aoc2020.solutions.day04.PassportDb
import mpdev.springboot.aoc2020.solutions.day04.ValidationGroups.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day04Test {

    private val day = 4                                     ///////// Update this for a new dayN test
    private val puzzleSolver = Day04()                        ///////// Update this for a new dayN test
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
    fun `Reads Input and Sets up Passport Db correctly`() {
        val passportDb = PassportDb(inputLines)
        passportDb.data.forEach { passport -> println(passport)  }
        assertThat(passportDb.data.size).isEqualTo(4)
    }

    @Test
    @Order(3)
    fun `Validates Passports Part 1`() {
        val passportDb = PassportDb(inputLines)
        val res = passportDb.data.count { passport -> passportDb.validatePassport(passport, Part1::class.java).isEmpty() }
        assertThat(res).isEqualTo(2)
    }

    @Test
    @Order(4)
    fun `Solves Part 1`() {
        assertThat(puzzleSolver.solvePart1().result).isEqualTo("2")
    }

    @Test
    @Order(5)
    fun `Validates Passports Part 2`() {
        val passportDb = PassportDb(inputLines)
        val res = passportDb.data.count { passport -> passportDb.validatePassport(passport, Part2::class.java).isEmpty() }
        assertThat(res).isEqualTo(2)
    }

    @Test
    @Order(6)
    fun `Solves Part 2`() {
        assertThat(puzzleSolver.solvePart2().result).isEqualTo("336")
    }
}
