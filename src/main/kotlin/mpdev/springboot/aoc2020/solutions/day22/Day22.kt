package mpdev.springboot.aoc2020.solutions.day22

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

@Component
class Day22: PuzzleSolver() {

    final override fun setDay() {
        day = 22
    }

    init {
        setDay()
    }

    var result = 0
    lateinit var deck: DeckOfCards

    override fun initSolver(): Pair<Long,String> {
        return Pair(0, "milli-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            deck = DeckOfCards(inputData)
            while(deck.playRoundSimpleDeck()) {}
            result = deck.getWinnersScore()
        }
        log.info("part 1, played {} rounds, winner is player{}", deck.round, deck.getWinner())
        return PuzzlePartSolution(1, result.toString(), elapsed)
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            deck = DeckOfCards(inputData)
            while(deck.playRoundRecursiveDeck()) {}
            result = deck.getWinnersScore()
        }
        log.info("part 2, played {} rounds, winner is player{}", deck.round, deck.getWinner())
        return PuzzlePartSolution(2, result.toString(), elapsed)
    }
}