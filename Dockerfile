# docker run --run -d -p 9999:9999 rrr/api
FROM adoptopenjdk/maven-openjdk11:latest
COPY . /usr/src/data-collector/tmp
EXPOSE 9999
WORKDIR /usr/src/data-collector/tmp
CMD ["sh", "start.sh"]
