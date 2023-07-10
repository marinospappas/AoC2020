package mpdev.springboot.aoc2020.solutions.day02

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

@Component
class Day02: PuzzleSolver() {

    final override fun setDay() {
        day = 2
    }

    init {
        setDay()
    }

    var result = 0

    override fun initSolver() {}

    override fun solvePart1(): PuzzlePartSolution {
        val passwordRules = PasswordRules(inputData)
        val elapsed = measureTimeMillis {
            result = passwordRules.countValidPwds1()
        }
        return PuzzlePartSolution(1, result.toString(), elapsed)
    }

    override fun solvePart2(): PuzzlePartSolution {
        val passwordRules = PasswordRules(inputData)
        val elapsed = measureTimeMillis {
            result = passwordRules.countValidPwds2()
        }
        return PuzzlePartSolution(2, result.toString(), elapsed)
    }

}