package `2020`

import readInput

fun main() {

    fun listOfTrees(
        input: List<String>,
        down: Int,
        right: Int
    ): List<Boolean> {
        val result = mutableListOf<String>()
        input.forEachIndexed { index, element ->
            if (index % down == 0) {
                val mod = (index / down * right) % element.length
                var resultString = ""
                val chars = element.toCharArray()
                for (i in 0..right) {
                    val second = (mod + i) % element.length
                    resultString += chars[second]
                }
                result.add(resultString)
            }
        }
        return result
            .map { it.toCharArray()[0] }
            .map { it == '#' }
            .filter { it }
    }

    fun part1(input: List<String>): Int {
        val sum = listOfTrees(input, 1, 3)
        return sum.size
    }

    fun part2(input: List<String>): Int {
        val sum1 = listOfTrees(input, 1, 1).size
        val sum2 = listOfTrees(input, 1, 3).size
        val sum3 = listOfTrees(input, 1, 5).size
        val sum4 = listOfTrees(input, 1, 7).size
        val sum5 = listOfTrees(input, 2, 1).size

        return sum1 * sum2* sum3 * sum4 * sum5
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2020/Day03_test")
    check(part1(testInput) == 0)

    val input = readInput("2020/Day03")
    println("part1:" + part1(input))
    println("part2:" + part2(input))
}
