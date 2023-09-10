```scala
def batchExecute(paths: List[String], args: String, format: String, quiet: Boolean = true)
```
Executes multiple encodes, for each file path in the list ```paths``` and uses ```args``` the arguments string you built.


The output file format will be the format argument. For each file, the output filename will be the same as the input file with the addition of "_new" and the new format.

Quiet is true by default, FFmpeg will only output warnings and errors.
