import ffscala.*
import ffscala.capture.*
import java.io.File

@main def main() = {
  for i <- showEntries("Awesome Wooper.png", Vector("codec_name", "duration")) do println(i)
  //batch()
  //encodedir()
  //testoptional()
  //testcapture()

  //extract()
  //getres()
  //testscreenshot()
  //testFilters()
  //testcombine()
  //funny()
}

def funny() =
  val x  = List(0.1f, 0.5f, 0.8f)
  val y = List(0.03f, 0.6f, 0.95f)

  val rx = List(0f, 0.5f, 0.6f, 1f)
  val ry = List(0f, 0.5f, 0.65f, 1f)

  val gx = List(0f, 0.5f, 0.6f, 1f)
  val gy = List(0f, 0.5f, 0.62f,1f)

  val bx = List(0f, 0.2f, 0.5f, 1f)
  val by = List(0f, 0.2f, 0.5f, 0.95f)

  val filters =
    setCurves(x, y, "all")
    ++ setCurves(rx, ry, "r")
    ++ setCurves(gx, gy, "g")
    ++ setCurves(bx, by, "b")
    ++ setHSV(0, 1.3, 0)

  val argsmp4 =
    setVideoEncoder("x264")
    ++ x264_setPreset("superfast")
    ++ setCRF(22)
    ++ setAudioEncoder("flac")

  val argsmxf =
    setVideoEncoder("x264")
    ++ x264_setPreset("superfast")
    ++ setCRF(22)
    ++ setAudioEncoder("copy")

  for f <- File(".").list() do
    println(f)
    if f.contains(".MP4") then
      encode(f, s"new/$f", argsmp4, filters, quiet = false)
    else if f.contains(".MXF") then
      encode(f, s"new/$f", argsmxf, filters, quiet = false)

def extract() =
  extractFrames("CHARGE.avi", "png", amt = 5)
// def batch() = {
//   val files: List[String] = List("Awesome Wooper.png", "Keroppi.png", "Tux OC.png")
//   val params = {scaleFactor(0.1, 0.1)
//   + webp_setQuality(100)
//   + webp_setLossless()
//   }
//   batchExecute(files, params, "webp", false)
//
// }

// def testoptional() = {
//   val encodeimg = scaleFactor(0.1, 0.1)
//   encode("Awesome Wooper.png", "Awesome but awesomer.png", encodeimg, false)
//   encode("Awesome Wooper.png", "Awesome but awesomer 2.png", encodeimg)
//   encode("Awesome Wooper.png", "Awesome but awesomer 3.png", encodeimg, exec = "ffmpeg")
//   encode("Awesome Wooper.png", "Awesome but awesomer 4.png", encodeimg, true, "ffnada")
//   encode("Awesome Wooper.png", "Awesome but awesomer 5.png", encodeimg, false, "ffnada")
// }

// def encodedir() = {
//   val params =
//     scaleFactor(0.1, 0.1)
//     ++ webp_setQuality(100)
//     ++ webp_setLossless()
//   batchDir(".", "webp", params, true)
// }
//
// def nametest() = { //whitespace, name is seen as 2 args
//   val encodeimg = scaleFactor(0.1, 0.1)
//   encode("Awesome Wooper.png", "Awesome but awesomer.png", encodeimg, false)
// }
//
// def firsttest() = {
//   val doremy =
//     setVideoEncoder("utvideo")
//     ++ setAudioEncoder("pcm24")
//     ++ scale(1280, 720)
//   println(doremy)
//   encode("CHARGE.mov", doremy, "CHARGE.avi")
// }
//
// def ohyeah() = {
//   val doremy =
//     setVideoEncoder("x265")
//     ++ x264_setPreset("veryfast")
//     ++ setCRF(18)
//     ++ setAudioEncoder("opus")
//     ++ setAudioBitrate(320)
//     ++ scale(1280, 720)
//     ++ setScaleFilter("bilinear")
//   println(doremy)
//   encode("CHARGE.mov", doremy, "CHARGE.mp4", false)
// }
//
//
// def croptest() = {
//   val doremy =
//     setVideoEncoder("x264")
//     ++ x264_setPreset("veryfast")
//     ++ setCRF(18)
//     ++ setAudioEncoder("opus")
//     ++ setAudioBitrate(320)
//     ++ cropToAspect(2, 1)
//   println(doremy)
//   encode("CHARGE.mov", doremy, "CHARGE.mp4", false)
// }


def testcapture() =
  val capture =
    captureVideo("x11grab", "0.0", 30)
    ++ captureAudio("pulse", "alsa_output.pci-0000_00_1f.3.analog-stereo.monitor")
    ++ captureAudio("pulse", "alsa_input.pci-0000_00_1f.3.analog-stereo")
    ++ addTracks(3)
    ++ setDuration(20)

  val args =
    setVideoEncoder("x264rgb")
    ++ x264_setPreset("superfast")
    ++ setCRF(5)
    ++ setAudioEncoder("pcm24")
  println(capture ++ args)
  record("test.mov", capture ++ args)

//this function is unfinished
def capturewindows() =
  val capture =
    captureVideo("dshow", "0.0", 30) //change inputs
    ++ captureAudio("dshow", "alsa_output.pci-0000_00_1f.3.analog-stereo.monitor")
    ++ setDuration(20)

  val args =
    setVideoEncoder("mjpeg")
    ++ setQuality(10)
    ++ setAudioEncoder("pcm24")
  println(capture ++ args)
  record("test.mov", capture ++ args)

def getres() =
  val res = getResolution("Awesome Wooper.png")
  println(res)

def testscreenshot() =
  takeScreenshot_auto("x11grab", "0.0", "screenshot.png", quiet=false)


def testFilters() =
  val argf =
    setCurves(List(0.3f, 0.5f, 0.7f), List(0.2f, 0.5f, 0.8f))
    ++ setCurves(List(0.0f, 0.5f, 1.0f), List(0.2f, 0.5f, 0.9f), "blue")
    ++ setHSV(0, 1.2, 0)
    ++ scaleFactor(2, 2)
    ++ setScaleFilter("bilinear")

  encode("Awesome Wooper.png", "filtertest.png", filters = argf)
