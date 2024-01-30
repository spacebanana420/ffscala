package ffscala.capture

import ffscala.*
import ffscala.misc.*

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
