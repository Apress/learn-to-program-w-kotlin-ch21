package lpk.imaging.test

import org.junit.Assert
import org.junit.Test
import lpk.imaging.PictureSummary
import lpk.imaging.loadPictureFromFile
import java.nio.file.Paths

val SUMMARY = "src/test/resources/images/summary/"

class PictureSummaryTest {
    @Test
    fun proportionBlackLeftTest() {
        Assert.assertEquals(0.0F, summary("wbwb_v.png").proportionBlackLeft)
        Assert.assertEquals(0.5F, summary("bwbw_v.png").proportionBlackLeft)
        Assert.assertEquals(0.25F, summary("bwbw_h.png").proportionBlackLeft)
        Assert.assertEquals(0.25F, summary("wbwb_h.png").proportionBlackLeft)
    }

    @Test
    fun proportionBlackRightTest() {
        Assert.assertEquals(0.0F, summary("bwbw_v.png").proportionBlackRight)
        Assert.assertEquals(0.5F, summary("wbwb_v.png").proportionBlackRight)
        Assert.assertEquals(0.25F, summary("bwbw_h.png").proportionBlackRight)
        Assert.assertEquals(0.25F, summary("wbwb_h.png").proportionBlackRight)
    }

    @Test
    fun proportionBlackTopTest() {
        Assert.assertEquals(0.0F, summary("wbwb_h.png").proportionBlackTop)
        Assert.assertEquals(0.5F, summary("bwbw_h.png").proportionBlackTop)
        Assert.assertEquals(0.25F, summary("bwbw_v.png").proportionBlackTop)
        Assert.assertEquals(0.25F, summary("wbwb_v.png").proportionBlackTop)
    }

    @Test
    fun proportionBlackBottomTest() {
        Assert.assertEquals(0.0F, summary("bwbw_h.png").proportionBlackBottom)
        Assert.assertEquals(0.5F, summary("wbwb_h.png").proportionBlackBottom)
        Assert.assertEquals(0.25F, summary("bwbw_v.png").proportionBlackBottom)
        Assert.assertEquals(0.25F, summary("wbwb_v.png").proportionBlackBottom)
    }

    @Test
    fun hasBottomBarTest() {
        Assert.assertTrue(summary("wbwb_h.png").hasBottomBar)
        Assert.assertTrue(summary("bottombar.png").hasBottomBar)
        Assert.assertFalse(summary("bwbw_h.png").hasBottomBar)
        Assert.assertFalse(summary("nobottombar.png").hasBottomBar)
    }

    @Test
    fun hasTopBarTest() {
        Assert.assertTrue(summary("bwbw_h.png").hasTopBar)
        Assert.assertTrue(summary("topbar.png").hasTopBar)
        Assert.assertFalse(summary("wbwb_h.png").hasTopBar)
        Assert.assertFalse(summary("notopbar.png").hasTopBar)
    }

    @Test
    fun hasCentreBlankTest() {
        Assert.assertTrue(summary("topbar.png").hasCentreBlank)
        Assert.assertTrue(summary("centre5.png").hasCentreBlank)
        Assert.assertFalse(summary("centre10.png").hasCentreBlank)
        Assert.assertFalse(summary("centre90.png").hasCentreBlank)
    }

    @Test
    fun hasCentreDarkTest() {
        Assert.assertFalse(summary("topbar.png").hasCentreDark)
        Assert.assertFalse(summary("centre5.png").hasCentreDark)
        Assert.assertFalse(summary("centre50.png").hasCentreDark)
        Assert.assertFalse(summary("centre65.png").hasCentreDark)
        Assert.assertTrue(summary("centre70.png").hasCentreDark)
        Assert.assertTrue(summary("centre90.png").hasCentreDark)
    }

    @Test
    fun heightToWidthTest() {
        Assert.assertEquals(1.0F, summary("bwbw_v.png").heightToWidth)
        Assert.assertEquals(0.7F, summary("bottombar.png").heightToWidth)
    }

    fun summary(name: String): PictureSummary {
        val file = Paths.get(SUMMARY + name).toFile()
        return PictureSummary(loadPictureFromFile(file))
    }
}