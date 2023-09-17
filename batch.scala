package ffscala

import java.io.File
import misc.*

def batchExecute(paths: List[String], args: String, format: String, quiet: Boolean = true) = {
    val supportedFormats = supportedExtensions()
    val isSupported = belongsToList(format, supportedFormats)

    if isSupported == true then {
        for path <- paths do {
            if File(path).isFile() == true then
                val output = removeExtension(path) + "_new." + format
                execute(path, args, output, quiet)
        }
    }
}

def batchDir(dir: String, args: String, format: String, quiet: Boolean = true) = {
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
                execute(path, args, output, quiet)
        }
}



//add async encoding, 1 thread per encode
