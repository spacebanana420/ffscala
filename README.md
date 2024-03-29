# FFscala

FFscala is a simple wrapper library for the FFmpeg command line, written in Scala 3.

FFscala works by using functions to transform your video encoding parameters into string lists composed of FFmpeg arguments. The command execution is independent of shell and OS, which makes it more portable.

FFscala is still in an early phase and so a new version update might bring big changes to the design and structure of the library.

To get started with using FFscala, I recommend reading [this page](docs/gettingstarted.md)

### Example 1 - Video transcoding
```scala
val encodeParams =
  setVideoEncoder("x264")
  ++ setVideoBitrate(4000)
  ++ setPixFmt("yuv420p")
  ++ setAudioEncoder("opus")
  ++ setAudioBitrate(320)
val filters = scale(1920, 1080)

encode("/home/banana/Videos/gameplay.mov", "/home/banana/Videos/gameplay.mp4", encodeParams, filters)
```

The equivalent command should be
```
ffmpeg -loglevel quiet -y -i /home/banana/Videos/gameplay.mov -c:v libx264 -b:v 4000k -filter:v scale=1920:1080 -pix_fmt yuv420p -c:a libopus -b:a 320k /home/banana/Videos/gameplay_new.mp4
```
Like when you use FFmpeg directly, most parameters are optional, as you can see in the second example.

### Example 2 - Image conversion and resize
```scala
val scaleimg = scale(700, 800)
encode("image.bmp", "biggerimage.png", filters = scaleimg)
```
The equivalent command should be
```
ffmpeg -loglevel quiet -y -i image.bmp -filter:v scale=700:800 biggerimage.png
```
Here, the relative paths for the images are used. Many less parameters are used here, you don't have to use all functions of this library.

Your path names can have spaces between them, as the command execution is shell-independent.

# Requirements

* [FFmpeg](https://ffmpeg.org/)
* [Scala 3](https://scala-lang.org/)

FFmpeg tested with version 6, but you won't have problems using other versions.

By default, you need FFmpeg to be in your PATH, but ```encode()``` and similar functions let you optionally specify the path to the executable or the program name if you prefer that way. Any function that has the optional argument ```exec``` lets you set a custom executable path or name.

(See [example 11](docs/examples.md))

# Download & how to use

You can find releases of FFscala [here](https://github.com/spacebanana420/ffscala/releases)

Choose the version of your choice (although the latest is recommended) and download the source code from that release.

You can download the archive on the releases page, if you just want the library files, or you can download the whole project from the repository.

Add all code in ```src``` into your project and import ```ffscala```. For video and audio capture support, you need to import ```ffscala.capture```:

```scala
  import ffscala.* //Most functionality

  import ffscala.capture.* //Video and audio capture functionality
```

(See [example 7](docs/examples.md))


# Documentation

FFscala has documentation separated into multiple pages, each being respective to a component of the library and a different type of FFmpeg functionality.

* [Getting Started](docs/gettingstarted.md)
  * An introductory guide to FFscala, what it can do and how to use the library.
* [FFmpeg](docs/ffmpeg.md)
  * Main functions for media encoding
* [Base](docs/base.md)
  * Base encoding and FFmpeg arguments.
* [Video](docs/video.md)
  * Encoder-specific parameters for video and image.
* [Audio](docs/audio.md)
  * Encoder-specific parameters for audio.
* [Filters](docs/filters.md)
  * Filter parameters
* [FFprobe](docs/ffprobe.md)
  * Media parsing/probing functions
* [FFplay](docs/ffplay.md)
  * Media playback with FFplay
* [Capture](docs/capture.md)
  * Screen and audio capture and recording functionality
* [Batch](docs/batch.md)
  * Batch processing and multiple file encoding functions

#### [List of examples](docs/examples.md)
