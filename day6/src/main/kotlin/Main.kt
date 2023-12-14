import java.io.File

fun main(args: Array<String>) {
    val data = getData("src/main/resources/input.txt")
    val numWinsMultiply = winMultiply(data)

    val bigRace = convertToOneRace(data)
    val bigRaceWins = getNumWaysToWin(bigRace.first, bigRace.second)

    println("Part One - Number of ways to win multiplied is $numWinsMultiply")
    println("Part Two - Number of ways to win a big single race is $bigRaceWins")
}

fun convertToOneRace(races: List<Pair<Long, Long>>): Pair<Long, Long> {
    var time = ""
    var distance = ""

    for (race in races) {
        time += race.first.toString()
        distance += race.second.toString()
    }

    return Pair(time.toLong(), distance.toLong())
}

fun winMultiply(races: List<Pair<Long, Long>>): Long {
    var winMultiply: Long = 1
    for (race in races) {
        winMultiply *= getNumWaysToWin(race.first, race.second)
    }

    return winMultiply
}

fun getNumWaysToWin(timeLimit: Long, distance: Long): Long {
    var wins: Long = 0
    for (buttonHold in 0 .. timeLimit) {
        //Time used holding the button is the same as the speed in millimeters per second
        if ((buttonHold * (timeLimit - buttonHold) > distance)) {
                wins += 1
        }
    }
    return wins
}

fun getData(fileName: String): List<Pair<Long, Long>> {

    val input = readFile(fileName)
    val races: MutableList<Pair<Long, Long>> = emptyList<Pair<Long, Long>>().toMutableList()
    val times = input[0]
        .substring("Time:".length)
        .split(" ")
        .filter { it.isNotEmpty() }
        .map { it.toLong() }

    val distances = input[1]
        .substring("Distance:".length)
        .split(" ")
        .filter { it.isNotEmpty() }
        .map { it.toLong() }

    for (i in 0 until minOf(times.size, distances.size)) {
        races.add(Pair(times[i], distances[i]))
    }

    return races.toList()
}

fun readFile(fileName: String): List<String>
        = File(fileName).readLines()