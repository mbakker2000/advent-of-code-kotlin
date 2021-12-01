package `2021`

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var inc = 0
        var pre = 0
        input.forEachIndexed() { index, it ->
            val current = it.toInt()

            if (current > pre && index != 0) inc++
            pre = current
        }
        return inc
    }

    fun part2(input: List<String>): Int {
        var first = 0
        var second = 0

        val sliding = mutableListOf<String>()

        input.forEachIndexed { index, s ->
            val third = second
            second = first
            first = s.toInt()
            if (index > 1) sliding.add("" + first + second + third)
        }

        return part1(sliding)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2021/Day01_test")
    check(part1(testInput) == 0)

    val input = readInput("2021/Day01")
    println("part1:" + part1(input))
    println("part2:" + part2(input))
}
