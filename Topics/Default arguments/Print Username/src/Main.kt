// complete this function
fun greetings(name: String = ""): String {
    return if (name == "") {
        "Hello, secret user!"
    } else "Hello, $name!"
}
