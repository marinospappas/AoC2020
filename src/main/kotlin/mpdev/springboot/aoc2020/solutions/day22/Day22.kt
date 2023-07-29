package mpdev.springboot.aoc2020.solutions.day22

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureNanoTime

@Component
class Day22: PuzzleSolver() {

    final override fun setDay() {
        day = 22
    }

    init {
        setDay()
    }

    var result = 0L
    lateinit var deck: DeckOfCards

    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureNanoTime {
            deck = DeckOfCards(inputData)
        }
        return Pair(elapsed/1000, "micro-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureNanoTime {
            while(deck.playRound()) {}
            result = deck.getWinnersScore()
        }
        return PuzzlePartSolution(1, result.toString(), elapsed/1000, "micro-sec")
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureNanoTime {
        }
        return PuzzlePartSolution(2, result.toString(), elapsed/1000, "micro-sec")
    }
}