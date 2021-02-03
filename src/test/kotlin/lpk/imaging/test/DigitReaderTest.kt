package lpk.imaging.test

import org.junit.Assert
import org.junit.Test
import lpk.imaging.PictureSummary
import lpk.imaging.loadPictureFromFile
import lpk.imaging.read
import java.nio.file.Paths

val DIGITS = "src/test/resources/images/digits"

class DigitReaderTest {
    @Test
    fun readTest() {
        @Test
        fun readTest() {
            for (i in 0..9) {
                Assert.assertEquals(i, read(loadSummary("$i.png")))
            }
        }
    }

    fun loadSummary(name: String): PictureSummary {
        val file = Paths.get(DIGITS + "/" + name).toFile()
        return PictureSummary(loadPictureFromFile(file))
    }
}