package day3

import readFileAsLinesUsingBufferedReader
import kotlin.math.max
import kotlin.math.min

private val asteriskMatcher = """\*""".toRegex()
private val numberMatcher = """\d+""".toRegex()
private val numbersArray = Array(140) { Array<MutableList<Int>>(140) { mutableListOf() } }

fun main(args: Array<String>) {
    val lines: List<String> = readFileAsLinesUsingBufferedReader("src/main/resources/day3.txt")

    for ((lineNumber, line) in lines.withIndex()) {
        for (number in numberMatcher.findAll(line)) {
            for (position in max(number.range.first-1, 0)..min(number.range.last+1, 139)) {
                if (lineNumber != 0) {
                    numbersArray[position][lineNumber-1].add(number.value.toInt())
                }
                numbersArray[position][lineNumber].add(number.value.toInt())
                if (lineNumber != 139) {
                    numbersArray[position][lineNumber+1].add(number.value.toInt())
                }
            }
        }
    }

    var gearSum: Int = 0
    for ((lineNumber, line) in lines.withIndex()) {
        for (symbol in asteriskMatcher.findAll(line)) {
            if (numbersArray[symbol.range.first][lineNumber].size == 2) {
                gearSum += Math.multiplyExact(numbersArray[symbol.range.first][lineNumber][0], numbersArray[symbol.range.first][lineNumber][1])
            }
        }
    }

    println("Part 2: The sum of all gear values is: $gearSum")
}