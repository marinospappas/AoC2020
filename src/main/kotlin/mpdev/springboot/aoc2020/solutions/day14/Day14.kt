package mpdev.springboot.aoc2020.solutions.day14

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

@Component
class Day14: PuzzleSolver() {


    final override fun setDay() {
        day = 14
    }

    init {
        setDay()
    }

    var result = 0L
    lateinit var decoder: Decoder

    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureTimeMillis {
            decoder = Decoder(inputData)
        }
        return Pair(elapsed, "milli-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            decoder.updateMemory()
            result = decoder.mem.values.sum()
        }
        return PuzzlePartSolution(1, result.toString(), elapsed)
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            decoder = Decoder(inputData, 2)
            decoder.updateMemory()
            result = decoder.mem.values.sum()
        }
        return PuzzlePartSolution(2, result.toString(), elapsed)
    }

}