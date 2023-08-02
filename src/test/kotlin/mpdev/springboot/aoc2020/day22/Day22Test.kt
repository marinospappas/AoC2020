package mpdev.springboot.aoc2020.day22

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day22.Day22
import mpdev.springboot.aoc2020.solutions.day22.DeckOfCards
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day22Test {

    private val day = 22                                    ///////// Update this for a new dayN test
    private val puzzleSolver = Day22()                      ///////// Update this for a new dayN test
    private val inputDataReader = InputDataReader("src/test/resources/inputdata/input")
    private var inputLines: List<String> = inputDataReader.read(day)

    @BeforeEach
    fun setup() {
        puzzleSolver.setDay()
        puzzleSolver.inputData = inputLines
        puzzleSolver.initSolver()
    }

    @Test
    @Order(1)
    fun `Sets Day correctly`() {
        assertThat(puzzleSolver.day).isEqualTo(day)
    }

    @Test
    @Order(2)
    fun `Reads Input and Sets up Deck and Players`() {
        val deck = DeckOfCards(inputLines)
        deck.print()
        assertThat(deck.player[0].numberOfCards()).isEqualTo(5)
        assertThat(deck.player[1].numberOfCards()).isEqualTo(5)
        assertThat(deck.player[0].getCards().sum()).isEqualTo(21)
        assertThat(deck.player[1].getCards().sum()).isEqualTo(34)
    }

    @Test
    @Order(3)
    fun `Plays rounds and identifies the winner of each round`() {
        val deck = DeckOfCards(inputLines)
        deck.print()
        while(deck.playRoundSimpleDeck())
            deck.print()
        deck.print()
        println("winner: ${deck.getWinner()}")
        val score = deck.getWinnersScore().also { println("score: $it") }
        assertThat(score).isEqualTo(306)
    }

    @Test
    @Order(6)
    fun `Solves Part 1`() {
        val res = puzzleSolver.solvePart1()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("306")
    }

    @Test
    @Order(7)
    fun `Plays Recursive rounds and identies winner`() {
        val deck = DeckOfCards(inputLines)
        while(deck.playRoundRecursiveDeck()) {
            println("Game #${deck.gameId}")
            deck.print()
        }
        deck.print()
        println("winner: ${deck.getWinner()}")
        val score = deck.getWinnersScore().also { println("score: $it") }
        assertThat(score).isEqualTo(291)
    }

    @Test
    @Order(9)
    fun `Solves Part 2`() {
        val res = puzzleSolver.solvePart2()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("291")
    }
}
