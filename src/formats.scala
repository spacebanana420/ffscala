package ffscala.misc

//Functions related to format support

def supportedExtensions(mode: String = ""): List[String] =
  val imageFormats = List("png", "apng", "avif", "jpeg", "jpg", "tiff", "tif", "bmp", "gif", "webp", "tga", "avif")
  val audioFormats = List("flac", "wav", "ogg", "opus", "m4a", "mp3", "aiff")
  val videoFormats = List("mp4", "mov", "m4v", "avi", "mkv", "webm")

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
  List("copy", "x264", "x264rgb", "x265", "nvenc", "nvenc265",
    "utvideo", "png", "dnxhd", "prores", "tiff", "cfhd", "vp9", "av1", "mjpeg", "targa")

def equivalentVideoCodecs(): List[String] =
  List("copy", "libx264", "lib264rgb", "libx265", "h264_nvenc", "hevc_nvenc",
    "utvideo", "png", "dnxhd", "prores_ks", "tiff", "cfhd", "libvpx-vp9", "libaom-av1", "mjpeg", "targa")


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
  List("rgb24", "rgb8", "rgb48", "rgb48le", "rgba", "rgba64le","gray",
  "gray16le", "yuv420p", "yuv422p", "yuv444p", "yuv422p10le", "yuv444p10le")
