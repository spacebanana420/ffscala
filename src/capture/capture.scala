package ffscala.capture

import ffscala.*
import scala.sys.process._
import java.io.File
import misc.*

def record(name: String, dir: String, args: List[String], quiet: Boolean = false, exec: String = "ffmpeg"): Int =
  try
    val arg_dir =
      if dir != "" && dir != "." && dir != "./" && File(dir).isDirectory() then
        s"$dir/$name"
      else
        name
    val cmd: List[String] =
      if quiet == true then
        List(exec, "-y", "-loglevel", "quiet") ++ args :+ arg_dir
      else
        List(exec, "-y") ++ args :+ arg_dir
    cmd.!
  catch
    case e: Exception => -1

// def captureFramerate(fps: Int): List[String] =
//   if fps < 1 then
//     List("-framerate", "30")
//   else
//     List("-framerate", fps.toString)
//
// def captureSize(x: Int, y: Int): List[String] =
//   if x < 1 || y < 1 then
//     List("-video_size", "1024x768")
//   else
//     List("-video_size", s"${x}x$y")

def captureVideo(mode: String, i: String, fps: Int, showmouse: Boolean = true, x: Int = 0, y: Int = 0): List[String] =
  val supported = List("x11grab") //x11 only for now

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
  val arg_mouse =
    if showmouse == true then
      List("-draw_mouse", "1")
    else
      List("-draw_mouse", "0")

  val input = List("-i", s":$i+$x,$y")
  List("-f", arg_mode) ++ arg_fps ++ arg_mouse ++ input

def captureAudio(mode: String, input: String, ch: Byte = 2, rate: Int = 48000): List[String] =
  val supported = List("alsa", "pulse")

  val arg_ch =
    if ch <= 0 then
      List("-ac", "2")
    else
      List("-ac", ch.toString)
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

  List("-f", arg_mode) ++ arg_ch ++ arg_rate ++ List("-i", input)

// def audioChannels(c: Int = 2): List[String] =
//   if c <= 0 then
//     List("-ac", "2")
//   else
//     List("-ac", c.toString)
//
// def audioSampleRate(rate: Int): List[String] =
//   if rate > 0 then
//     List("-sample_rate", rate.toString)
//   else
//     List("-sample_rate", "48000")

def addTracks(amt: Int): List[String] =
  def recurse(i: Int = 0, s: List[String] = List()): List[String] =
    if i >= amt then
      s
    else
      recurse(i+1, s :+ "-map" :+ i.toString)

  recurse()
