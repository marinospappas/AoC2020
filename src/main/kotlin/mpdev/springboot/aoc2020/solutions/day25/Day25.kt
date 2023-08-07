package mpdev.springboot.aoc2020.solutions.day25

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

@Component
class Day25: PuzzleSolver() {

    final override fun setDay() {
        day = 25
    }

    init {
        setDay()
    }

    var result = 0L
    lateinit var crypto: Crypto

    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureTimeMillis {
            crypto = Crypto(inputData)
        }
        return Pair(elapsed, "milli-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
           val serverLoopSize = crypto.getLoopSize(crypto.serverPublicKey)
           result = crypto.createKey(crypto.clientPublicKey, serverLoopSize)
        }
        return PuzzlePartSolution(1, result.toString(), elapsed)
    }

    override fun solvePart2(): PuzzlePartSolution {
        return PuzzlePartSolution(2, "All 50 Done!!", 0L)
    }
}