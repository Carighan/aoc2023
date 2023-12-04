package day3

import readFileAsLinesUsingBufferedReader

private val symbolMatcher = """[^\w\s\\.]""".toRegex()
private val numberMatcher = """\d+""".toRegex()
private val symbols: MutableList<Pair<Int, Int>> = mutableListOf()

fun main(args: Array<String>) {
    val lines: List<String> = readFileAsLinesUsingBufferedReader("src/main/resources/day3.txt")
    for ((lineNumber, line) in lines.withIndex()) {
        for (symbol in symbolMatcher.findAll(line)) {
            val range: IntRange = symbol.range
            val first: Int = range.first
            symbols.add(Pair(first, lineNumber))
        }
    }
    val partNumbers: MutableList<Int> = mutableListOf()
    for ((lineNumber, line) in lines.withIndex()) {
        for (number in numberMatcher.findAll(line)) {
            if (symbolNearRange(lineNumber, number.range)) {
                partNumbers.add(number.value.toInt())
            }
        }
    }
    println("Part 1: The sum of all part numbers is: ${partNumbers.sum()}")
}

private fun symbolNearRange(lineNumber: Int, input: IntRange): Boolean = input.any { position -> symbolNearby(position, lineNumber) }

private fun symbolNearby(x: Int, y: Int): Boolean = symbols.any { x - 1 <= it.first && it.first <= x + 1 && y - 1 <= it.second && it.second <= y + 1 }