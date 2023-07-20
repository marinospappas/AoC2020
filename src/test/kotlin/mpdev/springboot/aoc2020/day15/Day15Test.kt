package mpdev.springboot.aoc2020.day15

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day15.Day15
import mpdev.springboot.aoc2020.solutions.day15.MemoryGame
import mpdev.springboot.aoc2020.solutions.day15.NumbersInfo
import mpdev.springboot.aoc2020.utils.AocException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.Hashtable
import java.util.stream.Stream
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.system.measureTimeMillis

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day15Test {

    private val day = 15                                    ///////// Update this for a new dayN test
    private val puzzleSolver = Day15()                      ///////// Update this for a new dayN test
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
    fun `Reads Input and Sets up Memory Game`() {
        val memoryGame = MemoryGame(inputLines)
        println(memoryGame.startSequence)
        assertThat(memoryGame.startSequence).isEqualTo(listOf(0,3,6))
    }

    @Test
    @Order(3)
    fun `Identifies Numbers to Speak based on Rules`() {
        val memoryGame = MemoryGame(inputLines)
        repeat(9) { memoryGame.playRound(); print("${memoryGame.getLastNumber()}  ") }
        memoryGame.playRound()
        val last = memoryGame.getLastNumber()
        println(last)
        assertThat(last).isEqualTo(0)
    }

    @ParameterizedTest
    @Order(4)
    @MethodSource("part1And2Params")
    fun `Solves Part 1`(input: List<String>, expected: String) {
        puzzleSolver.inputData = input
        puzzleSolver.initSolver()
        val res = puzzleSolver.solvePart1()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo(expected)
    }

    @ParameterizedTest
    @Order(8)
    @MethodSource("part1And2Params")
    fun `Solves Part 2`(input: List<String>, _ignore: Any, expected: String) {
        puzzleSolver.inputData = input
        puzzleSolver.initSolver()
        val res = puzzleSolver.solvePart2()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo(expected)
    }

    private fun part1And2Params() = Stream.of(
        Arguments.of(inputLines, "436", "175594"),
        Arguments.of(listOf("1,3,2"), "1", "2578"),
        Arguments.of(listOf("2,1,3"), "10", "3544142"),
        Arguments.of(listOf("1,2,3"), "27", "261214"),
        Arguments.of(listOf("2,3,1"), "78", "6895259"),
        Arguments.of(listOf("3,2,1"), "438", "18"),
        Arguments.of(listOf("3,1,2"), "1836", "362"),
    )

    @ParameterizedTest
    @CsvSource(value = ["10000", "3000000", "30000000"])
    @Order(9)
    fun `NumberInfo cache Performance test`(size: String) {
        println("Size: $size")
        val map = mutableMapOf<Int,NumbersInfo>()
        val hashTable = Hashtable<Int,NumbersInfo>()
        val array = Array(size.toInt()){NumbersInfo()}
        val array1 = Array(size.toInt()/10+1){NumbersInfo()}
        val map1 = mutableMapOf<Int,Int>()

        repeat(size.toInt()) { map[it] = NumbersInfo() }
        repeat(size.toInt()) { hashTable[it] = NumbersInfo() }

        var elapsed = measureTimeMillis {
            repeat(30_000_000) {
                val key = Random.nextInt(10_000..20_000)
                var info = NumbersInfo()
                if (map[key] != null)
                    info = map[key] ?: throw AocException("map read error")
                info.timesSpoken++
                map[key] = info
            }
        }
        println("Kotlin MutableMap: $elapsed milli-sec")

        val immMap = map.toMap()
        elapsed = measureTimeMillis {
            repeat(30_000_000) {
                val key = Random.nextInt(10_000..20_000)
                var info = NumbersInfo()
                if (immMap[key] != null)
                    info = immMap[key] ?: throw AocException("map read error")
                info.timesSpoken++
            }
        }
        println("Kotlin immutable Map: $elapsed milli-sec")

        elapsed = measureTimeMillis {
            repeat(30_000_000) {
                val key = Random.nextInt(10_000..20_000)
                var info = NumbersInfo()
                if (hashTable[key] != null)
                    info = hashTable[key] ?: throw AocException("map read error")
                info.timesSpoken++
                hashTable[key] = info
            }
        }
        println("Java HashTable: $elapsed milli-sec")

        elapsed = measureTimeMillis {
            repeat(30_000_000) {
                val key = Random.nextInt(0..(size.toInt()/10))
                val info = array[key]
                info.timesSpoken++
            }
        }
        println("Array of full data: $elapsed milli-sec")

        var arrayIndx = 0

        elapsed = measureTimeMillis {
            repeat(30_000_000) {
                val key = Random.nextInt(10_000..(10_000+size.toInt()/10))
                if (map1[key] == null)
                    map1[key] = arrayIndx++
                val mappedKey = map1[key]!!
                val info = array1[mappedKey]
                info.timesSpoken++
            }
        }
        println("Array of data used with index mapping: $elapsed milli-sec (size of index map: ${map1.size})")
    }
}
