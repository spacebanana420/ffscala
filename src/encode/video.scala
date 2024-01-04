package ffscala

import ffscala.misc.*

//Encoder-specific parameters and configuration

def x264_setPreset(preset: String): List[String] =
  val presets = List("ultrafast", "superfast", "veryfast", "faster", "fast", "medium", "slow", "slower", "veryslow", "placebo")
  if belongsToList(preset, presets) then
    List("-preset:v", preset)
  else
    List("-preset:v", "veryfast")

def x264_setCoder(coder: String): List[String] =
  val coders = List("default", "cavlc", "cabac", "vlc", "ac")
  if belongsToList(coder, coders) then
    List("-coder", coder)
  else
    List()

def x264_motionEstimation(mode: String): List[String] =
  val modes = List("dia", "hex", "umh", "esa", "tesa")
  if belongsToList(mode, modes) then
    List("-motion-est", mode)
  else
    List("-motion-est", "-1")

def x264_directPrediction(mode: String): List[String] =
  val modes = List("none", "spatial", "temporal", "auto")
  if belongsToList(mode, modes) then
    List("-motion-est", mode)
  else
    List("-motion-est", "-1")

def nvenc_setPreset(preset: String): List[String] =
  val presets = List("lossless", "default", "slow", "medium", "fast", "hq")
  if belongsToList(preset, presets) then
    List("-preset:v", preset)
  else
    List()

def nvenc_setqp(value: Byte): List[String] =
  if value < 1 || value > 51 then
    List()
  else
    List("-rc", "constqp", "-qp", value.toString)

def nvenc_setcrf(value: Byte): List[String] =
  if value < 0 || value > 51 then
    List()
  else
    List("-rc", "vbr_hq", "-qmin", "0", "-cq", value.toString)

def qsv_setPreset(preset: String): List[String] =
  val presets = List("veryfast", "faster", "fast", "medium", "slow", "slower", "veryslow")
  if belongsToList(preset, presets) then
    List("-preset:v", preset)
  else
    List("-preset:v", "0")

def qsv_setProfile(profile: String): List[String] =
  val profiles = List("unknown", "baseline", "main", "high")
  if belongsToList(profile, profiles) then
    List("-profile", profile)
  else
    List("-profile", "unknown")

def qsv_lowPower(): List[String] = List("-low_power", "true")

def dnxhd_setPreset(preset: String): List[String] =
  val presets = List("dnxhd", "dnxhr_lb", "dnxhr_sq", "dnxhr_hq", "dnxhr_hqx", "dnxhr_444")
  if belongsToList(preset, presets) then
    List("-profile:v", preset)
  else
    List()

def prores_setProfile(preset: String): List[String] =
  val profiles = List("proxy", "lt", "standard", "hq", "4444", "4444xq")
  if belongsToList(preset, profiles) then
    List("-profile:v", preset)
  else
    List()

def prores_setAlphaDepth(depth: Byte): List[String] =
  if depth < 0 || depth > 16 then
    List()
  else
    List("-alpha_bits", depth.toString)

def vp9_setDeadline(preset: String): List[String] =
  val presets = List("best", "good", "realtime")
  if belongsToList(preset, presets) then
    List("-deadline", preset)
  else
    List()

def vp9_setcpu_used(value: Byte): List[String] =
  if value >= -8 && value <= 8 then
    List("-cpu-used", value.toString)
  else
    List()

def vp9_setLossless(): List[String] = List("-lossless")

def setRowMT(): List[String] = List("-row-mt", "true")

def av1_stillPicture(): List[String] = List("-still-picture", "true")

def av1_setcpu_used(value: Byte): List[String] =
  if value >= 0 && value <= 8 then
    List("-cpu-used", value.toString)
  else
    List()

def av1_setDeadline(preset: String): List[String] =
  val presets = List("good", "realtime", "allintra")
  if belongsToList(preset, presets) then
    List("-usage", preset)
  else
    List()

def av1_setTune(tune: String): List[String] =
  if tune == "psnr" || tune == "ssim" then
    List("-tune", tune)
  else
    List("-tune", "-1")

def av1_setTiles(w: Int, h: Int): List[String] =
  if w <= 0 || h <= 0 then
    List("-tile-columns", "-1", "-tile-rows", "-1")
  else
    List("-tile-columns", w.toString, "-tile-rows", h.toString)

def cfhd_setQuality(value: Int): List[String] =
  if value < 0 || value > 12 then
    List()
  else
    List("-quality", value.toString)

def utvideo_setPred(pred: String): List[String] =
  if pred == "none" || pred == "left" || pred == "gradient" || pred == "median" then
    List("-pred", pred)
  else
    List()

def png_setPred(mode: String): List[String] =
  val modes = List("none", "sub", "up", "avg", "paeth", "mixed")
  if belongsToList(mode, modes) then
    List("-pred", mode)
  else
    List()

def tiff_setCompression(compression: String): List[String] =
  val formats = List("packbits", "raw", "lzw", "deflate")
  if belongsToList(compression, formats) then
    List("-compression_algo", compression)
  else
    List()

def webp_setLossless(): List[String] = List("-lossless", "1")

def webp_setQuality(value: Byte): List[String] =
  if value < 0 || value > 100 then
    List()
  else
    List("-quality", value.toString)

def webp_setPreset(preset: String): List[String] =
  val presets = List("none", "default", "picture", "photo", "drawing", "icon", "text")
  if belongsToList(preset, presets) then
    List("-preset:v", preset)
  else
    List()
