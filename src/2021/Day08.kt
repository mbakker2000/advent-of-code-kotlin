package `2021`

import readInput

fun main() {

    fun doesNotContain(small: String, bigger: String): String {
        return bigger.filter { !small.contains(it) }
    }

    fun doesContain(small: String, bigger: String): String {
        return bigger.filter { small.contains(it) }
    }

    fun part1(input: List<String>): Int {
        val outputs = input.map { it.split(" | ")[1].split(" ") }
        return outputs.sumOf { output -> output.count { listOf(2, 3, 4, 7).contains(it.length) } }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.split(" | ") }
            .map {
                it[0].split(" ")
                    .map { it.toSortedSet().joinToString("") } to it[1]
            }
            .map {
                it.first
                    .map { s -> mutableMapOf(s.length to listOf(s)) }
                    .fold<MutableMap<Int, List<String>>, MutableMap<Int, List<String>>>(
                        mutableMapOf(
                            2 to listOf(),
                            3 to listOf(),
                            4 to listOf(),
                            5 to listOf(),
                            6 to listOf(),
                            7 to listOf()
                        )
                    ) { acc, map ->
                        acc[map.keys.first()] = acc[map.keys.first()]!!.plus(map.values.first())
                        acc
                    } to it.second
            }
            .map { pair ->
                pair.first to pair.second.split(" ")
                    .map { it.toSortedSet().joinToString("") }
                    .map {
                        when {
                            it.length == 2 -> it to "1"
                            it.length == 3 -> it to "7"
                            it.length == 4 -> it to "4"
                            it.length == 5 && doesNotContain(pair.first[2]!!.first(), it).length == 3 -> it to "3"
                            it.length == 5 && doesContain(pair.first[4]!!.first(), it).length == 3 -> it to "5"
                            it.length == 5 && doesContain(pair.first[4]!!.first(), it).length == 2 -> it to "2"
                            it.length == 6 && doesNotContain(pair.first[4]!!.first(), it).length == 2 -> it to "9"
                            it.length == 6 && doesContain(pair.first[2]!!.first(), it).length == 2 -> it to "0"
                            it.length == 6 && doesContain(pair.first[2]!!.first(), it).length == 1 -> it to "6"
                            else -> it to "8"
                        }
                    }
            }.map { pair -> pair.second.joinToString("") { it.second } }.sumOf { it.toInt() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2021/Day08_test")

    check(part1(testInput) != 37)

    val input = readInput("2021/Day08")
    println("part1:" + part1(input))//247
    //check(part2(testInput) == 1924)//1924
    println("part2:" + part2(input))//933305
}
