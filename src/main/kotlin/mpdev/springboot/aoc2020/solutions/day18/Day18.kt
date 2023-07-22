package mpdev.springboot.aoc2020.solutions.day18

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

@Component
class Day18: PuzzleSolver() {


    final override fun setDay() {
        day = 18
    }

    init {
        setDay()
    }

    var result = 0L
    lateinit var math: Math

    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureTimeMillis {
            math = Math(inputData)
        }
        return Pair(elapsed, "milli-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            result = math.expressionList.map { expr -> math.calculate(expr) }.sum()
        }
        return PuzzlePartSolution(1, result.toString(), elapsed)
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {

        }
        return PuzzlePartSolution(2, result.toString(), elapsed)
    }
}