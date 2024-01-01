package ffscala

import java.io.File
import ffscala.misc.*

//Functions for batch processing with FFmpeg

def batchExecute(paths: List[String], format: String, args: List[String] = List(), filters: List[String] = List(), quiet: Boolean = true, exec: String = "ffmpeg") = {
  val supportedFormats = supportedExtensions()
  val isSupported = belongsToList(format, supportedFormats)

  if isSupported == true then {
    for path <- paths do {
      if File(path).isFile() == true then
        val output = removeExtension(path) + "_new." + format
        encode(path, output, args, filters, quiet, exec)
    }
  }
}

def batchDir(dir: String, format: String, args: List[String] = List(), filters: List[String] = List(), quiet: Boolean = true, exec: String = "ffmpeg") = {
  if File(dir).isDirectory() == true then
    val paths = File(dir).list()
    val supportedFormats_i = supportedExtensions("image")
    val supportedFormats_v = supportedExtensions("video")
    val supportedFormats_a = supportedExtensions("audio")
    val formatsToSeek: List[String] =
      if belongsToList(format, supportedFormats_i) == true then
        supportedFormats_i
      else if  belongsToList(format, supportedFormats_v) == true then
        supportedFormats_v
      else if  belongsToList(format, supportedFormats_a) == true then
        supportedFormats_a
      else
        supportedFormats_i ++ supportedFormats_v ++ supportedFormats_a

    for path <- paths do {
      val pathfmt = getExtension(path)
      //println("path: " + path + "\nformat: " + pathfmt)
      if belongsToList(pathfmt, formatsToSeek) == true && File(path).isFile == true then
        val output = removeExtension(path) + "_new." + format
        //println("Encoding " + path)
        encode(path, output, args, filters, quiet, exec)
    }
}



//add async encoding, 1 thread per encode
