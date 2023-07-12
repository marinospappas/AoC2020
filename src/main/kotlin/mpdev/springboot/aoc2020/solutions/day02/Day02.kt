package mpdev.springboot.aoc2020.solutions.day02

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureNanoTime

@Component
class Day02: PuzzleSolver() {

    final override fun setDay() {
        day = 2
    }

    init {
        setDay()
    }

    var result = 0
    lateinit var passwordRules: PasswordRules

    override fun initSolver(): Pair<Int,String> {
        val elapsed = measureNanoTime {
            passwordRules = PasswordRules(inputData)
        }
        return Pair((elapsed/1000).toInt(), "micro-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureNanoTime {
            result = passwordRules.countValidPwds1()
        }
        return PuzzlePartSolution(1, result.toString(), elapsed/1000, "micro-sec")
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureNanoTime {
            result = passwordRules.countValidPwds2()
        }
        return PuzzlePartSolution(2, result.toString(), elapsed/1000, "micro-sec")
    }

}