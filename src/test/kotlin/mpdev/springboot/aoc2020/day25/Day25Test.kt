package mpdev.springboot.aoc2020.day25

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day25.Crypto
import mpdev.springboot.aoc2020.solutions.day25.Day25
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day25Test {

    private val day = 25                                    ///////// Update this for a new dayN test
    private val puzzleSolver = Day25()                      ///////// Update this for a new dayN test
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
    fun `Generates Public and Private Keys`() {
        val crypto = Crypto(inputLines)
        repeat(11) { println("${it+1}: ${crypto.createKey(Crypto.SUBJECT_NUMBER, (it + 1).toLong())}") }
        val clientLoopSize = crypto.getLoopSize(crypto.clientPublicKey).also { println("client loop size $it") }
        val serverLoopSize = crypto.getLoopSize(crypto.serverPublicKey).also { println("server loop size $it") }
        assertThat(clientLoopSize).isEqualTo(8)
        assertThat(serverLoopSize).isEqualTo(11)
        val clientPrivateKey = crypto.createKey(crypto.clientPublicKey, serverLoopSize).also { println("client private key $it") }
        val serverPrivateKey = crypto.createKey(crypto.serverPublicKey, clientLoopSize).also { println("server private key $it") }
        assertThat(clientPrivateKey).isEqualTo(14897079L)
        assertThat(serverPrivateKey).isEqualTo(clientPrivateKey)
    }

    @Test
    @Order(6)
    fun `Solves Part 1`() {
        val res = puzzleSolver.solvePart1()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("14897079")
    }

}
