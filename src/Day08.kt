import utils.extensions.repeatIndefinitely
import utils.lcm
import utils.println
import utils.readInput

typealias ParsedMap = Pair<String, Map<String, Pair<String, String>>>

fun main() {
    fun List<String>.parseMap(): ParsedMap {
        val instructions = first()

        val nodes = drop(2)
            .associate { line ->
                val label = line.substring(0..2)
                val left = line.substring(7..9)
                val right = line.substring(12..14)
                label to (left to right)
            }

        return instructions to nodes
    }

    fun ParsedMap.stepCount(source: String, isSink: String.() -> Boolean): Long {
        val (instructions, nodes) = this

        return instructions
            .toList()
            .repeatIndefinitely()
            .scan(source) { node, instruction ->
                nodes[node]!!.let { (left, right) ->
                    when (instruction) {
                        'L' -> left
                        'R' -> right
                        else -> error("Invalid instruction")
                    }
                }
            }
            .indexOfFirst { node -> node.isSink() }
            .toLong()
    }

    fun part1(input: List<String>): Long {
        return input
            .parseMap()
            .stepCount(
                source = "AAA",
                isSink = { this == "ZZZ" },
            )
    }

    fun part2(input: List<String>): Long {
        return with(input.parseMap()) {
            val (_, nodes) = this
            val sources = nodes.keys.filter { it.endsWith('A') }

            sources
                .map { source ->
                    stepCount(
                        source = source,
                        isSink = { endsWith('Z') },
                    )
                }
                .fold(1L) { s1, s2 -> lcm(s1, s2) }
        }
    }

    val testInput1 = readInput("Day08_test1")
    val testInput2 = readInput("Day08_test2")
    val testInput3 = readInput("Day08_test3")
    val input = readInput("Day08")

    check(part1(testInput1) == 2L)
    check(part1(testInput2) == 6L)
    check(part1(input).println() == 18673L)

    check(part2(testInput3) == 6L)
    check(part2(input).println() == 17972669116327)
}
