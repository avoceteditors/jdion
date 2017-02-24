#!/bin/sh

DION_HOME="$HOME/public/dion"
DION_VERSION="1.0-SNAPSHOT"
DIONJAR="$DION_HOME/target/dion-$DION_VERSION.jar"


if [ -f "${JAVA_HOME}/bin/java" ]; then
	JAVA=${JAVA_HOME}/bin/java
else
	JAVA=java
fi
export JAVA


# Execute Java
exec $JAVA -jar $DIONJAR --verbose --init
