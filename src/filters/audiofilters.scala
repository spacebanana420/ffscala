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
    List("a", "crystalizer=2:true")
  else
    List("a", s"crystalizer=$i:${clip.toString}")

def vibrato(f: Float, d: Float): List[String] =
  val arg_f =
    if f < 0.1 || f > 20000 then
      "5.0"
    else
      f.toString
  val arg_d =
    if d < 0 || d > 1 then
      "0.5"
    else
      d.toString
  List("a", s"vibrato=$arg_f:$arg_d")

def equalizer(frequency: Int, gain: Short, width: Int = 1, wtype: String = "q"): List[String] =
  def getInt(num: Int, min: Int, max: Int): String =
    if num < min then
      min.toString
    else if num > max then
      max.toString
    else
      num.toString
  def getShort(num: Short, min: Short, max: Short): String =
    if num < min then
      min.toString
    else if num > max then
      max.toString
    else
      num.toString

  val f = getInt(frequency, 0, 999999)
  val w = getInt(width, 0, 999999)
  val g = getShort(gain, -900, 900)
  val wt =
    if belongsToList(wtype, List("h", "q", "o", "s", "k")) then
      wtype
    else
      "q"
  List("a", s"equalizer=f=$f:t=$wt:w=$w:g=$g")

