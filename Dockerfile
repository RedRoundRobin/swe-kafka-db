FROM adoptopenjdk/maven-openjdk11:latest
# COPY . /usr/src/data-collector/tmp
# WORKDIR /usr/src/data-collector/tmp
WORKDIR .
CMD ["sh", "start.sh"]
