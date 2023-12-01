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
        //@formatter:off
        "one"   to 1,
        "two"   to 2,
        "three" to 3,
        "four"  to 4,
        "five"  to 5,
        "six"   to 6,
        "seven" to 7,
        "eight" to 8,
        "nine"  to 9,
        //@formatter:on
    )

    fun String.toDigit(): Int {
        return requireNotNull(this.toIntOrNull() ?: digitWordMap[this])
    }

    fun String.part2LineNumber(): Int {
        // List of all strings that represent digits ("1", "one", "2", "two", etc.):
        val digitStrings = buildList {
            addAll(digitWordMap.keys)
            addAll(digitWordMap.values.map(Int::toString))
        }

        val (_, firstDigitString) = this.findAnyOf(digitStrings)!!
        val (_, lastDigitString) = this.findLastAnyOf(digitStrings)!!

        return 10 * firstDigitString.toDigit() + lastDigitString.toDigit()
    }

    fun part2(input: List<String>): Int {
        return input.sumOf(String::part2LineNumber)
    }

    val testInput1 = readInput("Day01_test1")
    check(part1(testInput1) == 142)

    val testInput2 = readInput("Day01_test2")
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
