package ffscala

import java.io.File
import scala.sys.process._
//import misc.*

// private def checkForFile(path: String): Boolean = {
//     if File(path).isFile() == true then
//         true
//     else
//         false
// }


//def is webfriendly

// private def executeFFprobe(path: String): String = {
//     val cmd = List("ffprobe", path)
//     var stdout = new StringBuilder
//     var stderr = new StringBuilder
//     val process = ProcessLogger(out => stdout.append(out), err => stderr.append(err))
//     cmd.!(process)
//     stderr.result()
// }

def getVideoInfo(path: String): List[String] = {
    val cmd = List("ffprobe", "-loglevel", "0", "-show_streams", path)
    val mediaInfo = cmd.!!
    //val stream0 = getFFprobeLine(mediaInfo, "Stream #0:0")

    val format = getStreamContent(mediaInfo, "codec_name=")
    val width = getStreamContent(mediaInfo, "width=")
    val height = getStreamContent(mediaInfo, "height=")
    val pixFmt = getStreamContent(mediaInfo, "pix_fmt=")
    val fps = getStreamContent(mediaInfo, "r_frame_rate=")
    val bitrate = getStreamContent(mediaInfo, "bit_rate=")
    val profile = getStreamContent(mediaInfo, "profile=")
    List(format, width, height, pixFmt, bitrate, profile)
}

def getImageInfo(path: String): List[String] = {
    val cmd = List("ffprobe", "-loglevel", "0", "-show_streams", path)
    val mediaInfo = cmd.!!
    //val stream0 = getFFprobeLine(mediaInfo, "Stream #0:0")

    val format = getStreamContent(mediaInfo, "codec_name=")
    val width = getStreamContent(mediaInfo, "width=")
    val height = getStreamContent(mediaInfo, "height=")
    val pixFmt = getStreamContent(mediaInfo, "pix_fmt=")
    List(format, width, height, pixFmt)
}

def getAudioInfo(path: String): List[String] = {
    val cmd = List("ffprobe", "-loglevel", "0", "-show_streams", path)
    val mediaInfo = cmd.!!
    //val stream0 = getFFprobeLine(mediaInfo, "Stream #0:0")

    val format = getStreamContent(mediaInfo, "codec_name=")
    val bitDepth = getStreamContent(mediaInfo, "bits_per_sample=")
    val sampleRate = getStreamContent(mediaInfo, "sample_rate=")
    val channelNum = getStreamContent(mediaInfo, "channels=")
    val channelLayout = getStreamContent(mediaInfo, "channel_layout")
    val bitrate = getStreamContent(mediaInfo, "bit_rate=")
    List(format, bitDepth, sampleRate, channelNum, channelLayout, bitrate)
}

def getFullInfo(path: String): String = {
    val cmd = List("ffprobe", "-loglevel", "0", "-show_streams", path)
    cmd.!!
}

private def getStreamContent(stream: String, seek: String): String = {
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
}

// private def getFFprobeLine(output: String, seek: String): String = {
//     var foundline = false
//     var line = ""
//     var findSeek = ""
//     var i = 0
//     var startCopying = false
//     var finishedCopying = false
//
//     while finishedCopying == false && i < output.length() do {
//         for x <- i to i+seek.length()-1 do {
//             findSeek += output(x)
//         }
//         if findSeek == seek then
//             startCopying = true
//         if startCopying == true then
//             line += output(i)
//         if line.contains(" tbn") == true then
//             finishedCopying = true
//         i += 1
//     }
//     line
// }

// private def getStreamContent_specific(stream: String, infonum: Int): String = {
//     var found = false
//     var line = ""
//     var i = 0
//     var currentinfo = 0
//     var startCopying = false
//     while found == false && i < stream.length() do {
//         if stream(i) == ':' || stream(i) == ')' then
//             startCopying = true
//         else if stream(i) == '(' then
//             startCopying = false
//         else if stream(i) == ',' then
//             if currentinfo == infonum then
//                 found = true
//             else
//                 currentinfo += 1
//                 line = ""
//             currentinfo += 1
//         else if startCopying == true then
//             line += stream(i)
//         i += 1
//     }
//     if found == true then
//         line
//     else
//         ""
// }
