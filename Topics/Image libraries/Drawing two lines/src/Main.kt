import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main(){
    val file = File("lines.png")
    val image = drawLines()
    ImageIO.write(image, "png", file)
}

fun drawLines(): BufferedImage {
  // Add your code here
    val image = BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB)
    val graphics = image.createGraphics()

    graphics.color = Color.RED
    graphics.drawLine(0, 0, image.width, image.height)

    graphics.color = Color.GREEN
    graphics.drawLine( image.width, 0, 0, image.height)

    return image
}