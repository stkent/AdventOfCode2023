fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day01_test.txt")
    check(part1(testInput) == 1)

    val input = readInput("Day01.txt")
    part1(input).println()
    part2(input).println()
}
