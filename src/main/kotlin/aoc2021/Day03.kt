package aoc2021

import println
import readInput

fun main() {
    fun pivot(input: List<String>): List<String> {
        return buildList {
            input[0].indices.forEach { idx ->
                add(input.map { line -> line[idx] }.joinToString(""))
            }
        }
    }

    fun binaryToDecimal(l: List<Char>): Int {
        val reversed = l.reversed()

        return reversed.indices.sumOf { reversed[it].digitToInt().shl(it) }
    }

    fun mostCommonDigit(s: String, default: Char): Char {
        val zeroCount = s.count { it == '0' }
        val oneCount = s.count { it == '1' }

        if (zeroCount > oneCount) return '0'
        if (oneCount > zeroCount) return '1'
        return default
    }

    fun leastCommonDigit(s: String, default: Char): Char {
        val zeroCount = s.count { it == '0' }
        val oneCount = s.count { it == '1' }

        if (zeroCount > oneCount) return '1'
        if (oneCount > zeroCount) return '0'
        return default
    }

    fun part1(input: List<String>): Int {
        val pivoted = pivot(input)

        val gamma = pivoted.map { s -> mostCommonDigit(s, '1') }
        val epsilon = pivoted.map { s -> leastCommonDigit(s, '0') }

        return binaryToDecimal(gamma) * binaryToDecimal(epsilon)
    }

    fun part2(input: List<String>): Int {
        var remainingInput = input

        for (idx in input[0].indices) {
            val gamma = pivot(remainingInput).map { s -> mostCommonDigit(s, '1') }

            remainingInput = remainingInput.filter { it[idx] == gamma[idx] }

            if (remainingInput.size == 1) break
        }

        val oxygenGeneratorRating = binaryToDecimal(remainingInput.first().toList())

        remainingInput = input

        for (idx in input[0].indices) {
            val epsilon = pivot(remainingInput).map { s -> leastCommonDigit(s, '0') }
            remainingInput = remainingInput.filter { it[idx] == epsilon[idx] }

            if (remainingInput.size == 1) break
        }

        val co2ScrubberRating = binaryToDecimal(remainingInput.first().toList())

        return oxygenGeneratorRating * co2ScrubberRating
    }

    val testInput = readInput("2021_Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("2021_Day03")
    part1(input).println()
    part2(input).println()
}

