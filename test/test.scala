import ffscala.*
import ffscala.capture.*

@main def main() = {
  //batch()
  //encodedir()
  //testoptional()
  //testcapture()
  getres()
  testscreenshot()
  testFilters()
  testcombine()
}

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
  takeScreenshot("x11grab", "0.0", "screenshot.png", quiet=false)


def testFilters() =
  val argf =
    setCurves(List(0.3f, 0.5f, 0.7f), List(0.2f, 0.5f, 0.8f))
    ++ setCurves(List(0.0f, 0.5f, 1.0f), List(0.2f, 0.5f, 0.9f), "blue")
    ++ setHSV(0, 1.2, 0)
    ++ scaleFactor(2, 2)
    ++ setScaleFilter("bilinear")

  encode("Awesome Wooper.png", "filtertest.png", filters = argf)
