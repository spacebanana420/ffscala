# Getting started

#### This documentation file is unfinished, it's slowly being built.

FFscala is a wrapper library for the FFmpeg command line. Most functions return command line arguments for FFmpeg, and by composing them you can make use of FFmpeg in your software. Some functionality requires more function composition than others. All command line arguments are organized into string lists of type ```List[String]```.

Some functions execute FFmpeg for specific functionality, like ```encode()``` for encoding media files, or ```record()``` for capturing your desktop or audio devices.

To add FFscala to your code and use it, write:

```scala
import ffscala.*
```

If you want desktop and audio capture functionality, also do:

```scala
import ffscala.capture.*
```
Note that, in order to use FFscala, you need to have FFmpeg installed in your system. By default, FFmpeg needs to be in your system's PATH environment variable, but if you don't want to do that, you can specify a custom path to the FFmpeg executable whenever running a function that calls FFmpeg, such as the ones mentioned at the start of this page.

To check if you have FFmpeg installed and in your PATH environment variable, you can run ```ffmpeg``` on a terminal. Alternatively, you can execute FFscala's ```checkFFmpeg()``` function.

Now you can use FFscala for many things FFmpeg is capable of, such as encoding media, playing back media files, probing media and getting their metadata, capturing your desktop or audio, etc.

# Encoding

To encode media files, you need to first obtain the arguments and parameters you want, then you pass them to ```encode()```.

You can also pass filter arguments to process your media.

You can find the functions for base [FFmpeg encoding](ffmpeg.md), [video encoder-specific](video.md) parameters, [audio encoder-specific](audio.md) parameters and [filters](filters.md).

Just like in FFmpeg, you don't need to pass many arguments for a simple file encode. Although of course, the less arguments you specify, the more defaults will be used, or parameters that are assumed based on the input video.

A simple example of a media conversion could be something like this:

```scala
encode("image.bmp", "image.png")
```

Without any additional arguments, this function will call FFmpeg to convert the file "image.bmp" in your current working diretory into a PNG image of name "image.png", using the default parameters for PNG encoding.

You can specify the absolute path to a file somewhere else on your system, as well as print out FFmpeg's messages, which is useful for debugging:

```scala
encode("/path/to/image.bmp", "image.png", quiet = false)
```
The argument ```quiet``` is set to ```false```. This argument is optional as it's set to ```true``` by default, but, if you set it to false, FFmpeg will print its output to the terminal.

Things get more fun when you add encoding parameters:

```scala
val args =
  setVideoEncoder("x264")
  ++ setCRF(12)
  ++ x264_setPreset("veryfast")
  ++ setAudioEncoder("copy")
encode("video.mov", "newvideo.mov", args)
```

Now we are passing FFmpeg arguments so we can set the encoders we want, video properties and encoding parameters. In this case, the input video will be transcoded into an H.264 video with x264. x264 has encoding presets, and so ```veryfast``` was chosen here. For the bitrate target, CRF with a value of 12 was used. The audio is untouched, using the ```copy``` encoder, which will pass the audio data to the new video file without changing it.

Here's another example for encoding:

```scala
val args =
  setVideoEncoder("webp")
  ++ webp_setLossless()
  ++ webp_setQuality(100)
val filters=
  ++ scaleFactor(2, 2)
  ++ setScaleFilter("bilinear")
encode("image.png", "image.webp", args, filters)
```
With these arguments, FFmpeg will convert "image.png" into a lossless webp with high compression efficiency with double the resolution, upscaled with the bilinear filter.

Filter parameters have to be passed to the filters argument, separate from the arguments. The only exception to this is ```setScaleFilter()``` which can be included in the arguments list.


# Media probing

FFmpeg has probing capabilities to read all technical information about media files you need. You can use this functionality with [these functions](ffprobe.md).

# Media playback

FFmpeg also has its own FFplay, a simple and barebones media player. With FFscala, you can call FFplay to play back media files and you can even develop your own simple media player that uses FFplay in the backend. The functionality for FFplay is [here](ffplay.md).

Like FFmpeg encoding, FFplay takes arguments. Here's a quick example:

```scala
val args =
  setFullscreen()
  ++ setPlayVolume(12)
  ++ setAutoExit()
  ++ setWindowTitle("Homemade Cinema")
execplay("movie.mkv", args)
```

This will call FFplay to open and play back the video "movie.mkv" in fullscreen with the volume at 12%. FFplay will close once the video ends and the display window will be titled "Homemade Cinema".
