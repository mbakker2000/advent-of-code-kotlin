package `2020`

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        input.forEach {
            val a = it.toInt()
            val b = input
                .map { it.toInt() }
                .filter { it + a == 2020 }
                .fold(0) { acc, i ->  acc + i}
            if (a + b == 2020) return a * b
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        input.forEach { it ->
            val a = it.toInt()
            input.forEach { item ->
                val b = item.toInt()
                val c = input
                    .map { line -> line.toInt() }
                    .filter { it + a + b == 2020 }
                    .fold(0) { acc, i ->  acc + i}
                if (a + b + c == 2020) return a * b * c
            }
        }
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2020/Day01_test")
    check(part1(testInput) == 2000 * 20)

    val input = readInput("2020/Day01")
    println(part1(input))
    println(part2(input))
}
