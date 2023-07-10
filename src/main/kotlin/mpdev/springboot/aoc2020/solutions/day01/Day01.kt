package mpdev.springboot.aoc2020.solutions.day01

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

@Component
class Day01: PuzzleSolver() {

    final override fun setDay() {
        day = 1
    }

    init {
        setDay()
    }

    var result = 0

    override fun initSolver() {}

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            val intList = inputData.map { Integer.parseInt(it) }
            val res = find2ComponentsForSum(intList, 2020)
            result = res.first * res.second
        }
        return PuzzlePartSolution(1, result.toString(), elapsed)
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            val intList = inputData.map { Integer.parseInt(it) }
            for (i in intList) {
                val res = find2ComponentsForSum(intList, 2020-i)
                if (res.first >= 0) {
                    result = i * res.first * res.second
                    break
                }
            }
        }
        return PuzzlePartSolution(2, result.toString(), elapsed)
    }

    private fun find2ComponentsForSum(intList: List<Int>, sum: Int): Pair<Int,Int> {
        val list1 = intList.filter { it >= sum / 2 }
        val list2 = intList.filter { it < sum / 2 }
        for (i in list1)
            for (j in list2)
                if (i + j == sum)
                    return Pair(i, j)
        return Pair(-1,-1)
    }
}