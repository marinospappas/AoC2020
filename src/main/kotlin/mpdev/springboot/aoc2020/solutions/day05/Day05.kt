package mpdev.springboot.aoc2020.solutions.day05

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureNanoTime

@Component
class Day05: PuzzleSolver() {

    final override fun setDay() {
        day = 5
    }

    init {
        setDay()
    }

    var result = 0
    lateinit var seatData: Set<Int>

    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureNanoTime {
            seatData = inputData
                // the input is actually a binary number: F = 0, B = 1, L = 0, R = 1
                .map {
                    it.replace("F", "0").replace("B", "1")
                        .replace("R", "1").replace("L", "0")
                }
                .map { it.toInt(2) }
                .sorted()
                .reversed()
                .toSet()
        }
        return Pair(elapsed/1000, "micro-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureNanoTime {
            // the number form the first 7 bits times 8 plus the number of the lower 3 bits
            // is actually the original number!! no transformation needed
            result = seatData.first()
        }
        return PuzzlePartSolution(1, result.toString(), elapsed/1000, "micro-secs")
    }

    override fun solvePart2(): PuzzlePartSolution {
        result = 0
        val elapsed = measureNanoTime {
            var prevSeat = 0
            for (seat in seatData) {
                if (prevSeat - seat == 2)
                    result = (seat + prevSeat) / 2
                prevSeat = seat
            }
        }
        return PuzzlePartSolution(2, result.toString(), elapsed/1000, "micro-secs")
    }

}