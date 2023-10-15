package ffscala

import misc.*

def scale(width: Int, height: Int): String = {
    if width <= 0 || height <= 0 then
        ""
    else
        "-filter:v scale=" + width + ":" + height  + " "
}

def scaleFactor(width: Float, height: Float): String = {
    if width <= 0 || height <= 0 then
        ""
    else
        "-filter:v scale=iw*" + width + ":ih*" + height  + " "
}

def setScaleFilter(filter: String): String = {
    val supportedFilters = List("bicubic", "bilinear", "neighbor", "lanczos")
    val foundFilter = belongsToList(filter, supportedFilters)
    if foundFilter == false then
        ""
    else
        "-sws_flags " + filter  + " "
}

def normalizeAudio(): String = "-filter:a loudnorm "

// def compressAudio(threshold: Float): String = {
//     if threshold < 0.000976563 || threshold > 1
//         ""
//     else
//         "-filter:a acompressor " + threshold + " "
// }

def changeVolume(volume: String): String = { //1.0  1.8  10dB -5dB
    "-filter:a volume=" + volume + " "
}


def crop(x: Int, y: Int, width: Int, height: Int): String = {
    if x < 0 || y < 0 || width <= 0 || height <= 0 then
        ""
    else
        "-filter:v crop=" + width + ":" + height + ":" + x + ":" + y + " "
}

def cropCenter(width: Int, height: Int): String = {
    if width <= 0 || height <= 0 then
        ""
    else
        "-filter:v crop=" + width + ":" + height + " "
}

def cropToAspect(width: Byte, height: Byte): String = {
    if width <= 0 || height <= 0 || width == height then
        ""
    else if width > height then
        val ih: String = s"$height*iw/$width"
        "-filter:v crop=iw:" + ih + " "
    else
        val iw: String = s"$width*ih/$height"
        "-filter:v crop=" + iw + ":ih "
}
