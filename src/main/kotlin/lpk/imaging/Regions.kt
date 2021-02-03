package lpk.imaging

class Regions(val rows: Int, val columns: Int) {
    val seventhHeight: Int
    val sixSeventhsHeight: Int
    val quarterWidth: Int
    val threeQuarterWidth: Int
    val quarterHeight: Int
    val threeQuarterHeight: Int

    init {
        seventhHeight = rows / 7
        sixSeventhsHeight = rows - seventhHeight
        quarterWidth = columns / 4
        threeQuarterWidth = columns - quarterWidth
        quarterHeight = rows / 4
        threeQuarterHeight = rows - quarterHeight
    }

    fun isInTopQuarter(row: Int): Boolean {
        return row < quarterHeight
    }

    fun isInBottomQuarter(row: Int): Boolean {
        return row >= threeQuarterHeight
    }

    fun isInLeftQuarter(column: Int): Boolean {
        return column < quarterWidth
    }

    fun isInRightQuarter(column: Int): Boolean {
        return column >= threeQuarterWidth
    }

    fun isInCentre(row: Int, column: Int): Boolean {
        if (isInTopQuarter(row)) return false
        if (isInBottomQuarter(row)) return false
        if (isInLeftQuarter(column)) return false
        if (isInRightQuarter(column)) return false
        return true
    }

    fun isInBottomBar(row: Int): Boolean {
        if (row >= sixSeventhsHeight) return true
        return false
    }

    fun isInTopBar(row: Int): Boolean {
        if (row < seventhHeight) return true
        return false
    }

    fun bottomBarArea(): Int {
        return columns * seventhHeight
    }

    fun centrePixelCount(): Int {
        return columns * rows / 4
    }
}