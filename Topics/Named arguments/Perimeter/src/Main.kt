fun main() {
    print(perimeter(0.0, 0.0, 12.0, 0.0, 0.0, 5.0))
}

fun perimeter(
    x1: Double, y1: Double,
    x2: Double, y2: Double,
    x3: Double, y3: Double,
    x4: Double? = null, y4: Double? = null): Double {

    var perimeter = 0.0

    perimeter += Math.hypot(x2-x1, y2-y1)
    perimeter += Math.hypot(x3-x2, y3-y2)
    if (x4 == null || y4 == null){
        perimeter += Math.hypot(x1-x3, y1-y3)
    } else {
        perimeter += Math.hypot(x4-x3, y4-y3)
        perimeter += Math.hypot(x1-x4, y1-y4)
    }

    return perimeter
}