package ffscala

import misc.*

def x264_setPreset(preset: String): String = {
    val presets = List("ultrafast", "superfast", "veryfast", "faster", "fast", "medium", "slow", "slower", "veryslow", "placebo")
    if belongsToList(preset, presets) == true then
        "-preset:v " + preset + " "
    else
        ""
}

def x264_setCoder(coder: String): String = {
    val coders = List("default", "cavlc", "cabac", "vlc", "ac")
    if belongsToList(coder, coders) == true then
        "-coder " + coder + " "
    else
        ""
}

def nvenc_setPreset(preset: String): String = {
    val presets = List("lossless", "default", "slow", "medium", "fast", "hq")
    if belongsToList(preset, presets) == true then
        "-preset:v " + preset + " "
    else
        ""
}

def nvenc_setqp(value: Byte): String = {
    if value < 1 || value > 51 then
        ""
    else
        "-rc constqp -qp " + value + " "
}

def nvenc_setcrf(value: Byte): String = {
    if value < 0 || value > 51 then
        ""
    else
        "-rc vbr_hq -qmin 0 -cq " + value + " "
}

def dnxhd_setPreset(preset: String): String = {
    val presets = List("dnxhd", "dnxhr_lb", "dnxhr_sq", "dnxhr_hq", "dnxhr_hqx", "dnxhr_444")
    if belongsToList(preset, presets) == true then
        "-profile:v " + preset + " "
    else
        ""
}

def vp9_setDeadline(preset: String): String = {
    val presets = List("best", "good", "realtime")
    if belongsToList(preset, presets) == true then
        "-deadline " + preset + " "
    else
        ""
}

def vp9_setcpu_used(value: Byte): String = {
    if value >= -8 && value < 8 then
        "-cpu-used " + value + " "
    else
        ""
}

def vp9_setRowMT(): String = "-row-mt true "

def vp9_setLossless(): String = "-lossless "

def cfhd_setQuality(value: Int): String = {
    if value < 0 || value > 12 then
        ""
    else
        "-quality " + value + " "
}

def png_setPred(mode: String): String = {
    val modes = List("none", "sub", "up", "avg", "paeth", "mixed")
    if belongsToList(mode, modes) == true then
        "-pred " + mode + " "
    else
        ""
}

def tiff_setCompression(compression: String): String = {
    val formats = List("packbits", "raw", "lzw", "deflate")
    if belongsToList(compression, formats) == true then
        "-compression_algo " + compression + " "
    else
        ""
}

def webp_setLossless(): String = "-lossless 1 "


def webp_setQuality(value: Int): String = {
    if value < 0 || value > 100 then
        ""
    else
        "-quality " + value + " "
}
