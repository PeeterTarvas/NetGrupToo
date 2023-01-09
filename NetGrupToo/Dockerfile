FROM openjdk:17-oracle
ADD NetGrupToo/build/libs/NetGrupToo-0.0.1-SNAPSHOT.jar NetGrupToo-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/
COPY NetGrupToo/build/libs/NetGrupToo-0.0.1-SNAPSHOT.jar NetGrupToo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "NetGrupToo-0.0.1-SNAPSHOT.jar"]
EXPOSE 8000
