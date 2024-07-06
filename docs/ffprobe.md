You can collect and parse information about media files with these functions, such as resolution, framerate, bitrate, pixel format, encoding format, etc.

Just like with other execution functions (such as ```encode()```), ```exec``` determines the name or the path to the FFprobe program, defaulting to "ffprobe".

---

```scala
def getVideoInfo(path: String, exec: String = "ffprobe"): List[String]
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

---

```scala
def getImageInfo(path: String, exec: String = "ffprobe"): List[String]
```
Returns a list containing information of the image.

The order of the list is as follows:
* Encoding format
* Width
* Height
* Pixel format

---

```scala
def getAudioInfo(path: String, exec: String = "ffprobe"): List[String]
```
Returns a list containing information of the audio

The order of the list is as follows:
* Encoding format
* Bit depth
* Sample rate
* Number of channels
* Channel layout
* Bitrate (in bits)

---

```scala
def getResolution(path: String, exec: String = "ffprobe"): List[Int]
```
Gets the resolution of each media channel, returning a list with the width and height. If an error occurs when retrieving the resolution, such as when attempting to get it from an audio file, the function returns the list ```List[Int](0, 0)```.

---

```scala
def getCodec(path: String, exec: String = "ffprobe"): List[String]
```
Gets the codec names of the file's media channels.

---

```scala
def getBitrate(path: String, exec: String = "ffprobe"): List[Long]
```
Gets the bitrate of each media channel, measured in bits/second. If an error occurs when retrieving the resolution, the function returns the list ```List[Long](0)```.

---

```scala
def showEntries(path: String, entries: Seq[String], exec: String = "ffprobe"): List[String]
```
Obtains a set of entries of your choice from the file `path`.

If you are unsure what kind of entries you can prompt, try using `getFullInfo()` first and see what's available for the media file in question.

---

```scala
def getFullInfo(path: String, exec: String = "ffprobe"): String
```
Returns the ffprobe output, unparsed.

Use this if you want to parse the media information yourself.

