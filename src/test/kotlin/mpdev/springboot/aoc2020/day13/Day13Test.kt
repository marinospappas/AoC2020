package mpdev.springboot.aoc2020.day13

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day13.BusTimeTable
import mpdev.springboot.aoc2020.solutions.day13.Day13
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day13Test {

    private val day = 13                                    ///////// Update this for a new dayN test
    private val puzzleSolver = Day13()                      ///////// Update this for a new dayN test
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
    fun `Reads Input and Sets up Bus Time Table correctly`() {
        println(inputLines)
        assertThat(inputLines.size).isEqualTo(2)
        val timetable = BusTimeTable(inputLines[1])
        val departureTime = inputLines[0].toInt()
        timetable.timetableData.forEach { println(it) }
        println(departureTime)
        assertThat(timetable.timetableData.size).isEqualTo(5)
        assertTrue(timetable.timetableData.values.all { it == listOf<Int>() })
        assertThat(timetable.timetableData.keys).isEqualTo(setOf(7,13,59,31,19))
        assertThat(departureTime).isEqualTo(939)
    }

    @Test
    @Order(3)
    fun `Fills Timetable`() {
        val timetable = BusTimeTable(inputLines[1])
        val departureTime = inputLines[0].toInt()
        timetable.fillTimetable(departureTime)
        timetable.timetableData.forEach { println(it) }
    }

    @Test
    @Order(4)
    fun `Solves Part 1`() {
        val res = puzzleSolver.solvePart1()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("295")
    }

    @Test
    @Order(5)
    fun `Calculates Time for Part 2`() {
        val timetable = BusTimeTable(inputLines[1])
        println(timetable.busSchedule)
        assertThat(timetable.busSchedule.size).isEqualTo(5)
        timetable.busesSequence(4).take(5).forEach { println("[$it]     ${it*19}") }
        assertThat(timetable.busesSequence(4).take(1).first() * 19 - 7).isEqualTo(1068781)
    }

    @Test
    @Order(7)
    fun `Solves Part 2`() {
        val res = puzzleSolver.solvePart2()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("1068781")
    }

}
