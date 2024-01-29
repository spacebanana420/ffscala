echo "Building ffscala with test.scala main"
scalac src/*.scala src/*/*.scala test/*.scala -d test.jar
echo "Running ffscala tests"
scala test.jar && rm test.jar
