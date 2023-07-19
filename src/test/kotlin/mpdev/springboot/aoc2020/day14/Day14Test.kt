package mpdev.springboot.aoc2020.day14

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day14.Day14
import mpdev.springboot.aoc2020.solutions.day14.Decoder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day14Test {

    private val day = 14                                    ///////// Update this for a new dayN test
    private val puzzleSolver = Day14()                      ///////// Update this for a new dayN test
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
    fun `Reads Input and Sets up Packets`() {
        val decoder = Decoder(inputLines)
        decoder.data.forEach { println(it) }
        assertThat(decoder.data.size).isEqualTo(1)
        assertThat(decoder.data[0].values.size).isEqualTo(3)
    }

    @Test
    @Order(3)
    fun `Applies Masks and Sets up memory for Part 1`() {
        val decoder = Decoder(inputLines)
        decoder.updateMemory()
        decoder.mem.forEach { println(it) }
        assertThat(decoder.mem.size).isEqualTo(2)
        assertThat(decoder.mem[7]).isEqualTo(101)
        assertThat(decoder.mem[8]).isEqualTo(64)
        assertThat(decoder.mem.values.sum()).isEqualTo(165)
    }

    @Test
    @Order(4)
    fun `Solves Part 1`() {
        val res = puzzleSolver.solvePart1()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("165")
    }

    private fun part2Input() = listOf(
        "mask = 000000000000000000000000000000X1001X",
        "mem[42] = 100",
        "mask = 00000000000000000000000000000000X0XX",
        "mem[26] = 1"
    )

    @Test
    @Order(5)
    fun `Reads Input and Sets up Packets V2`() {
        val decoder = Decoder(part2Input(), 2)
        decoder.data.forEach { println(it) }
        assertThat(decoder.data.size).isEqualTo(2)
        assertThat(decoder.data[0].values.size).isEqualTo(1)
        assertThat(decoder.data[1].values.size).isEqualTo(1)
    }

    @Test
    @Order(6)
    fun `Calculates Mem Addresses based on Address Mask`() {
        val decoder = Decoder(part2Input(), 2)
        decoder.data.forEach { p ->
            println(p)
            p.values.map { it.first }.forEach { addr ->
                println("orig address: $addr - addresses from mask: ${decoder.getAddresses(addr, p.orMask, p.addressMask)}")
            }
            println()
        }
        var packet = decoder.data[0]
        assertThat(decoder.getAddresses(packet.values[0].first, packet.orMask, packet.addressMask))
            .isEqualTo(listOf<Long>(26, 27, 58, 59))
        packet = decoder.data[1]
        assertThat(decoder.getAddresses(packet.values[0].first, packet.orMask, packet.addressMask))
            .isEqualTo(listOf<Long>(16, 17, 18, 19, 24, 25, 26, 27))
    }

    @Test
    @Order(7)
    fun `Applies Masks and Sets up memory from V2 Packets`() {
        val decoder = Decoder(part2Input(), 2)
        decoder.data.forEach { println(it) }
        decoder.updateMemory()
        decoder.mem.forEach { println(it) }
        assertThat(decoder.mem.size).isEqualTo(10)
        assertThat(decoder.mem[58]).isEqualTo(100)
        assertThat(decoder.mem[59]).isEqualTo(100)
        setOf<Long>(16,17,18,19,24,25,26,27).forEach { assertThat(decoder.mem[it]).isEqualTo(1) }
        assertThat(decoder.mem.values.sum()).isEqualTo(208)
    }

    @Test
    @Order(8)
    fun `Solves Part 2`() {
        puzzleSolver.inputData = part2Input()
        puzzleSolver.initSolver()
        val res = puzzleSolver.solvePart2()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("208")
    }

}
