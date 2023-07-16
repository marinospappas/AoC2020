package mpdev.springboot.aoc2020.utils

import java.awt.Point
import java.lang.StringBuilder
import kotlin.math.abs

operator fun Point.plus(other: Point) =
    Point(this.x + other.x, this.y + other.y)

operator fun Point.times(n: Int) =
    Point(n * this.x, n * this.y)

operator fun Point.minus(other: Point) =
    Point(this.x - other.x, this.y - other.y)

fun Point.manhattan(other: Point): Int =
    abs(this.x - other.x) + abs(this.y - other.y)

fun String.splitRepeatedChars(): List<String> {
    if (isEmpty())
        return emptyList()
    val s = StringBuilder(this)
    var index = 0
    val delimiter = '_'
    var previous = s.first()
    while (index < s.length) {
        if (s[index] != previous)
            s.insert(index++, delimiter)
        previous = s[index]
        ++index
    }
    return s.split(delimiter)
}

fun Int.lastDigit() = this % 10