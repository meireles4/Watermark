fun String?.capitalize(): String? {
    println("Before: $this")
    return when {
        isNullOrBlank() -> this
        length == 1 -> uppercase()
        else -> this[0].uppercase() + substring(1)
    }.also {
        println("After: $it")
    }
}

fun isPrime(number: Int): Boolean {
    for (i in 2..(number / 2)) {
        if (number % i != 0)
            continue
        else
            return false
    }
    return true
}

fun main() {

    val list = listOf<Int>(1)
    val l = mutableListOf(1,2,3)
    l.removeAll(listOf(1))

}