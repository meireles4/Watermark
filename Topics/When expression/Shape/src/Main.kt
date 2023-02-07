const val SQUARE = 1
const val CIRCLE = 2
const val TRIANGLE = 3
const val RHOMBUS = 4

fun main(args: Array<String>) {

    val shape = readln().toInt()
    val message = "You have chosen a "

    when (shape) {
        SQUARE -> println(message + "square")
        CIRCLE -> println(message + "circle")
        TRIANGLE -> println(message + "triangle")
        RHOMBUS -> println(message + "rhombus")
        else -> println("There is no such shape!")
    }

}
