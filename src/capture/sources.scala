package ffscala.capture

import ffscala.*
import scala.sys.process._
import java.io.File
import ffscala.misc.*

private def parse(sources: String, s: String = "", l: List[String] = List(), i: Int = 0): List[String] =
  if i >= s.length then
    if s != "" then
      l :+ s
    else
      l
  else if sources(i) == '\n' then
    parse(sources, "", l :+ s, i+1)
  else if sources(i) != ' ' && sources(i) != '*' then
    parse(sources, s + sources(i), l, i+1)
  else
    parse(sources, s, l, i+1)

private def filterSources(s: List[String], f: List[String] = List(), i: Int = 0): List[String] =
  def filter(source: String, filtered: String = "", c: Int = 0): String =
    if c >= source.length || source(c) == ' ' then
      filtered
    else
      filter(source, filtered + source(c), i+1)

  if i >= s.length then
    f
  else
    filterSources(s, f :+ filter(s(i)), i+1)

def listSources(mode: String, full: Boolean = false, exec: String = "ffmpeg"): List[String] =
  val supported = supportedCaptureModes("audio")

  if mode == "all" then
    val cmd = List(exec, "-loglevel", "quiet", "-sources")
    parse(cmd.!!)
  else if belongsToList(mode, supported) then
    val cmd = List(exec, "-loglevel", "quiet") ++ getSourcesArgs(mode)
    val sources = parse(cmd.!!)
    if full == false || (mode == "dshow" || mode == "avfoundation") then
      sources
    else
      filterSources(sources)
  else
    List()

private def getSourcesArgs(mode: String): List[String] =
  mode match
    case "dshow" =>
      List("-list_devices", "true", "-f", "dshow", "-i", "dummy")
    case "avfoundation" =>
      List("-f", "avfoundation", "-list_devices", "true", "-i", "\"\"")
    case _ =>
      List("-sources", mode)

// def listDirectSources(exec: String = "ffmpeg"): List[String] =
//   List(exec, "-list_devices", "true", "-f", "dshow", "-i", "dummy").!!
