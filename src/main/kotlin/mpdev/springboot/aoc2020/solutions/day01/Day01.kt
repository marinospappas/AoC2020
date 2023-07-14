package mpdev.springboot.aoc2020.solutions.day01

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import mpdev.springboot.aoc2020.utils.get2SumComponents
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

    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureNanoTime {
            intList = inputData.map { Integer.parseInt(it) }.sorted()
        }
        return Pair(elapsed/1000, "micro-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureNanoTime {
            val (first, second) = get2SumComponents(intList, 2020)
            result = first * second
        }
        return PuzzlePartSolution(1, result.toString(), elapsed/1000, "micro-sec")
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureNanoTime {
            for (i in intList) {
                val (first, second) = get2SumComponents(intList - setOf(i), 2020-i)
                if (first >= 0) {
                    result = i * first * second
                    break
                }
            }
        }
        return PuzzlePartSolution(2, result.toString(), elapsed/1000, "micro-sec")
    }

}