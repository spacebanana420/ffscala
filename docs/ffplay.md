```scala
def execplay(input: String, args: List[String], quiet: Boolean = true, exec: String = "ffplay"): Int
```
Executes ffplay, similarly to ```execute``` command for ffmpeg, but without the unnecessasry restrictions.

```scala
def setPlayVolume(volume: Byte): List[String]
```
Sets the volume ffplay will start at. The supported volume range goes from 0 to 100.

```scala
def disableBorder(): List[String]
```
Disables window border.

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
