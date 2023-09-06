# FFscala
### This library is unfinished and is not meant to be used in serious cases yet until a release is published on this repo

FFscala is a simple wrapper library for the FFmpeg command line, written in Scala 3.

FFscala works by using functions to transform your video encoding properties into a string composed of FFmpeg arguments. Before executing the command, the string is converted into a list of arguments so the command execution is independent of shell and OS, which makes it more portable.

### Example 1 - Video transcoding
```scala
val transcodeVideo = { ffmpeg.openFile("/home/banana/Videos/gameplay.mov")
+ ffscala.setVideoEncoder("x264")
+ ffscala.setVideoBitrate("cbr", 4000)
+ ffscala.scale(1920, 1080)
+ ffscala.setPixFmt("yuv420p")
+ ffscala.setAudioEncoder("opus")
+ ffscala.setAudioBitrate(320)
+ ffscala.setOutput("/home/banana/Videos/gameplay_new", "mp4")
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
+ ffscala.scale(700, 800)
+ ffscala.setOutput("biggerimage", "png")
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

* You need to have [FFmpeg](https://ffmpeg.org/) installed (tested in version 6, but most versions should work)
* Scala 3
* Your program needs to compile to JVM, since FFscala uses java.io.File

# How to use

Import the .scala files of this repository into yours

In the code files where you want to use the library, import ```ffscala```

# Documentation
Most functions return an empty string or int of value -1 when there is an error in the arguments you passed to them

The order of the function calls is not important for most cases, as long as the first function called is ```openFile``` and the last one is ```setOutput```

The documentation is separated into multiple pages, each being respective to a component of the library.

#### [FFmpeg doc](https://github.com/spacebanana420/ffscala/blob/main/docs/ffmpeg.md)
#### [Video doc](https://github.com/spacebanana420/ffscala/blob/main/docs/video.md)
#### [Filters doc](https://github.com/spacebanana420/ffscala/blob/main/docs/filters.md)
#### [FFprobe doc](https://github.com/spacebanana420/ffscala/blob/main/docs/ffprobe.md)

FFmpeg doc contains the documentation for the main functions of this library.

Video doc contains the documentation for the functions related to encoder-specific parameters and presets.

Filters doc contains the documentation for FFmpeg filters.

FFprobe doc contains the documentation for parsing media information.

#### [List of examples](https://github.com/spacebanana420/ffscala/blob/main/docs/examples.md)
