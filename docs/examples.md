### Example 1 - Video transcoding
```scala
val transcodeVideo = { ffscala.openFile("/home/banana/Videos/gameplay.mov")
+ ffscala.setVideoEncoder("x264")
+ ffscala.setVideoBitrate("cbr", 4000)
+ ffscala.scale(1920, 1080)
+ ffscala.setPixFmt("yuv420p")
+ ffscala.setAudioEncoder("opus")
+ ffscala.setAudioBitrate(320)
+ ffscala.setOutput("/home/banana/Videos/gameplay_new", "mp4")
}
ffscala.execute(transcodeVideo)
```

The equivalent command should be
```
ffmpeg -y -loglevel 32 -i /home/banana/Videos/gameplay.mov -c:v libx264 -b:v 4000k -filter:v scale=1920:1080 -pix_fmt yuv420p -c:a libopus -b:a 320k /home/banana/Videos/gameplay_new.mp4
```
Like when you use FFmpeg directly, most parameters are optional, as you can see in the second example. The order of most parameters does not matter either.

### Example 2 - Image conversion and resize
```scala
val convertImage = { ffscala.openFile("image.bmp")
+ ffscala.scale(700, 800)
+ ffscala.setOutput("biggerimage", "png")
}
ffscala.execute(convertImage)
```
The equivalent command should be
```
ffmpeg -i image.bmp -filter:v scale=700:800 biggerimage.png
```
Here, the relative paths for the images are used. Many less parameters are used here, you don't have to use all functions of this library.

Your path names can have spaces between them, as the command execution is shell-independent.

### Example 3 - Exporting audio from video without re-encoding
(Assuming the audio is encoded in opus)

```scala
val getAudio = { ffscala.openFile("/path/to/video.mp4")
+ ffscala.removeElement("video")
+ ffscala.setAudioEncoder("copy")
+ ffscala.setOutput("/path/to/audio.ogg")
}
ffscala.execute(getAudio)
```

It's assumed in this example that the audio is encoded in opus, an encoding format supported by the ogg file.

You use ```removeElement``` to tell FFmpeg that you do not want your output file to have the video contents of the input.

In ```setAudioEncoder```, you want to use ```copy```, which translates to not re-encoding the audio contents of the video.

### Example 4 - capturing a frame from a video

```scala
ffmpeg.captureScreenshot("/path/to/video.mp4", "/path/to/frame.png", "00:13:24")
```

This function works by itself and does not need to be used alongside the others (openFile, setOutput, execute, etc)

This time argument will tell FFmpeg to seek for the frame at 13 minutes and 24 seconds

You can use other time formats, like this:

```scala
ffmpeg.captureScreenshot("/path/to/video.mp4", "/path/to/frame.png", "320.5")
```

Here. the time argument will tell FFmpeg to get the frame at 320.5 seconds

### Example 5 - CRF video encoding, with audio intact
```scala
val transcodeVideo = { ffscala.openFile("/home/banana/Videos/gameplay.mov")
+ ffscala.setVideoEncoder("x264")
+ ffscala.setVideoBitrate("crf", 12)
+ ffscala.x264_setPreset("veryfast")
+ ffscala.setAudioEncoder("copy")
+ ffscala.setOutput("/home/banana/Videos/gameplay_new", "mp4")
}
ffscala.execute(transcodeVideo)
```

This will transcode the input video into a video encoded in x264 with the preset veryfast, using the CRF bitrate control method with a CRF value of 12. The output audio will stay intact, exactly the same as from the input.

### Example 6 - Unusual function order
```scala
val transcodeVideo = { ffscala.openFile("/home/banana/Videos/gameplay.mov")
+ ffscala.setVideoBitrate("crf", 12)
+ ffscala.setAudioEncoder("copy")
+ ffscala.setVideoEncoder("x264")
+ ffscala.x264_setPreset("veryfast")
+ ffscala.setOutput("/home/banana/Videos/gameplay_new", "mp4")
}
ffscala.execute(transcodeVideo)
```

This is the same example as above, but with the order of the function calls altered. The command is still functional and the output is the same, as long as you start with ```openFile``` and end with ```setOutput```. This however is more unpredictable if you want to include multiple input sources.


### Example 7 - Program example

```scala
import ffscala.*

@main def main() = {
    val encodeVideo = {openFile("/path/to/video.mov")
    + setVideoEncoder("x265")
    + x264_setPreset("veryfast")
    + setVideoBitrate("crf", 18)
    + setAudioEncoder("opus")
    + setAudioBitrate(320)
    + scale(1280, 720)
    + scaleFilter("bilinear")
    + setOutput("/path/to/newvideo", "mp4")
    }
    println("The command arguments are " + encodeVideo + "\n")
    executeSilent(encodeVideo)
}

```

This is a real-world example of a simple FFscala implementation.

This code is functional and would compile if /path/to/video.mov was the path to a real video file.