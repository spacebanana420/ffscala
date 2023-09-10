### Example 1 - Video transcoding
```scala
val encodeParams = {
setVideoEncoder("x264")
+ setVideoBitrate("cbr", 4000)
+ scale(1920, 1080)
+ setPixFmt("yuv420p")
+ setAudioEncoder("opus")
+ setAudioBitrate(320)
}
execute("/home/banana/Videos/gameplay.mov", encodeParams, "/home/banana/Videos/gameplay.mp4")
```

The equivalent command should be
```
ffmpeg -y -i /home/banana/Videos/gameplay.mov -c:v libx264 -b:v 4000k -filter:v scale=1920:1080 -pix_fmt yuv420p -c:a libopus -b:a 320k /home/banana/Videos/gameplay_new.mp4
```
Like when you use FFmpeg directly, most parameters are optional, as you can see in the second example.

### Example 2 - Image conversion and resize
```scala
val scaleimg = scale(700, 800)
execute("image.bmp", scaleimg, "biggerimage.png")
```
The equivalent command should be
```
ffmpeg -y -i image.bmp -filter:v scale=700:800 biggerimage.png
```
Here, the relative paths for the images are used. Many less parameters are used here, you don't have to use all functions of this library.

Your path names can have spaces between them, as the command execution is shell-independent.

### Example 3 - Exporting audio from video without re-encoding
(Assuming the audio is encoded in opus)

```scala
val getAudio = removeElement("video") + setAudioEncoder("copy")
execute("/path/to/video.mp4", getAudio, "/path/to/audio.ogg")
```

It's assumed in this example that the audio is encoded in opus, an encoding format supported by the ogg file.

You use ```removeElement``` to tell FFmpeg that you do not want your output file to have the video contents of the input.

In ```setAudioEncoder```, you want to use ```copy```, which translates to not re-encoding the audio contents of the video.

### Example 4 - capturing a frame from a video

```scala
captureScreenshot("/path/to/video.mp4", "/path/to/frame.png", "00:13:24")
```

This function works by itself and does not need to be used alongside the others (openFile, setOutput, execute, etc)

This time argument will tell FFmpeg to seek for the frame at 13 minutes and 24 seconds

You can use other time formats, like this:

```scala
captureScreenshot("/path/to/video.mp4", "/path/to/frame.png", "320.5")
```

Here. the time argument will tell FFmpeg to get the frame at 320.5 seconds

### Example 5 - CRF video encoding, with audio intact
```scala
val transcodeVideo = {
setVideoEncoder("x264")
+ setVideoBitrate("crf", 12)
+ x264_setPreset("veryfast")
+ setAudioEncoder("copy")
}
execute("/home/banana/Videos/gameplay.mov", transcodeVideo, "/home/banana/Videos/gameplay_new.mp4")
```

This will transcode the input video into a video encoded in x264 with the preset veryfast, using the CRF bitrate control method with a CRF value of 12. The output audio will stay intact, exactly the same as from the input.

### Example 6 - Unusual function order
```scala
val transcodeVideo = {
setVideoBitrate("crf", 12)
+ setAudioEncoder("copy")
+ setVideoEncoder("x264")
+ x264_setPreset("veryfast")
}
execute("/home/banana/Videos/gameplay.mov", transcodeVideo, "/home/banana/Videos/gameplay_new.mp4")
```

This is the same example as above, but with the order of the function calls altered. The command is still functional and the output is the same, as long as you start with ```openFile``` and end with ```setOutput```. This however is more unpredictable if you want to include multiple input sources.


### Example 7 - Program example

```scala
import ffscala.*

@main def main() = {
    val encodeVideo = {
    setVideoEncoder("x265")
    + x264_setPreset("veryfast")
    + setVideoBitrate("crf", 18)
    + setAudioEncoder("opus")
    + setAudioBitrate(320)
    + scale(1280, 720)
    + scaleFilter("bilinear")
    }
    println("The command arguments are " + encodeVideo + "\n")
    execute("/path/to/video.mov", encodeVideo, "/path/to/newvideo.mp4", false)
}

```

This is a real-world example of a simple FFscala implementation.

This code is functional and would compile if /path/to/video.mov was the path to a real video file.

```execute``` has a last optional argument ```quiet```. By default it is set to true, but for this example it is explicitly set to false. Wh


### Example 8 - Encode with 16:10 crop
```scala
val transcodeVideo = {
setVideoEncoder("x264")
+ x264_setPreset("veryfast")
+ setVideoBitrate("crf", 15)
+ setAudioEncoder("copy")
+ cropToAspect(16, 10)
}
execute("/home/banana/Videos/video.mov", transcodeVideo, "/home/banana/Videos/video_new.mp4")
```
The video will be cropped to 16:10 and centered, alongside the usual encoding parameters specified.

### Example 9 - Batch encoding
```scala
val filesToEncode: List[String] = List("/path/to/video1" , "/path/to/video2", "/path/to/video3")
val encodeSettings = {
setVideoEncoder("x264")
+ x264_setPreset("veryfast")
+ setVideoBitrate("crf", 15)
+ setAudioEncoder("copy")
+ cropToAspect(16, 10)
}

batchExecute(filesToEncode, encodeSettings, "mov")
```


### Example 10 - Manual batch encoding
```scala
val filesToEncode: List[String] = List("/path/to/video1" , "/path/to/video2", "/path/to/video3")
val encodeSettings = {
setVideoEncoder("x264")
+ x264_setPreset("veryfast")
+ setVideoBitrate("crf", 15)
+ setAudioEncoder("copy")
+ cropToAspect(16, 10)
}

for file <- filesToEncode do {
    execute(file, encodeSettings, removeExtension(file) + "_new.mov")
}
```
You can create a list, array, whatever, and loop through all elements in it yourself to encode multiple files with the same encoding parameters.

To use removeExtension, you need to import ```misc```.

Assuming video1 is named "video1.mp4", the result will be "video1_new.mov".
