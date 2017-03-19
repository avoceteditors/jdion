
VERSION = 0.1.0
TARGET = target/uberjar
JAR = $(TARGET)/dion-$(VERSION)-SNAPSHOT.jar

all: run

build: $(JAR)

$(JAR): src/dion/*.clj
	lein uberjar

run: $(JAR) 
	./script/dion start	
