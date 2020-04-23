FROM adoptopenjdk/maven-openjdk11:latest
COPY . /usr/src/data-collector/tmp
WORKDIR /usr/src/data-collector/tmp
CMD ["sh", "start.sh"]
