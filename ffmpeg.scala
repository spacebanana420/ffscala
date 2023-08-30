package ffmpeg
import scala.sys.process._
import java.io.File

private def belongsToList(text: String, group: List[String]): Boolean = {
    for i <- group do {
        if text == i then
            return true
    }
    false
}

private def indexFromList(text: String, group: List[String]): Int = {
    for i <- 0 to group.length - 1 do {
        if text == group(i) then
            return i
    }
    -1
}

// private def isAlright_Image(cmd: List[String]): Boolean = {
//     var cmdstring: String = ""
//     val badArguments = List("-c:a", "-b:a", "-b:v", "-g", "-bf", "-crf")
//     for i <- cmd do {
//         cmdstring += i
//     }
//     for i <- badArguments do {
//         if cmdstring.contains(i) == true then
//             return false
//     }
//     true
// }
//
// private def isAlright_Audio(cmd: List[String]): Boolean = {
//     var cmdstring: String = ""
//     val badArguments = List("-c:v", "-b:v", "-g", "-bf", "-crf", "-pix_fmt", "-filter:v", "-preset:v")
//     for i <- cmd do {
//         cmdstring += i
//     }
//     for i <- badArguments do {
//         if cmdstring.contains(i) == true then
//             return false
//     }
//     true
// }

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

private def stringToList(text: String): List[String] = {
    var stringArray: Array[String] = Array()
    var arg = ""
    for i <- text do {
        if i == ' ' then
            stringArray :+= arg
            arg = ""
        else
            arg += i
    }
    stringArray :+= arg
    stringArray.toList
}

def execute(mediaType: String, args: String): Int = { // types: 0 = image, 1 = video, 2 = audio
    var isAlright = false
    if mediaType == "image" then
        isAlright = isAlright_Image(args)
    else if mediaType == "audio" then
        isAlright = isAlright_Audio(args)
    else
        isAlright = true
    if isAlright == false then
        return -1
    val cmd: List[String] = "ffmpeg" +: stringToList(args)
    val output = cmd.!
    output
}

def openFile(path: String): String = {
    //val supportedFormats = List(".png", ".bmp", ".gif")
    if File(path).exists() == false || File(path).isFile == false then
        return ""
    "-i " + path
}

def setVideoEncoder(encoder: String): String = {
    val supportedFormats = List("copy", "libx264", "libx265", "utvideo", "png", "dnxhd", "tiff", "cfhd")
    val supported = belongsToList(encoder, supportedFormats)

    if supported == false then
        return ""
    "-c:v " + encoder
}

def setVideoBitrate(method: String, bitrate: Int, dimension: String): String = {
    method match
        case "cbr" =>
            if bitrate <= 0 then return ""
            if dimension == "" then
                "-b:v " + bitrate + "k"
            else
                "-b:v " + bitrate + dimension
        case "crf" =>
            if bitrate < 0 then return ""
            "-crf " + bitrate
        case "cqp" =>
            ""
        case _ =>
            ""
}

def setVideoResolution(width: Int, height: Int): String = {
    if width <= 0 || height <= 0 then
        ""
    else
        "-filter:v scale=" + width + ":" + height
}

def setScaleFilter(filter: String): String = {
    val supportedFilters = List("bicubic", "bilinear", "neighbour")
    val foundFilter = belongsToList(filter, supportedFilters)
    if foundFilter == false then
        ""
    else
        "-sws_flags " + filter
}

def setKeyframeInterval(interval: Int): String = {
    if interval < 0 then
        return ""
    "-g " + interval
}

def setBFrames(interval: Byte): String = {
    if interval < 0 || interval > 16 then
        return ""
    "-bf " + interval
}

def setPixFmt(newfmt: String): String = { //needs to check for variants like rgb48be
    val supportedFormats = List("rgb24", "rgb8", "rgb48", "gray", "yuv420p", "yuv422p", "yuv444p")
    //val ffmpegEquivalents = List("rgb24", "rgb8", "rgb48", "gray", "yuv420p", "yuv422p", "yuv444p")
    val foundformat = belongsToList(newfmt, supportedFormats)

    if foundformat == false then
        return ""
    "-pix_fmt " + newfmt
}

def setAudioEncoder(encoder: String): String = {
    val supportedFormats = List("copy", "aac", "opus", "vorbis", "mp3", "ac3", "flac", "pcm16", "pcm24", "pcm32")
    val ffmpegEquivalents = List("copy", "aac", "libopus", "libvorbis", "libmp3lame", "ac3", "flac", "pcm_s16le", "pcm_s24le", "pcm_s32le")
    val i = indexFromList(encoder, supportedFormats)
    if i == -1 then
        return ""
    "-c:a " + ffmpegEquivalents(i)
}

def setAudioBitrate(bitrate: Int, dimension: String): String = {
    if bitrate <= 0 then return ""
    if dimension == "" then
        "-b:a " + bitrate + "k"
    else
        "-b:a " + bitrate + dimension
}

def normalizeAudio(): String = "-filter:a loudnorm"

def removeElement(element: String): String = {
    element match
        case "video" =>
            "-vn"
        case "audio" =>
            "-an"
        case "subtitle" =>
            "-sn"
        case _ =>
            ""
}

def setOutput(name: String, format: String): String = {
    if name == "" || format == "" then
        return ""

    val supportedFormats = List("png", "apng", "avif", "jpeg", "jpg", "tiff", "tif", "bmp", "gif", "webp", "mp4", "mov",
    "m4v", "avi", "mkv", "webm", "flac", "wav", "ogg", "opus", "m4a", "mp3", "aiff")
    val isSupported = belongsToList(format, supportedFormats)

    if isSupported == false then
        return ""
    name + "." + format
}
