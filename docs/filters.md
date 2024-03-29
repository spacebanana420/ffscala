This file contains the documentation for the functions related to filtering and processing media. You can use these alongside other encoding parameter arguments whenever encoding media.

Unlike all other encoding and video arguments, filter arguments must be passed to a different argument in the ```encode()``` function.

For more information on FFmpeg filters, see [here](https://ffmpeg.org/ffmpeg-filters.html)

---

# Audio Filters

```scala
def normalizeAudio(): List[String]
```
Adds a filter to normalize the audio.

---

```scala
def changeVolume(volume: String): List[String]
```
Adjusts the media's volume.

You can set the volume percentage with "1.0", "0.5", "1.6", etc.
You can also increase or lower the volume in decibels: "4dB", "-7dB"

---

```scala
def audioContrast(contrast: Byte): List[String]
```

Sets the contrast of the audio's dynamic range.

Default value is 33.

Minimum and maximum values supported are 0 and 100.

---

```scala
def crystalize(i: Float, clip: Boolean = true): List[String]
```
Sharpens the audio's noise (or applies the inverse effect). Use a negative value for "i" to invert the effect.

Intensiy of the filter "i" ranges from values -10 to 10. 0 is neutral.

Clip toggles audio clipping.

---

```scala
def vibrato(f: Float, d: Float): List[String]
```
Adds a vibrato filter.

"f" sets the modulation frequency. Values can range from 0.1Hz to 20000Hz.

"d" sets the depth modulation percentage. Values can range from 0.0 to 1.0

---

```scala
def equalizer(frequency: Int, gain: Short, width: Int = 1, wtype: String = "q"): List[String]
```
Applies an equalizer on a certain frequency center and range. This filter only focuses on 1 center frequency. To equalize multiple points, use the filter multiple times.

```frequency``` sets the center frequency. Accepted values are from 0 to 999999.

```gain``` sets the boost/atenuation in decibels. Accepted values are from -900 to -900.

```width``` sets the width that affects the surrounding frequencies to the center ```frequency```. Accepted values are from 0 to 999999.

```wtype``` sets the type of width measurement.

#### Supported width types:

* h (Hz)
* q (Q-factor)
* o (octave)
* s (slope)
* k (kHz)

---

# Video Filters

```scala
def scale(width: Int, height: Int, filter: String = ""): List[String]
```
Sets the output video/image's width and height resolution in pixels.

You can set a specific scale filter, though if unspecified it will default to bicubic.

#### Supported scaling filters:
* bicubic
* bilinear
* neighbor
* lanczos

---

```scala
def scaleFactor(width: Float, height: Float): List[String]
```
Sets the video/image's resolution factor.

For example, a width of 2 and a height of 2 will correspond to 2x the input's width and height

---

```scala
def setScaleFilter(filter: String): List[String]
```
Sets the video's scaling filter separately in case you use ```scale()```.

If unspecified, it will default to bicubic.

#### Supported scaling filters:
* bicubic
* bilinear
* neighbor
* lanczos

---

```scala
def crop(x: Int, y: Int, width: Int, height: Int): List[String]
```
Crops the video/image, with starting points X and Y

---

```scala
def cropCenter(width: Int, height: Int): List[String]
```
Applies a centered crop to the video/image

---

```scala
def cropToAspect(width: Byte, height: Byte): List[String]
```
Applies a centered crop based on the aspect ratio.

For now, the function does not support square aspect (1:1, 5:5, 45:45, etc).

Example: ```cropToAspect(2, 1)``` will crop with 2:1 aspect ratio

---

```scala
def cropAndScale(crop_w: Int, crop_h: Int, scale_w: Int, scale_h: Int): List[String]
```
Crops the video/image and then scales it. The cropped result is centered.

```crop_w``` and ```crop_h``` set the dimensions for cropping.

```scale_w``` and ```scale_h``` set the dimensions for scaling.

This function is merely for convenience. This results in the same as using ```crop()``` and ```scale()``` by this order.

---

```scala
def setCurves(x: List[Float], y: List[Float], channel: String = "all"): List[String]
```
Uses the curves filter for manipulating color, brightness and contrast smoothly for all channels or individually red, green or blue. The argument lists must have the same length and the list "x"  s values must be in ascending order, for example,```x[0]``` can have a value of 0 or 0.3 or any value, but then ```x[1]``` must have a higher value.

The elements in "x" represent the brightness values of the input image, while the elements in y represent the brightness values of the output image. If ```x[0]``` is ```0.2``` and ```y[0]``` is ```0.3```, then the output image's pixels of brightness 20% will have the brightness raised to 30%.

#### Example of using curves to raise the contrast of the image

```scala
val x = List(0.2f, 0.5f, 0.8f)
val y = List(0.1f, 0.5f, 0.9f)

val args = setCurves(x, y)

encode("image.png", "image_contrast.png", filters = args)
```

In this example, shadows are darkened, the middle gray point is kept intact, and highlights are brightened.

Instead of modifying all color channels, you can instead modify just the red, green or blue channel.

#### Supported channels:
* all
* red (or r)
* green (or g)
* blue (or b)

---

```scala
def setFade(mode: String = "in", start: Long, duration: Long): List[String]
```

Sets a fade into or from black to the video.

```mode``` tells whether it's a fade-in or a fade-out. The supported modes are ```in``` and ```out```.

```start``` is the position in frames of where the fade starts.

```duration``` is the duration in frames of the fade.

---

```scala
def colorize(h: Short, s: Float = 0.5, l: Float = 0.5, mix: Float = 1): List[String]
```
Colorizes the video/image.

"h" is the hue to use for colorizing. Values range from 0 to 360.

"s" is the saturation of the chosen hue. Values range from 0 to 1.

"l" is the lightness of the chosen hue. Values range from 0 to 1.

"mix" is the intensity of the filter. Values range from 0 to 1. 1 means it's fully colorized, 0 means the filter doesn't change anything.

---

```scala
def pixelize(w: Int, h: Int, mode: String): List[String]
```
Pixelizes the video, with resolution "w" and "h". If "w" or "h" are equal or below to 0, they default to 16.

Supported modes:
* avg
* min
* max

---

```scala
def unsharp(lx: Byte, ly: Byte, la: Float, cx: Byte, cy: Byte, ca: Float, ax: Byte, ay: Byte, aa: Float): List[String]
```
Sharpens or blurs the image/video.

All arguments that start with "l", "c" and "a" are for luma, chroma and alpha respectively.

Arguments that end with "x" and "y" define the sharpening radius respectively.

Arguments that end with "a" define the intensity of the sharpening for the channel in question.

Supported radius values are between 3 and 23, with 5 being default. Supported intensity values are between -2 to 5, with 1 being default

---

```scala
def trim(start: Long, end: Long): List[String]
```
Trims the video.

```start``` and ```end``` are the start and end of the video, in seconds.
