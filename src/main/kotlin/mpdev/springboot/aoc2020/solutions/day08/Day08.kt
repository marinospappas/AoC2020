package mpdev.springboot.aoc2020.solutions.day08

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

@Component
class Day08: PuzzleSolver() {

    final override fun setDay() {
        day = 8
    }

    init {
        setDay()
    }

    var result = 0
    lateinit var gameConsole: GameConsole


    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureTimeMillis {
            gameConsole = GameConsole(inputData)
        }
        return Pair(elapsed, "milli-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureNanoTime {
            gameConsole.runBootProg()
            result = gameConsole.getAcc()
        }
        return PuzzlePartSolution(1, result.toString(), elapsed/1000, "micro-sec")
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureNanoTime {
            gameConsole.repairBoorProg()
            result = gameConsole.getAcc()
        }
        return PuzzlePartSolution(2, result.toString(), elapsed/1000, "micro-sec")
    }

}