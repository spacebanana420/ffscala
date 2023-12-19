This file contains the documentation for the functions related to filtering and processing media. You can use these alongside other encoding parameter arguments whenever encoding media.

---

```scala
def scale(width: Int, height: Int): List[String]
```
Sets the video/image's width and height resolution in pixels.

```scala
def scaleFactor(width: Float, height: Float): List[String]
```
Sets the video/image's resolution factor.

For example, a width of 2 and a height of 2 will correspond to 2x the input's width and height

```scala
def setScaleFilter(filter: String): List[String]
```
Sets the video's scaling filter in case you use ```setVideoResolution```

If unspecified, it will default to bicubic

#### Supported scaling filters:
* bicubic
* bilinear
* neighbor
* lanczos

```scala
def normalizeAudio(): List[String]
```
Adds a filter to normalize the audio.

```scala
def changeVolume(volume: String): List[String]
```
Adjusts the media's volume.

You can set the volume percentage with "1.0", "0.5", "1.6", etc.
You can also increase or lower the volume in decibels: "4dB", "-7dB"

```scala
def crop(x: Int, y: Int, width: Int, height: Int): List[String]
```
Crops the video/image, with starting points X and Y

```scala
def cropCenter(width: Int, height: Int): List[String]
```
Applies a centered crop to the video/image

```scala
def cropToAspect(width: Byte, height: Byte): List[String]
```
Applies a centered crop based on the aspect ratio.

For now, the function does not support square aspect (1:1, 5:5, 45:45, etc).

Example: ```cropToAspect(2, 1)``` will crop with 2:1 aspect ratio
