You can play any supported media with FFplay. These functions allow you to open and playback videos, images and audio and customize how it works.

---

```scala
def execplay(input: String, args: List[String], quiet: Boolean = true, exec: String = "ffplay"): Int
```
Executes FFplay, similarly to ```execute``` command for ffmpeg, but without the unnecessasry restrictions.

```scala
def setPlayVolume(volume: Byte): List[String]
```
Sets the volume ffplay will start at. The supported volume range goes from 0 to 100.

```scala
def disableBorder(): List[String]
```
Disables window border.

```scala
def disableDisplay(): List[String]
```
Disables the graphical FFplay display.

```scala
def setFullscreen(): List[String]
```
Sets fullscreen mode.

```scala
def setDimensions(x: Int, y: Int): List[String]
```
Sets the window size.

```scala
def setFrameDrop(): List[String]
```
Enables frame dropping to avoid desyncing the audio with the video.

```scala
def setLoop(times: Int): List[String]
```
Sets how many times ffplay loops the media. 0 means forever.

```scala
def setAutoExit(): List[String]
```
FFplay will exit after reaching the end of the media playback. This isn't recommended for image files (except animated GIFs), because FFplay will exit instantly.

```scala
def setExitOnMouse(): List[String]
```
FFscala stops media playback on any mouse press.

```scala
def setExitOnKey(): List[String]
```
FFscala stops media playback on any key press.

```scala
def setWindowTitle(title: String): List[String]
```
Sets the title of the window FFplay opens, in case it opens one.

```scala
def setShowMode(mode: String): List[String]
```
Sets what the FFplay graphical window displays.

Supported modes:
* video (default)
* waves
* rdft

```scala
def setSeekInterval(interval: Float): List[String]
```
Sets the seek interval for when you seek using the left and right arrow keys.
