package misc

def supportedExtensions(): List[String] = List("png", "apng", "avif", "jpeg", "jpg", "tiff", "tif", "bmp", "gif", "webp", "tga", "mp4", "mov", "m4v", "avi", "mkv", "webm", "flac", "wav", "ogg", "opus", "m4a", "mp3", "aiff")


def belongsToList(text: String, group: List[String]): Boolean = {
    for i <- group do {
        if text == i then
            return true
    }
    false
}

def indexFromList(text: String, group: List[String]): Int = {
    for i <- 0 to group.length - 1 do {
        if text == group(i) then
            return i
    }
    -1
}

def stringToList(text: String): List[String] = {
    var stringArray: Array[String] = Array()
    var arg = ""
    for i <- text do {
        if i == ' ' then
            stringArray :+= arg
            arg = ""
        else
            arg += i
    }
    if arg != "" then
        stringArray :+= arg
    stringArray.toList
}

def removeExtension(path: String): String = {
    var finalpath = ""
    var startcopying = false
    for i <- path.length-1 to 0 do {
        if startcopying == true then
            finalpath += path(i)
        else if path(i) == '.' then
            startcopying = true
    }
    finalpath
}
