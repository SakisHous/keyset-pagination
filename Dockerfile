# Stage 1: Extract the layers
FROM eclipse-temurin:21-jdk as builder

WORKDIR /builder
ARG JAR_FILE=libs/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=tools -jar application.jar extract --layers --launcher

# Stage 2: Build the runtime image
FROM eclipse-temurin:21-jre

RUN addgroup --system keyset && adduser --system --ingroup keyset keyset
USER keyset:keyset
WORKDIR /app

# Copy the layes
COPY --from=builder /builder/application/dependencies/ ./
COPY --from=builder /builder/application/spring-boot-loader/ ./
COPY --from=builder /builder/application/snapshot-dependencies/ ./
COPY --from=builder /builder/application/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]