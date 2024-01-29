These functions handle screen and audio capture.

This part of FFscala is experimental, and support for your system can't be guaranteed. I can only guarantee it works under x11 Linux with Pulseaudio or Alsa.

### Current capture modes

* x11grab       (video)             (x11)
* dshow         (video and audio)   (Windows)
* gdigrab       (video)             (Windows)
* pulse         (audio)             (pulseaudio)
* alsa          (audio)             (alsa)
* oss           (audio)             (oss)
* avfoundation  (video and audio)   (MacOS)

x11, pulseaudio, alsa and oss are common backends among open source operating systems, especially systems of the Linux and BSD families. Wayland/pipewire screen capture is not yet available on FFmpeg, so Wayland users cannot make use of screen capture yet.

Remember to import ```ffscala.capture``` to use these functions.

For desktop and audio capture functionality, you need to give the functions the input device to capture. To know your input devices, check the function ```listSources()```.

---

```scala
def record(output: String, args: List[String], quiet: Boolean = true, exec: String = "ffmpeg"): Int
```
This executes FFmpeg and begins the desktop recording. ```output``` is for the output file's path.

```args``` can be a mix of the argument functions seen here and encoder/filter argument functions.

To stop the recording, press ctrl + C. You can alternatively add a recording duration with ```setDuration()``` (see [FFmpeg doc](../docs/ffmpeg.md)).

---

```scala
def captureVideo(mode: String, i: String, fps: Int): List[String]
```
Returns the configuration to capture your screen.
```fps``` sets the frame rate for the video capture

The ```i``` is input screen. This varies between capture modes.

You can specify a positive offset for the screen's position with x and y

Supported desktop capture modes:
* x11grab
* dshow
* gdigrab

For x11grab, the default input is "0.0", which will capture the whole main screen.

---

```scala
def captureAudio(mode: String, input: String, ch: Byte = 2, rate: Int = 48000): List[String]
```
Returns the configuration to capture audio.

Mode is the screen capture method. Right now only alsa or pulse are supported.

The ```input``` is input audio device. This varies between capture modes.

```ch``` is the channel count. By default, the capture is set to stereo with 2 channels, you can change this if you want.
```rate``` is the audio sample rate, defaults to 48000.

Supported:
* alsa
* pulse
* oss
* dshow

For Linux, it's recommended to use ```pulse```.

---

```scala
def addTracks(amt: Int): List[String]
```
This maps multiple tracks to be included in the final file. If you capture multiple tracks of the same type (video, audio), you need to use this function so the tracks are properly set up.

Let's imagine you want to record your desktop screen, your desktop audio and your microphone audio. Since you are capturing 2 audio sources, being of the same type, you need to add 3 tracks with ```addTracks(3)```.

---

```scala
def listSources(mode: String, full: Boolean = false, exec: String = "ffmpeg"): List[String]
```
This function calls FFmpeg to retrieve the currently available audio sources from the ```mode``` capture method.

```full``` is set to false by default, which means the retrieved audio source strings are the source IDs that can be used directly on ```captureAudio```. If it's set to true, the full name of the source is displayed. If the given mode is either ```dshow``` or ```avfoundation```, the full names will be given as if full was true.

Supported:
* alsa
* pulse
* oss
* dshow
* avfoundation
* all

For ```all```, all sources that can be listed with ```ffmpeg -sources``` will be listed and not parsed or processed.

---

```scala
def takeScreenshot(mode: String, i: String, output: String, showmouse: Boolean = false, args: List[String] = List(), quiet: Boolean = true, exec: String = "ffmpeg"): Int
```
Takes a screenshot of your screen and saves it as a png file. ```output``` is the path to the screenshot, it can just be the file name if you want to save the image in the current directory.

The image file format is assumed by the extension. If an unsupported or unknown extension is used, FFscala will default to PNG.

```showmouse``` tells to either include the mouse in the screen capture or not
Mode is the screen capture method.

All the encoding parameter arguments can be added through ```args```. This is very useful for most image formats that are not PNG and require some manual encoding configuration to suit well your necessities.

Right now only x11 is supported.

The ```i``` is input screen. This varies between capture modes.

You can specify a positive offset for the screen's position with x and y

Supported desktop capture modes:
* x11grab
* dshow
