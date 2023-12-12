import java.awt.geom.PathIterator
import java.io.File

fun main() {

    val scratchCardList: List<Pair<List<Int>, List<Int>>> =
        getCards("src/main/resources/input.txt")

    val partOneSum = getCardListSum(
        scratchCardList = scratchCardList,
        start = 1,
        end = scratchCardList.size)

    println("PartOne - Your pile of scratchcards is worth $partOneSum points")

    val numOfCards = MutableList(scratchCardList.size) { 0 }
    var partTwoSum = 0
    for (i in 1 until scratchCardList.size) {
        val numWNums: Int = getWinningNumbers(scratchCardList[i].first, scratchCardList[i].second).size

        numOfCards[i] = (numOfCards[i] + 1)
        for (j in (i + 1) .. (i + numWNums)) {
            numOfCards[j] = (numOfCards[j] + (1 * numOfCards[i]))
        }
        partTwoSum += numOfCards[i]
    }
    numOfCards.removeFirst()

    println("PartTwo - You end up with a total number of $partTwoSum scratch cards")
}


fun readFile(fileName: String): List<String>
        = File(fileName).readLines()

fun getCardListSum(scratchCardList: List<Pair<List<Int>, List<Int>>>, start: Int, end: Int): Int {
    var sum: Int = 0
    for (i in start until minOf(end, scratchCardList.size)) {

        val givenNumbers: List<Int> = scratchCardList[i].first
        val winningNumbers: List<Int> = scratchCardList[i].second
        val givenWinningNumbers: List<Int> = getWinningNumbers(givenNumbers, winningNumbers)

        if (givenWinningNumbers.isNotEmpty()) {
            sum += givenWinningNumbers.dropLast(1).fold(1) { acc, _ -> acc * 2 }
        }
    }
    return sum
}

fun getWinningNumbers(wNumbers: List<Int>, myNumbers: List<Int>): List<Int> {
    return myNumbers.filter { it in wNumbers }
}

fun getCards(fileName: String): List<Pair<List<Int>, List<Int>>> {
    val scratchCardList: MutableList<Pair<List<Int>, List<Int>>> = emptyList<Pair<List<Int>, List<Int>>>().toMutableList()
    scratchCardList.add(Pair(listOf(0), listOf(0)))

    for (line in readFile(fileName)) {

        val match = Regex("Card\\s*?(\\d+?):\\s*([0-9\\s]*)\\|([0-9\\s]*)").find(line) ?: continue

        val cardNumber: List<Int> = match.groupValues[1].split(" ").mapNotNull { it.toIntOrNull() }
        val wNumbers: List<Int> = match.groupValues[2].split(" ").mapNotNull { it.toIntOrNull() }
        val myNumbers: List<Int> = match.groupValues[3].split(" ").mapNotNull { it.toIntOrNull() }

        scratchCardList.add(index = cardNumber[0], element = Pair(wNumbers, myNumbers))
    }
    return scratchCardList.toList()
}