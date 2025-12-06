fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.toInt() }.zipWithNext().count { it.second > it.first }
    }

    fun part2(input: List<String>): Int {
        val windows = input.map { it.toInt() }.windowed(3).map { it.sum() }

        return windows.zipWithNext().count { it.second > it.first }
    }

    val testInput = readInput("2021_Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("2021_Day01")
    part1(input).println()
    part2(input).println()
}
