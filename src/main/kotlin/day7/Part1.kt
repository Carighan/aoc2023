package day7

import readFileAsLinesUsingBufferedReader
import java.util.*
import kotlin.Comparator

/**
 * Created on 06.12.2023.
 */
private val cards = arrayOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')

fun main(args: Array<String>) {
    val lines: List<String> = readFileAsLinesUsingBufferedReader("src/main/resources/day7.txt")
    val hands: SortedMap<Hand, Int> = readHands(lines)
    val scores: MutableList<Int> = mutableListOf()
    for (hand in hands) {
        scores.add(hand.value)
    }
    var sum = 0
    for ((index, score) in scores.withIndex()) {
        sum += score * (index+1)
    }
    println("Part 1: Total winnings are $sum")
}

private fun readHands(lines: List<String>): SortedMap<Hand, Int> {
    val result: MutableMap<Hand, Int> = mutableMapOf()
    for (line in lines) {
        val parts = line.split(" ")
        result[Hand(parts[0])] = parts[1].toInt()
    }
    return result.toSortedMap(Comparator.comparing(Hand::value))
}

private data class Hand(val hand: MutableMap<Char, Int>, val raw: String) {
    var value: Int = 0

    constructor(raw: String) : this(parseCards(raw), raw) {
        value = computeSetValue() + computeCardValue()
    }

    fun computeCardValue(): Int {
        return 10000 * (13 - cards.indexOf(raw[0])) + 1000 * (13 - cards.indexOf(raw[1])) + 100 * (13 - cards.indexOf(raw[2])) + 10 * (13 - cards.indexOf(raw[3])) + (13 - cards.indexOf(raw[4]))
    }

    fun computeSetValue(): Int {
        if (hand.containsValue(5)) {
            return 6000000
        }
        if (hand.containsValue(4)) {
            return 5000000
        }
        if (hand.containsValue(3) && hand.containsValue(2)) {
            return 4000000
        }
        if (hand.containsValue(3)) {
            return 3000000
        }
        if (hand.count { entry -> entry.value == 2 } == 2) {
            return 2000000
        }
        if (hand.containsValue(2)) {
            return 1000000
        }
        return 0
    }
}


private fun parseCards(raw: String): MutableMap<Char, Int> {
    val result: MutableMap<Char, Int> = mutableMapOf()
    for (card in cards) {
        result[card] = raw.count { letter -> letter == card }
    }
    return result
}