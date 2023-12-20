You can collect and parse information about media files with these functions.

These functions currently require "ffprobe" to be in your PATH environment variable, they don't let you set a custom path or name for the executable. I will change this in the future.

---

```scala
def getVideoInfo(path: String): List[String]
```
Returns a list containing information of the video.

The order of the list is as follows:
* Encoding format
* Width
* Height
* Pixel format
* Frame rate
* Bitrate (in bits)
* Profile

```scala
def getImageInfo(path: String): List[String]
```
Returns a list containing information of the image.

The order of the list is as follows:
* Encoding format
* Width
* Height
* Pixel format

```scala
def getAudioInfo(path: String): List[String]
```
Returns a list containing information of the audio

The order of the list is as follows:
* Encoding format
* Bit depth
* Sample rate
* Number of channels
* Channel layout
* Bitrate (in bits)

```scala
def getResolution(path: String): List[Int]
```
Gets the resolution of each media channel, returning a list with the width and height. If an error occurs when retrieving the resolution, such as when attempting to get it from an audio file, the function returns the list ```List[Int](0, 0)```.

```scala
def getCodec(path: String): List[String]
```
Gets the codec names of the file's media channels.

```scala
def getBitrate(path: String): List[Long]
```
Gets the bitrate of each media channel, measured in bits/second. If an error occurs when retrieving the resolution, the function returns the list ```List[Long](0)```.

```scala
def getFullInfo(path: String): String
```
Returns the ffprobe output, unparsed.

Use this if you want to parse the media information yourself.

