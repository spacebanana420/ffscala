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

def opus_setApplication(mode: String): List[String] =
  val supported = List("voip", "audio", "lowdelay")
  if belongsToList(mode, supported) then
    List("-application", mode)
  else
    List("-application", "audio")

def flac_predictionMethod(mode: String): List[String] =
  val supported = List("estimation", "2level", "4level", "8level", "search", "log")
  if belongsToList(mode, supported) then
    List("-prediction_order_method", mode)
  else
    List("-prediction_order_method", "-1")

def flac_decorrelation(mode: String): List[String] =
  val supported = List("auto", "indep", "left_side", "right_side", "mid_side")
  if belongsToList(mode, supported) then
    List("-ch_mode", mode)
  else
    List("-ch_mode", "auto")
