# ================================================================================
# Dockerfile - Compilación y ejecución de Catálogo de Camisetas V2
# ================================================================================

# ======================== ETAPA 1: COMPILACIÓN ========================
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /build

COPY pom.xml .
COPY src/ src/

# Compilar sin ejecutar ni compilar tests
RUN mvn clean package -DskipTests=true -Dmaven.test.skip=true -q

# ======================== ETAPA 2: EJECUCIÓN ========================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /build/target/*.war /app/application.war

ENV JAVA_OPTS="-Xmx512m -Xms256m"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/application.war"]

# ================================================================================
