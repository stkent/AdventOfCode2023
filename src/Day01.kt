import utils.readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            line
                .mapNotNull { char -> "$char".toIntOrNull() }
                .let { 10 * it.first() + it.last() }
        }
    }

    val digitWordMap = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )

    fun String.part2FindDigits(): List<Int> {
        val result = mutableListOf<Int>()

        // Iterate through this line from left to right, accumulating digits as we go:
        for (index in indices) {
            // Check for digit characters:
            val char = get(index)
            if (char.isDigit()) result.add("$char".toInt())

            // Check for digit words:
            for ((string, int) in digitWordMap) {
                if (startsWith(string, startIndex = index)) {
                    result.add(int)
                    break
                }
            }
        }

        return result
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            line
                .part2FindDigits()
                .let { 10 * it.first() + it.last() }
        }
    }

    val testInput1 = readInput("Day01_test1")
    check(part1(testInput1) == 142)

    val testInput2 = readInput("Day01_test2")
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
