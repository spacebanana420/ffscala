These are the main functions of FFscala. This doc file contains the most commonly-used and important functions, as well as general functionality.

---

```scala
def encode
(
input: String, output: String, args: List[String] = List(), filters: List[String] = List(),
quiet: Boolean = true, exec: String = "ffmpeg"
): Int
```
Executes FFmpeg to encode the input file.

```input``` should be the absolute or relative path to the file you want to process.

```output``` should be the destination path for the final file, including filename and extension.

```args``` are the FFmpeg encoding arguments you get through most functions, except for filter functions.

```filters``` is the argument where you pass filters to. They cannot be passed to ```args``` because they are handled differently.

```quiet``` tells FFmpeg whether or not it should print information to stdout. By default, quiet is true, and FFmpeg won't output anything.

```exec``` is the path to your FFmpeg executable. By default, it is set to "ffmpeg", assuming you have FFmpeg in your PATH. If you do not have it in your PATH, or if you want to package your software with the binary or just use a custom path, you can specify the path yourself.

---

```scala
def setThreads(threads: Short): List[String]
```
Sets the amount of CPU threads to use. 0 is automatic/optimal, 1 is 1 thread, 2 is 2 threads, etc.

---

```scala
def setVideoEncoder(encoder: String): List[String]
```
Sets the video encoder.

#### Supported video encoders:
* copy
* x264
* x264rgb
* x265
* nvenc
* nvenc265
* qsv
* vaapi
* utvideo
* png
* dnxhd
* prores
* tiff
* cfhd
* vp9
* av1
* mjpeg
* targa
* bmp
* h263
* h263p
* ppm

---

```scala
def setVideoBitrate(bitrate: Int): List[String]
```
Sets the video bitrate, measured in kilobits per second.

---

```scala
def setCRF(value: Byte): List[String]
```
Sets the value for control rate factor. Minimum value is 0 which represents lossless compression.

---

```scala
def setPixFmt(newfmt: String): List[String]
```
Sets the video/image's pixel format
#### Supported pixel formats:
* rgb24
* bgr24
* bgr0
* gbrp
* gbrp10le
* gbrp12le
* gbrap
* rgb48
* rgb48le
* rgba
* rgba64le
* gray
* gray10le
* gray12le
* gray16le
* yuv420p
* yuv422p
* yuv444p
* yuv420p10le
* yuv422p10le
* yuv444p10le
* yuv420p12le
* yuv422p12le
* yuv444p12le
* yuvj420p
* yuvj422p
* yuvj444p
* nv12
* nv16
* nv21
* nv20le
* qsv
* vaapi

---

```scala
def setKeyframeInterval(interval: Short): List[String]
```
Sets the video's keyframe interval in frames (0 or higher).

---

```scala
def setBFrames(interval: Byte): List[String]
```
Sets the video's bframes (between 0 and 16).

---

```scala
def setAudioEncoder(encoder: String): List[String]
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
* pcm64
* pcm16be
* pcm24be
* pcm32be
* pcm64be

---

```scala
def setAudioBitrate(bitrate: Int): List[String]
```
Sets the audio bitrate in kilobits.

---

```scala
def setSampleFormat(fmt: String): List[String]
```
Sets the audio's sample format
#### Supported sample formats:
* u8
* s16
* s32
* flt
* dbl
* u8p
* s16p
* s32p
* fltp
* dblp
* s64
* s64p

---

```scala
def removeElement(element: String): List[String]
```
Removes a whole type of element of the input media, such as the video channels, audio channels or subtitle channels.

#### Values:
* "video" - removes video
* "audio" - removes audio
* "subtitle" - removes subtitles

---

```scala
def mapChannel(media: String, input: Byte, channel: Byte): List[String]
```
Maps the channels of the inputs.

Media is the channel type you want to map.

Input can be a value of 0 or higher.

Channel can be a value of -1 or higher. -1 means all channels of the input.

#### Media types:
* "video" - maps video
* "audio" - maps audio
* "subtitle" - maps subtitles

Example: ```mapChannel("video", 0, 0)``` grabs the first video channel of the first file input. ```mapChannel("audio", 0, -1)``` grabs all audio channels of the first file input.

---

```scala
def setDuration(seconds: Float): List[String]
```
Sets the duration of the media encode/playback. This is compatible with both FFmpeg and FFplay. The float value represents the point in time in seconds.

---

```scala
def getScreenshot(input: String, output: String, time: String, quiet: Boolean = true, exec = "ffmpeg")
```
Captures a frame from a video or image sequence.

Time can be formatted like this: ```hh:mm:ss```

Or this: ```15s```
