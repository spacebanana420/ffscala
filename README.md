# FFscala
### This library is untested and unfinished and is not meant to be used yet while in this state until a release is published on this repo

FFscala is a simple wrapper library for the FFmpeg command line, written in Scala 3.

FFscala works by using functions to transform your video encoding properties into a string composed of FFmpeg arguments. Before executing the command, the string is converted into a list of arguments so the command execution is independent of shell and OS, which makes it more portable.

### Example 1 - Video transcoding
```scala
val transcodeVideo = { ffmpeg.openFile("/home/banana/Videos/gameplay.mov")
+ ffmpeg.setVideoEncoder("libx264")
+ ffmpeg.setVideoBitrate(0, 4000, "k")
+ ffmpeg.setVideoResolution(1920, 1080)
+ ffmpeg.setPixFmt("yuv420p")
+ ffmpeg.setAudioEncoder("opus")
+ ffmpeg.setAudioBitrate(320, "k")
+ ffmpeg.setOutput("/home/banana/Videos/gameplay_new", "mp4")
}
ffmpeg.execute(1, transcodeVideo)
```

The equivalent command should be
```
ffmpeg -i /home/banana/Videos/gameplay.mov -c:v libx264 -b:v 4000k -filter:v scale=1920:1080 -pix_fmt yuv420p -c:a libopus -b:a 320k /home/banana/Videos/gameplay_new.mp4
```
As it is when you use FFmpeg directly, most parameters are optional, as you can see in the second example.

### Example 2 - Image conversion
```scala
val convertImage = ffmpeg.openFile("image.bmp") + ffmpeg.setVideoResolution(700, 800) + ffmpeg.setOutput("biggerimage", "png")
ffmpeg.execute(0, convertImage)
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

```scala
def openFile(path: String): String
```
Returns the initial arguments for importing the file in FFmpeg.

```scala
def setOutput(name: String, format: String): String
```
Returns the path, a combination of the name and format. Use relative paths only for the paths in starting working directory of your program

```scala
def execute(mediaType: Byte, args: String): Int
```
Executes the final command ```args``` and checks for errors in the command based on the media type.
#### Values for mediaType:
* 0: image
* 1: video
* 2: audio

```scala
def setVideoEncoder(encoder: String): String
```
Sets the video encoder
#### Supported video encoders:
* copy
* libx264
* libx265
* utvideo
* png
* dnxhd
* tiff
* cfhd

```scala
def setVideoBitrate(method: Byte, bitrate: Int, dimension: String): String
```
Sets the video bitrate control method, bitrate and unit (dimension)
#### Supported methods:
* cbr
* crf
* cqp (to be added)
#### Unit examples:
* k - kilobits/second
* M - megabits/second

```scala
def setVideoResolution(width: Int, height: Int): String
```
Sets the video's width and height resolution in pixels

```scala
def setPixFmt(newfmt: String): String
```
Sets the video/image's pixel format
#### Supported pixel formats:
* rgb24
* rgb48
* gray
* yuv420p
* yuv422p
* yuv444p

```scala
def setKeyframeInterval(interval: Int): String
```
Sets the video's keyframe interval in frames (0 or higher)

```scala
def setBFrames(interval: Byte): String
```
Sets the video's bframes (between 0 and 16)

```scala
def setAudioEncoder(encoder: String): String
```
Sets the audio encoder
#### Supported encoders:
* copy
* aac
* opus
* vorbis
* mp3
* ac3
* flac
* pcm16
* pcm24
* pcm32

```scala
def setAudioBitrate(bitrate: Int, dimension: String): String
```
Sets the audio bitrate and unit (dimension)
#### Unit examples:
* k - kilobits/second
* M - megabits/second

```scala
def normalizeAudio(): String
```
Normalizes the audio with the loudnorm filter


```scala
def removeElement(element: Byte): String
```
Removes a whole type of element of the input media, such as the video channels, audio channels or subtitle channels
#### Values:
* 0 - removes video
* 1 - removes audio
* 2 - removes subtitles
