package mpdev.springboot.aoc2020.solutions.day12

import java.awt.Point

interface Instruction {
    fun execute(nd: NavigationData, param: Int)
}

@Suppress("unused")
enum class NavigationInstruction1(val apply: (NavigationData, Int) -> Unit): Instruction {
    /*
    Action N means to move north by the given value.
    Action S means to move south by the given value.
    Action E means to move east by the given value.
    Action W means to move west by the given value.
    Action L means to turn left the given number of degrees.
    Action R means to turn right the given number of degrees.
    Action F means to move forward by the given value in the direction the
    */
    N({ nd, param -> nd.position = nd.position.move(param, Direction.NORTH) }),
    S({ nd, param -> nd.position = nd.position.move(param, Direction.SOUTH) }),
    E({ nd, param -> nd.position = nd.position.move(param, Direction.EAST) }),
    W({ nd, param -> nd.position = nd.position.move(param, Direction.WEST) }),
    L({ nd, param -> nd.direction = nd.direction.turn(param, Rotation.LEFT) }),
    R({ nd, param -> nd.direction = nd.direction.turn(param, Rotation.RIGHT) }),
    F({ nd, param -> nd.position = nd.position.move(param, nd.direction) });

    override fun execute(nd: NavigationData, param: Int) {
        apply(nd, param)
    }
}

@Suppress("unused")
enum class NavigationInstruction2(val apply: (NavigationData, Int) -> Unit): Instruction {
    /*
    Action N means to move the waypoint north by the given value.
    Action S means to move the waypoint south by the given value.
    Action E means to move the waypoint east by the given value.
    Action W means to move the waypoint west by the given value.
    Action L means to rotate the waypoint around the ship left (counter-clockwise) the given number of degrees.
    Action R means to rotate the waypoint around the ship right (clockwise) the given number of degrees.
    Action F means to move forward to the waypoint a number of times equal to the given value.
    */
    N({ nd, param -> nd.waypoint = nd.waypoint.move(param, Direction.NORTH) }),
    S({ nd, param -> nd.waypoint = nd.waypoint.move(param, Direction.SOUTH) }),
    E({ nd, param -> nd.waypoint = nd.waypoint.move(param, Direction.EAST) }),
    W({ nd, param -> nd.waypoint = nd.waypoint.move(param, Direction.WEST) }),
    L({ nd, param -> nd.waypoint = nd.waypoint.rotate(param, Rotation.LEFT) }),
    R({ nd, param -> nd.waypoint = nd.waypoint.rotate(param, Rotation.RIGHT) }),
    F({ nd, param -> nd.position = nd.moveToWaypoint(param) });

    override fun execute(nd: NavigationData, param: Int) {
        apply(nd, param)
    }
}

enum class Direction {
    NORTH, EAST, SOUTH, WEST;

    fun turn(param: Int, leftRight: Rotation): Direction {
        val currentDirIndex = values().indexOf(this)
        val newDirIndex = (currentDirIndex + Rotation.degrees2IndxIncrement(param, leftRight) ) % 4
        return values()[newDirIndex]
    }
}

enum class Rotation {
    LEFT, RIGHT;

    companion object {
        fun degrees2IndxIncrement(degrees: Int, leftRight: Rotation): Int {
            var turnBy = (degrees / 90) % 4
            if (leftRight == LEFT)
                turnBy = 4 - turnBy
            return turnBy
        }
    }
}

open class Position(x: Int = 0, y: Int = 0): Point(x, y) {

    open fun move(distance: Int, currentDirection: Direction): Position {
        val newPosition = Position(x, y)
        when (currentDirection) {
            Direction.NORTH -> newPosition.y += distance
            Direction.SOUTH -> newPosition.y -= distance
            Direction.EAST -> newPosition.x += distance
            Direction.WEST -> newPosition.x -= distance
        }
        return newPosition
    }

    override fun toString() = "[$x,$y]"
}

class Waypoint(x: Int = 0, y: Int = 0): Position(x ,y) {

    override fun move(distance: Int, currentDirection: Direction): Waypoint {
        val position = super.move(distance, currentDirection)
        return Waypoint(position.x, position.y)
    }

    fun rotate(param: Int, leftRight: Rotation): Waypoint {
        val newWaypoint = Waypoint(x, y)
        when (Rotation.degrees2IndxIncrement(param, leftRight)) {
            1 ->  { newWaypoint.x = y; newWaypoint.y = -x }
            2 ->  { newWaypoint.x = -x; newWaypoint.y = -y }
            3 ->  { newWaypoint.x = -y; ; newWaypoint.y = x }
        }
        return newWaypoint
    }

    override fun toString() = "[$x,$y]"
}
