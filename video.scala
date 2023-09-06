package ffscala

import misc.*

def x264_setPreset(preset: String): String = {
    val presets = List("ultrafast", "superfast", "veryfast", "faster", "fast", "medium", "slow", "slower", "veryslow", "placebo")
    if belongsToList(preset, presets) == true then
        "-preset:v " + preset + " "
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

def vp9_setLossless(): String = "-lossless "

def cfhd_setQuality(value: Int): String = {
    if value < 0 || value > 12 then
        ""
    else
        "-quality " + value + " "
}
