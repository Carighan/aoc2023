package day4

import readFileAsLinesUsingBufferedReader
import kotlin.math.pow

private val numberMatcher = """\d+""".toRegex()

fun main(args: Array<String>) {
    val lines: List<String> = readFileAsLinesUsingBufferedReader("src/main/resources/day4.txt")
    var score = 0
    for (line in lines) {
        val parts = line.split("""[:|]""".toRegex(), 3)
        val winningNumbers = numberMatcher.findAll(parts[1]).map { it.value }.map { it.toInt() }.toSet()
        val myNumbers = numberMatcher.findAll(parts[2]).map { it.value }.map { it.toInt() }.toSet()
        val wins = myNumbers.intersect(winningNumbers).size
        println("$wins in line $line")
        if (wins > 0) {
            score += 2.toDouble().pow(wins-1).toInt()
        }
    }
    println()
    println("In total, that's a score of $score")
}