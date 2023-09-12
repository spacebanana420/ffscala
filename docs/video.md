
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
def x264_setCoder(coder: String): String
```
Sets the x264 coder.

#### Supported coders:
* default
* cavlc
* cabac
* vlc
* ac

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
def nvenc_setqp(value: Byte): String
```
Sets constant quantization bitrate control and its value for NVENC.

Values between 1 and 51 are supported. Lower means more information/quality and file size.

```scala
def nvenc_setcrf(value: Byte): String
```
Sets CRF encoding for NVENC.

Values between 0 and 51 are supported. Lower means more information/quality and file size.

For CRF, 0 means lossless, but I'm unsure if that's true for NVENC. If you want lossless compression on NVENC, use ```nvenc_setPreset("lossless")```

```scala
def dnxhd_setPreset(preset: String): String
```
Sets the DNxHD/DNxHR encoding preset.

dnxhr_hq is recommended for most cases.

#### Supported presets:
* dnxhd
* dnxhr_lb
* dnxhr_sq
* dnxhr_hq
* dnxhr_hqx
* dnxhr_444

```scala
def prores_setProfile(preset: String): String
```
Sets the ProRes encoding profile.

hq is recommended for most cases.

#### Supported presets:
* proxy
* lt
* standard
* hq
* 4444
* 4444qx

```scala
def prores_setAlphaDepth(depth: Byte): String
```
Sets the bit depth for the alpha channel when encoding using ProRes.

Values between 0 and 16 are supported. Use 0 to disable the alpha channel.

```scala
def vp9_setDeadline(preset: String): String
```
Sets the deadline for VP9, which will define the compression efficiency as well as its speed.

#### Supported presets:
* best
* good
* realtime

```scala
def vp9_setcpu_used(value: Byte): String
```
Sets the cpu-used value for VP9, which will fine-tune the compression efficiency further.

Values between -8 and 8 are accepted.

```scala
def vp9_setRowMT(): String
```
Enables row-based multi-threading for VP9.

```scala
def vp9_setLossless(): String
```
Sets lossless encoding for VP9.

```scala
def av1_setRowMT(): String
```
Enables row-based multi-threading for AV1.

```scala
def av1_stillPicture(): String
```
Enables single-frame mode for AV1. Useful for encoding AVIF images.

```scala
def av1_setDeadline(preset: String): String
```
Sets the deadline for AV1, which will define the compression efficiency as well as its speed.

#### Supported presets:
* good
* realtime
* allintra

```scala
def av1_setcpu_used(value: Byte): String
```
Sets the cpu-used value for AV1, which will fine-tune the compression efficiency further.

Values between 0 and 8 are accepted.

```scala
def cfhd_setQuality(value: Int): String
```
Sets the encoding quality for Cineform. This affects the bitrate used.

It must be a value between 0 and 12.

Higher value = lower bitrate and quality.

```scala
def utvideo_setPred(pred: String): String
```
Sets the prediction method for Utvideo.

#### Supported methods:
* none
* left
* gradient
* median

```scala
def png_setPred(mode: String): String
```
Sets the prediction method.

#### Supported methods:
* none
* sub
* up
* avg
* paeth
* mixed

```scala
def tiff_setCompression(compression: String): String
```
Sets the compression algorithm for the tiff encoder.

#### Supported values:
* packbits
* raw
* lzw
* deflate

```scala
def webp_setLossless(): String
```
Sets lossless compression for webp.

```scala
def webp_setQuality(value: Byte): String
```
Sets the quality value for webp encoding.

Values between 0 and 100 are accepted

```scala
def webp_setPreset(preset: String): String
```
Sets the x264/x265 encoding preset.

Veryfast and superfast are recommended for most use cases

#### Supported presets:
* none
* default
* picture
* photo
* drawing
* icon
* text
