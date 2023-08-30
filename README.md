# FFscala
### This library is untested and unfinished and is not meant to be used yet while in this state

FFscala is a simple wrapper library for the FFmpeg command line, written in Scala.

### Example 1
```scala
val transcodeVideo = { ffmpeg.openFile("/home/banana/Videos/gameplay.mov")
+ ffmpeg.setVideoEncoder("libx264")
+ ffmpeg.setVideoBitrate(0, 4000, "k")
+ ffmpeg.setVideoResolution(1920, 1080)
+ ffmpeg.setPixFmt("yuv420p")
+ ffmpeg.setAudioEncoder("opus")
+ ffmpeg.setAudioBitrate(320, "k")
+ ffmpeg.setOutput("gameplay_new", "mp4")
}
ffmpeg.execute(1, transcodeVideo)
```

The equivalent command should be
```
ffmpeg -i /home/banana/Videos/gameplay.mov -c:v libx264 -b:v 4000k -filter:v scale=1920:1080 -pix_fmt yuv420p -c:a libopus -b:a 320k gameplay_new.mp4
```

### Example 2 
```scala
val convertImage = ffmpeg.openFile("/home/user/image.bmp") + ffmpeg.setVideoResolution(700, 800) + ffmpeg.setOutput("biggerimage", "png")
ffmpeg.execute(0, convertImage)
```
The equivalent command should be
```
ffmpeg -i /home/user/image.bmp -filter:v scale=700:800 biggerimage.png
```
