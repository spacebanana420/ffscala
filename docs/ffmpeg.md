```scala
def openFile(path: String): String
```
Sets the input media for FFmpeg to read.

This must be the first function to be called, as the returned string should be at the start of your FFmpeg command's arguments.

```scala
def setOutput(name: String, format: String): String
```
Returns the output path, a combination of the name and format. Use relative paths only for the paths in starting working directory of your program.

This must be the last function to be called before calling ```execute```, as the returned string should be at the end of your FFmpeg command's arguments.

#### Supported formats:
* png
* apng
* avif
* jpeg
* jpg
* tiff
* tif
* bmp
* gif
* webp
* tga
* mp4
* mov
* m4v
* avi
* mkv
* webm
* flac
* wav
* ogg
* opus
* m4a
* mp3
* aiff

```scala
def execute(args: String): Int
```
Executes the final command ```args``` and checks for errors in the command.

```scala
def executeSilent(args: String): Int
```
Executes the final command ```args``` silently and checks for errors in the command.

```scala
def setVideoEncoder(encoder: String): String
```
Sets the video encoder
#### Supported video encoders:
* copy
* x264
* x264rgb
* x265
* nvenc
* nvenc265
* utvideo
* png
* dnxhd
* tiff
* cfhd
* vp9

```scala
def setVideoBitrate(method: String, bitrate: Int): String
```
Sets the video bitrate control method and value.

For CBR, the bitrate is measured in kilobits

For CRF, the "bitrate" value is just the CRF value.

CQP is not implemented yet
#### Supported methods:
* cbr
* crf
* cqp (to be added)

```scala
def setPixFmt(newfmt: String): String
```
Sets the video/image's pixel format
#### Supported pixel formats:
* rgb24
* rgb48
* rgb48le
* rgba
* rgba64le
* gray
* gray16le
* yuv420p
* yuv422p
* yuv444p
* yuv422p10le
* yuv444p10le

```scala
def setKeyframeInterval(interval: Short): String
```
Sets the video's keyframe interval in frames (0 or higher).

```scala
def setBFrames(interval: Byte): String
```
Sets the video's bframes (between 0 and 16).

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
def setAudioBitrate(bitrate: Int): String
```
Sets the audio bitrate in kilobits.

```scala
def setSampleFormat(fmt: String): String
```
Sets the audio's sample format
#### Supported sample formats:
* s16
* s32

```scala
def removeElement(element: String): String
```
Removes a whole type of element of the input media, such as the video channels, audio channels or subtitle channels.
#### Values:
* "video" - removes video
* "audio" - removes audio
* "subtitle" - removes subtitles

```scala
def getScreenshot(input: String, output: String, time: String): String
```
Captures a frame from a video or image sequence.

Time can be formatted like this: ```hh:mm:ss```

Or this: ```15s```
