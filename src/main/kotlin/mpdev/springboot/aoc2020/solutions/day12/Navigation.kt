package mpdev.springboot.aoc2020.solutions.day12

import mpdev.springboot.aoc2020.utils.AocException
import mpdev.springboot.aoc2020.utils.manhattan
import java.awt.Point

class Navigation(input: List<String>, part1Or2: Int = 1) {

    var instructions: List<Pair<Instruction,Int>> = input.map { line -> instructionFromString(line, part1Or2) }
    val data = NavigationData(Position(), Direction.EAST, Waypoint(10, 1))

    fun navigate() {
        instructions.forEach {
            val (instruction, parameter) = it
            instruction.execute(data, parameter)
        }
    }

    fun instructionFromString(s: String, part1Or2: Int): Pair<Instruction,Int> {
        val match = Regex("""([NSEWLRF])(\d+)""").find(s)
        try {
            val (instr, param) = match!!.destructured
            return when (part1Or2) {
                1 -> Pair(NavigationInstruction1.valueOf(instr), param.toInt())
                2 -> Pair(NavigationInstruction2.valueOf(instr), param.toInt())
                else -> throw AocException("invalid part 1 or 2 value [$part1Or2]")
            }
        } catch (e: Exception) {
            throw AocException("bad input line $s")
        }
    }
}

data class NavigationData(var position: Position, var direction: Direction, var waypoint: Waypoint) {

    private val start = Point(0, 0)

    fun moveToWaypoint(factor: Int): Position {
        val newPosition = Position()
        newPosition.x = position.x
        newPosition.y = position.y
        newPosition.x += waypoint.x * factor
        newPosition.y += waypoint.y * factor
        return newPosition
    }

    fun distanceCovered() = position.manhattan(start)
}
