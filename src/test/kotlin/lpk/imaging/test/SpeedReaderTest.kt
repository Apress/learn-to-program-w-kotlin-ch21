package lpk.imaging.test

import org.junit.Assert
import org.junit.Test
import lpk.imaging.SpeedReader
import java.nio.file.Paths

val SPEED_SIGNS = "src/test/resources/images/signs/"

class SpeedReaderTest {
    @Test
    fun readTest() {
        Assert.assertEquals(5, reader("5.png").speed())
        Assert.assertEquals(10, reader("10.png").speed())
        Assert.assertEquals(20, reader("20.png").speed())
        Assert.assertEquals(40, reader("40.png").speed())
        Assert.assertEquals(60, reader("60.png").speed())
        Assert.assertEquals(70, reader("70.png").speed())
        Assert.assertEquals(80, reader("80.png").speed())
        Assert.assertEquals(90, reader("90.png").speed())
        Assert.assertEquals(130, reader("130.png").speed())
    }

    fun reader(name: String): SpeedReader {
        val file = Paths.get(SPEED_SIGNS + "/" + name).toFile()
        return SpeedReader(file)
    }
}