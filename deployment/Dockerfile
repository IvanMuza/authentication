# ============================
# Step 1: Build with Gradle
# ============================
FROM gradle:8.9-jdk17 AS build

WORKDIR /app
COPY . .

# Generate the JAR using Gradle (bootJar)
RUN ./gradlew :app-service:bootJar -x validateStructure --no-daemon

# ============================
# Step 2: Runtime with JRE
# ============================
FROM eclipse-temurin:17-jre-jammy AS runtime

WORKDIR /app

COPY --from=build /app/applications/app-service/build/libs/*.jar app.jar

# Create a non-root user for security reasons
RUN adduser --disabled-password --gecos '' appuser && chown -R appuser /app
USER appuser

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
