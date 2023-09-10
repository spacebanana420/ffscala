package ffscala

import java.io.File
import misc.*

def batchExecute(paths: List[String], args: String, format: String, quiet: Boolean = true) = {
    val supportedFormats = supportedExtensions()
    val isSupported = belongsToList(format, supportedFormats)

    if isSupported == true then {
        for path <- paths do {
            if File(path).isFile() == true then
                val output = removeExtension(path) + "_new." + format
                if quiet == true then
                    execute("-i " + path + " " + args + " " + output)
                else
                    execute("-i " + path + " " + args + " " + output, false)
        }
    }
}

//add async encoding, 1 thread per encode

//detect if its a dir, to automatically read all files there instead
