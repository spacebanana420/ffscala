import ffscala.*

@main def main() = {
    //batch()
    //encodedir()
    testoptional()
}

// def batch() = {
//     val files: List[String] = List("Awesome Wooper.png", "Keroppi.png", "Tux OC.png")
//     val params = {scaleFactor(0.1, 0.1)
//     + webp_setQuality(100)
//     + webp_setLossless()
//     }
//     batchExecute(files, params, "webp", false)
//
// }

def testoptional() = {
    val encodeimg = scaleFactor(0.1, 0.1)
    execute("Awesome Wooper.png", encodeimg, "Awesome but awesomer.png", false)
    execute("Awesome Wooper.png", encodeimg, "Awesome but awesomer 2.png")
    execute("Awesome Wooper.png", encodeimg, "Awesome but awesomer 3.png", exec = "ffmpeg")
    execute("Awesome Wooper.png", encodeimg, "Awesome but awesomer 4.png", true, "ffnada")
    execute("Awesome Wooper.png", encodeimg, "Awesome but awesomer 5.png", false, "ffnada")
}

def encodedir() = {
    val params = {scaleFactor(0.1, 0.1)
    + webp_setQuality(100)
    + webp_setLossless()
    }
    batchDir(".", params, "webp", true)
}

def nametest() = { //whitespace, name is seen as 2 args
    val encodeimg = scaleFactor(0.1, 0.1)
    execute("Awesome Wooper.png", encodeimg, "Awesome but awesomer.png", false)
}

def firsttest() = {
    val doremy = {setVideoEncoder("utvideo")
    + setAudioEncoder("pcm24")
    + scale(1280, 720)
    }
    println(doremy)
    execute("CHARGE.mov", doremy, "CHARGE.avi")
}

def ohyeah() = {
    val doremy = {setVideoEncoder("x265")
    + x264_setPreset("veryfast")
    + setVideoBitrate("crf", 18)
    + setAudioEncoder("opus")
    + setAudioBitrate(320)
    + scale(1280, 720)
    + setScaleFilter("bilinear")
    }
    println(doremy)
    execute("CHARGE.mov", doremy, "CHARGE.mp4", false)
}


def croptest() = {
    val doremy = {setVideoEncoder("x264")
    + x264_setPreset("veryfast")
    + setVideoBitrate("crf", 18)
    + setAudioEncoder("opus")
    + setAudioBitrate(320)
    + cropToAspect(2, 1)
    }
    println(doremy)
    execute("CHARGE.mov", doremy, "CHARGE.mp4", false)
}