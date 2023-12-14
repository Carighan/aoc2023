package day10

import readFileAsLinesUsingBufferedReader

/**
 * Created on 12.12.2023.
 */
fun main(args: Array<String>) {
    val lines: List<String> = readFileAsLinesUsingBufferedReader("src/main/resources/day10.txt")
    val data: Array<Array<Node>> = readLocations(lines)
}

private fun readLocations(lines: List<String>): Array<Array<Node>> {
    val result: Array<Array<Node>>

}

private data class Node(val up: Boolean, val right: Boolean, val down: Boolean, val left: Boolean, var distance: Int)