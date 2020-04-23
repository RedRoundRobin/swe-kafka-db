#!/bin/sh

if [ ! -f ../../../kafka-data-collector.jar ]; then

	cd ../../../kafka-data-collector

	mvn clean package

	mv -f target/kafka-data-collector*dependencies.jar ../../../../kafka-data-collector.jar
	cd ../../../
	rm -rf ../../../kafka-data-collector
	rm -rf ../../../.github
	rm -rf ../../../kafka
	rm -f ../../../.gitignore
	rm -f ../../../LICENSE
	rm -f ../../../README.md
	
fi

java -jar ../../../kafka-data-collector.jar

echo "Kafka data collector started..."
