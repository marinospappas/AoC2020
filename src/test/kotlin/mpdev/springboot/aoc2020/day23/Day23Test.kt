package mpdev.springboot.aoc2020.day23

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day23.Day23
import mpdev.springboot.aoc2020.solutions.day23.RotatingCups
import mpdev.springboot.aoc2020.utils.AocException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day23Test {

    private val day = 23                                    ///////// Update this for a new dayN test
    private val puzzleSolver = Day23()                      ///////// Update this for a new dayN test
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
    fun `Reads Input and Sets up Rotating Cups`() {
        val rotatingCups = RotatingCups(inputLines)
        println(rotatingCups)
        assertThat(rotatingCups.size).isEqualTo(9)
        assertThat(rotatingCups.maxCupId).isEqualTo(9)
        println("current cup: ${rotatingCups.current}")
        assertThat(rotatingCups.current.id).isEqualTo(3)
        println("cups list")
        println(rotatingCups.getCup(3))
        println(rotatingCups.getCup(8))
        println(rotatingCups.getCup(9))
        println(rotatingCups.getCup(1))
        println(rotatingCups.getCup(2))
        println(rotatingCups.getCup(5))
        println(rotatingCups.getCup(4))
        println(rotatingCups.getCup(6))
        println(rotatingCups.getCup(7))
        assertThat(rotatingCups.getCup(3).next.id).isEqualTo(8)
        assertThat(rotatingCups.getCup(8).next.id).isEqualTo(9)
        assertThat(rotatingCups.getCup(9).next.id).isEqualTo(1)
        assertThat(rotatingCups.getCup(1).next.id).isEqualTo(2)
        assertThat(rotatingCups.getCup(2).next.id).isEqualTo(5)
        assertThat(rotatingCups.getCup(5).next.id).isEqualTo(4)
        assertThat(rotatingCups.getCup(4).next.id).isEqualTo(6)
        assertThat(rotatingCups.getCup(6).next.id).isEqualTo(7)
        assertThat(rotatingCups.getCup(7).next.id).isEqualTo(3)
        assertThrows<AocException> { rotatingCups.getCup(10) }
    }

    @Test
    @Order(3)
    fun `Plays Round and Rearranges Cups as per rules`() {
        val rotatingCups = RotatingCups(inputLines)
        println(rotatingCups)
        repeat(10) {
            println("round ${it+1}")
            rotatingCups.playRound()
            println(rotatingCups)
        }
        assertThat(rotatingCups.cupsToList(1)).isEqualTo(listOf(1,9,2,6,5,8,3,7,4))
    }

    @Test
    @Order(6)
    fun `Solves Part 1`() {
        val res = puzzleSolver.solvePart1()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("67384529")
    }

    @Test
    @Order(9)
    fun `Solves Part 2`() {
        val res = puzzleSolver.solvePart2()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("149245887792")
    }
}
