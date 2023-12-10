import java.io.File

fun main() {

    print(partOne("src/main/resources/input.txt"))

}

fun readFile(fileName: String): List<String>
        = File(fileName).readLines()


fun partOne(fileName: String): String {
    var sum: Int = 0
    for (line in readFile(fileName)) {

        val match = Regex("Card\\s*?\\d+?:\\s*([0-9\\s]*)\\|([0-9\\s]*)").find(line) ?: continue

        val wNumbers: List<Int> = match.groupValues[1].split(" ").mapNotNull { it.toIntOrNull() }
        val myNumbers: List<Int> = match.groupValues[2].split(" ").mapNotNull { it.toIntOrNull() }

        val myWinningNumbers: List<Int> = myNumbers.filter { it in wNumbers }

        if (myWinningNumbers.isNotEmpty()) {
            sum += myWinningNumbers.dropLast(1).fold(1) { acc, _ -> acc * 2 }
        }
    }
    return "Your pile of scratchcards is worth $sum points"
}