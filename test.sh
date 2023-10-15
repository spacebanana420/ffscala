echo "Building ffscala with test.scala main"
scalac src/*.scala test/*.scala -d test.jar && scala test.jar && rm test.jar
