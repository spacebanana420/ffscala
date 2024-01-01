package ffscala

import ffscala.misc.*

//Image and audio processing and manipulation filters

def scale(width: Int, height: Int): List[String] =
  if width <= 0 || height <= 0 then
    List()
  else
    List("v", s"scale=$width:$height")

def scaleFactor(width: Float, height: Float): List[String] =
  if width <= 0 || height <= 0 then
    List()
  else
    List("v", s"scale=iw*$width:ih*$height")

def setScaleFilter(filter: String): List[String] =
  val supportedFilters = List("bicubic", "bilinear", "neighbor", "lanczos")
  val foundFilter = belongsToList(filter, supportedFilters)
  if foundFilter == false then
    List()
  else
    List("-sws_flags", filter)

def crop(x: Int, y: Int, width: Int, height: Int): List[String] =
  if x < 0 || y < 0 || width <= 0 || height <= 0 then
    List()
  else
    List("v", s"crop=$width:$height:$x:$y")

def cropCenter(width: Int, height: Int): List[String] =
  if width <= 0 || height <= 0 then
    List()
  else
    List("v", s"crop=$width:$height")

def cropToAspect(width: Byte, height: Byte): List[String] =
  if width <= 0 || height <= 0 || width == height then
    List()
  else if width > height then
    val ih: String = s"$height*iw/$width"
    List("v", s"crop=iw:$ih")
  else
    val iw: String = s"$width*ih/$height"
    List("v", "crop=$iw:ih")

def setCurves(x: List[Float], y: List[Float], channel: String = "all"): List[String] =
  def recurse(points: String = "", i: Int = 0): String =
    if i >= x.length then
      points
    else if i == 0 then
      recurse(points + s"${x(i).toString}/${y(i).toString}", i+1)
    else
      recurse(points + s" ${x(i).toString}/${y(i).toString}", i+1)

  def isListOk(l: List[Float], i: Int = 0): Boolean =
    if i >= l.length then
      true
    else if i > 0 && l(i) <= l(i)-1 then
      false
    else
      isListOk(l, i+1)
  val channel_filtered =
    channel match
      case "red" | "r" =>
        "r"
      case "green" | "g" =>
        "g"
      case "blue" | "b" =>
        "b"
      case _ =>
        "all"
  if x.length == y.length && x.length > 0 && isListOk(x) then
    List("v", s"curves=$channel_filtered='${recurse()}'")
  else
    List()

def setHSV(h: Float, s: Float, v: Float): List[String] =
  val val_h =
    if h < -360 then
      -360.0f
    else if h > 360 then
      360.0f
    else
      h
  val val_s =
    if s < -10 then
      -10.0f
    else if s > 10 then
      10.0f
    else
      s
  val val_v =
    if v < -10 then
      -10.0f
    else if v > 10 then
      10.0f
    else
      v
  List("v", s"hue=h=$val_h:s=$val_s:b=$val_v")

def setFade(mode: String = "in", start: Long, duration: Long): List[String] =
  val arg_mode =
    if mode == "in" || mode == "out" then
      mode
    else
      "in"
  val arg_start =
    if start >= 0 then
      start
    else
      0
  val arg_duration =
    if duration > 0 then
      duration
    else
      30
  List("v", s"fade=t=$arg_mode:s=$arg_start:n=$arg_duration")

def colorize(h: Short, s: Float = 0.5, l: Float = 0.5, mix: Float = 1): List[String] =
  def filterFloat(in: Float): Float =
    if in < 0 then
      0
    else if in > 1 then
      1
    else
      in

  val arg_h: Short =
    if h < 0 then
      0
    else if h > 360 then
      360
    else
      h
  val arg_s = filterFloat(s)
  val arg_l = filterFloat(l)
  val arg_mix = filterFloat(mix)

  List("v", s"colorize=hue=$arg_h:saturation=$arg_s:lightness=$arg_l:mix=$arg_mix")
