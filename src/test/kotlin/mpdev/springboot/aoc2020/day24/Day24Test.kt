package mpdev.springboot.aoc2020.day24

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day24.Day24
import mpdev.springboot.aoc2020.solutions.day24.TileFloor
import mpdev.springboot.aoc2020.utils.HexGrid.Directions.*
import mpdev.springboot.aoc2020.solutions.day24.Tile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import java.awt.Point


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day24Test {

    private val day = 24                                    ///////// Update this for a new dayN test
    private val puzzleSolver = Day24()                      ///////// Update this for a new dayN test
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
    fun `Reads Input and Sets up Hex Grid and Instructions`() {
        val tileFloor = TileFloor(inputLines)
        tileFloor.instructions.forEach { println(it) }
        tileFloor.tiles.setDimensions(10, 10)
        tileFloor.tiles.print()
    }

    @Test
    @Order(3)
    fun `Flips Tiles as per Instructions`() {
        val tileFloor = TileFloor(inputLines)
        tileFloor.flipTile(listOf(E,SE,W))
        tileFloor.tiles.setDimensions(5, 5, -5, -5)
        tileFloor.tiles.print()
        assertThat(tileFloor.tiles.countOf(Tile.BLACK)).isEqualTo(1)
        assertThat(tileFloor.tiles.getDataPoint(Point(1,-1))).isEqualTo(Tile.BLACK)
        tileFloor.flipTile(listOf(NW,W,SW,E,E ))
        tileFloor.tiles.print()
        assertThat(tileFloor.tiles.countOf(Tile.BLACK)).isEqualTo(2)
        assertThat(tileFloor.tiles.getDataPoint(Point(0,0))).isEqualTo(Tile.BLACK)
    }

    @Test
    @Order(6)
    fun `Solves Part 1`() {
        val res = puzzleSolver.solvePart1()
        puzzleSolver.tileFloor.print()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("10")
    }

    @Test
    @Order(7)
    fun `Performs Daily Tile Flip`() {
        val tileFloor = TileFloor(inputLines)
        tileFloor.flipAllTiles()
        println("initial set up")
        tileFloor.print()
        var expected = intArrayOf(15,12,25,14,23,28,41,37,49,37)
        repeat(10) {
            println("day ${it+1}")
            tileFloor.flipTilesDaily()
            tileFloor.print()
            assertThat(tileFloor.tiles.countOf(Tile.BLACK)).isEqualTo(expected[it])
        }
        expected = intArrayOf(132,259,406,566,788,1106,1373,1844,2208)
        repeat(9) {
            repeat(10) { tileFloor.flipTilesDaily() }
            println("day ${10 * (it + 1) + 10}")
            tileFloor.print()
            assertThat(tileFloor.tiles.countOf(Tile.BLACK)).isEqualTo(expected[it])
        }
    }

    @Test
    @Order(9)
    fun `Solves Part 2`() {
        puzzleSolver.solvePart1()
        val res = puzzleSolver.solvePart2()
        puzzleSolver.tileFloor.print()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("2208")
    }
}
