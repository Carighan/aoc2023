package day1

import java.io.File

private val forward = """\d|one|two|three|four|five|six|seven|eight|nine""".toRegex()
private val backward = """\d|eno|owt|eerht|ruof|evif|xis|neves|thgie|enin""".toRegex()

fun main(args: Array<String>) {
    val lines: List<String> = readFileAsLinesUsingBufferedReader("src/main/resources/day1.txt")
    val sum1 = lines.map { extractPart1(it) }.reduce { first, second -> first + second }
    println("Part 1 sum: $sum1")
    val sum2 = lines.map { extractPart2(it) }.reduce { first, second -> first + second }
    println("Part 2 sum: $sum2")
}

fun extractPart1(input: String): Int {
    val leftmost = input.first { it -> it.isDigit() }
    val rightmost = input.last { it -> it.isDigit() }
    return leftmost.digitToInt().times(10).plus(rightmost.digitToInt())
}

fun extractPart2(input: String): Int {
    println("Input line: $input")
    val matchesForward: MatchResult = forward.find(input) ?: throw Throwable("null match!")
    val matchesBackward: MatchResult = backward.find(input.reversed()) ?: throw Throwable("null match!")
    val leftmost: String = matchesForward.value
    val rightmost: String = matchesBackward.value
    val combined = mapToInt(leftmost).times(10).plus(mapToInt(rightmost))
    println("Combined value: $combined")
    println("")
    return combined
}

fun mapToInt(oneMatch: String): Int {
    println("Matched substring: $oneMatch")
    when (oneMatch) {
        "1", "one", "eno" -> return 1
        "2", "two", "owt" -> return 2
        "3", "three", "eerht" -> return 3
        "4", "four", "ruof" -> return 4
        "5", "five", "evif" -> return 5
        "6", "six", "xis" -> return 6
        "7", "seven", "neves" -> return 7
        "8", "eight", "thgie" -> return 8
        "9", "nine", "enin" -> return 9
    }
    throw Throwable("Not a valid digit!")
}

fun readFileAsLinesUsingBufferedReader(fileName: String): List<String> = File(fileName).bufferedReader().readLines()