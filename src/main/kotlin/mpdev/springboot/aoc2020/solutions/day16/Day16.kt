package mpdev.springboot.aoc2020.solutions.day16

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

@Component
class Day16: PuzzleSolver() {


    final override fun setDay() {
        day = 16
    }

    init {
        setDay()
    }

    var result = 0
    lateinit var ticketScanner: TicketScanner

    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureTimeMillis {
            ticketScanner = TicketScanner(inputData)
        }
        return Pair(elapsed, "milli-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            ticketScanner.updateInvalidTicketData()
            result = ticketScanner.tickets
                .filter { it.invalidData.isNotEmpty() }
                .sumOf { it.invalidData.sum() }
        }
        return PuzzlePartSolution(1, result.toString(), elapsed)
    }

    override fun solvePart2(): PuzzlePartSolution {
        val result2: Long
        val elapsed = measureTimeMillis {
            ticketScanner.decodeTicketFields()
            result2 = ticketScanner.rules.map { it.name }
                .filter { it.startsWith("departure") }
                .map { ticketScanner.getOwnTicketField(it) }
                .fold(1L) { acc, i -> acc * i }
        }
        return PuzzlePartSolution(2, result2.toString(), elapsed)
    }
}