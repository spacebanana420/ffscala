package ffscala

import java.io.File
import scala.sys.process._

//Functions for getting and parsing media file information

def getVideoInfo(path: String): List[String] =
  val cmd = List("ffprobe", "-loglevel", "0", "-show_streams", path)
  val mediaInfo = cmd.!!

  val format = getStreamContent(mediaInfo, "codec_name=")
  val width = getStreamContent(mediaInfo, "width=")
  val height = getStreamContent(mediaInfo, "height=")
  val pixFmt = getStreamContent(mediaInfo, "pix_fmt=")
  val fps = getStreamContent(mediaInfo, "r_frame_rate=")
  val bitrate = getStreamContent(mediaInfo, "bit_rate=")
  val profile = getStreamContent(mediaInfo, "profile=")
  List(format, width, height, pixFmt, bitrate, profile)

def getImageInfo(path: String): List[String] =
  val cmd = List("ffprobe", "-loglevel", "0", "-show_streams", path)
  val mediaInfo = cmd.!!

  val format = getStreamContent(mediaInfo, "codec_name=")
  val width = getStreamContent(mediaInfo, "width=")
  val height = getStreamContent(mediaInfo, "height=")
  val pixFmt = getStreamContent(mediaInfo, "pix_fmt=")
  List(format, width, height, pixFmt)

def getAudioInfo(path: String): List[String] =
  val cmd = List("ffprobe", "-loglevel", "0", "-show_streams", path)
  val mediaInfo = cmd.!!

  val format = getStreamContent(mediaInfo, "codec_name=")
  val bitDepth = getStreamContent(mediaInfo, "bits_per_sample=")
  val sampleRate = getStreamContent(mediaInfo, "sample_rate=")
  val channelNum = getStreamContent(mediaInfo, "channels=")
  val channelLayout = getStreamContent(mediaInfo, "channel_layout")
  val bitrate = getStreamContent(mediaInfo, "bit_rate=")
  List(format, bitDepth, sampleRate, channelNum, channelLayout, bitrate)

def getDuration(path: String): String = //maybe change it to a list
  val info = List("ffprobe", "-loglevel", "0", "-show_entries", "stream=duration", path).!!
  filterString(info)

def getResolution(path: String): List[Int] =
  val info = List("ffprobe", "-loglevel", "0", "-show_entries", "stream=width,height", path).!!
  try
    filterOutput(info).map(x=>x.toInt)
  catch
    case e: Exception => List(0, 0)

def getCodec(path: String): List[String] =
  val info = List("ffprobe", "-loglevel", "0", "-show_entries", "stream=codec_name", path).!!
  filterOutput(info)

def getBitrate(path: String): List[Long] =
  val info = List("ffprobe", "-loglevel", "0", "-show_entries", "stream=bit_rate", path).!!
  try
    filterOutput(info).map(x=>x.toLong)
  catch
    case e: Exception => List(0)

def getFullInfo(path: String): String =
  List("ffprobe", "-loglevel", "0", "-show_streams", path).!!

private def filterString(stream: String, s: String = "", i: Int = 0, copy: Boolean = true): String =
  if i >= stream.length then
    s
  else if stream(i) == '[' then
    filterString(stream, s, i+1, false)
  else if stream(i) == ']' then
    filterString(stream, s, i+1, true)
  else if copy then
    filterString(stream, s + stream(i), i+1, copy)
  else
    filterString(stream, s, i+1, copy)

private def filterOutput(stream: String, full: Boolean = false): List[String] =
  def parseLines(l: List[String] = List(), line: String = "", i: Int = 0): List[String] =
    if i >= stream.length then
        l
    else if stream(i) == '\n' then
      if line.contains("STREAM]") then
        parseLines(l, "", i+1)
      else
        parseLines(l :+ line, "", i+1)
    else
      parseLines(l, line + stream(i), i+1)

  def remove(s: String, new_s: String = "", i: Int = 0, copy: Boolean = false): String =
    if i >= s.length then
      new_s
    else if s(i) == '=' then
      remove(s, new_s, i+1, true)
    else if copy then
      remove(s, new_s + s(i), i+1, copy)
    else
      remove(s, new_s, i+1, copy)

  def removeTitles(l: List[String], new_l: List[String] = List(), i: Int = 0): List[String] =
    if i >= l.length then
      new_l
    else
      removeTitles(l, new_l :+ remove(l(i)), i+1)

  val info = parseLines()
  if full then
    info
  else
    removeTitles(info)

//improve to find multiple variants of the same info, like with video and audio combined
private def getStreamContent(stream: String, seek: String): String =
  var done = false
  var line = ""
  var lineValue = ""
  var i = 0
  var startCopying = false

  while done == false && i < stream.length() do {
    if stream(i) == '\n' then
      if line.contains(seek) == true then
        done = true
      else
        line = ""
    else
      line += stream(i)
    i += 1
  }
  if done == true then
    for c <- line do {
      if startCopying == true then
        lineValue += c
      if c == '=' then
        startCopying = true
    }
    lineValue
  else
    ""
