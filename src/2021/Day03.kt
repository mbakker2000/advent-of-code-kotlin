package `2021`

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2021/Day03_test")
    check(part1(testInput) == 1)

    val input = readInput("2021/Day03")
    println("part1:" + part1(input))
    check(part2(testInput) == 1)
    println("part2:" + part2(input))
}
