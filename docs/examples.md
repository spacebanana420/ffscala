### Example 1 - Video transcoding
```scala
val transcodeVideo = { ffmpeg.openFile("/home/banana/Videos/gameplay.mov")
+ ffmpeg.setVideoEncoder("x264")
+ ffmpeg.setVideoBitrate("cbr", 4000, "k")
+ filters.setVideoResolution(1920, 1080)
+ ffmpeg.setPixFmt("yuv420p")
+ ffmpeg.setAudioEncoder("opus")
+ ffmpeg.setAudioBitrate(320, "k")
+ ffmpeg.setOutput("/home/banana/Videos/gameplay_new", "mp4")
}
ffmpeg.execute("", transcodeVideo)
```

The equivalent command should be
```
ffmpeg -i /home/banana/Videos/gameplay.mov -c:v libx264 -b:v 4000k -filter:v scale=1920:1080 -pix_fmt yuv420p -c:a libopus -b:a 320k /home/banana/Videos/gameplay_new.mp4
```
Like when you use FFmpeg directly, most parameters are optional, as you can see in the second example. The order of most parameters does not matter either.

### Example 2 - Image conversion and resize
```scala
val convertImage = { ffmpeg.openFile("image.bmp")
+ filters.setVideoResolution(700, 800)
+ ffmpeg.setOutput("biggerimage", "png")
}
ffmpeg.execute("image", convertImage)
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
val getAudio = { ffmpeg.openFile("/path/to/video.mp4")
+ ffmpeg.removeElement("video")
+ ffmpeg.setAudioEncoder("copy")
+ ffmpeg.setOutput("/path/to/audio.ogg")
}
ffmpeg.execute("audio", getAudio)
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
val transcodeVideo = { ffmpeg.openFile("/home/banana/Videos/gameplay.mov")
+ ffmpeg.setVideoEncoder("x264")
+ ffmpeg.setVideoBitrate("CRF", 12, "")
+ video.x264_setPreset("veryfast")
+ ffmpeg.setAudioEncoder("copy")
+ ffmpeg.setOutput("/home/banana/Videos/gameplay_new", "mp4")
}
ffmpeg.execute("", transcodeVideo)
```

This will transcode the input video into a video encoded in x264 with the preset veryfast, using the CRF bitrate control method with a CRF value of 12. The output audio will stay intact, exactly the same as from the input.

### Example 6 - Unusual function order
```scala
val transcodeVideo = { ffmpeg.openFile("/home/banana/Videos/gameplay.mov")
+ ffmpeg.setVideoBitrate("CRF", 12, "")
+ ffmpeg.setAudioEncoder("copy")
+ ffmpeg.setVideoEncoder("x264")
+ video.x264_setPreset("veryfast")
+ ffmpeg.setOutput("/home/banana/Videos/gameplay_new", "mp4")
}
ffmpeg.execute("", transcodeVideo)
```

This is the same example as above, but with the order of the function calls altered. The command is still functional and the output is the same, as long as you start with ```openFile``` and end with ```setOutput```. This however is more unpredictable if you want to include multiple input sources.
