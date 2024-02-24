package ffscala
import ffscala.misc.*

//Some of the base/universal FFmpeg parameters

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

def setFramerate(fps: Int): List[String] = List("-r", fps.toString)

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

def setQuality(q: Short): List[String] =
  val qs = q.toString
  if q >= 30 then
    List("-qmax:v", qs, "-q:v", qs)
  if q >= 1 then
    List("-qmin:v", qs, "-q:v", qs)
  else
    List("-qmin:v", "1", "-q:v", "1")

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
  val supportedFormats = supportedAudioCodecs()
  val ffmpegEquivalents = equivalentAudioCodecs()

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
  val supportedFormats = supportedSampleFormats()
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
def mapChannel(mode: String, input: Byte, channel: Byte): List[String] = //test maybe
  if input < 0 || channel < -1 || (mode != "video" && mode != "audio" && mode != "subtitle") then
    List()
  else
    val mediashort =
      mode match
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
