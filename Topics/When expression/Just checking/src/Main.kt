fun main() {
    val l = readln().toInt()

    when (l) {
        1, 3, 4 -> println("No!")
        2 -> println("Yes!")
        else -> println("Unknown number")
    }
}
