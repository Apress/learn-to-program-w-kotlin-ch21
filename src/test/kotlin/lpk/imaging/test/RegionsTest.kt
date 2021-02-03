package lpk.imaging.test

import org.junit.Assert
import org.junit.Test
import lpk.imaging.Regions

class RegionsTest {
    @Test
    fun isInTopQuarterTest() {
        val regions = Regions(40, 60)
        for (i in 0..9) {
            Assert.assertTrue(regions.isInTopQuarter(i))
        }
        for (i in 10..39) {
            Assert.assertFalse(regions.isInTopQuarter(i))
        }
    }

    @Test
    fun isInBottomQuarterTest() {
        val regions = Regions(40, 60)
        for (i in 0..29) {
            Assert.assertFalse(regions.isInBottomQuarter(i))
        }
        for (i in 30..39) {
            Assert.assertTrue(regions.isInBottomQuarter(i))
        }
    }

    @Test
    fun isInLeftQuarterTest() {
        val regions = Regions(40, 60)
        for (i in 0..14) {
            Assert.assertTrue(regions.isInLeftQuarter(i))
        }
        for (i in 15..59) {
            Assert.assertFalse(regions.isInLeftQuarter(i))
        }
    }

    @Test
    fun isInRightQuarterTest() {
        val regions = Regions(40, 60)
        for (i in 0..44) {
            Assert.assertFalse(regions.isInRightQuarter(i))
        }
        for (i in 45..59) {
            Assert.assertTrue(regions.isInRightQuarter(i))
        }
    }

    @Test
    fun isInCentreTest() {
        val regions = Regions(40, 60)
        for (i in 0..39) {
            for (j in 0..59) {
                if (i >= 10 && i < 30 && j >= 15 && j < 45) {
                    Assert.assertTrue(regions.isInCentre(i, j))
                } else {
                    Assert.assertFalse(regions.isInCentre(i, j))
                }
            }
        }
    }

    @Test
    fun centrePixelCountTest() {
        val regions = Regions(40, 60)
        Assert.assertEquals(600, regions.centrePixelCount())
    }

    @Test
    fun isInBottomBarTest() {
        val regions = Regions(70, 60)
        for (i in 0..59) {
            Assert.assertFalse(regions.isInBottomBar(i))
        }
        for (i in 60..69) {
            Assert.assertTrue(regions.isInBottomBar(i))
        }
    }

    @Test
    fun isInTopBarTest() {
        val regions = Regions(70, 60)
        for (i in 0..9) {
            Assert.assertTrue(regions.isInTopBar(i))
        }
        for (i in 10..69) {
            Assert.assertFalse(regions.isInTopBar(i))
        }
    }

    @Test
    fun bottomBarAreaTest() {
        val regions = Regions(70, 60)
        Assert.assertEquals(600, regions.bottomBarArea())
    }
}