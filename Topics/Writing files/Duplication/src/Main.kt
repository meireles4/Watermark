// Write your code here. Do not import any libraries
val text = readLine()!!
    val path = "MyFile.txt"
    val myFile = File(path)
    myFile.writeText("$text$text")
