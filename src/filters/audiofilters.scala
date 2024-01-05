package ffscala

import ffscala.misc.*

def normalizeAudio(): List[String] = List("a", "loudnorm")

// def compressAudio(threshold: Float): String = {
//   if threshold < 0.000976563 || threshold > 1
//     ""
//   else
//     "a acompressor " + threshold + " "
// }

def changeVolume(volume: String): List[String] = //1.0  1.8  10dB -5dB
  List("a", s"volume=$volume")

def audioContrast(contrast: Byte): List[String] =
  val contrast_filtered =
    if contrast < 0 then
      0
    else if contrast > 100 then
      100
    else
      contrast
  List("a", s"acontrast=$contrast_filtered")

def crystalize(i: Float, clip: Boolean = true): List[String] =
  if i < -10.0 || i > 10.0 then
    List("a", "crystalize=2:true")
  else
    List("a", s"crystalize=$i:${clip.toString}")
