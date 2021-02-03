package lpk.imaging

import java.awt.Color

class PictureSummary(picture: Picture) {
    val heightToWidth: Float
    val hasBottomBar: Boolean
    val hasTopBar: Boolean
    val hasCentreBlank: Boolean
    val hasCentreDark: Boolean
    val proportionBlackLeft: Float
    val proportionBlackRight: Float
    val proportionBlackTop: Float
    val proportionBlackBottom: Float

    init {
        heightToWidth = picture.height().toFloat() / picture.width().toFloat()
        val regions = Regions(picture.height(), picture.width())
        var leftQuarterBlack = 0
        var rightQuarterBlack = 0
        var topQuarterBlack = 0
        var bottomQuarterBlack = 0
        var centreBlack = 0
        var bottomBarBlack = 0
        var topBarBlack = 0
        var black = 0
        for (row in 0..picture.height() - 1) {
            for (column in 0..picture.width() - 1) {
                val isBlack = picture.pixelByRowColumn(row, column) == Color.BLACK
                if (isBlack) {
                    black = black + 1
                    if (regions.isInTopBar(row)) topBarBlack++
                    if (regions.isInBottomBar(row)) bottomBarBlack++
                    if (regions.isInCentre(row, column)) centreBlack++
                    if (regions.isInLeftQuarter(column)) leftQuarterBlack++
                    if (regions.isInRightQuarter(column)) rightQuarterBlack++
                    if (regions.isInTopQuarter(row)) topQuarterBlack++
                    if (regions.isInBottomQuarter(row)) bottomQuarterBlack++
                }
            }
        }
        proportionBlackRight = rightQuarterBlack.toFloat() / black.toFloat()
        proportionBlackLeft = leftQuarterBlack.toFloat() / black.toFloat()
        proportionBlackTop = topQuarterBlack.toFloat() / black.toFloat()
        proportionBlackBottom = bottomQuarterBlack.toFloat() / black.toFloat()
        val bottomBarRatio = bottomBarBlack.toFloat() / regions.bottomBarArea().toFloat()
        hasBottomBar = bottomBarRatio > .75
        val topBarRatio = topBarBlack.toFloat() / regions.bottomBarArea().toFloat()
        hasTopBar = topBarRatio > .75
        val centreRatio = centreBlack.toFloat() / regions.centrePixelCount().toFloat()
        hasCentreBlank = centreRatio < .1
        hasCentreDark = centreRatio > .65
    }
}