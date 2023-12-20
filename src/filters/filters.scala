package ffscala

import ffscala.misc.*

//Image and audio processing and manipulation filters

def scale(width: Int, height: Int): List[String] =
    if width <= 0 || height <= 0 then
        List("")
    else
        List("-filter:v", s"scale=$width:$height")

def scaleFactor(width: Float, height: Float): List[String] =
    if width <= 0 || height <= 0 then
        List()
    else
        List("-filter:v", s"scale=iw*$width:ih*$height")

def setScaleFilter(filter: String): List[String] =
    val supportedFilters = List("bicubic", "bilinear", "neighbor", "lanczos")
    val foundFilter = belongsToList(filter, supportedFilters)
    if foundFilter == false then
        List()
    else
        List("-sws_flags", filter)

def normalizeAudio(): List[String] = List("-filter:a", "loudnorm")

// def compressAudio(threshold: Float): String = {
//     if threshold < 0.000976563 || threshold > 1
//         ""
//     else
//         "-filter:a acompressor " + threshold + " "
// }

def changeVolume(volume: String): List[String] = //1.0  1.8  10dB -5dB
    List("-filter:a", s"volume=$volume")

def crop(x: Int, y: Int, width: Int, height: Int): List[String] =
    if x < 0 || y < 0 || width <= 0 || height <= 0 then
        List()
    else
        List("-filter:v", s"crop=$width:$height:$x:$y")

def cropCenter(width: Int, height: Int): List[String] =
    if width <= 0 || height <= 0 then
        List()
    else
        List("-filter:v", s"crop=$width:$height")

def cropToAspect(width: Byte, height: Byte): List[String] =
    if width <= 0 || height <= 0 || width == height then
        List()
    else if width > height then
        val ih: String = s"$height*iw/$width"
        List("-filter:v", s"crop=iw:$ih")
    else
        val iw: String = s"$width*ih/$height"
        List("-filter:v", "crop=$iw:ih")
