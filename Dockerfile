FROM adoptopenjdk/maven-openjdk11:latest
RUN mkdir -p /usr/src/data-collector/tmp
COPY start.sh /usr/src/data-collector
COPY . /usr/src/data-collector/tmp
WORKDIR /usr/src/data-collector
CMD ["sh", "start.sh"]
