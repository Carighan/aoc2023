package day6

import readFileAsLinesUsingBufferedReader

private val numberMatcher = """\d+""".toRegex()

/**
 * Created on 06.12.2023.
 */
fun main(args: Array<String>) {
    val lines: List<String> = readFileAsLinesUsingBufferedReader("src/main/resources/day6.txt")
    val races: Map<Int, Int> = splitIntoRaces(lines)
    var tally: Int = 1
    for (race in races) {
        val wins: List<Int> = findWins(race.value, race.key)
        tally *= wins.size
    }
    println("Part 1: Multiplying the amount of all wins together yields: $tally")

    val race: Pair<Long, Long> = readAsSingleRace(lines)
    val firstWin: Long = findSingleWin(race.second, race.first, 1..<race.first)
    val lastWin: Long = findSingleWin(race.second, race.first, (1..<race.first).reversed())

    println("Part 2: I can win a race of distance ${race.second} with time ${race.first} in ${lastWin - firstWin + 1} ways!")
}

fun findSingleWin(distanceToBeat: Long, timeAvailable: Long, timeRange: LongProgression): Long {
    for (time in timeRange) {
        if ((timeAvailable - time) * time > distanceToBeat) {
            return time
        }
    }
    throw Throwable("No way to win this!")
}

fun readAsSingleRace(lines: List<String>): Pair<Long, Long> {
    val time = numberMatcher.findAll(lines[0]).map(MatchResult::value).joinToString("").toLong()
    val distance = numberMatcher.findAll(lines[1]).map(MatchResult::value).joinToString("").toLong()
    return Pair(time, distance)
}

private fun findWins(distanceToBeat: Int, timeAvailable: Int): List<Int> {
    var wins: MutableList<Int> = mutableListOf()
    for (time in 1..<timeAvailable) {
        if ((timeAvailable - time) * time > distanceToBeat) {
            wins.add(time)
        }
    }
    return wins
}

private fun splitIntoRaces(lines: List<String>): Map<Int, Int> {
    val result: MutableMap<Int, Int> = mutableMapOf()
    val times = numberMatcher.findAll(lines[0]).map(MatchResult::value).map(String::toInt).toList()
    val distances = numberMatcher.findAll(lines[1]).map(MatchResult::value).map(String::toInt).toList()
    for ((index, time) in times.withIndex()) {
        result.put(time, distances[index])
    }
    return result
}
