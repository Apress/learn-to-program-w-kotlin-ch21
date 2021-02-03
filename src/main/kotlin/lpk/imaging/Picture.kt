package lpk.imaging

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun loadPictureFromFile(imageFile: File): Picture {
    val image = ImageIO.read(imageFile)
    val width = image.width
    val height = image.height
    val pixels = Array<Array<Color>>(height) {
        row ->
        Array<Color>(width) {
            column ->
            Color(image.getRGB(column, row))
        }
    }
    return Picture(pixels)
}

class Picture(val pixels: Array<Array<Color>>) {
    fun height(): Int {
        return pixels.size
    }

    fun width(): Int {
        return pixels[0].size
    }

    fun pixelByRowColumn(row: Int, column: Int): Color {
        return pixels[row][column]
    }

    fun cropTo(rowAt: Int, columnAt: Int, h: Int, w: Int): Picture {
        val cropArray = Array<Array<Color>>(h) {
            row ->
            Array<Color>(w) {
                column ->
                pixelByRowColumn(rowAt + row, columnAt + column)
            }
        }
        return Picture(cropArray)
    }

    fun chopIntoSquares(sideLength: Int): Array<Array<Picture>> {
        val resultRows = height() / sideLength
        val resultColumns = width() / sideLength
        val result = Array<Array<Picture>>(resultRows) {
            blockRow ->
            Array<Picture>(resultColumns) {
                blockColumn ->
                cropTo(blockRow * sideLength, blockColumn * sideLength, sideLength, sideLength)
            }
        }
        return result
    }

    fun averageColor(): Color {
        var totalRed = 0
        var totalGreen = 0
        var totalBlue = 0
        for (row in 0..height() - 1) {
            for (column in 0..width() - 1) {
                val pixel = pixelByRowColumn(row, column)
                totalRed = totalRed + pixel.red
                totalGreen = totalGreen + pixel.green
                totalBlue = totalBlue + pixel.blue
            }
        }
        val count = height() * width()
        return Color(totalRed / count, totalGreen / count, totalBlue / count)
    }

    fun scaleDown(factor: Int): Picture {
        //First break it into a double array
        //of factor-by-factor square sub-pictures.
        val blocks = chopIntoSquares(factor)
        //Initialise a pixel array using the blocks.
        val newPixels = Array<Array<Color>>(blocks.size) {
            blocksRow ->
            Array<Color>(blocks[blocksRow].size) {
                blocksColumn ->
                //Each pixel is the average color of the
                //corresponding block.
                blocks[blocksRow][blocksColumn].averageColor()
            }
        }
        return Picture(newPixels)
    }

    fun saveTo(file: File) {
        val image = BufferedImage(width(), height(), BufferedImage.TYPE_INT_RGB)
        val width = width()
        val height = height()
        for (row in 0..height - 1) {
            for (column in 0..width - 1) {
                image.setRGB(column, row, pixelByRowColumn(row, column).rgb)
            }
        }
        ImageIO.write(image, "png", file)
    }

    fun transform(pixelTransformation: (Color) -> (Color)): Picture {
        val transformed = Array<Array<Color>>(height()) {
            row ->
            Array<Color>(width()) {
                column ->
                val pixel = pixelByRowColumn(row, column)
                pixelTransformation(pixel)
            }
        }
        return Picture(transformed)
    }

    fun sliceVerticallyIntoPicturesContaining(toMatch: Color): List<Picture> {
        return sliceIntoPicturesMatching(false, toMatch)
    }

    fun sliceHorizontallyIntoPicturesContaining(toMatch: Color): List<Picture> {
        return sliceIntoPicturesMatching(true, toMatch)
    }

    private fun sliceIntoPicturesMatching(sliceHorizontally: Boolean, toMatch: Color) : List<Picture> {
        val result = mutableListOf<Picture>()
        var firstMatch = -1
        var lastMatch = -1
        val limit = if (sliceHorizontally) height() - 1 else width() - 1
        for (i in 0..limit) {
            val sliceContainsMatchingPixel = if (sliceHorizontally) {
                rowContainsPixelMatching(i, toMatch)
            } else {
                columnContainsPixelMatching(i, toMatch)
            }
            if (sliceContainsMatchingPixel) {
                if (firstMatch == -1) {
                    firstMatch = i
                }
                lastMatch = i
            } else {
                if (firstMatch == -1) {
                    //Still looking for first match.
                    //Keep looking
                } else {
                    //This is a gap.
                    if (firstMatch >= 0) {
                        //The current sub-picture is complete.
                        val sliceseInCurrentPiece = lastMatch - firstMatch + 1
                        val piece = if (sliceHorizontally) {
                            cropTo(firstMatch, 0, sliceseInCurrentPiece, width())
                        } else {
                            cropTo(0, firstMatch, height(), sliceseInCurrentPiece)
                        }
                        result.add(piece)
                        //Reset the markers.
                        firstMatch = -1
                        lastMatch = -1
                    }
                }
            }
        }
        //There may be a piece left over,
        //that extends to the edge of the picture.
        //Add it, if it exists.
        if (firstMatch >= 0) {
            val sliceseInCurrentPiece = lastMatch - firstMatch + 1
            val piece = if (sliceHorizontally) {
                cropTo(firstMatch, 0, sliceseInCurrentPiece, width())
            } else {
                cropTo(0, firstMatch, height(), sliceseInCurrentPiece)
            }
            result.add(piece)
        }
        return result
    }


    fun columnContainsPixelMatching(column: Int, toMatch: Color): Boolean {
        for (row in 0..height() - 1) {
            if (pixelByRowColumn(row, column) == toMatch) {
                return true
            }
        }
        return false
    }

    fun rowContainsPixelMatching(row: Int, toMatch: Color): Boolean {
        for (column in 0..width() - 1) {
            if (pixelByRowColumn(row, column) == toMatch) {
                return true
            }
        }
        return false
    }
}