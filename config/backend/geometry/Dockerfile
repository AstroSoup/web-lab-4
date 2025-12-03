FROM amazoncorretto:21-al2023-jdk AS builder
WORKDIR /app
COPY ./backend/geometryService .

RUN ./gradlew clean build

FROM quay.io/wildfly/wildfly:37.0.1.Final-jdk21 as runtime

WORKDIR /app

COPY --from=builder /app/build/libs/ROOT.war /opt/jboss/wildfly/standalone/deployments/

ENTRYPOINT ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-Djboss.http.proxy-address-forwarding=true"]

EXPOSE 8080