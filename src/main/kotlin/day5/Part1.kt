package day5

import readFileAsLinesUsingBufferedReader

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
        println("Done with step, numbers are now: $shifted")
        workList = shifted
    }
    println("Minimal location value after mapping is ${workList.min()}")
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
                println("Found a shift for $input, ${shift.key} -> ${shift.value}")
                return input + shift.value
            }
        }
        return input;
    }
}