package aoc2021

import println
import readInput
import kotlin.collections.forEach

fun main() {
    val day6 = Day06()

    val testData = readInput("2021_Day06_test")
    check(day6.part1(testData) == 5934L)

    val puzzleData = readInput("2021_Day06")
    day6.part1(puzzleData).println()

    check(day6.part2(testData) == 26984457539L)
    day6.part2(puzzleData).println()
}

class Day06 {
    fun part1(input: List<String>): Long {
        return compute(input, 80)
    }

    fun part2(input: List<String>): Long {
        return compute(input, 256)
    }

    private fun compute(input: List<String>, iterations: Int): Long {
        val timerCounts = input.first().split(",").map { it.toInt() }.groupingBy { it }.eachCount()
            .mapValues { it.value.toLong() } // Converts the Int value to Long
            .toMutableMap()

        repeat(iterations) {
            val newFishes = timerCounts[0] ?: 0

            (1..8).forEach { timer ->
                timerCounts[timer - 1] = timerCounts[timer] ?: 0
            }

            timerCounts[8] = newFishes
            timerCounts[6] = (timerCounts[6] ?: 0) + newFishes
        }

        return timerCounts.values.sum()
    }
}