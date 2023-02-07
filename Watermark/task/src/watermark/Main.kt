package watermark

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.system.exitProcess

fun main() {
    val bundle = Bundle()
    bundle.image = askForImage()
    bundle.watermark = askForImage(watermark = true)

    bundle.compareImages()

    if (bundle.watermark.colorModel.hasAlpha()) {
        bundle.useAlpha = useAlphaChannel(bundle.watermark)
    }
    else {
        bundle.transparencyColor = askForTransparencyColor()
    }
    bundle.watermarkWeight = askWatermarkColorWeight()
    
    bundle.method = askMethod()
    if (bundle.method == "single"){
        bundle.singlePoint = askSinglePoint(bundle.image, bundle.watermark)
    }
    bundle.resultImageName = askOutputImageName()
    bundle.applyWatermark()
    bundle.saveResult()
}

class Bundle() {
    var image: BufferedImage = BufferedImage(1,1,1)
    var watermark: BufferedImage = BufferedImage(1,1,1)
    private var resultImage: BufferedImage = BufferedImage(1,1,1)
    var resultImageName: String = ""
    var useAlpha = false
    var watermarkWeight: Int = 50
    var transparencyColor: Color? = null
    var method: String = ""
    var singlePoint = Point()

    fun compareImages() {
        if (watermark.width > image.width ||  watermark.height > image.height){
            println("The watermark's dimensions are larger.")
            exitProcess(1)
        }
    }

    fun saveResult() {
        ImageIO.write(resultImage, resultImageName.substringAfterLast("."), File(resultImageName))
        println("The watermarked image $resultImageName has been created.")
    }

    fun applyWatermark(){
        if (method == "single") {
            resultImage = applySingleWatermark(image)
        }
        else {
            resultImage = applyGridWatermark(image)
        }
    }

    private fun applyGridWatermark(originalImage: BufferedImage): BufferedImage {
        var tempImage = originalImage
        singlePoint = Point(0,0)

        val jumpX = watermark.width
        val jumpY = watermark.height

        for (x in 0..originalImage.width step jumpX){
            for (y in 0..originalImage.height step jumpY){
                tempImage = applySingleWatermark(tempImage)
                singlePoint.y += jumpY
            }
            singlePoint.x += jumpX
            singlePoint.y = 0
        }

        return tempImage
    }

    private fun applySingleWatermark(originalImage: BufferedImage): BufferedImage{
        var tempImage = originalImage

        for (waterPositionX in 0 until watermark.width){
            val tempImgX = singlePoint.x + waterPositionX
            if (tempImgX > image.width-1){
                break
            }
            for (waterPositionY in 0 until watermark.height){
                val tempImgY = singlePoint.y + waterPositionY

                if ( tempImgY > image.height-1) {
                    break
                }
                else{
                    val i = Color(image.getRGB(tempImgX, tempImgY), useAlpha)
                    val w = Color(watermark.getRGB(waterPositionX, waterPositionY), useAlpha)

                    val color = if ((useAlpha && w.alpha == 0) ||
                        (transparencyColor != null && w.rgb == transparencyColor!!.rgb)
                    ) {
                        i
                    } else {
                        calculateColor(i, w, watermarkWeight)
                    }

                    tempImage.setRGB(tempImgX, tempImgY, color.rgb)
                }
            }
        }
        return tempImage
    }

    private fun calculateColor(i: Color, w: Color, weight: Int): Color {
        return Color(
            (weight * w.red + (100 - weight) * i.red) / 100,
            (weight * w.green + (100 - weight) * i.green) / 100,
            (weight * w.blue + (100 - weight) * i.blue) / 100
        )
    }
}

class Point(var x: Int = 0, var y: Int = 0)

fun askForImage(watermark: Boolean = false): BufferedImage {

    val messageAskImage: String
    val errorMessageColorComponents: String
    val errorMessagePixelSize: String

    if (!watermark){
        messageAskImage = "Input the image filename:"
        errorMessageColorComponents = "The number of image color components isn't 3."
        errorMessagePixelSize = "The image isn't 24 or 32-bit."
    }
    else {
        messageAskImage = "Input the watermark image filename:"
        errorMessageColorComponents = "The number of watermark color components isn't 3."
        errorMessagePixelSize = "The watermark isn't 24 or 32-bit."
    }

    println(messageAskImage)

    val filePath = readln()
    val imageFile = File(filePath)

    if (!imageFile.exists()){
        println("The file $filePath doesn't exist.")
        exitProcess(1)
    }

    val image: BufferedImage = ImageIO.read(imageFile)

    if(image.colorModel.numColorComponents != 3) {
        println(errorMessageColorComponents)
        exitProcess(1)

    } else if(image.colorModel.pixelSize !in intArrayOf(24,32)) {
        println(errorMessagePixelSize)
        exitProcess(1)
    }

    return image

}

fun useAlphaChannel(watermark: BufferedImage): Boolean {
    //watermark has alpha channel
    if (watermark.transparency == BufferedImage.TRANSLUCENT && watermark.colorModel.hasAlpha()) {
        println("Do you want to use the watermark's Alpha channel?")
        if (readln().equals("yes", true))
            return true
    }
    return false
}

fun askWatermarkColorWeight(): Int {
    println("Input the watermark transparency percentage (Integer 0-100):")

    val percentage = readln().toIntOrNull()

    if (percentage == null ){
        println("The transparency percentage isn't an integer number.")
        exitProcess(1)
    } else if (percentage !in (0..100)) {
        println("The transparency percentage is out of range.")
        exitProcess(1)
    }

    return percentage
}

fun askForTransparencyColor(): Color? {
    println("Do you want to set a transparency color?")
    val setTransparencyColor = readln()

    if (setTransparencyColor == "yes") {
        println("Input a transparency color ([Red] [Green] [Blue]):")
        val transpColorArray = readln().split(" ")

        if (transpColorArray.size != 3){
            println("The transparency color input is invalid.")
            exitProcess(1)
        }

        for (i in transpColorArray){
            if (i.toInt() !in 0..255){
                println("The transparency color input is invalid.")
                exitProcess(1)
            }
        }

        return Color(transpColorArray[0].toInt(), transpColorArray[1].toInt(), transpColorArray[2].toInt())
    }

    return null
}

fun askMethod(): String {
    println("Choose the position method (single, grid):")
    val input = readln()
    if(input != "single" && input != "grid"){
        println("The position method input is invalid.")
        exitProcess(1)
    }
    return input
}

fun askSinglePoint(image: BufferedImage, watermark:BufferedImage): Point {

    val maxWidth = image.width - watermark.width
    val maxHeight = image.height - watermark.height
    println("Input the watermark position ([x 0-$maxWidth] [y 0-$maxHeight]):")

    val input = readln().split(" ").map { it.toIntOrNull() }

    if (input[0] == null || input[1] == null){
        println("The position input is invalid.")
        exitProcess(1)
    } else if (input[0] !in 0..maxWidth || input[1] !in 0.. maxHeight )  {
        println("The position input is out of range.")
        exitProcess(1)
    } else {
        return Point(input[0]!!, input[1]!!)
    }
}

fun askOutputImageName(): String {
    println("Input the output image filename (jpg or png extension):")

    val filename = readln()

    if (!filename.endsWith(".jpg") && !filename.endsWith(".png")){
        println("The output file extension isn't \"jpg\" or \"png\".")
        exitProcess(1)
    }

    return filename
}














/*
fun getTransparency(transparency: Int): String{
    when (transparency) {
        Transparency.OPAQUE -> return "OPAQUE"
        Transparency.BITMASK -> return "BITMASK"
        Transparency.TRANSLUCENT -> return "TRANSLUCENT"
    }

    return "Value unknown for Transparency"
}

fun printDetails(imageFile: File, filePath: String) {
    val image = ImageIO.read(imageFile)

    println(
        """
Image file: $filePath
Width: ${image.width}
Height: ${image.height}
Number of components: ${image.colorModel.numComponents}
Number of color components: ${image.colorModel.numColorComponents}
Bits per pixel: ${image.colorModel.pixelSize}
Transparency: ${getTransparency(image.transparency)}
""".trimIndent()
    )
}
*/