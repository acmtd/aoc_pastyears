fun main() {

    fun part1(input: List<String>): Int {
        val horizontal = input.filter { it.startsWith("forward") }.sumOf { it.split(" ")[1].toInt() }
        val depth = input.filter { it.startsWith("down") }
            .sumOf { it.split(" ")[1].toInt() } - input.filter { it.startsWith("up") }
            .sumOf { it.split(" ")[1].toInt() }

        return horizontal * depth
    }

    fun part2(input: List<String>): Int {
        val course = input.map { it.split(" ") }.map { it[0] to it[1].toInt() }

        var horizontal = 0
        var depth = 0
        var aim = 0

        course.forEach {
            when (it.first) {
                "forward" -> {
                    horizontal += it.second
                    depth += aim * it.second
                }

                "down" -> aim += it.second
                "up" -> aim -= it.second
            }
        }

        return horizontal * depth
    }

    val testInput = readInput("2021_Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("2021_Day02")
    part1(input).println()
    part2(input).println()
}
