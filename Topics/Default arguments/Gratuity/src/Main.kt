import java.io.File

fun main(){
    val dirPath = "C:\\Users\\Andre\\Documents\\basedir"
    val files= File(dirPath)

    findDirWithMaxFiles(dirPath, files)

}

fun findDirWithMaxFiles(dirPath: String, files: File) {
    var biggestDir = ""
    var maxFiles = 0

    files.walkTopDown().forEach {
        if (it.isDirectory){
            val currentFiles = it.listFiles().filter { obj -> obj.isFile }

            if (currentFiles.size > maxFiles) {
                maxFiles = currentFiles.size
                biggestDir = it.name
                println("maxFiles: $maxFiles biggestDir: $biggestDir")
            }
        }
    }

    println("$biggestDir $maxFiles")
}

fun findEmptyDirNames(dirPath: String, files: File) {

    val emptyDirs = mutableListOf<String>()

    files.walkTopDown().forEach {
        if (it.isDirectory && it.listFiles().isEmpty()){
            emptyDirs.add(it.name)
        }
    }

    println(emptyDirs.joinToString())

}
