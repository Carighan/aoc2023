package day4

import readFileAsLinesUsingBufferedReader

private val numberMatcher = """\d+""".toRegex()
private val amountArray = Array(197) { 1 }

fun main(args: Array<String>) {
    val lines: List<String> = readFileAsLinesUsingBufferedReader("src/main/resources/day4.txt")
    var total = 0
    for ((index, line) in lines.withIndex()) {
        total += amountArray[index]
        val parts = line.split("""[:|]""".toRegex(), 3)
        val winningNumbers = numberMatcher.findAll(parts[1]).map { it.value }.map { it.toInt() }.toSet()
        val myNumbers = numberMatcher.findAll(parts[2]).map { it.value }.map { it.toInt() }.toSet()
        val wins = myNumbers.intersect(winningNumbers).size
        if (wins == 0) continue
        for (offset in 1..wins) {
            val target = index + offset
            if (target < 197) {
                amountArray[target] += amountArray[index]
            }
        }
    }
    println("In total, I processed $total scratchcards")
}