class Car(val make: String, val year: Int) {

    var speed: Int = 0

    fun accelerate() {
        this.speed += 5
    }

    fun decelerate() {
        if (this.speed >= 5)
            speed -= 5
        else
            this.speed = 0
    }
}