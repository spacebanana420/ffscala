package ffscala.capture

import ffscala.*
import ffscala.misc.*

//Default arguments for desktop and audio capture

def x11grab_default(): List[String] = List("-f", "x11grab", "-i", ":0.0")

def alsa_default(): List[String] = List("-f", "alsa", "-i", "default")

def pulse_default(): List[String] = List("-f", "pulse", "-i", "default")

def oss_default(): List[String] = List("-f", "oss", "-i", "/dev/dsp")
