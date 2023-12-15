import java.io.File

fun main(args: Array<String>) {
    val hands = getData("src/main/resources/inputtest.txt")
    val sortedHands = sortHands(hands)

    println(getData("src/main/resources/inputtest.txt"))
}

fun sortHands(hands: List<Pair<String, Int>>): List<Pair<String, Int>> {
    val sortedHands = mergeSort(hands)




    return sortedHands
}

fun mergeSort(a: List<Pair<String, Int>>): List<Pair<String, Int>> {
    val n = a.size
    if (n <= 1) {
        return a
    }

    val i = n/2
    val a1: List<Pair<String, Int>> = mergeSort(a.subList(0, i))
    val a2: List<Pair<String, Int>> = mergeSort(a.subList(i, n))
    return merge(a1, a2)
}

fun merge(a1: List<Pair<String, Int>>,
          a2: List<Pair<String, Int>>): List<Pair<String, Int>> {
    var i = 0
    var j = 0
    val a: MutableList<Pair<String, Int>> = emptyList<Pair<String, Int>>().toMutableList()

    while(i < a1.size && j < a2.size) {
        if (compareHands(a2[j].first, a1[i].first)) {
            a[i + j] = a1[i]
            i++
        } else {
            a[i + j] = a2[j]
            j++
        }
    }

    while (i < a1.size) {
        a[i + j] = a1[i]
        i++
    }

    while (j < a2.size) {
        a[i + j] = a2[j]
        j++
    }

    return a
}

// If h1 is greater than h2, return true, else false
fun compareHands(h1: String, h2: String): Boolean {
    val labels: List<Char> = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')



    return true
}

fun getData(fileName: String): List<Pair<String, Int>> {
    val input = readFile(fileName)
    val hands: MutableList<Pair<String, Int>> = emptyList<Pair<String, Int>>().toMutableList()

    for (hand in input) {
        val handData = hand.split(" ")
        hands.add(Pair(handData[0], handData[1].toInt()))
    }

    return hands
}

fun readFile(fileName: String): List<String>
        = File(fileName).readLines()

//readdata

// sort the list of handss

// compare two hands (type) (value)