package ffscala

import java.io.File
import scala.sys.process.*
import ffscala.misc.*

//FFplay functionality to play media

def execplay(input: String, args: List[String] = List(), filters: List[String] = List(), quiet: Boolean = true, exec: String = "ffplay"): Int =
  if File(input).isFile() == true then
    try
      val filter_args = mkFilterArgs(filters)
      val cmd =
        if quiet then
          List(exec, "-loglevel", "quiet", input) ++ args ++ filter_args
        else
          List(exec, "-hide_banner", input) ++ args ++ filter_args
      cmd.!
    catch
      case e: Exception => -1
  else
    -1

// def displayHistogram(input: String, l_height: Short, s_height: Byte, d_mode: String = "stack", l_mode: String = "linear",
// fgopacity: Float = 0.7, bgopacity: Float = 0.5, c_mode: String = "whiteonblack", quiet: Boolean = false, excec: String = "ffplay"): Int =
//   val colormodes = List("whiteonblack", "blackonwhite", "whiteongray", "blackongray", "coloronblack",
//   "coloronwhite", "colorongray", "blackoncolor", "whiteoncolor", "grayoncolor")
//
//   val arg_lheight =
//     if l_height < 50 || l_height > 2048 then
//       "200"
//     else
//       l_height.toString
//   val arg_sheight =
//     if s_height < 0 || s_height > 40 then
//       "12"
//     else
//       s_height.toString
//   val arg_dmode =
//     if belongsToList(d_mode, List("stack", "parade", "overlay")) then
//       d_mode
//     else
//       "stack"
//   val arg_lmode =
//     if belongsToList(l_mode, List("linear", "logarithmic")) then
//       l_mode
//     else
//       "linear"
//   val arg_cmode =
//     if belongsToList(c_mode, colormodes) then
//       c_mode
//     else
//       "whiteonblack"
//   val arg_fop =
//     if fgopacity >= 0 && fgopacity <= 1 then
//       fgopacity.toString
//     else
//       "0.7"
//   val arg_bop =
//     if bgopacity >= 0 && bgopacity <= 1 then
//       bgopacity.toString
//     else
//       "0.5"
//   val histogram_args = s"histogram=level_height=$arg_lheight:scale_height=$arg_sheight:display_mode=$arg_dmode:levels_mode=$arg_lmode:fgopacity=$arg_fop:bgopacity=$arg_bop:color_mode=$arg_cmode"
//
//   val cmd = getBaseArgs(exec, quiet) ++ List("-i", input, histogram_args)
//   cmd.!

def setPlayVolume(volume: Byte): List[String] =
  val filteredvolume: Byte =
    if volume < 0 then
      0
    else if volume > 100 then
      100
    else
      volume
  List("-volume", s"$volume")

def disableBorder(): List[String] = List("-noborder")

def disableDisplay(): List[String] = List("-nodisp")

def setFullscreen(): List[String] = List("-fs")

def setDimensions(x: Int, y: Int): List[String] =
  if x <= 0 || y <= 0 then
    List("")
  else
    List("-x", s"$x", "-y", s"$y")

def setFrameDrop(): List[String] = List("-framedrop")

def setLoop(times: Int): List[String] =
  val filteredTimes =
    if times < 0 then
      0
    else
      times
  List("-loop", times.toString)

def setAutoExit(): List[String] = List("-autoexit")

def setExitOnMouse(): List[String] = List("-exitonmousedown")

def setExitOnKey(): List[String] = List("-exitonkeydown")

def setWindowTitle(title: String): List[String] =
  if title == "" then
    List()
  else
    List("-window_title", title)

def setShowMode(mode: String): List[String] =
  mode match
    case "waves" => List("-showmode", "1")
    case "rdft" => List("-showmode", "2")
    case _ => List("-showmode", "0")

def setSeekInterval(interval: Float): List[String] =
  if interval < 0 then
    List()
  else
    List("-seek_interval", interval.toString)

def enableHWaccel(): List[String] = List("-hwaccel")
