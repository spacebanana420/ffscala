import ffscala.*
import ffscala.capture.*

def testcombine() =
  combineMedia("combine.mov", List("CHARGE.avi", "CHARGE.mov"), List("CHARGE.avi", "CHARGE.mov"))
