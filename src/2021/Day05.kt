package `2021`

import readInput
import kotlin.math.absoluteValue

fun main() {
    val regex = """
        ^([0-9]|,){3,7}\s(->){1}\s([0-9]|,){3,7}
    """.trimIndent().toRegex()

    fun list(
        input: List<String>, filterFn: (Pair<Pair<Int, Int>, Pair<Int, Int>>) -> Boolean,
        calcFn: (Pair<Pair<Int, Int>, Pair<Int, Int>>) -> List<Pair<Int, Int>>
    ): List<Pair<Int, Int>> {
        val result = input
            .filter { it.matches(regex) }
            .map {
                val split = it.split(" -> ")
                split[0] to split[1]
            }.map {
                val splita = it.first.split(",")
                val splitb = it.second.split(",")
                (splita[0].toInt() to splita[1].toInt()) to (splitb[0].toInt() to splitb[1].toInt())
            }
            .filter(filterFn)
            .map {
                (it.first.first to it.first.second) to ((it.second.first - it.first.first) to (it.second.second - it.first.second))
            }.map {
                if (it.second.first <= 0 && it.second.second <= 0) (it.first.first + it.second.first to it.first.second + it.second.second) to ((it.second.first * -1) to (it.second.second * -1)) else it
            }.map {
                if (it.second.first < 0) (it.first.first + it.second.first to it.first.second + it.second.second) to ((it.second.first * -1) to (it.second.second * -1)) else it
            }
            .map(calcFn).flatten()
        return result
    }

    fun part1Solution(input: List<String>): List<Pair<Int, Int>> {
        val result = list(
            input,
            filterFn = {
                (it.first.first == it.second.first) || (it.first.second == it.second.second)
            },
            calcFn = {
                val set = mutableSetOf<Pair<Int, Int>>()
                for (i in 0..it.second.first) {
                    set.add(it.first.first + i to (it.first.second))
                }
                for (i in 0..it.second.second) {
                    set.add(it.first.first to (it.first.second + i))
                }
                set.toList()
            })
        return result
    }

    fun part2Solution(input: List<String>): List<Pair<Int, Int>> {
        val result = list(
            input,
            filterFn = {
                (it.first.first != it.second.first) && (it.first.second != it.second.second)
            },
            calcFn = {
                val set = mutableSetOf<Pair<Int, Int>>()
                for (i in 0..it.second.first) {
                    set.add(it.first.first + i to
                            (it.first.second) + i * (it.second.second / it.second.second.absoluteValue))
                }
                set.toList()
            })
        return result
    }

    fun part1(input: List<String>): Int {
        val result = part1Solution(input)
        return result.groupingBy { it }.eachCount().filter { it.value > 1 }.size
    }

    fun part2(input: List<String>): Int {
        val result = part1Solution(input).plus(part2Solution(input))
        return result.groupingBy { it }.eachCount().filter { it.value > 1 }.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2021/Day05_test")
    check(part1(testInput) == 5)

    val input = readInput("2021/Day05")

    println("part1:" + part1(input))//5576
    check(part2(testInput) == 12)//12
    println("part2:" + part2(input))//25925
}
