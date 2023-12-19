package ffscala.capture

import ffscala.*
import scala.sys.process._
import java.io.File
import misc.*

def x11grab_default(): List[String] = List("-f", "x11grab", "-i", ":0.0")

def x11grab_hideMouse(): List[String] = List("-draw_mouse", "0") //check which is the default

def x11grab_setInput(display: Int, screen: Int): List[String] =
  if display < 0 || screen < 0 then
    List("-i", ":0.0")
  else
    List("-i", s":$display.$screen")
