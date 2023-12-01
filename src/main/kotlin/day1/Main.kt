package day1

import java.io.File

fun main(args: Array<String>) {
    val lines = readFileAsLinesUsingBufferedReader("src/main/resources/day1.txt")
    val sum = lines.map { extractTarget(it) }.reduce { first, second -> first + second}
    println("Sum of all line results: $sum")
}

fun extractTarget(input: String): Int {
    val leftmost = input.first { it -> it.isDigit() }
    val rightmost = input.last { it -> it.isDigit() }
    return leftmost.digitToInt().times(10).plus(rightmost.digitToInt())
}

fun readFileAsLinesUsingBufferedReader(fileName: String): List<String> = File(fileName).bufferedReader().readLines()