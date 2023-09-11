package misc

def supportedExtensions(mode: String = "all"): List[String] = {
    val imageFormats: List[String] = List("png", "apng", "avif", "jpeg", "jpg", "tiff", "tif", "bmp", "gif", "webp", "tga", "avif")
    val audioFormats: List[String] = List("flac", "wav", "ogg", "opus", "m4a", "mp3", "aiff")
    val videoFormats: List[String] = List("mp4", "mov", "m4v", "avi", "mkv", "webm")

    mode match
        case "image" => imageFormats
        case "video" => videoFormats
        case "audio" => audioFormats
        case "all" => imageFormats ++ videoFormats ++ audioFormats
        case _ => imageFormats ++ videoFormats ++ audioFormats
}


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

def containsList(text: String, group: List[String]): Boolean = {
    for i <- group do {
        if text.contains(i) then
            return true
    }
    false
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
}

def getExtension(path: String): String = {
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
}
