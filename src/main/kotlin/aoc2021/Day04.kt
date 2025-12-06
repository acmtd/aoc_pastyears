package aoc2021

import println
import readBlocks

fun main() {
    val testData = readBlocks("2021_Day04_test")
    check(part1(testData) == 4512)

    val puzzleData = readBlocks("2021_Day04")
    part1(puzzleData).println()

    check(part2(testData) == 1924)
    part2(puzzleData).println()
}

fun part1(data: List<String>): Int {
    val numbers = data[0].split(",").map { it.toInt() }
    val bingoCards = data.drop(1).map { BingoCard.fromString(it) }

    return playBingo(numbers, bingoCards)
}

fun part2(data: List<String>): Int {
    val numbers = data[0].split(",").map { it.toInt() }
    val bingoCards = data.drop(1).map { BingoCard.fromString(it) }

    return letTheSquidWin(numbers, bingoCards)
}

fun playBingo(numbers: List<Int>, bingoCards: List<BingoCard>): Int {
    numbers.forEachIndexed { index, number ->
        val calledNumbers = numbers.subList(0, index + 1).toSet()

        bingoCards.forEach { card ->
            if (card.isBingo(calledNumbers)) {
                return card.uncalledTotal(calledNumbers) * number
            }
        }
    }

    error("No bingo called!")
}

fun letTheSquidWin(numbers: List<Int>, bingoCards: List<BingoCard>): Int {
    val remainingCards = mutableListOf<BingoCard>()
    remainingCards.addAll(bingoCards)

    numbers.forEachIndexed { index, number ->
        val calledNumbers = numbers.subList(0, index + 1).toSet()

        val winningCards = remainingCards.filter { it.isBingo(calledNumbers) }

        if (winningCards.isNotEmpty()) {
            if (remainingCards.size == 1) {
                return winningCards.first().uncalledTotal(calledNumbers) * number
            } else {
                remainingCards.removeAll(winningCards)
            }
        }
    }

    error("No bingo called!")
}

data class Point(val row: Int, val col: Int)
data class CardItem(val pos: Point, val number: Int)

class BingoCard(val cards: Set<CardItem>) {
    companion object {
        fun fromString(str: String): BingoCard {
            val cards = buildSet {
                str.lines().forEachIndexed { row, line ->
                    addAll(
                        line.trim().split("\\s+".toRegex())
                            .mapIndexed { column, value -> CardItem(Point(row, column), value.toInt()) })
                }
            }

            return BingoCard(cards)
        }
    }

    fun isBingo(calledNumbers: Set<Int>): Boolean {
        val completed = cards.filter { calledNumbers.contains(it.number) }

        (0..4).forEach { num ->
            if (completed.count { it.pos.row == num } == 5 || completed.count { it.pos.col == num } == 5) return true
        }

        return false
    }

    fun uncalledTotal(calledNumbers: Set<Int>): Int {
        return cards.filterNot { calledNumbers.contains(it.number) }.sumOf { it.number }
    }
}