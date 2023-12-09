package day7

import readFileAsLinesUsingBufferedReader

/**
 * Created on 06.12.2023.
 */
fun main(args: Array<String>) {
    val lines: List<String> = readFileAsLinesUsingBufferedReader("src/main/resources/day6.txt")
    var hands: Map<Hand,Int> = readHands(lines)
    var sorted = hands.toSortedMap()
}

private fun readHands(lines: List<String>): Map<Hand, Int> {
    val result: MutableMap<Hand,Int> = mutableMapOf()
    for (line in lines) {
        val parts = line.split(" ")
        result[Hand(parts[0])] = parts[1].toInt()
    }
    return result
}

private data class Hand(val cards: List<Card>) {
    private var value: Int = 0

    constructor(raw: String) : this(parseCards(raw)) {
        value = computeSetValue() + computeCardValue()
    }

    fun computeSetValue(): Int {
        if ()
    }
}

private fun parseCards(raw: String): List<Card> {
    return raw.map { char -> Card.from(char)!! }.toList()
}

private enum class Card(val label: Char) {
    ACE('A'),
    KING('K'),
    QUEEN('Q'),
    JACK('J'),
    TEN('T'),
    NINE('9'),
    EIGHT('8'),
    SEVEN('7'),
    SIX('6'),
    FIVE('5'),
    FOUR('4'),
    THREE('3'),
    TWO('2');

    companion object {
        private val map = entries.associateBy { it.label }
        infix fun from(value: Char) = map[value]
    }
}
