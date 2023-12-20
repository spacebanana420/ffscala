package ffscala

import ffscala.misc.*

//Encoder-specific parameters and configuration

def aac_setCoder(coder: String): List[String] =
  val supported = List("anmr", "twoloop", "fast")
  if belongsToList(coder, supported) then
    List("-aac_coder", coder)
  else
    List("-aac_coder", "twoloop")

def opus_setVBR(mode: String): List[String] =
  val supported = List("on", "off", "constrained")
  if belongsToList(mode, supported) then
    List("-vbr", mode)
  else
    List("-vbr", "on")

