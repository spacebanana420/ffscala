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
