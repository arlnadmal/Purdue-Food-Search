.PHONY: run build clean

run:
	mvn -q compile exec:java -Dexec.mainClass=org.example.Main

build:
	mvn -q compile

clean:
	mvn -q clean