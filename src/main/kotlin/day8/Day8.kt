package day8

import readFileAsLinesUsingBufferedReader

/**
 * Created on 12.12.2023.
 */
fun main(args: Array<String>) {
    val lines: List<String> = readFileAsLinesUsingBufferedReader("src/main/resources/day8.txt")
    val commands: List<Char> = lines[0].toList()
    val network: Map<String, Pair<String, String>> = readNetwork(lines.subList(2, lines.size))
    var current: String = "AAA"
    var steps: Int = 0
    while (current != "ZZZ") {
        print("Step $steps: I am at $current, ")
        val command = commands[steps % commands.size]
        print("using command ${steps % commands.size}, which is $command, ")
        if (command == 'L') {
            current = network[current]?.first!!
            println("so I'll be going left to $current!")
        } else {
            current = network[current]?.second!!
            println("so I'll be going right to $current!")
        }
        steps++
    }
    println("Part 1: Reached 'ZZZ' in $steps steps")

    val currentNodes: MutableList<String> = network.keys.filter { key -> key.endsWith('A') }.toMutableList()
    var multiSteps = 0
    while (!currentNodes.all { node -> node.endsWith('Z') }) {
        val command = commands[multiSteps % commands.size]
        currentNodes.replaceAll { node -> doStep(node, network, command) }
        multiSteps++
    }
    println("Part 2: Reached all 'xxZ's in $multiSteps steps")
}

private fun doStep(node: String, network: Map<String, Pair<String, String>>, command: Char): String {
    return if (command == 'L') {
        network[node]?.first!!
    } else {
        network[node]?.second!!
    }
}

private fun readNetwork(lines: List<String>): Map<String, Pair<String, String>> {
    val result: MutableMap<String, Pair<String, String>> = mutableMapOf()
    for (line in lines) {
        val position = line.substring(0, 3)
        val left = line.substring(7, 10)
        val right = line.substring(12, 15)
        result[position] = Pair(left, right)
    }
    return result
}
