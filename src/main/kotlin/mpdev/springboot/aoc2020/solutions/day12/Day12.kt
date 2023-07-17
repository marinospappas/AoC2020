package mpdev.springboot.aoc2020.solutions.day12

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureNanoTime

@Component
class Day12: PuzzleSolver() {


    final override fun setDay() {
        day = 12
    }

    init {
        setDay()
    }

    var result = 0
    lateinit var navigation: Navigation

    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureNanoTime {
            navigation = Navigation(inputData)
        }
        return Pair(elapsed/1000, "micro-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureNanoTime {
            navigation.navigate()
            result = navigation.data.distanceCovered()
        }
        return PuzzlePartSolution(1, result.toString(), elapsed/1000, "micro-sec")
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureNanoTime {
            navigation = Navigation(inputData, 2)
            navigation.navigate()
            result = navigation.data.distanceCovered()
        }
        return PuzzlePartSolution(2, result.toString(), elapsed/1000, "micro-sec")
    }

}