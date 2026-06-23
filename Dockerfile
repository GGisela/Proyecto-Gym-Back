# --- Etapa 1: Compilación (Build) ---
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos el pom y descargamos las dependencias para cachear capas
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Cambiá la línea del pom si está adentro de una subcarpeta (ej: demo)
COPY demo/pom.xml .

# Y cambiá la línea 10 para que apunte a esa subcarpeta
COPY demo/src ./src

# --- Etapa 2: Ejecución (Runtime) ---
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copiamos el archivo JAR compilado desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Render asigna dinámicamente un puerto mediante la variable de entorno PORT,
# pero exponemos el 8080 por estándar local
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]