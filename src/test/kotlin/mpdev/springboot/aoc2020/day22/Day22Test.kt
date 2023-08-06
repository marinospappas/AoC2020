package mpdev.springboot.aoc2020.day22

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day22.Day22
import mpdev.springboot.aoc2020.solutions.day22.DeckOfCards
import mpdev.springboot.aoc2020.solutions.day22.Player
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.math.BigInteger


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
        assertThat(deck.player[0].cardsToList().sum()).isEqualTo(21)
        assertThat(deck.player[1].cardsToList().sum()).isEqualTo(34)
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
    fun `Player cards are updated correctly`() {
        var player = Player()
        player.takeCard(1).takeCard(2).takeCard(3).takeCard(4)
        assertThat(player.numberOfCards()).isEqualTo(4)
        assertThat(player.cardsToList()).isEqualTo(listOf(1,2,3,4))
        assertThat(player.getCards()).isEqualTo(BigInteger.valueOf(16_777_216L * 4 + 65536 * 3 + 256 * 2 + 1))

        player = Player(BigInteger.valueOf(16_777_216L * 4 + 65536 * 3 + 256 * 2 + 1))
        assertThat(player.numberOfCards()).isEqualTo(4)
        assertThat(player.cardsToList()).isEqualTo(listOf(1,2,3,4))

        val cardsSublist = player.sublistNCards(2)
        assertThat(cardsSublist).isEqualTo(BigInteger.valueOf(256 * 2 + 1))
    }

    @Test
    @Order(8)
    fun `Plays Recursive rounds and identies winner`() {
        val deck = DeckOfCards(inputLines)
        while(deck.playRoundRecursiveDeck()) {
            println("Game #${deck.gameId}")
            deck.print()
        }
        println("Game #${deck.gameId}")
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
