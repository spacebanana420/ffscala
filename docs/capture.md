These functions handle screen and audio capture.

This part of FFscala is experimental, and support for your system can't be guaranteed. I can only guarantee it works under x11 Linux with Pulseaudio or Alsa.

Check the [FFmpeg device documentation](https://ffmpeg.org/ffmpeg-devices.html) for more information on how these capture backends work and what you can do with them.

### Current capture modes

* x11grab
  * (video) (x11)
* dshow
  * (video and audio) (Windows)
* gdigrab
  * (video) (Windows)
* pulse
  * (audio) (pulseaudio)
* alsa
  * (audio) (alsa)
* oss
  * (audio) (oss)
* jack
  * (audio) (jack)
* avfoundation
  * (video and audio) (MacOS)
* android_camera
  * (video) (Android)

x11, pulseaudio, alsa and oss are common backends among open source operating systems, especially systems of the Linux and BSD families. Wayland/pipewire screen capture is not yet available on FFmpeg, so Wayland users cannot make use of screen capture yet.

Remember to import ```ffscala.capture``` to use these functions.

For desktop and audio capture functionality, you need to give the functions the input device to capture. To know your input devices, check the function ```listSources()```.

---
# Recording functions

```scala
def record(
output: String, captureargs: List[String], args: List[String] = List(), filters: List[String] = List(),
hwaccel: String = "", quiet: Boolean = true, exec: String = "ffmpeg"): Int
```
This executes FFmpeg and begins the desktop recording. ```output``` is for the output file's path.

```captureargs``` should be the FFmpeg arguments generated by the functions seen here.

```args``` are the encoding arguments used in functions like ```encode()```.

```filters``` are the filter arguments used in functions like ```encode()```.

```hwaccel``` sets a hardware acceleration encoding mode that needs extra configuration, like Intel QuickSync. Note that, if you use qsv hwaccel, you must use the ```qsv``` encoder with the ```nv12``` or ```qsv``` pixel format. QSV is only supported by Intel GPUs.

Supported hwaccel values:
* qsv

To stop the recording, type Q. You can also stop the recording with ctrl + C, but this will also shut down your program. You can alternatively add a recording duration with ```setDuration()``` (see [Base doc](../docs/base.md)).

---

```scala
def takeScreenshot(
output: String, captureargs: List[String], args: List[String] = List(),
filters: List[String] = List(), quiet: Boolean = true, exec: String = "ffmpeg"): Int
```
Takes a screenshot and saves it at path ```output```. Behavior is identical to `record()`, but only 1 frame is captured.

```captureargs``` should be the FFmpeg arguments generated by the functions seen here.

```args``` are the encoding arguments used in functions like ```encode()```.

```filters``` are the filter arguments used in functions like ```encode()```.

---

```scala
def takeScreenshot_auto(mode: String, i: String, output: String, showmouse: Boolean = false,
args: List[String] = List(), filters: List[String] = List(),
quiet: Boolean = true, exec: String = "ffmpeg"): Int
```
Takes a screenshot and saves it at path ```output```. This function sets the screen/window capture arguments automatically based on the `mode` you choose, as a more convenient alternative to `takeScreenshot()`.

```showmouse``` tells to either include the mouse in the screen capture or not
Mode is the screen capture method.

All the encoding parameter arguments can be added through ```args```. This is very useful for most image formats that are not PNG and require some manual encoding configuration to suit well your necessities.

The ```i``` is input screen. This varies between capture modes.

Supported desktop capture modes:
* x11grab
* dshow
* dshow_video
* gdigrab

---

# Capture argument functions

```scala
def x11grab_captureVideo(input: String, fps: Int, showmouse: Boolean = true, width: Int = 0, height: Int = 0, x: Int = 0, y: Int = 0): List[String]
```
Uses x11grab for video capture, for any OS that uses an x11 server.

```input``` sets the display and screen to capture. Use "0.0" to use display 0 and screen 0, probably the ones you want to capture.

```fps``` is the capture frame rate.

```showmouse``` tells x11grab to whether show the mouse cursor in the capture.

```width``` and ```height``` set the dimensions of the screen capture. Default is the native size of the screen you are capturing, and lower values than that will crop.

The arguments ```x y``` set the position offset of the capture, starting at the top left.

---

```scala
def dshow_captureVideo(input: String, fps: Int, width: Int = 0, height: Int = 0): List[String]
```
Uses DirectShow for video capture, for Windows systems.

```input``` sets the input video device to capture. Check ```listSources()``` to know which you have in your system.

```fps``` is the capture frame rate.

```width``` and ```height``` set the dimensions of the screen capture. Default is the native size of the screen you are capturing, and lower values than that will crop.

---

```scala
def gdigrab_captureVideo(fps: Int, showmouse: Boolean = true, input: String = "", width: Int = 0, height: Int = 0, x: Int = 0, y: Int = 0): List[String]
```
Uses Gdigrab for video capture, for Windows systems.

```fps``` is the capture frame rate.

```showmouse``` tells Gdigrab to whether show the mouse cursor in the capture.

```input``` sets the input video device to capture. By default it is set to "", which will capture the whole screen (equivalent to "desktop"). Set it to the name of a program window to capture that window instead.

```width``` and ```height``` set the dimensions of the screen capture. Default is the native size of the screen/window you are capturing, and lower values than that will crop.

The arguments ```x y``` set the position offset of the capture, starting at the top left.

---

```scala
def dshow_captureAudio(input: String, samplerate: Int, channels: Byte, depth: Byte): List[String]
```
Uses DirectShow for audio capture, for Windows systems.

```input``` sets the input audio device to capture. Check ```listSources()``` to know which you have in your system.

```sampleraate``` is the audio sample rate. Minimum value accepted is 1.

```channels``` sets the amount of audio channels. Minimum value accepted is 1.

```depth``` sets the audio bit depth. Minimum value accepted is 8.

---

```scala
def avfoundation_capture(vinput: String, ainput: String, fps: Int, showmouse: Boolean = true, width: Int = 0, height: Int = 0): List[String]
```
Uses Avfoundation for video capture, for MacOS systems.

```vinput``` and ```ainput``` are respectively the input devices for your video and audio capture. Use index numbers, starting from 0. Leave them at "" to use the system's defaults. Set them to "none" to disable their capture.

```fps``` is the capture frame rate.

```showmouse``` tells Avfoundation to whether show the mouse cursor in the capture.

```width``` and ```height``` set the dimensions of the screen capture. Default is the native size of the screen/window you are capturing, and lower values than that will crop.

---

```scala
def captureVideo(mode: String, i: String, fps: Int): List[String]
```
Returns the configuration to capture your screen. It's preferred you use the functions above instead, as they are specifically tailored for their respective capture backend, while this function is more generic and supports less features.

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
def pulse_captureAudio(input: String, samplerate: Int = 0, channels: Byte = 0): List[String]
```
```scala
def alsa_captureAudio(input: String, samplerate: Int = 0, channels: Byte = 0): List[String]
```
```scala
def oss_captureAudio(input: String, samplerate: Int = 0, channels: Byte = 0): List[String]
```
```scala
def jack_captureAudio(input: String, channels: Byte = 0): List[String]
```
Use pulseaudio, alsa, oss or jack to capture audio.

```input``` is the input audio device.

```samplerate``` and ```channels``` are the audio sample rate and the amount of audio channels, respectively. For both arguments, if the value is 0 or lower, FFmpeg assumes the sample rate and channel count.

The jack function doesn't take a sample rate argument.

---

```scala
def captureAudio(mode: String, input: String, ch: Byte = 2, rate: Int = 48000): List[String]
```
Returns the configuration to capture audio. Just like ```captureVideo()```, it's preferred you use the functions above instead, as they are specifically tailored for their respective capture backend, while this function is more generic and supports less features.

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
def androidcamera_capture(input: Byte, fps: Int, width: Int, height: Int): List[String]
```
Uses android_camera to capture the video of your android phone's cameras.

```input``` Is an index number. 0 tends to be your main back-facing camera.

```fps``` is the capture frame rate.


```width``` and ```height``` set the resolution of the capture. If the given dimensions are not available in your Android camera, then it will fallback to the default setting.

---

# Other functions

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

```full``` is set to false by default, which means the retrieved audio source strings are the source IDs that can be used directly on audio capture backends. If it's set to ```true```, the full name of the source is displayed. This won't happen if mode is ```dshow``` or ```avfoundation```.

Supported:
* alsa
* pulse
* oss
* dshow_video
* dshow_audio
* dshow
* avfoundation
* all

For ```all```, all sources that can be listed with ```ffmpeg -sources``` will be listed and not parsed or processed.

---

```scala
def listSources_OSS(): Array[String]
```
Lists all available devices for the OSS audio system. Use this instead of ```listSources()``` if it works better for you.
