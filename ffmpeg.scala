package ffscala

import scala.sys.process._
import java.io.File
import misc.*

private def isAlright_Image(args: String): Boolean = {
    val badArguments = List("-c:a", "-b:a", "-b:v", "-g", "-bf", "-crf")
    for i <- badArguments do {
        if args.contains(i) == true then
            return false
    }
    true
}

private def isAlright_Audio(args: String): Boolean = {
    val badArguments = List("-c:v", "-b:v", "-g", "-bf", "-crf", "-pix_fmt", "-filter:v", "-preset:v")
    for i <- badArguments do {
        if args.contains(i) == true then
            return false
    }
    true
}

def execute(args: String, quiet: Boolean = true): Int = {
    var i = args.length()-1
    var done = false
    var foundfmt = ""
    while done == false && i > 0 do {
        if args(i) == '.' then
            done = true
        else
            foundfmt += args(i)
        i -= 1
    }
    val imageFormats = List("png", "apng", "avif", "jpeg", "jpg", "tiff", "tif", "bmp", "gif", "webp", "tga")
    //val videoFormats = List("mp4", "mov", "m4v", "avi", "mkv", "webm")
    val audioFormats = List("flac", "wav", "ogg", "opus", "m4a", "mp3", "aiff")
    var isAlright = false

    if belongsToList(foundfmt, imageFormats) == true then
        isAlright = isAlright_Image(args)
    else if belongsToList(foundfmt, audioFormats) == true then
        isAlright = isAlright_Audio(args)
    else
        isAlright = true

    if isAlright == false then
        -1
    else
        if quiet == true then
            val cmd: List[String] = "ffmpeg" +: "-y" +: "-loglevel" +: "quiet" +: stringToList(args)
            cmd.!
        else
            val cmd: List[String] = "ffmpeg" +: "-y" +: stringToList(args)
            cmd.!
}

def openFile(path: String): String = { //add support for multiple inputs and detection
    if File(path).exists() == false || File(path).isFile == false then
        ""
    else
        "-i " + path + " "
}

def setVideoEncoder(encoder: String): String = {
    val supportedFormats = List("copy", "x264", "x264rgb", "x265", "nvenc", "nvenc265", "utvideo", "png", "dnxhd", "tiff", "cfhd", "vp9")
    val ffmpegEquivalents = List("copy", "libx264", "lib264rgb", "libx265", "h264_nvenc", "hevc_nvenc", "utvideo", "png", "dnxhd", "tiff", "cfhd", "libvpx-vp9")
    val i = indexFromList(encoder, supportedFormats)

    if i == -1 then
        ""
    else
        "-c:v " + ffmpegEquivalents(i)  + " "
}

def setVideoBitrate(method: String, bitrate: Int): String = {
    method match
        case "cbr" =>
            if bitrate <= 0 then
                ""
            else
                "-b:v " + bitrate + "k "
        case "crf" =>
            if bitrate < 0 then
                ""
            else
                "-crf " + bitrate  + " "
        case "cqp" =>
            ""
        case _ =>
            ""
}

def setKeyframeInterval(interval: Short): String = {
    if interval < 0 then
        ""
    else
        "-g " + interval  + " "
}

def setBFrames(interval: Byte): String = {
    if interval < 0 || interval > 16 then
        ""
    else
        "-bf " + interval  + " "
}

def setPixFmt(newfmt: String): String = { //use ffmpeg equivalents
    val supportedFormats = List("rgb24", "rgb8", "rgb48", "rgb48le", "rgba", "rgba64le", "gray", "gray16le", "yuv420p", "yuv422p", "yuv444p", "yuv422p10le", "yuv444p10le")
    //val ffmpegEquivalents = List("rgb24", "rgb8", "rgb48", "gray", "yuv420p", "yuv422p", "yuv444p")
    val foundformat = belongsToList(newfmt, supportedFormats)

    if foundformat == false then
        ""
    else
        "-pix_fmt " + newfmt  + " "
}

def setAudioEncoder(encoder: String): String = {
    val supportedFormats = List("copy", "aac", "opus", "vorbis", "mp3", "ac3", "flac", "pcm16", "pcm24", "pcm32")
    val ffmpegEquivalents = List("copy", "aac", "libopus", "libvorbis", "libmp3lame", "ac3", "flac", "pcm_s16le", "pcm_s24le", "pcm_s32le")
    val i = indexFromList(encoder, supportedFormats)
    if i == -1 then
        return ""
    "-c:a " + ffmpegEquivalents(i)  + " "
}

def setAudioBitrate(bitrate: Int): String = {
    if bitrate <= 0 then
        ""
    else
        "-b:a " + bitrate + "k "
}

def setSampleFormat(fmt: String): String = {
    val supportedFormats = List("s16", "s32")
    val foundformat = belongsToList(fmt, supportedFormats)

    if foundformat == false then
        ""
    else
        "-sample_fmt " + fmt  + " "
}

def removeElement(element: String): String = {
    element match
        case "video" =>
            "-vn "
        case "audio" =>
            "-an "
        case "subtitle" =>
            "-sn "
        case _ =>
            ""
}

def mapChannel(media: String, input: Byte, channel: Byte): String = { //test maybe
    if input < 0 || channel < -1 || (media != "video" && media != "audio" && media != "subtitle") then
        ""
    else
        var mediashort = ""
        media match
            case "video" => mediashort = "v"
            case "audio" => mediashort = "a"
            case "subtitle" => mediashort = "s"
        if channel == -1 then
            "-map " + input + ":" + mediashort + " "
        else
            "-map " + input + ":" + mediashort + ":" + channel + " "
}

def setOutput(name: String, format: String): String = { //replace wrong format instead of returning empty
    val supportedFormats = supportedExtensions()
    val isSupported = belongsToList(format, supportedFormats)

    if isSupported == false || name == "" || format == "" then
        ""
    else
        name + "." + format  + " "
}

def getScreenshot(input: String, output: String, time: String) = {
    execute("-y -loglevel quiet -ss " + time + " -i " + input + " -frames:v 1 " + output)
}
