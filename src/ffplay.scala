package ffscala

import java.io.File
import scala.sys.process.*
import misc.*

def execplay(input: String, args: String, quiet: Boolean = true, exec: String = "ffplay"): Int = {
  if File(input).isFile == true then
    val fullArgs: List[String] = stringToList(args)
    if quiet == true then
        val cmd: List[String] = exec +: "-y" +: "-loglevel" +: "quiet" +: fullArgs
        cmd.!
    else
        val cmd: List[String] = exec +: "-y" +: fullArgs
        cmd.!
  else
    -1
}

def setPlayVolume(volume: Byte): String = {
  val filteredvolume: Byte =
    if volume < 0 then
      0
    else if volume > 100 then
      100
    else
      volume
  s"-volume $volume "
}

def disableBorder(): String = "-noborder "

def setFullscreen(): String = "-fs "

def setDimensions(x: Int, y: Int): String = {
  if x <= 0 || y <= 0 then
    ""
  else
    s"-x $x -y $y "
}

def setFrameDrop(): String = "-framedrop "
