package ffscala.capture

import ffscala.*
import scala.sys.process._
import ffscala.misc.*

def record(output: String, args: List[String], quiet: Boolean = false, exec: String = "ffmpeg"): Int =
  try
    val cmd: List[String] =
      if quiet == true then
        List(exec, "-y", "-loglevel", "quiet") ++ args :+ output
      else
        List(exec, "-y", "-hide_banner") ++ args :+ output
    cmd.!
  catch
    case e: Exception => -1

def captureVideo(mode: String, i: String, fps: Int): List[String] =
  val supported = supportedCaptureModes("video")

  val arg_mode =
    if belongsToList(mode, supported) then
      mode
    else
      "x11grab"
  val arg_fps =
    if fps < 1 then
      List("-framerate", "30")
    else
      List("-framerate", fps.toString)

  List("-f", arg_mode) ++ arg_fps ++ processDesktopInput(i, mode)

def captureAudio(mode: String, input: String, ch: Byte = 2, rate: Int = 48000): List[String] =
  val supported = supportedCaptureModes("audio")

  val arg_ch =
    if ch <= 0 then
      processAudioChannels(2, mode)
    else
      processAudioChannels(ch, mode)
  val arg_rate =
    if rate > 0 then
      List("-sample_rate", rate.toString)
    else
      List("-sample_rate", "48000")
  val arg_mode =
    if belongsToList(mode, supported) then
      mode
    else
      "pulse"

  List("-f", arg_mode) ++ arg_ch ++ arg_rate ++ processAudioInput(input, mode)

def addTracks(amt: Int): List[String] =
  def recurse(i: Int = 0, s: List[String] = List()): List[String] =
    if i >= amt then
      s
    else
      recurse(i+1, s :+ "-map" :+ i.toString)

  recurse()

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
