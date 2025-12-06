package aoc2021

import println
import readInput

fun main() {
    val day5 = Day5()

    val testData = readInput("2021_Day05_test")
    check(day5.part1(testData) == 5)

    val puzzleData = readInput("2021_Day05")
    day5.part1(puzzleData).println()

    check(day5.part2(testData) == 12)
    day5.part2(puzzleData).println()
}

class Day5 {
    fun part1(input: List<String>): Int {
        return countOverlappingVents(parse(input).filterNot { it.isDiagonal() })
    }

    fun part2(input: List<String>): Int {
        return countOverlappingVents(parse(input))
    }

    fun countOverlappingVents(vents: List<Vent>) =
        vents.flatMap { it.allPoints() }.groupingBy { it }.eachCount().filter { it.value > 1 }.count()

    data class Vent(val start: Point, val end: Point) {
        fun isHorizontal(): Boolean {
            return start.x == end.x
        }

        fun isVertical(): Boolean {
            return start.y == end.y
        }

        fun isDiagonal(): Boolean {
            return !isHorizontal() && !isVertical()
        }

        fun allPoints(): List<Point> {
            return buildList {
                if (isHorizontal()) {
                    val yRange = if (start.y > end.y) start.y.downTo(end.y) else start.y..end.y

                    yRange.forEach {
                        add(Point(start.x, it))
                    }
                } else if (isVertical()) {
                    val xRange = if (start.x > end.x) start.x.downTo(end.x) else start.x..end.x

                    xRange.forEach {
                        add(Point(it, start.y))
                    }
                } else {
                    val yRange = if (start.y > end.y) start.y.downTo(end.y) else start.y..end.y
                    val xRange = if (start.x > end.x) start.x.downTo(end.x) else start.x..end.x

                    xRange.forEachIndexed { index, value ->
                        add(Point(value, yRange.first + (index * yRange.step)))
                    }
                }
            }
        }
    }

    data class Point(val x: Int, val y: Int) {}

    fun parse(input: List<String>): List<Vent> {
        return input.map { line ->
            Vent(
                line.substringBefore(" ->").split(",").map { it.toInt() }.let { Point(it[0], it[1]) },
                line.substringAfter("-> ").split(",").map { it.toInt() }.let { Point(it[0], it[1]) })
        }
    }
}
