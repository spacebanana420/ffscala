```scala
def setVideoResolution(width: Int, height: Int): String
```
Sets the video/image's width and height resolution in pixels.

```scala
def setVideoResolution_factor(width: Float, height: Float): String
```
Sets the video/image's resolution factor.

For example, a width of 2 and a height of 2 will correspond to 2x the input's width and height

```scala
def setScaleFilter(filter: String): String
```
Sets the video's scaling filter in case you use ```setVideoResolution```

If unspecified, it will default to bicubic

#### Supported scaling filters:
* bicubic
* bilinear
* neighbor
* lanczos

```scala
def normalizeAudio(): String
```
Adds a filter to normalize the audio.

```scala
def changeVolume(volume: String): String
```
Adjusts the media's volume.

You can set the volume percentage with "1.0", "0.5", "1.6", etc.
You can also increase or lower the volume in decibels: "4dB", "-7dB"

```scala
def crop(x: Int, y: Int, width: Int, height: Int): String
```
Crops the video/image, with starting points X and Y

```scala
def cropCenter(width: Int, height: Int): String
```
Applies a centered crop to the video/image
