import java.io.File

fun main() {

    val separator = File.separator
    val path = ".\\Topics\\When expression\\From string to a number\\src\\words_sequence.txt"

    val file = File(path)

    var maxLength = 0
    var maxWord = ""
    file.forEachLine {
        if (it.length > maxLength){
            maxLength = it.length
            maxWord = it
        }
    }

    println("$maxLength $maxWord")

    // write your code here
    /*val input = readln()
    when (input) {
        "one" -> println("1")
        "two" -> println("2")
        "three" -> println("3")
        "four" -> println("4")
        "five" -> println("5")
        "six" -> println("6")
        "seven" -> println("7")
        "eight" -> println("8")
        "nine" -> println("9")
    }*/
}
