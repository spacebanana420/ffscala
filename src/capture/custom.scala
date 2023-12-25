package ffscala.capture

import ffscala.*
import scala.sys.process._
import java.io.File
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
