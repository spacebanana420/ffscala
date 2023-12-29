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
    List(path, "-loglevel", "quiet", "-version").!
    true
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
  def processFilters(vf: String = "", af: String = "", i: Int = 0): List[String] =
    val comma =
      if i >= filters.length-2 then
        ""
      else
        ","
    if i >= filters.length then
      List(vf, af)
    else if filters(i) == "v" && i < filters.length-1 then
      processFilters(vf + filters(i+1) + comma, af, i+2)
    else if filters(i) == "a" && i < filters.length-1 then
      processFilters(vf, af + filters(i+1) + comma, i+2)
    else
      processFilters(vf, af, i+1)

  def getNonFilters(nf: List[String] = List(), i: Int = 0): List[String] =
    if i >= filters.length then
      nf
    else if filters(i) == "v" || filters(i) == "a" then
      getNonFilters(nf, i+2)
    else
      getNonFilters(nf :+ filters(i), i+1)

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
  val filterlist = processFilters()
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
  val nonfilters = getNonFilters()
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

def setThreads(threads: Short): List[String] =
  if threads >= 0 then
    List("-threads", threads.toString)
  else
    List()

def setVideoEncoder(encoder: String): List[String] =
  val supportedFormats = supportedVideoCodecs()
  val ffmpegEquivalents = equivalentVideoCodecs()
  val i = indexFromList(encoder, supportedFormats)

  if i == -1 then
    List()
  else
    List("-c:v", ffmpegEquivalents(i))

def setVideoBitrate(bitrate: Int): List[String] =
  if bitrate <= 0 then
    List()
  else
    List("-b:v", s"${bitrate}k")

def setCRF(value: Byte): List[String] =
  if value < 0 then
    List("-crf", "0")
  else
    List("-crf", value.toString)

def setQuality(q: Int): List[String] =
  val qs = q.toString
  if q >= 51 then
    List("-qmax", qs, "-q", qs)
  if q >= 0 then
    List("-qmin", qs, "-q", qs)
  else
    List("-qmin", "0", "-q", "0")

def setKeyframeInterval(interval: Short): List[String] =
  if interval < 0 then
    List()
  else
    List("-g", interval.toString)

def setBFrames(interval: Byte): List[String] =
  if interval < 0 || interval > 16 then
    List()
  else
    List("-bf", interval.toString)

def setPixFmt(fmt: String): List[String] = //use ffmpeg equivalents
  val supportedFormats = supportedPixelFormats()
  //val ffmpegEquivalents = List("rgb24", "rgb8", "rgb48", "gray", "yuv420p", "yuv422p", "yuv444p")
  val foundformat = belongsToList(fmt, supportedFormats)

  if foundformat == false then
    List()
  else
    List("-pix_fmt", fmt)

def setAudioEncoder(encoder: String): List[String] =
  val supportedFormats = List("copy", "aac", "opus", "vorbis", "mp3", "ac3", "flac", "pcm16", "pcm24", "pcm32", "pcm64")
  val ffmpegEquivalents = List("copy", "aac", "libopus", "libvorbis", "libmp3lame", "ac3", "flac", "pcm_s16le", "pcm_s24le", "pcm_s32le", "pcm64le")
  val i = indexFromList(encoder, supportedFormats)
  if i == -1 then
    List()
  else
    List("-c:a", ffmpegEquivalents(i))

def setAudioBitrate(bitrate: Int): List[String] =
  if bitrate <= 0 then
    List()
  else
    List("-b:a", s"${bitrate}k")

def setSampleFormat(fmt: String): List[String] =
  val supportedFormats = List("s16", "s32")
  val foundformat = belongsToList(fmt, supportedFormats)

  if foundformat == false then
    List()
  else
    List("-sample_fmt", fmt)

def removeElement(element: String): List[String] =
  element match
    case "video" =>
      List("-vn")
    case "audio" =>
      List("-an")
    case "subtitle" =>
      List("-sn")
    case _ =>
      List()
//this doesnt work for combining multiple sources
def mapChannel(media: String, input: Byte, channel: Byte): List[String] = //test maybe
  if input < 0 || channel < -1 || (media != "video" && media != "audio" && media != "subtitle") then
    List()
  else
    val mediashort =
      media match
        case "video" => "v"
        case "audio" => "a"
        case "subtitle" => "s"
    if channel == -1 then
      List("-map", s"$input:$mediashort")
    else
      List("-map", s"$input:$mediashort:$channel")

def setDuration(seconds: Float): List[String] =
  if seconds <= 0 then
    List()
  else
    List("-t", seconds.toString)

def getScreenshot(input: String, output: String, time: String, quiet: Boolean = true) =
  val fullArgs: List[String] = List("-ss", time, "-i", input, "-frames:v", "1", output)
  if quiet == true then
    val cmd: List[String] = "ffmpeg" +: "-y" +: "-loglevel" +: "quiet" +: fullArgs
    cmd.!
  else
    val cmd: List[String] = "ffmpeg" +: "-y" +: fullArgs
    cmd.!
