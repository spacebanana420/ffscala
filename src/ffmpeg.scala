package ffscala

import scala.sys.process._
import java.io.File
import ffscala.misc.*

//This is the main file of ffscala, containing the base and most common FFmpeg functionality


private def isAlright_global(args: List[String], badargs: List[String], alright: Boolean = false, c: Int = 0): Boolean =
  def isSimilar(element: String, i: Int = 0): Boolean =
    if i >= badargs.length then
      false
    else if element.contains(badargs(i)) then
      true
    else
      isSimilar(element, i+1)

  if c >= args.length then
    false
  else if isSimilar(args(c)) then
    true
  else
    isAlright_global(args, badargs, alright, c+1)


private def isAlright_Image(args: List[String]): Boolean =
  val badArguments = List("-c:a", "-b:a", "-b:v", "-g", "-bf", "-crf")
  isAlright_global(args, badArguments)

private def isAlright_Audio(args: List[String]): Boolean =
  val badArguments = List("-c:v", "-b:v", "-g", "-bf", "-crf", "-pix_fmt", "-filter:v", "-preset:v")
  isAlright_global(args, badArguments)

def checkFFmpeg(path: String = "ffmpeg"): Boolean =
  try
    if List(path, "-loglevel", "quiet", "-version").!(ProcessLogger(line => ())) == 0 then
      true
    else
      false
  catch
    case e: Exception => false

//maybe the input should be a processed list with path, start time, output, etc
//make variant for muxing, demuxing, etc
//what if args and filters are mixed as one?
def encode
(
input: String, output: String, args: List[String] = List(), filters: List[String] = List(),
quiet: Boolean = true, exec: String = "ffmpeg"
): Int =
  val imageFormats = supportedExtensions("image")
  val audioFormats = supportedExtensions("audio")
  //maybe i should scrap this idea
  val isAlright =
    if belongsToList(output, imageFormats) == true then
      isAlright_Image(args)
    else if belongsToList(output, audioFormats) == true then
      isAlright_Audio(args)
    else
      true
  val filterlist = processFilters(filters)
  val filters_v =
    if filterlist(0).length > 0 then
      List("-filter:v", filterlist(0))
    else
      List()
  val filters_a =
    if filterlist(1).length > 0 then
      List("-filter:a", filterlist(1))
    else
      List()
  val nonfilters = getNonFilters(filters)
  if isAlright == false then
    -1
  else
    try
      val cmd: List[String] =
        if quiet == true then
          List(exec, "-y", "-loglevel", "quiet", "-i", input) ++ args ++ filters_v ++ filters_a ++ nonfilters :+ output
        else
          List(exec, "-y", "-hide_banner", "-i", input) ++ args ++ filters_v ++ filters_a ++ nonfilters :+ output
      cmd.!
    catch
      case e: Exception => -1

def getScreenshot(input: String, output: String, time: String, quiet: Boolean = true, exec: String = "ffmpeg") =
  val fullArgs: List[String] = List("-ss", time, "-i", input, "-frames:v", "1", output)
  if quiet == true then
    val cmd: List[String] = exec +: "-y" +: "-loglevel" +: "quiet" +: fullArgs
    cmd.!
  else
    val cmd: List[String] = exec +: "-y" +: fullArgs
    cmd.!

def extractFrames
(
input: String, fmt: String, start: Int = 0, amt: Int = 0, args: List[String] = List(),
quiet: Boolean = true, exec: String = "ffmpeg"
): Int =
  if isFormatSupported(fmt, "image") then
    val output = s"${removeExtension(input)}-%d.$fmt"
    val startarg =
      if start <= 0 then
        List()
      else
        List("-ss", start.toString)
    val amtarg =
      if amt <= 0 then
        List()
      else
        List("-frames:v", amt.toString)
    val cmd = getBaseArgs(exec, quiet) ++ startarg ++ List("-i", input) ++ amtarg ++ args :+ output //finish and add filter processing
    cmd.!
  else
    -1

def combineMedia
(
output: String,
video: List[String] = List(), audio: List[String] = List(), subs: List[String] = List(),
quiet: Boolean = true, exec: String = "ffmpeg"
): Int =
  def inputArgs(f: List[String], nl: List[String] = List(), i: Int = 0): List[String] =
      if i >= f.length then
        nl
      else
        inputArgs(f, nl ++ List("-i", f(i)), i+1)

  def mapArgs(len: Int, mode: String, nl: List[String] = List(), i: Int = 0): List[String] =
      if i >= len then
        nl
      else
        mapArgs(len, mode, nl ++ List("-map", s"$i:$mode"), i+1)

  if video.length > 0 || audio.length > 0 || subs.length > 0 then
    val inargs = inputArgs(video ++ audio ++ subs)
    val mapargs =
      mapArgs(video.length, "v")
      ++ mapArgs(video.length + audio.length, "a", i = video.length)
      ++ mapArgs(video.length + audio.length + subs.length, "s", i = video.length + audio.length)

    val cmd = getBaseArgs(exec, quiet) ++ inargs ++ mapargs ++ List("-c", "copy", output)
    cmd.!
  else
    -1
