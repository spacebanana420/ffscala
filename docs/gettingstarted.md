# Getting started

FFscala is a wrapper library for the FFmpeg command line. Most functions return command line arguments for FFmpeg, and by composing them you can make use of FFmpeg in your software. Some functionality requires more function composition than others.

Some functions execute FFmpeg for specific functionality, like ```execute()``` for encoding media files, or ```record()``` for capturing your desktop or audio devices.

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

To encode media files, you need to first obtain the arguments and parameters you want, then you pass them to ```execute()```.

You can also pass filter arguments to process your media.

You can find the functions for base [FFmpeg encoding](ffmpeg.md), [video encoder-specific](video.md) parameters, [audio encoder-specific](audio.md) parameters and [filters](filters.md).

Just like in FFmpeg, you don't need to pass many arguments for a simple file encode. Although of course, the less arguments you specify, the more defaults will be used, or parameters that are assumed based on the input video.

A simple example of a media conversion could be something like this:

```scala
execute("image.bmp", List(), "image.png")
```

Without any additional arguments, this function will call FFmpeg to convert the file "image.bmp" in your current working diretory into a PNG image of name "image.png", using the default parameters for PNG encoding.

```List()``` is an empty list being passed where you would pass additional FFmpeg arguments.

You can specify the absolute path to a file somewhere else on your system, as well as print out FFmpeg's messages, which is useful for debugging:

```scala
execute("/path/to/image.bmp", List(), "image.png", false)
```
```false``` is being passed to the "quiet" parameter. This argument is optional as it's set to ```true``` by default, but, if you set it to false, FFmpeg will print its output to the terminal.

Things get more fun when you add encoding parameters:

```scala
val args =
  setVideoEncoder("x264")
  ++ setCRF(12)
  ++ x264_setPreset("veryfast")
  ++ setAudioEncoder("copy")
execute("video.mov", args, "newvideo.mov")
```

Now we are passing FFmpeg arguments so we can set the encoders we want, video properties and encoding parameters. In this case, the input video will be transcoded into an H.264 video with x264. x264 has encoding presets, and so ```veryfast``` was chosen here. For the bitrate target, CRF with a value of 12 was used. The audio is untouched, using the ```copy``` encoder, which will pass the audio data to the new video file without changing it.

**This documentation file is unfinished so I didn't make a link to it yet.**
