FROM azul/zulu-openjdk-alpine:8

RUN addgroup -S springboot && adduser -S springboot -G springboot
ARG COMMIT=""
LABEL revision=${COMMIT}
LABEL title="island"
LABEL description="templateDescriptionVar"
# Expose the ports we're interested in
EXPOSE 8080
USER root

COPY ./island-application/target/island-application*.jar /opt/island.jar

WORKDIR /opt
RUN mkdir log
RUN chown -R springboot:springboot /opt
RUN chown -R springboot:springboot /opt/log
RUN chmod 755 /opt
RUN chmod 755 /opt/log

USER springboot

# This will boot springboot
CMD ["java", "-jar", "/opt/island.jar"]