package ffscala

import java.io.File
import scala.sys.process.*
import ffscala.misc.*

//FFplay functionality to play media

def execplay(input: String, args: List[String], quiet: Boolean = true, exec: String = "ffplay"): Int =
  if File(input).isFile() == true then
    try
      val cmd =
      if quiet == true then
          List(exec, "-y", "-loglevel", "quiet") ++ args
      else
          List(exec, "-y", "-hide_banner") ++ args
      cmd.!
    catch
      case e: Exception => -1
  else
    -1

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
