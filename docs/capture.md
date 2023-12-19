These functions handle screen and audio capture. Currently only Linux is supported and tested, with x11grab for screen capture and pulse/alsa for audio capture.

---

```scala
def record(name: String, dir: String, args: List[String], quiet: Boolean = true, exec: String = "ffmpeg"): Int
```
This executes FFmpeg and begins the desktop recording. The name is for the output file, dir is for where to save the file.

Args can be a mix of the argument functions seen here and encoder/filter argument functions.

```scala
def captureVideo(mode: String, i: String, fps: Int, showmouse: Boolean = true, x: Int = 0, y: Int = 0): List[String]
```
Returns the configuration to capture your screen.
"fps" sets the frame rate for the video capture

"showmouse" tells to either include the mouse in the screen capture or not
Mode is the screen capture method. Right now only x11 is supported.

The "i" is input screen. This varies between capture modes.

You can specify a positive offset for the screen's position with x and y

Currently, there is only 1 supported backend mode:
* x11grab

```scala
def captureAudio(mode: String, input: String, ch: Byte = 2, rate: Int = 48000): List[String]
```
Returns the configuration to capture audio.

Mode is the screen capture method. Right now only alsa or pulse are supported.

The "input" is input audio device. This varies between capture modes.

"ch" is the channel count. By default, the capture is set to stereo with 2 channels, you can change this if you want.
"rate" Is the audio sample rate, defaults to 48000.

Supported:
* alsa
* pulse

```scala
def addTracks(amt: Int): List[String]
```
This maps multiple tracks to be included in the final file. If you capture multiple tracks of the same type (video, audio), you need to use this function so the tracks are properly set up.

Let's imagine you want to record your desktop screen, your desktop audio and your microphone audio. Since you are capturing 2 audio sources, being of the same type, you need to add 3 tracks with ```addTracks(3)```.

```scala
def listAudioSources(mode: String, full: Boolean = false, exec: String = "ffmpeg"): List[String]
```
This function calls FFmpeg to retrieve the currently available audio sources from the "mode" audio server.

"full" is set to false by default, which means the retrieved audio source strings are the source IDs that can be used directly on ```captureAudio```. If it's set to true, the full name of the source is displayed.
