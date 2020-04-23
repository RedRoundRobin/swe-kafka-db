FROM adoptopenjdk/maven-openjdk11:latest
COPY start.sh /usr/local/bin
RUN ln -s /usr/local/bin/start.sh /
ENTRYPOINT ["start.sh"]
