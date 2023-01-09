FROM adoptopenjdk/openjdk16:ubi
ADD build/libs/NetGrupToo-0.0.1-SNAPSHOT.jar NetGrupToo-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/
COPY build/libs/NetGrupToo-0.0.1-SNAPSHOT.jar NetGrupToo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "NetGrupToo-0.0.1-SNAPSHOT.jar"]
EXPOSE 8000
