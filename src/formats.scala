package ffscala.misc

//Functions related to format support

def supportedExtensions(mode: String = ""): List[String] =
  lazy val imageFormats = List("png", "ppm", "apng", "avif", "jpeg", "jpg", "tiff", "tif", "bmp", "gif", "webp", "tga", "avif")
  lazy val audioFormats = List("flac", "wav", "ogg", "opus", "m4a", "mp2", "mp3", "aiff", "ac3")
  lazy val videoFormats = List("mp4", "mov", "m4v", "avi", "mkv", "webm", "mpeg", "h264", "hevc", "dnxhd")

  mode match
    case "image" => imageFormats
    case "video" => videoFormats
    case "audio" => audioFormats
    case _ => imageFormats ++ videoFormats ++ audioFormats

def isFormatSupported(path: String, mode: String): Boolean =
  val fmts = supportedExtensions(mode)
  val extension = getExtension(path)
  if belongsToList(extension, fmts) then
    true
  else
    false

def supportedVideoCodecs(): List[String] =
  List("copy", "cinepak", "x264", "x264rgb", "x265", "nvenc", "nvenc265", "qsv", "qsv265", "vaapi",
    "utvideo", "huffyuv", "png", "dnxhd", "prores", "tiff", "cfhd", "vp9", "av1", "mjpeg", "mjpegqsv", "targa", "bmp", "h263", "h263p", "ppm")

def equivalentVideoCodecs(): List[String] =
  List("copy", "cinepak", "libx264", "libx264rgb", "libx265", "h264_nvenc", "hevc_nvenc", "h264_qsv", "hevc_qsv", "h264_vaapi",
    "utvideo", "huffyuv", "png", "dnxhd", "prores_ks", "tiff", "cfhd", "libvpx-vp9", "libaom-av1", "mjpeg", "mjpeg_qsv", "targa", "bmp", "h263", "h263p", "ppm")

def supportedAudioCodecs(): List[String] =
  List("copy", "aac", "opus", "vorbis", "mp3", "ac3", "flac", "pcm16", "pcm24", "pcm32", "pcm64", "pcm16be", "pcm24be", "pcm32be", "pcm64be")


def equivalentAudioCodecs(): List[String] =
  List("copy", "aac", "libopus", "libvorbis", "libmp3lame", "ac3", "flac", "pcm_s16le", "pcm_s24le", "pcm_s32le", "pcm_s64le", "pcm_s16be", "pcm_s24be", "pcm_s32be", "pcm_s64be")


def getVideoCodec(codec: String): String =
  val fmts = supportedVideoCodecs()
  val i = indexFromList(codec, fmts)
  if i != -1 then
    equivalentVideoCodecs()(i )
  else
    ""

def isVideoCodecSupported(codec: String): Boolean =
  val fmts = supportedVideoCodecs()
  belongsToList(codec, fmts)

def supportedPixelFormats(): List[String] =
  List(
  "rgb24", "bgr24", "bgr0", "gbrp", "gbrp10le", "gbrp12le", "gbrap", "bgra",
  "rgb8", "rgb48", "rgb48le", "rgba", "rgba64le",
  "gray", "gray10le", "gray12le", "gray16le",
  "yuv420p", "yuv422p", "yuv444p",
  "yuv420p10le", "yuv422p10le", "yuv444p10le",
  "yuv420p12le", "yuv422p12le", "yuv444p12le",
  "yuvj420p", "yuvj422p", "yuvj444p",
  "nv12", "nv16", "nv21", "nv20le",
  "qsv", "vaapi", "vuyx", "y210le",
  "yuyv422", "p010le", "p012le",
  "x2rgb10le", "xv30le"
  )

def supportedSampleFormats(): List[String] =
  List(
  "u8", "s16", "s32", "flt", "dbl", "u8p",
  "s16p", "s32p", "fltp", "dblp", "s64", "s64p"
  )

def supportedHWAccel(): List[String] =
  List("qsv")