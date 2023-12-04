package day2

import readFileAsLinesUsingBufferedReader

private val blue = """\d+(?=(\sblue))""".toRegex()
private val green = """\d+(?=(\sgreen))""".toRegex()
private val red = """\d+(?=(\sred))""".toRegex()

fun main(args: Array<String>) {
    val lines: List<String> = readFileAsLinesUsingBufferedReader("src/main/resources/day2.txt")
    val validGames = mapValidity(lines)
    println("Part 1 valid games: ${validGames.filter { it.value }.keys}")
    println("Sum of part 1: ${validGames.filter { it.value }.map { it.key }.sum()}")
    val minPowers = extractMinPowers(lines)
    println("Part 2: sum of powers is ${minPowers.map { it.value }.sum()}")
}

fun extractMinPowers(input: List<String>): Map<Int, Int> {
    var result: MutableMap<Int, Int> = mutableMapOf()
    input.forEach {line ->
        val game = """\d+(?=(:))""".toRegex().find(line)?.value!!.toInt()
        val minBlue: Int = blue.findAll(line).map { it.value }.map { it.toInt() }.max()
        val minGreen: Int = green.findAll(line).map { it.value }.map { it.toInt() }.max()
        val minRed: Int = red.findAll(line).map { it.value }.map { it.toInt() }.max()
        result[game] = minBlue * minGreen * minRed
    }
    return result;
}

fun mapValidity(input: List<String>): Map<Int, Boolean> {
    var result: MutableMap<Int, Boolean> = mutableMapOf()
    input.forEach {line ->
        val game = """\d+(?=(:))""".toRegex().find(line)?.value!!.toInt()
        val blueImpossible: Boolean = blue.findAll(line).map { it.value }.map { it.toInt() }.any { it.isGreaterThan(14) }
        val greenImpossible: Boolean = green.findAll(line).map { it.value }.map { it.toInt() }.any { it.isGreaterThan(13) }
        val redImpossible: Boolean = red.findAll(line).map { it.value }.map { it.toInt() }.any { it.isGreaterThan(12) }
        result[game] = !(blueImpossible || greenImpossible || redImpossible)
    }
    return result;
}

private fun Int?.isGreaterThan(other: Int?) = this != null && other != null && this > other