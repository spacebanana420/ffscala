These are the base argument functions of FFscala. These arguments are commonly used in FFmpeg, as well as encoder-specific parameters.

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
* cinepak
* x264
* x264rgb
* x265
* nvenc
* nvenc265
* qsv
* qsv265
* vaapi
* utvideo
* huffyuv
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

Not all encoders support CRF bitrate control, but many do, such as x264, x265, VP9 and AV1.

---

```scala
def setQuality(q: Short): List[String]
```
Sets the general setting for video/image quality. Minimum value is 1. Higher values mean lower quality and lower file sizes.

This setting is usually useful for JPG/MJPEG. Not all encoders use this parameter

---

```scala
def setPixFmt(newfmt: String): List[String]
```
Sets the video/image's pixel/color encoding format.

If unspecified, it will use the input data's original pixel format.

#### Supported pixel formats:
* rgb24
* bgr24
* bgr0
* gbrp
* gbrp10le
* gbrp12le
* bgra
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
* vuyx
* y210le
* yuyv422
* p010le
* p012le
* x2rgb10le
* xv30le

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
Sets the audio encoder.

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
Sets the audio's sample format.

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
def mapChannel(mode: String, input: Byte, channel: Byte): List[String]
```
Maps the channels of the inputs.

Media is the channel type you want to map.

Input can be a value of 0 or higher.

Channel can be a value of -1 or higher. -1 means all channels of the input.

#### Mode types:
* "video" - maps video
* "audio" - maps audio
* "subtitle" - maps subtitles

Example: ```mapChannel("video", 0, 0)``` grabs the first video channel of the first file input. ```mapChannel("audio", 0, -1)``` grabs all audio channels of the first file input.

---

```scala
def setDuration(seconds: Float): List[String]
```
Sets the duration of the media encode/playback. This is compatible with both FFmpeg and FFplay. The float value represents the duration in seconds.
