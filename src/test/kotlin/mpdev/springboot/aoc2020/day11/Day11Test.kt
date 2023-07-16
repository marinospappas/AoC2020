package mpdev.springboot.aoc2020.day11

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day11.Day11
import mpdev.springboot.aoc2020.solutions.day11.Seat
import mpdev.springboot.aoc2020.solutions.day11.SeatPlan
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day11Test {

    private val day = 11                                    ///////// Update this for a new dayN test
    private val puzzleSolver = Day11()                      ///////// Update this for a new dayN test
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
    fun `Reads Input and Sets up Seats correctly`() {
        val seatplan = SeatPlan(inputLines)
        seatplan.printSeatPlan()
        assertThat(seatplan.maxX).isEqualTo(10)
        assertThat(seatplan.maxY).isEqualTo(10)
        assertThat(seatplan.grid.getData().size).isEqualTo(100)
        assertThat(seatplan.grid.countOf(Seat.EMPTY)).isEqualTo(71)
        assertThat(seatplan.grid.countOf(Seat.FLOOR)).isEqualTo(29)
    }

    @Test
    @Order(3)
    fun `Adjusts Seating as per Rules for Part 1`() {
        val seatplan = SeatPlan(inputLines)
        seatplan.printSeatPlan()
        assertThat(seatplan.countOf(Seat.EMPTY)).isEqualTo(71)
        val expected = listOf(71, 20, 51, 30, 37, 37)
        for (i in (1..6)) {
            seatplan.adjustSeating({ s, p -> seatplan.getAdjacentOccupiedPart1(s, p) }, 4)
            println("$i")
            seatplan.printSeatPlan()
            assertThat(seatplan.countOf(Seat.OCCUPIED)).isEqualTo(expected[i-1])
        }
    }

    @Test
    @Order(4)
    fun `Solves Part 1`() {
        val res = puzzleSolver.solvePart1()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("37")
    }

    @Test
    @Order(5)
    fun `Adjusts Seating as per Rules for Part 2`() {
        val seatplan = SeatPlan(inputLines)
        seatplan.printSeatPlan()
        assertThat(seatplan.countOf(Seat.EMPTY)).isEqualTo(71)
        var previousSeating = seatplan.grid.getData()
        for (i in (1..6)) {
            println("$i")
            seatplan.adjustSeating({ s, p -> seatplan.getAdjacentOccupiedPart2(s, p) }, 5)
            seatplan.printSeatPlan()
            previousSeating = seatplan.grid.getData()
        }
        assertThat(seatplan.countOf(Seat.OCCUPIED)).isEqualTo(26)
        assertThat(previousSeating.count { it.value == Seat.OCCUPIED }).isEqualTo(26)
    }

    @Test
    @Order(7)
    fun `Solves Part 2`() {
        val res = puzzleSolver.solvePart2()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("26")
    }

}
