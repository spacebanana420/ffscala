package ffscala.misc

//These functions are used by ffscala

def supportedExtensions(mode: String = ""): List[String] =
  val imageFormats = List("png", "apng", "avif", "jpeg", "jpg", "tiff", "tif", "bmp", "gif", "webp", "tga", "avif")
  val audioFormats = List("flac", "wav", "ogg", "opus", "m4a", "mp3", "aiff")
  val videoFormats = List("mp4", "mov", "m4v", "avi", "mkv", "webm")

  mode match
    case "image" => imageFormats
    case "video" => videoFormats
    case "audio" => audioFormats
    case _ => imageFormats ++ videoFormats ++ audioFormats

//unused for now
def getBaseArgs(exec: String, quiet: Boolean): List[String] =
  if quiet == true then
    List(exec, "-y", "-loglevel", "quiet")
  else
    List(exec, "-y", "-hide_banner")
// def addIntArg(a: Int, s: String): List[String] = List(s, a.toString)
//
// def addFloatArg(a: Float, s: String): List[String] = List(s, a.toString)
//
// def appendIntArg(a: Int, l: List[String]): List[String] = l :+ a.toString
//
// def appendFloatArg(a: Float, l: List[String]): List[String] = l :+ a.toString

def belongsToList(text: String, group: List[String], i: Int = 0): Boolean =
  if i >= group.length then
    false
  else if group(i) == text then
    true
  else
    belongsToList(text, group, i+1)

def indexFromList(text: String, group: List[String], i: Int = 0): Int =
  if i >= group.length then
    -1
  else if group(i) == text then
    i
  else
    indexFromList(text, group, i+1)


def containsList(text: String, group: List[String], i: Int = 0): Boolean =
  if i >= group.length then
    false
  else if text.contains(group(i)) then
    true
  else
    containsList(text, group, i+1)

// def stringToList(text: String): List[String] = { //use recursion
//   var stringArray: Array[String] = Array()
//   var arg = ""
//   for i <- text do {
//     if i == ' ' then
//       stringArray :+= arg
//       arg = ""
//     else
//       arg += i
//   }
//   if arg != "" then
//     stringArray :+= arg
//   stringArray.toList
// }

def stringToList(str: String, arg: String = "", strlist: List[String] = List[String](), i: Int = 0): List[String] =
  if i == str.length then
    if arg == "" then
      strlist
    else
      strlist :+ arg
  else if str(i) == ' ' then
    stringToList(str, "", strlist :+ arg, i + 1)
  else
    stringToList(str, arg + str(i), strlist, i + 1)

def removeExtension(path: String): String =
  var finalpath = ""
  var finalpath_fixed = ""
  var startcopying = false

  for i <- path.length-1 to 0 by -1 do {
    if startcopying == true then
      finalpath += path(i)
    else if path(i) == '.' then
      startcopying = true
  }
  if finalpath == "" then
    ""
  else
    for i <- finalpath.length-1 to 0 by -1 do {
      finalpath_fixed += finalpath(i)
    }
    finalpath_fixed

def getExtension(path: String): String =
  var finalpath = ""
  var finalpath_fixed = ""
  var startcopying = true

  for i <- path.length()-1 to 0 by -1 do {
    if path(i) == '.' then
      startcopying = false
    else if startcopying == true then
      finalpath += path(i)
  }
  if finalpath == "" then
    ""
  else
    for i <- finalpath.length-1 to 0 by -1 do {
      finalpath_fixed += finalpath(i)
    }
    finalpath_fixed
