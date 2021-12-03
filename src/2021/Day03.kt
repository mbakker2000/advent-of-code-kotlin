package `2021`

import readInput

fun main() {

    fun part1(input: List<String>): Int {
        fun combine() = { acc: MutableList<String>, mutableList: MutableList<String> ->
            for (index in mutableList.indices) {
                val value = acc[index] + mutableList[index]
                acc.removeAt(index)
                acc.add(index, value)
            }
            acc
        }

        val map = input.asSequence()
            .map { s ->
                val list = mutableListOf<String>()
                for (i in s.indices) {
                    list.add(i, s[i].toString())
                }
                list
            }
            .reduce(combine())
            .map { item ->
                val zeros = item.toCharArray().filter { it == '0' }.size
                val ones = item.toCharArray().size - zeros
                mutableListOf(if (zeros > ones) "0" else "1", if (zeros > ones) "1" else "0")
            }
            .reduce(combine())
            .map { it.toInt(2) }.toList()

        return map[0] * map[1]
    }

    fun part2(input: List<String>): Int {
        fun select(ox1: List<String>, index: Int, fn: (List<String>, List<String>) -> List<String>): List<String> {
            val (a, b) = if (ox1.size == 1) ox1 to ox1 else ox1.partition { it[index] == '1' }
            return fn(a, b)
        }

        fun ratings(): Int {
            var ox = input
            var co = input
            return input.first().toCharArray()
                .mapIndexed() { index, item ->
                    ox = select(ox, index) { a, b ->
                        if (b.size > a.size) b else a
                    }
                    co = select(co, index) { a, b ->
                        if (a.size < b.size) a else b
                    }
                    ox to co
                }
                .filter { it.first.size == 1 && it.second.size == 1 }
                .map { it.first[0].toInt(2) to it.second[0].toInt(2) }
                .map { it.first * it.second }
                .reduce { acc, i -> i }
        }

        return ratings()
    }
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2021/Day03_test")
    check(part1(testInput) == 198)

    val input = readInput("2021/Day03")
    println("part1:" + part1(input))//2498354
    check(part2(testInput) == 230)
    println("part2:" + part2(input))//3277956
}
