# FFscala
### This library is untested and unfinished and is not meant to be used yet while in this state until a release is published on this repo

FFscala is a simple wrapper library for the FFmpeg command line, written in Scala 3.

FFscala works by using functions to transform your video encoding properties into a string composed of FFmpeg arguments. Before executing the command, the string is converted into a list of arguments so the command execution is independent of shell and OS, which makes it more portable.

### Example 1 - Video transcoding
```scala
val transcodeVideo = { ffmpeg.openFile("/home/banana/Videos/gameplay.mov")
+ ffmpeg.setVideoEncoder("x264")
+ ffmpeg.setVideoBitrate("cbr", 4000, "k")
+ ffmpeg.setVideoResolution(1920, 1080)
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
+ ffmpeg.setVideoResolution(700, 800)
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

# Requirements

* You need to have [FFmpeg](https://ffmpeg.org/) installed
* Library only tested with Scala 3

# Documentation
Most functions return an empty string or int of value -1 when there is an error in the arguments you passed to them

The order of the function calls is not important for most cases, as long as the first function called is ```openFile``` and the last one is ```setOutput```

The documentation is separated into multiple pages, each being respective to a component of the library.

### [FFmpeg doc](https://github.com/spacebanana420/ffscala/blob/main/docs_ffmpeg.md)
### [Video doc](https://github.com/spacebanana420/ffscala/blob/main/docs_video.md)
### [Filters doc](https://github.com/spacebanana420/ffscala/blob/main/docs_filters.md)

FFmpeg doc contains the documentation for the main functions of this library

Video doc contains the documentation for the functions related to encoder-specific parameters and presets

Filters doc contains the documentation for FFmpeg filters
