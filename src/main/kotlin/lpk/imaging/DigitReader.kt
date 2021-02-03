package lpk.imaging

fun read(summary: PictureSummary): Int {
    if (summary.heightToWidth > 3.0) return 1
    if (summary.hasBottomBar) return 2
    if (summary.hasTopBar) {
        if (summary.proportionBlackRight < .2) {
            return 7
        }
        return 5
    }
    if (summary.hasCentreBlank) return 0;
    //Of the remaining digits:
    // 6 is bottom-heavy
    if (summary.proportionBlackBottom > .28 && summary.proportionBlackTop < .12) return 6
    // 9 is top-heavy
    if (summary.proportionBlackBottom < .12 && summary.proportionBlackTop > .28) return 9
    // 4 has a dense centre and light left side
    if (summary.hasCentreDark && summary.proportionBlackLeft < .2) return 4
    // 3 is right-heavy and has an almost empty centre
    if (summary.proportionBlackRight > 2 * summary.proportionBlackLeft) return 3
    return 8
}