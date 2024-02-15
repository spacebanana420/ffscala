package ffscala.capture

import ffscala.*
import scala.sys.process.*
import ffscala.misc.*

def record(output: String, captureargs: List[String], args: List[String] = List(), quiet: Boolean = true, exec: String = "ffmpeg"): Int =
  try
    val cmd: List[String] =
      if quiet then
        List(exec, "-y", "-loglevel", "quiet") ++ captureargs ++ args :+ output
      else
        List(exec, "-y", "-hide_banner") ++ captureargs ++ args :+ output
    cmd.!<
  catch
    case e: Exception => -1

def takeScreenshot(mode: String, i: String, output: String, showmouse: Boolean = false, args: List[String] = List(), quiet: Boolean = true, exec: String = "ffmpeg"): Int =
  val path =
    if isFormatSupported(output, "image") then
      output
    else
      s"${removeExtension(output)}.png"
  val supported = supportedCaptureModes("video")
  val arg_mode =
    if belongsToList(mode, supported) then
      List("-f", mode)
    else
      List("-f", "x11grab")
  val arg_mouse =
    if showmouse then
      List("-draw_mouse", "1")
    else
      List("-draw_mouse", "0")

  val arg_base = getBaseArgs(exec, quiet)
  val cmd: List[String] =
    if args != List() then
      arg_base ++ arg_mode ++ arg_mouse ++ processDesktopInput(i, mode) ++ List("-frames:v", "1", path)
    else
      arg_base ++ arg_mode ++ arg_mouse ++ processDesktopInput(i, mode) ++ List("-frames:v", "1") ++ args :+ path
  try
    cmd.!
  catch
    case e: Exception => -1

def addTracks(amt: Int): List[String] =
  def recurse(i: Int = 0, s: List[String] = List()): List[String] =
    if i >= amt then
      s
    else
      recurse(i+1, s :+ "-map" :+ i.toString)

  recurse()
