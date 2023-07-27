package mpdev.springboot.aoc2020.solutions.day20

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

@Component
class Day20: PuzzleSolver() {


    final override fun setDay() {
        day = 20
    }

    init {
        setDay()
    }

    var result = 0L
    lateinit var jigsaw: JigsawSolver

    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureTimeMillis {
            jigsaw = JigsawSolver(inputData)
        }
        log.info("jigsaw size {}", jigsaw.tiles.size)
        return Pair(elapsed, "milli-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            jigsaw.solve()
            result = jigsaw.getSolutionCornerIds().fold(1L) { acc, i -> acc * i }
        }
        return PuzzlePartSolution(1, result.toString(), elapsed)
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {

        }
        return PuzzlePartSolution(2, result.toString(), elapsed)
    }
}