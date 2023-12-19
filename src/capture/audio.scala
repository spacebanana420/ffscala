package ffscala.capture

import ffscala.*
import scala.sys.process._
import java.io.File
import misc.*

def alsa_default(): List[String] = List("-f", "alsa", "-i", "default")

def pulse_default(): List[String] = List("-f", "pulse", "-i", "default")

def oss_default(): List[String] = List("-f", "oss", "-i", "/dev/dsp")
