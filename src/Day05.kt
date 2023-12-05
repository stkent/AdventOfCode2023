import utils.readInput

data class KindMap(val entries: List<KindMapEntry>) {
    fun dstFor(src: Long): Long {
        return entries.firstNotNullOfOrNull { entry -> entry.dstFor(src) } ?: src
    }
}

data class KindMapEntry(
    val dstStart: Long,
    val srcStart: Long,
    val range: Long,
) {
    fun dstFor(src: Long): Long? {
        if (src < srcStart) return null
        if (src >= srcStart + range) return null
        return dstStart + (src - srcStart)
    }
}

fun main() {
    fun List<String>.parseSeeds(): List<Long> {
        return this[0]
            .drop(7)
            .split(" ")
            .map(String::toLong)
    }

    fun List<String>.parseMappings(): List<KindMap> {
        if (isEmpty()) return emptyList()

        val mappingLength = takeWhile(String::isNotBlank).count()

        return buildList {
            add(
                KindMap(
                    this@parseMappings
                        .take(mappingLength)
                        .drop(1)
                        .map { line ->
                            line.split(" ")
                                .map(String::toLong)
                                .let { KindMapEntry(it[0], it[1], it[2]) }
                        }
                )
            )

            addAll(
                this@parseMappings
                    .drop(mappingLength + 1)
                    .parseMappings()
            )
        }
    }

    fun List<String>.parseAlmanac(): Pair<List<Long>, List<KindMap>> {
        return parseSeeds() to drop(2).parseMappings()
    }

    fun seedLocation(seed: Long, mappings: List<KindMap>): Long {
        return mappings.fold(seed) { curr, mapping ->
            mapping.dstFor(curr)
        }
    }

    fun part1(input: List<String>): Long {
        val (seeds, mappings) = input.parseAlmanac()

        return seeds.minOf { seed -> seedLocation(seed, mappings) }
    }

    fun part2(input: List<String>): Long {
        val (seedRanges, mappings) = input.parseAlmanac()
        val seeds: Sequence<Long> =
            seedRanges
                .chunked(2)
                .map { (start, range) ->
                    generateSequence(start) { prev ->
                        (prev + 1).takeIf { it <= start + range }
                    }
                }
                .reduce(Sequence<Long>::plus)


        return seeds.minOf { seed -> seedLocation(seed, mappings) }
    }

    val testInput = readInput("Day05_test")
    val input = readInput("Day05")

    check(part1(testInput) == 35L)
    println(part1(input))

    check(part2(testInput) == 46L)
    // Warning: unoptimized, takes minutes to run:
    println(part2(input))
}
