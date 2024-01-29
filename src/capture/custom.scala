package ffscala.capture

import ffscala.*
import ffscala.misc.*

//Functions for custom arguments for desktop and audio capture
//It's not yet recommended to use them.

def x11grab_captureVideo(input: String, fps: Int, showmouse: Boolean = true, width: Int = 0, height: Int = 0, x: Int = 0, y: Int = 0): List[String] =
  val arg_fps =
    if fps < 1 then
      List("-framerate", "30")
    else
      List("-framerate", fps.toString)
  val arg_input =
    if x <= 0 && y <= 0 then
      List("-i", s":$input")
    else
      List("-i", s":$input+$x,$y")
  val arg_mouse =
    if showmouse == true then
      List("-draw_mouse", "1")
    else
      List("-draw_mouse", "0")
  val arg_size =
    if width <= 0 || y <= 0 then
      List()
    else
      List("-video_size", s"${width}x$height")
  List("-f", "x11grab") ++ arg_fps ++ arg_mouse ++ arg_size ++ arg_input

def dshow_captureVideo(input: String, fps: Int, width: Int = 0, height: Int = 0): List[String] =
  val arg_fps =
    if fps < 1 then
      List("-framerate", "30")
    else
      List("-framerate", fps.toString)

  val arg_input = List("-i", s"Video=\"$input\"")

  val arg_size =
    if width <= 0 || height <= 0 then
      List()
    else
      List("-video_size", s"${width}x$height")
  List("-f", "dshow") ++ arg_fps ++ arg_size ++ arg_input

def gdigrab_captureVideo(fps: Int, showmouse: Boolean = true, input: String = "", width: Int = 0, height: Int = 0, x: Int = 0, y: Int = 0): List[String] =
  val arg_fps =
    if fps < 1 then
      List("-framerate", "30")
    else
      List("-framerate", fps.toString)

  val arg_input =
    if input != "" then
      List("-i", s"title=\"$input\"")
    else
      List("-i", "desktop")

  val arg_mouse =
    if showmouse == true then
      List("-draw_mouse", "1")
    else
      List("-draw_mouse", "0")

  val arg_size =
    if width <= 0 || y <= 0 then
      List()
    else
      List("-video_size", s"${width}x$height")

  val arg_offset =
    if x <= 0 && y <= 0 then
      List()
    else
      List("-offset_x", x.toString, "-offset_y", y.toString)
  List("-f", "gdigrab") ++ arg_fps ++ arg_size ++ arg_offset ++ arg_mouse ++ arg_input

def dshow_captureAudio(input: String, samplerate: Int, channels: Byte, depth: Byte): List[String] =
  val arg_sample =
    if samplerate < 1 then
      List()
    else
      List("-sample_rate", samplerate.toString)

  val arg_input = List("-i", s"Audio=\"$input\"")

  val arg_ch =
    if channels < 1 then
      List()
    else
      List("-channels", channels.toString)
  val arg_depth =
    if depth < 8 then
      List()
    else
      List("-sample_size", depth.toString)
  List("-f", "dshow") ++ arg_sample ++ arg_ch ++ arg_depth ++ arg_input

// def x11grab_hideMouse(): List[String] = List("-draw_mouse", "0") //check which is the default
// //add the rest
// def x11grab_setInput(display: Int, screen: Int): List[String] =
//   if display < 0 || screen < 0 then
//     List("-i", ":0.0")
//   else
//     List("-i", s":$display.$screen")

// def custom_audioChannels(c: Int = 2): List[String] =
//   if c <= 0 then
//     List("-ac", "2")
//   else
//     List("-ac", c.toString)

// def custom_audioSampleRate(rate: Int): List[String] =
//   if rate > 0 then
//     List("-sample_rate", rate.toString)
//   else
//     List("-sample_rate", "48000")

// def custom_framerate(fps: Int): List[String] =
//   if fps < 1 then
//     List("-framerate", "30")
//   else
//     List("-framerate", fps.toString)
//
// def custom_captureSize(x: Int, y: Int): List[String] =
//   if x < 1 || y < 1 then
//     List("-video_size", "1024x768")
//   else
//     List("-video_size", s"${x}x$y")
