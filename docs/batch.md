These functions handle batch processing of multiple files.

---

```scala
def batchExecute(paths: List[String], format: String, args: List[String] = List(), filters: List[String] = List()  quiet: Boolean = true, exec = "ffmpeg")
```
Executes multiple encodes, for each file path in the list ```paths``` and uses ```args``` the arguments string you built.


The output file format will be the format argument. For each file, the output filename will be the same as the input file with the addition of "_new" and the new format.

Quiet is true by default, FFmpeg will only output warnings and errors.

---

```scala
def batchDir(dir: List[String], format: String, args: List[String] = List(), filters: List[String] = List()  quiet: Boolean = true, exec = "ffmpeg")
```
Executes multiple encodes, for each file path in the directory you specify, and uses ```args``` the arguments string you built.

The output format will tell FFscala which type of media it should look for (image, video or audio), and then only process those. If your output format doesn't match the supported formats for image, video and audio, all paths in ```dir``` will be processed as a fallback solution.

The output file format will be the format argument. For each file, the output filename will be the same as the input file with the addition of "_new" and the new format.

Quiet is true by default, FFmpeg will only output warnings and errors.


