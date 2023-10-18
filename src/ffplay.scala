package ffscala

import java.io.File
import scala.sys.process.*
import misc.*

def execplay(input: String, args: List[String], quiet: Boolean = true, exec: String = "ffplay"): Int = {
  if File(input).isFile == true then
//     val fullArgs: List[String] = stringToList(args)
    if quiet == true then
        val cmd: List[String] = exec +: "-y" +: "-loglevel" +: "quiet" +: args
        cmd.!
    else
        val cmd: List[String] = exec +: "-y" +: args
        cmd.!
  else
    -1
}

def setPlayVolume(volume: Byte): List[String] = {
  val filteredvolume: Byte =
    if volume < 0 then
      0
    else if volume > 100 then
      100
    else
      volume
  List("-volume", s"$volume")
}

def disableBorder(): List[String] = List("-noborder")

def setFullscreen(): List[String] = List("-fs")

def setDimensions(x: Int, y: Int): List[String] = {
  if x <= 0 || y <= 0 then
    List("")
  else
    List("-x", s"$x", "-y", s"$y")
}

def setFrameDrop(): List[String] = List("-framedrop")
