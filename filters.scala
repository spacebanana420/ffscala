package filters

import misc.*

def setVideoResolution(width: Int, height: Int): String = {
    if width <= 0 || height <= 0 then
        ""
    else
        "-filter:v scale=" + width + ":" + height  + " "
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

def setVolume(volume: String): String = { //1.0  1.8  10dB -5dB
    "-filter:a volume=" + volume + " "
}


def crop(x: Int, y: Int, width: Int, height: Int): String = {
    if x < 0 || y < 0 || width <= 0 || height <= 0
        ""
    else
        "-filter:v crop=" + width + ":" + height + ":" + x + ":" + y + " "
}

def cropCenter(width: Int, height: Int): String = {
    if width <= 0 || height <= 0
        ""
    else
        "-filter:v crop=" + width + ":" + height + " "
}
