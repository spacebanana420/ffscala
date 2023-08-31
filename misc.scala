package misc

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
    stringArray :+= arg
    stringArray.toList
}
