# FFscala

FFscala is a simple wrapper library for the FFmpeg command line, written in Scala 3.

FFscala works by using functions to transform your video encoding parameters into a string composed of FFmpeg arguments. Before executing the command, the string is converted into a list of arguments so the command execution is independent of shell and OS, which makes it more portable.

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
ffmpeg -loglevel quiet -y -i /home/banana/Videos/gameplay.mov -c:v libx264 -b:v 4000k -filter:v scale=1920:1080 -pix_fmt yuv420p -c:a libopus -b:a 320k /home/banana/Videos/gameplay_new.mp4
```
Like when you use FFmpeg directly, most parameters are optional, as you can see in the second example.

### Example 2 - Image conversion and resize
```scala
val scaleimg = scale(700, 800)
execute("image.bmp", scaleimg, "biggerimage.png")
```
The equivalent command should be
```
ffmpeg -loglevel quiet -y -i image.bmp -filter:v scale=700:800 biggerimage.png
```
Here, the relative paths for the images are used. Many less parameters are used here, you don't have to use all functions of this library.

Your path names can have spaces between them, as the command execution is shell-independent.

# Requirements

* [FFmpeg](https://ffmpeg.org/)
* Scala 3
* Your program needs to compile to JVM, since FFscala uses java.io.File

FFmpeg tested with version 6, but you won't have problems using other versions.

By default, you need FFmpeg to be in your PATH, but execute() lets you optionally specify the path to the executable if you prefer that way.

(See [example 11](https://github.com/spacebanana420/ffscala/blob/main/docs/examples.md))

# Download & how to use

You can find releases of FFscala [here](https://github.com/spacebanana420/ffscala/releases)

Choose the version of your choice (although the latest is recommended) and download the source code from that release.

Add the library code into your project and import ```ffscala```

(See [example 7](https://github.com/spacebanana420/ffscala/blob/main/docs/examples.md))


# Documentation

Most functions return an empty string or int of value -1 when there is an error in the arguments you passed to them

The documentation is separated into multiple pages, each being respective to a component of the library.

#### [FFmpeg doc](https://github.com/spacebanana420/ffscala/blob/main/docs/ffmpeg.md)
#### [Video doc](https://github.com/spacebanana420/ffscala/blob/main/docs/video.md)
#### [Filters doc](https://github.com/spacebanana420/ffscala/blob/main/docs/filters.md)
#### [FFprobe doc](https://github.com/spacebanana420/ffscala/blob/main/docs/ffprobe.md)
#### [Batch doc](https://github.com/spacebanana420/ffscala/blob/main/docs/batch.md)

FFmpeg doc contains the documentation for the main functions of this library.

Video doc contains the documentation for the functions related to encoder-specific parameters and presets.

Filters doc contains the documentation for FFmpeg filters.

FFprobe doc contains the documentation for parsing media information.

Batch doc contains the documentation for encoding multiple files.

#### [List of examples](https://github.com/spacebanana420/ffscala/blob/main/docs/examples.md)
