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
