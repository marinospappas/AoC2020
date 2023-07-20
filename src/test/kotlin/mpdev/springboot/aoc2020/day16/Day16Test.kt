package mpdev.springboot.aoc2020.day16

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day16.Day16
import mpdev.springboot.aoc2020.solutions.day16.TicketScanner
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day16Test {

    private val day = 16                                    ///////// Update this for a new dayN test
    private val puzzleSolver = Day16()                      ///////// Update this for a new dayN test
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
    fun `Reads Input and Sets up Rules and Tickets`() {
        val ticketScanner = TicketScanner(inputLines)
        ticketScanner.rules.forEach { println(it) }
        println()
        println(ticketScanner.ownTicket)
        println()
        ticketScanner.tickets.forEach { println(it) }
        assertThat(ticketScanner.rules.size).isEqualTo(3)
        assertThat(ticketScanner.tickets.size).isEqualTo(4)
    }

    @Test
    @Order(3)
    fun `Validates Tickets and calculates Error Rate`() {
        val ticketScanner = TicketScanner(inputLines)
        ticketScanner.updateInvalidTicketData()
        println(ticketScanner.tickets.count { it.invalidData.isEmpty() })
        ticketScanner.tickets.filter { it.invalidData.isEmpty() }.forEach { println(it) }
        println(ticketScanner.tickets.count { it.invalidData.isNotEmpty() })
        ticketScanner.tickets.filter { it.invalidData.isNotEmpty() }.forEach { println(it) }
        assertThat(ticketScanner.tickets.count { it.invalidData.isEmpty() }).isEqualTo(1)
        val errorRate = ticketScanner.tickets.filter { it.invalidData.isNotEmpty() }.sumOf { it.invalidData.sum() }
        println(errorRate)
        assertThat(errorRate).isEqualTo(71)
    }

    @Test
    @Order(4)
    fun `Solves Part 1`() {
        val res = puzzleSolver.solvePart1()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("71")
    }

    @Test
    @Order(5)
    fun `Decodes Ticket Fields`() {
        val ticketScanner = TicketScanner(part2Input())
        ticketScanner.rules.forEach { println(it) }
        println(ticketScanner.ownTicket)
        ticketScanner.updateInvalidTicketData()
        println(ticketScanner.tickets.count { it.invalidData.isEmpty() })
        ticketScanner.tickets.filter { it.invalidData.isEmpty() }.forEach { println(it) }
        ticketScanner.decodeTicketFields()
        ticketScanner.fieldsMap.forEach { println(it) }
        assertThat(ticketScanner.getOwnTicketField("row")).isEqualTo(11)
        assertThat(ticketScanner.getOwnTicketField("class")).isEqualTo(12)
        assertThat(ticketScanner.getOwnTicketField("seat")).isEqualTo(13)
    }

    private fun part2Input() = listOf(
        "class: 0-1 or 4-19",
        "row: 0-5 or 8-19",
        "seat: 0-13 or 16-19",
        "",
        "your ticket:",
        "11,12,13",
        "",
        "nearby tickets:",
        "3,9,18",
        "15,1,5",
        "5,14,9"
    )
    @Test
    @Order(8)
    @Disabled
    fun `Solves Part 2`() {
        val res = puzzleSolver.solvePart2()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("expected")
    }

}
