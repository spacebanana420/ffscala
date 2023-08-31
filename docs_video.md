
```scala
def x264_setPreset(preset: String): String
```
Sets the x264/x265 encoding preset.

Veryfast and superfast are recommended for most use cases

#### Supported presets:
* ultrafast
* superfast
* veryfast
* faster
* fast
* medium
* slow
* slower
* veryslow
* placebo

```scala
def nvenc_setPreset(preset: String): String
```
Sets the NVENC encoding preset.

#### Supported presets:
* lossless
* default
* slow
* medium
* fast
* hq

```scala
def dnxhd_setPreset(preset: String): String
```
Sets the DNxHD/DNxHR preset.

dnxhr_hq is recommended for most cases.

#### Supported presets:
* dnxhd
* dnxhr_lb
* dnxhr_sq
* dnxhr_hq
* dnxhr_hqx
* dnxhr_444

```scala
def vp9_setDeadline(preset: String): String
```
Sets the deadline for VP9, which will define the compression efficiency as well as its speed.

#### Supported presets:
* best
* good
* realtime

```scala
def vp9_setLossless(): String
```
Sets lossless encoding for VP9.

```scala
def cfhd_setQuality(value: Int): String
```
Sets the encoding quality for Cineform. This affects the bitrate used

It must be a value between 0 and 12

Higher value = lower bitrate and quality
