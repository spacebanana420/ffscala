package ffscala.capture

import ffscala.*
import scala.sys.process._
import java.io.File
import ffscala.misc.*

//Functions for custom arguments for desktop and audio capture
//It's not yet recommended to use them.

def x11grab_hideMouse(): List[String] = List("-draw_mouse", "0") //check which is the default

def x11grab_setInput(display: Int, screen: Int): List[String] =
  if display < 0 || screen < 0 then
    List("-i", ":0.0")
  else
    List("-i", s":$display.$screen")

def custom_audioChannels(c: Int = 2): List[String] =
  if c <= 0 then
    List("-ac", "2")
  else
    List("-ac", c.toString)

def custom_audioSampleRate(rate: Int): List[String] =
  if rate > 0 then
    List("-sample_rate", rate.toString)
  else
    List("-sample_rate", "48000")

def custom_Framerate(fps: Int): List[String] =
  if fps < 1 then
    List("-framerate", "30")
  else
    List("-framerate", fps.toString)

def custom_captureSize(x: Int, y: Int): List[String] =
  if x < 1 || y < 1 then
    List("-video_size", "1024x768")
  else
    List("-video_size", s"${x}x$y")
