import java.io.File

fun main() {
// Write your code here. Do not import any libraries
    val path = "MyCases.txt"
    val myFile = File(path)
    myFile.appendText("\nThe Sign of the Four")
}