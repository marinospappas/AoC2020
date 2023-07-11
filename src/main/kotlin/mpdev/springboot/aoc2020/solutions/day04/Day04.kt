package mpdev.springboot.aoc2020.solutions.day04

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import mpdev.springboot.aoc2020.solutions.day04.ValidationGroups.*
import org.springframework.stereotype.Component
import kotlin.system.measureNanoTime

@Component
class Day04: PuzzleSolver() {

    final override fun setDay() {
        day = 4
    }

    init {
        setDay()
    }

    var result = 0

    override fun initSolver() {}

    override fun solvePart1(): PuzzlePartSolution {
        val passportDb = PassportDb(inputData)
        val elapsed = measureNanoTime {
            result = passportDb.data.count { passport -> passportDb.validatePassport(passport, Part1::class.java).isEmpty()  }
        }
        return PuzzlePartSolution(1, result.toString(), elapsed/1000, "micro-sec")
    }

    override fun solvePart2(): PuzzlePartSolution {
        val passportDb = PassportDb(inputData)
        val elapsed = measureNanoTime {
            result = passportDb.data.count { passport -> passportDb.validatePassport(passport, Part2::class.java).isEmpty()  }
        }
        return PuzzlePartSolution(2, result.toString(), elapsed/1000, "micro-sec")
    }

}