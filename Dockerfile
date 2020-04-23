FROM adoptopenjdk/maven-openjdk11:latest
COPY start.sh /usr/local/bin
WORKDIR /usr/local/bin
CMD ["sh", "start.sh"]
