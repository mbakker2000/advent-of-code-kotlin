package `2021`

import readInput

fun main() {
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


    fun solution(cycles: Int, input: List<Pair<Int, Long>>): Long {
        val list = 0..cycles
        var fishes = input
        list.forEachIndexed() { index, it ->
            fishes = fishes
                .map { fish ->
                    if (fish.first == 0) listOf(6 to fish.second, 8 to 1L) else listOf(fish.first - 1 to fish.second)
                }
                .fold(initial = empty) { acc, list ->
                    acc.map { fish ->
                        if (list.any { pair -> fish.first == pair.first })
                            fish.first to fish.second + list[0].second
                        else fish
                    }
                }
        }
        return fishes.map { it.second }.fold(0) { acc, l ->
            acc + l
        }
    }

    fun part1(input: List<Pair<Int, Long>>): Long {
        return solution(79, input)
    }

    fun part2(input: List<Pair<Int, Long>>): Long {
        return solution(255, input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2021/Day06_test")
    val testFishes: List<Pair<Int, Long>> = testInput[0].split(",").map { it.toInt() to 1L }
    check(part1(testFishes) == 5934L)

    val input = readInput("2021/Day06")
    val fishes: List<Pair<Int, Long>> = input[0].split(",").map { it.toInt() to 1L }

    println("part1:" + part1(fishes))//5576
    check(part2(testFishes) == 26984457539)//12
    println("part2:" + part2(fishes))//25925
}
