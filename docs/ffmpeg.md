These are the main functions for executing FFmpeg to encode media.

For FFmpeg arguments, see the [base arguments](base.md), the [video encoder arguments](video.md) and the [audio encoder arguments](audio.md).

For the filter arguments, see [here](filers.md)

---

```scala
def checkFFmpeg(path: String = "ffmpeg"): Boolean
```
Returns ```true``` if the path points to a working FFmpeg executable, otherwise ```false```.

---

```scala
def encode
(
input: String, output: String, args: List[String] = List(), filters: List[String] = List(),
hwaccel: String = "", quiet: Boolean = true, exec: String = "ffmpeg"
): Int
```
Executes FFmpeg to encode the input file.

```input``` should be the absolute or relative path to the file you want to process.

```output``` should be the destination path for the final file, including filename and extension.

```args``` are the FFmpeg encoding arguments you get through most functions, except for filter functions.

```filters``` is the argument where you pass filters to. They cannot be passed to ```args``` because they are handled differently.

```hwaccel``` sets a hardware acceleration encoding mode that needs extra configuration, like Intel QuickSync. Note that, if you use qsv hwaccel, you must use the ```qsv``` encoder with the ```nv12``` or ```qsv``` pixel format. QSV is only supported by Intel GPUs.

```quiet``` tells FFmpeg whether or not it should print information to stdout. By default, quiet is true, and FFmpeg won't output anything.

```exec``` is the path to your FFmpeg executable. By default, it is set to "ffmpeg", assuming you have FFmpeg in your PATH. If you do not have it in your PATH, or if you want to package your software with the binary or just use a custom path, you can specify the path yourself.

---

```scala
def getScreenshot(input: String, output: String, time: String, quiet: Boolean = true, exec = "ffmpeg")
```
Captures a frame from a video or image sequence.

Time can be formatted like this: ```hh:mm:ss```

Or this: ```15s```

---

```scala
def extractFrames
(
input: String, fmt: String, start: Int = 0, amt: Int = 0, args: List[String] = List(),
quiet: Boolean = true, exec: String = "ffmpeg"
): Int
```
Extracts the frames of the input video ```input```.

```fmt``` is the image file extension to use. The image format is assumed by the extension. If the extension prompted isn't supported, the function will not execute FFmpeg and will return -1.

```start``` sets where to start at the video to extract frames, in seconds. If the value is equal or less than 0, then the start of the video is used.

```amt``` is the amount of frames to extract from the start onwards. If the value is equal or less than 0, all frames of the video are extracted.

```args``` are used for any encoding arguments, except filters.

```quiet``` and ```exec``` are the same as in ```encode()```.

---

```scala
def combineMedia
(
output: String,
video: List[String] = List(), audio: List[String] = List(), subs: List[String] = List(),
quiet: Boolean = true, exec: String = "ffmpeg"
): Int
```
Combines the media tracks from multiple files into one, without converting or modifying the data.

```output``` is the output media path. Remember to use a supported file format. If you just want to mix a video and audio source, you can use any output video. If you want to just mix audio, remember to set the output to an audio file.

The lists ```video```, ```audio``` and ```subs``` are the list of input files. The video, audio, and subtitle channels will be merged into the final video.

```quiet``` and ```exec``` are the same as in ```encode()```.

---

# Supported formats

FFscala's ```encode()``` function supports the following file extensions:

#### Image
* png
* ppm
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
* avif

#### Audio
* flac
* wav
* ogg
* opus
* m4a
* mp2
* mp3
* aiff
* ac3

#### Video
* mp4
* mov
* m4v
* avi
* mkv
* webm
* mpeg
* h264
* hevc
* dnxhd

If the encoder is unspecified in the arguments list, the default encoder respective to the file extension is used.
