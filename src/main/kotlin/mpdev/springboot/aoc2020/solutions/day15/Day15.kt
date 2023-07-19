package mpdev.springboot.aoc2020.solutions.day15

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

@Component
class Day15: PuzzleSolver() {


    final override fun setDay() {
        day = 15
    }

    init {
        setDay()
    }

    var result = 0
    lateinit var memoryGame: MemoryGame

    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureTimeMillis {
            memoryGame = MemoryGame(inputData)
        }
        return Pair(elapsed, "milli-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            repeat(2020) { result = memoryGame.playRound() }
        }
        log.info("part 1 distinct numbers: {}, most frequent number: {} ({}) times, least frequent number {} ({}) times",
            memoryGame.numbersIndex.size,
            memoryGame.numbersIndex.maxBy { it.value.timesSpoken }.key,
            memoryGame.numbersIndex.maxBy { it.value.timesSpoken }.value.timesSpoken,
            memoryGame.numbersIndex.minBy { it.value.timesSpoken }.key,
            memoryGame.numbersIndex.minBy { it.value.timesSpoken }.value.timesSpoken)
        return PuzzlePartSolution(1, result.toString(), elapsed)
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            memoryGame = MemoryGame(inputData)
            repeat(30_000_000) { result = memoryGame.playRound() }
        }
        log.info("part 2 distinct numbers: {}, most frequent number: {} ({}) times, least frequent number {} ({}) times",
            memoryGame.numbersIndex.size,
            memoryGame.numbersIndex.maxBy { it.value.timesSpoken }.key,
            memoryGame.numbersIndex.maxBy { it.value.timesSpoken }.value.timesSpoken,
            memoryGame.numbersIndex.minBy { it.value.timesSpoken }.key,
            memoryGame.numbersIndex.minBy { it.value.timesSpoken }.value.timesSpoken)
        return PuzzlePartSolution(2, result.toString(), elapsed)
    }

}