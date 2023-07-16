package mpdev.springboot.aoc2020.solutions.day11

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import mpdev.springboot.aoc2020.utils.AocException
import org.springframework.stereotype.Component
import java.awt.Point
import kotlin.system.measureTimeMillis

@Component
class Day11: PuzzleSolver() {

    companion object {
        const val MAX_LOOP = 100
        const val MIN_OCCUPIED_P1 = 4
        const val MIN_OCCUPIED_P2 = 5
    }

    final override fun setDay() {
        day = 11
    }

    init {
        setDay()
    }

    var result = 0
    lateinit var seatplan: SeatPlan


    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureTimeMillis {
            seatplan = SeatPlan(inputData)
        }
        return Pair(elapsed, "milli-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            var prevSeating = mapOf<Point,Seat>()
            for (i in (1..MAX_LOOP)) {
                if (i == MAX_LOOP)
                    throw AocException("reached max iteration limit")
                seatplan.adjustSeating({ s, p -> seatplan.getAdjacentOccupiedPart1(s, p) }, MIN_OCCUPIED_P1)
                if (seatplan.grid.getData() == prevSeating) {
                    result = seatplan.countOf(Seat.OCCUPIED)
                    log.info("number of iterations: $i")
                    break
                }
                prevSeating = seatplan.grid.getData()
            }
        }
        return PuzzlePartSolution(1, result.toString(), elapsed)
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            seatplan = SeatPlan(inputData)      // re-initialise grid
            var prevSeating = mapOf<Point,Seat>()
            for (i in (1..MAX_LOOP)) {
                if (i == MAX_LOOP)
                    throw AocException("reached max iteration limit")
                seatplan.adjustSeating({ s, p -> seatplan.getAdjacentOccupiedPart2(s, p) }, MIN_OCCUPIED_P2)
                if (seatplan.grid.getData() == prevSeating) {
                    result = seatplan.countOf(Seat.OCCUPIED)
                    log.info("number of iterations: $i")
                    break
                }
                prevSeating = seatplan.grid.getData()
            }
        }
        return PuzzlePartSolution(2, result.toString(), elapsed)
    }

}