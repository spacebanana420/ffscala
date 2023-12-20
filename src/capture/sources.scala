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

def listAudioSources(mode: String, full: Boolean = false, exec: String = "ffmpeg"): List[String] =
  val supported = List("alsa", "pulse")
  if belongsToList(mode, supported) then
    val sources = parse(List(exec, "-loglevel", "8", "-sources", mode).!!)
    if full == false then
      sources
    else
      filterSources(sources)
  else
    List()

// def listDirectSources(exec: String = "ffmpeg"): List[String] =
//   List(exec, "-list_devices", "true", "-f", "dshow", "-i", "dummy").!!
