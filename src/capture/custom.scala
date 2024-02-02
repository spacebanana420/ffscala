package ffscala.capture

import ffscala.*
import ffscala.misc.*

//Backend-specific implementations for video and audio capture

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
    if width <= 0 || height <= 0 then
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

  val arg_input = List("-i", s"video=\"$input\"")

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
    if width <= 0 || height <= 0 then
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

  val arg_input = List("-i", s"audio=\"$input\"")

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

def avfoundation_capture(vinput: String, ainput: String, fps: Int, showmouse: Boolean = true, width: Int = 0, height: Int = 0): List[String] =
  val vi =
    if vinput == "" then
      "default"
    else
      vinput
  val ai =
    if ainput == "" then
      "default"
    else
      ainput
  val arg_input = List("-i", s"$vi:$ai")

  val arg_fps =
    if fps < 1 then
      List("-framerate", "30")
    else
      List("-framerate", fps.toString)

  val arg_mouse =
    if showmouse == true then
      List("-capture_cursor", "1")
    else
      List("-capture_cursor", "0")

  val arg_size =
    if width <= 0 || height <= 0 then
      List()
    else
      List("-video_size", s"${width}x$height")

  List("-f", "avfoundation") ++ arg_fps ++ arg_size ++ arg_mouse ++ arg_input

def pulse_captureAudio(input: String, samplerate: Int, channels: Byte): List[String] =
  generic_captureAudio("pulse", input, samplerate, channels)

def alsa_captureAudio(input: String, samplerate: Int, channels: Byte): List[String] =
  generic_captureAudio("alsa", input, samplerate, channels)

def oss_captureAudio(input: String, samplerate: Int, channels: Byte): List[String] =
  generic_captureAudio("oss", input, samplerate, channels)

def jack_captureAudio(input: String, channels: Byte): List[String] =
  generic_captureAudio("jack", input, 0, channels)

private def generic_captureAudio(mode: String, input: String, samplerate: Int, channels: Byte): List[String] =
  val arg_input = List("-i", input)

  val arg_sample =
    if samplerate < 1 then
      List()
    else
      List("-sample_rate", samplerate.toString)

  val arg_ch =
    if channels < 1 then
      List()
    else
      List("-channels", channels.toString)

  List("-f", mode) ++ arg_sample ++ arg_ch ++ arg_input


def androidcamera_capture(input: Byte, fps: Int, width: Int, height: Int): List[String] =
  val arg_fps =
    if fps < 1 then
      List("-framerate", "30")
    else
      List("-framerate", fps.toString)

  val arg_input = List("-camera_index", input.toString)

  val arg_size =
    if width <= 0 || height <= 0 then
      List()
    else
      List("-video_size", s"${width}x$height")

  List("-f", "android_camera") ++ arg_fps ++ arg_size ++ arg_input
