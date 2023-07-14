package mpdev.springboot.aoc2020.solutions.day09

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import mpdev.springboot.aoc2020.utils.get2SumComponents
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

@Component
class Day09: PuzzleSolver() {

    final override fun setDay() {
        day = 9
    }

    init {
        setDay()
    }

    var result = 0L
    lateinit var data: LongArray
    var preambleSize = 25

    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureTimeMillis {
            data = inputData.map { it.toLong() }.toLongArray()
        }
        return Pair(elapsed, "milli-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            val (invalidNumber, _) = getFirstInvalidNumber(data, preambleSize)
            result = invalidNumber
        }
        return PuzzlePartSolution(1, result.toString(), elapsed)
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            val (invalidNumber, invIndex) = getFirstInvalidNumber(data, preambleSize)
            result = getConsecutiveNumbersThatAddToInvNumber(data, preambleSize, invalidNumber, invIndex)
                .let { it.first() + it.last() }
        }
        return PuzzlePartSolution(2, result.toString(), elapsed)
    }


    fun getFirstInvalidNumber(data: LongArray, preambleSize: Int): Pair<Long,Int> {
        for (i in preambleSize until data.size)
            if (get2SumComponents(
                    data.sliceArray(IntRange(i - preambleSize, i - 1)).toList(), data[i], false
                ).first < 0)
                return Pair(data[i],i)
        return Pair(-1L,-1)
    }

    fun getConsecutiveNumbersThatAddToInvNumber(data: LongArray, preambleSize: Int, invNumber: Long, invIndex: Int):
            List<Long> {
        var startIndex: Int
        for (i in invIndex-1 downTo 2)
            if (numbersAddUp(data, i, invNumber).also { startIndex = it } >= 0)
                return data.sliceArray(IntRange(startIndex,i)).toList().sorted()
        return listOf(0,0)
    }

    private fun numbersAddUp(data: LongArray, index: Int, sum: Long): Int {
        for (i in index-1 downTo 0) {
            if (data.sliceArray(IntRange(i, index)).sum() == sum)
                return i
        }
        return -1
    }

}