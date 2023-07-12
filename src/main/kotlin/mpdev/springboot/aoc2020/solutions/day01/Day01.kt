package mpdev.springboot.aoc2020.solutions.day01

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureNanoTime

@Component
class Day01: PuzzleSolver() {

    final override fun setDay() {
        day = 1
    }

    init {
        setDay()
    }

    var result = 0
    lateinit var intList: List<Int>

    override fun initSolver(): Pair<Int,String> {
        val elapsed = measureNanoTime {
            intList = inputData.map { Integer.parseInt(it) }
        }
        return Pair((elapsed/1000).toInt(), "micro-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureNanoTime {
            val res = find2ComponentsForSum(intList, 2020)
            result = res.first * res.second
        }
        return PuzzlePartSolution(1, result.toString(), elapsed/1000, "micro-sec")
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureNanoTime {
            for (i in intList) {
                val res = find2ComponentsForSum(intList, 2020-i)
                if (res.first >= 0) {
                    result = i * res.first * res.second
                    break
                }
            }
        }
        return PuzzlePartSolution(2, result.toString(), elapsed/1000, "micro-sec")
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