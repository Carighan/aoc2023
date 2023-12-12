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
        val command = commands[steps % commands.size]
        current = doStep(current, network, command)
        steps++
    }
    println("Part 1: Reached 'ZZZ' in $steps steps")

    val startNodes: MutableList<String> = network.keys.filter { key -> key.endsWith('A') }.toMutableList()
    val solutions: MutableMap<String, Long> = mutableMapOf()
    for (startNode in startNodes) {
        var workNode = startNode
        var workSteps = 0
        while (!workNode.endsWith('Z')) {
            val command = commands[workSteps % commands.size]
            workNode = doStep(workNode, network, command)
            workSteps++
        }
        solutions[startNode] = workSteps.toLong()
    }
    println("Part 2: Reached all 'xxZ's in ${lcm(solutions.values.toList())} steps")
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

private fun gcd(a: Long, b: Long): Long {
    var a = a
    var b = b
    while (b > 0) {
        val temp = b
        b = a % b // % is remainder
        a = temp
    }
    return a
}

private fun lcm(a: Long, b: Long): Long {
    return a * (b / gcd(a, b))
}

private fun lcm(input: List<Long>): Long {
    var result = input[0]
    for (i in 1 until input.size) result = lcm(result, input[i])
    return result
}