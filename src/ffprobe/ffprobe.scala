package ffscala
import ffscala.misc.*

import java.io.File
import scala.sys.process._

//Functions for getting and parsing media file information

private def getEntries(path: String, exec: String, streams: String): String =
  exec_safe(List(exec, "-loglevel", "0", "-show_entries", s"stream=$streams", path))

def getVideoInfo(path: String, exec: String = "ffprobe"): List[String] =
  val mediainfo = getEntries(path, exec, "codec_name,width,height,pix_fmt,r_frame_rate,bit_rate,profile")
  filterOutput(mediainfo)

def getImageInfo(path: String, exec: String = "ffprobe"): List[String] =
  val mediainfo = getEntries(path, exec, "codec_name,width,height,pix_fmt")
  filterOutput(mediainfo)

def getAudioInfo(path: String, exec: String = "ffprobe"): List[String] =
  val mediainfo = getEntries(path, exec, "codec_name,bits_per_sample,sample_rate,channels,channel_layout,bit_rate")
  filterOutput(mediainfo)

def getDuration(path: String, exec: String = "ffprobe"): String = //maybe change it to a list
  val info = exec_safe(List("ffprobe", "-loglevel", "0", "-show_entries", "stream=duration", path))
  filterString(info)

def getResolution(path: String, exec: String = "ffprobe"): List[Int] =
  val info = exec_safe(List("ffprobe", "-loglevel", "0", "-show_entries", "stream=width,height", path))
  try
    filterOutput(info).map(x=>x.toInt)
  catch
    case e: Exception => List(0, 0)

def getCodec(path: String, exec: String = "ffprobe"): List[String] =
  val info = exec_safe(List(exec, "-loglevel", "0", "-show_entries", "stream=codec_name", path))
  filterOutput(info)

def getBitrate(path: String, exec: String = "ffprobe"): List[Long] =
  val info = exec_safe(List(exec, "-loglevel", "0", "-show_entries", "stream=bit_rate", path))
  try
    filterOutput(info).map(x=>x.toLong)
  catch
    case e: Exception => List(0)

def showEntries(path: String, entries: Seq[String], exec: String = "ffprobe"): List[String] =
  def mkstring(str_entries: String = "", i: Int = 0): String =
    if i >= entries.length then str_entries
    else if i == entries.length-1 then mkstring(str_entries + entries(i), i+1)
    else mkstring(str_entries + s"${entries(i)},", i+1)

  val arg_entries = mkstring()
  val output = getEntries(path, exec, arg_entries)
  filterOutput(output)

def getFullInfo(path: String, exec: String = "ffprobe"): String =
  exec_safe(List(exec, "-loglevel", "0", "-show_streams", path))

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
