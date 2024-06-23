package ffscala.capture

import ffscala.misc.*

def supportedCaptureModes(mode: String = ""): List[String] =
  lazy val desktop = List("x11grab", "dshow_video", "gdigrab")
  lazy val audio = List("alsa", "pulse", "dshow_audio", "oss")
  lazy val agnostic = List("dshow", "avfoundation")

  mode match
    case "desktop" =>
      desktop ++ agnostic
    case "audio" =>
      audio ++ agnostic
    case _ =>
      desktop ++ audio ++ agnostic

def isCaptureSupported(codec: String): Boolean =
  val fmts = supportedCaptureModes("")
  belongsToList(codec, fmts)

def processDesktopInput(input: String, mode: String): List[String] =
  mode match
    case "dshow" =>
      List("-i", s"video=\"$input\"")
    case "x11grab" =>
      List("-i", s":$input")
    case "gdigrab" =>
      if input == "" || input == "desktop" then List("-i", "desktop")
      else List("-i", s"title=$input")
    case _ =>
      List("-i", input)

def processAudioInput(input: String, mode: String): List[String] =
  mode match
    case "dshow" =>
      List("-i", s"audio=\"$input\"")
    case _ =>
      List("-i", input)

def processAudioChannels(ch: Byte, mode: String): List[String] =
  mode match
    case "dshow" | "alsa" =>
      List("-channels", ch.toString)
    case _ =>
      List("-ac", ch.toString)
