package mpdev.springboot.aoc2020.solutions.day23

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

@Component
class Day23: PuzzleSolver() {

    final override fun setDay() {
        day = 23
    }

    init {
        setDay()
    }

    var result = ""
    lateinit var rotatingCups: RotatingCups

    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureTimeMillis {
            rotatingCups = RotatingCups(inputData)
        }
        return Pair(elapsed, "milli-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        rotatingCups = RotatingCups(inputData)
        log.info("part 1, initial cups setup: {}", rotatingCups.toString())
        val elapsed = measureTimeMillis {
            repeat(100) { rotatingCups.playRound() }
        }
        log.info("part 1, final cups setup: {}", rotatingCups.toString())
        result = rotatingCups.cupsToList(1).joinToString("").substring(1)
        return PuzzlePartSolution(1, result, elapsed)
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            rotatingCups = RotatingCups(inputData, 2)
            log.info("part 2, initial cups setup: {} ...", rotatingCups.toString(30))
            repeat(10_000_000) {
                if ((it+1) % 1_000_000 == 0)
                    log.info("round ${it+1}")
                rotatingCups.playRound()
            }
        }
        log.info("part 2, final cups setup: {} ...", rotatingCups.toString(30))
        val cupsList = rotatingCups.cupsToList(1, 10)
        result = (cupsList[1].toLong() * cupsList[2].toLong()).toString()
        return PuzzlePartSolution(2, result, elapsed)
    }
}