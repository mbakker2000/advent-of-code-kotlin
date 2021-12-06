package `2021`

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var fishes = input[0].split(",").map { it.toInt() }
        val list = 0..79
        list.forEach {
            fishes = fishes
                .map { fish -> fish - 1 }
                .map { fish -> if (fish == -1) listOf(6, 8) else listOf(fish) }
                .flatten()
        }
        return fishes.size
    }

    fun part2(input: List<String>): Long {
        var fishes : List<Pair<Int, Long>> = input[0].split(",").map { it.toInt() to 1L }
        val empty = listOf<Pair<Int, Long>>(
            (0 to 0),
            (1 to 0),
            (2 to 0),
            (3 to 0),
            (4 to 0),
            (5 to 0),
            (6 to 0),
            (7 to 0),
            (8 to 0)
        )
        val list = 0..255
        list.forEachIndexed() { index, it ->
            fishes = fishes
                .map { fish ->
                    fish.first - 1 to fish.second
                }
                .map { fish ->
                    if (fish.first == -1) listOf(6 to fish.second, 8 to 1L) else listOf(fish)
                }
                .fold(initial = empty) { acc, list ->
                    acc
                        .map {
                            if (list[0].first == it.first) {
                                it.first to it.second + list[0].second
                            } else {
                                it
                            }
                        }
                        .map {
                            if (list.size > 1 && list[1].first == it.first) {
                                it.first to it.second + list[0].second
                            } else {
                                it
                            }
                        }
                }
        }

        return fishes.map { it.second }.fold(0) { acc, l ->
            acc + l
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2021/Day06_test")
    check(part1(testInput) == 5934)

    val input = readInput("2021/Day06")

    println("part1:" + part1(input))//5576
    check(part2(testInput) == 26984457539)//12
    println("part2:" + part2(input))//25925
}
