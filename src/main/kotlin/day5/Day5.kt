package day5

import readFileAsLinesUsingBufferedReader
import kotlin.math.min

fun main(args: Array<String>) {
    val lines: List<String> = readFileAsLinesUsingBufferedReader("src/main/resources/day5.txt")
    val seeds: List<Long> = extractSeeds(lines.get(0))
    val soils: Step = extractSteps(lines, "seed-to-soil map:")
    val fertilizers: Step = extractSteps(lines, "soil-to-fertilizer map:")
    val waters: Step = extractSteps(lines, "fertilizer-to-water map:")
    val lights: Step = extractSteps(lines, "water-to-light map:")
    val temperatures: Step = extractSteps(lines, "light-to-temperature map:")
    val humidities: Step = extractSteps(lines, "temperature-to-humidity map:")
    val locations: Step = extractSteps(lines, "humidity-to-location map:")

    var workList = seeds
    println("Starting, seeds are: $workList")
    for (step in listOf(soils,fertilizers,waters,lights,temperatures,humidities,locations)) {
        val shifted = workList.map(step::shift)
        workList = shifted
    }
    println("Part 1: Minimal location value after mapping is ${workList.min()}")

    var currentMin = Long.MAX_VALUE
    var expandedSeeds = seeds.chunked(2).map { chunk -> LongRange(chunk[0], chunk[0]+chunk[1]-1) }
    for (range in expandedSeeds) {
        for ((index, chunk) in range.toList().chunked(1000).withIndex()) {
            var chunkList = chunk
            for (step in listOf(soils,fertilizers,waters,lights,temperatures,humidities,locations)) {
                val shifted = chunkList.map(step::shift)
                chunkList = shifted
            }
            currentMin = min(currentMin, chunkList.min())
        }
    }
    println("Part 2: Minimal location value after mapping is $currentMin")
}

private fun extractSeeds(line: String): List<Long> {
    return line.split(":")[1].split(" ").filterNot { it -> it.isBlank() }.map { entry: String -> entry.toLong() }.toList()
}

private fun extractSteps(lines: List<String>, headline: String): Step {
    var start = 0
    var end: Int = lines.size
    for ((index, line) in lines.withIndex()) {
        if (line == headline) {
            start = index + 1
            for ((inner, stepline) in lines.subList(start, lines.size - 1).withIndex()) {
                if (stepline.isBlank()) {
                    end = start + inner
                    return Step(lines.subList(start, end))
                }
            }
        }
    }
    return Step(lines.subList(start, end))
}

private class Step(mappings: List<String>) {
    private val shifts: MutableMap<LongRange, Long> = mutableMapOf()

    init {
        for (mapping in mappings) {
            val split: List<String> = mapping.split(" ")
            val destination = split[0].toLong()
            val source = split[1].toLong()
            val length = split[2].toLong()
            shifts[LongRange(source, source+length-1)] = destination-source
        }
    }

    fun shift(input: Long): Long {
        for (shift in shifts) {
            if (shift.key.contains(input)) {
                return input + shift.value
            }
        }
        return input;
    }
}