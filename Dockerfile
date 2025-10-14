# Etapa 1: Construcción con Maven
FROM maven:3.8.5-openjdk-17 AS build

# Copiar el código fuente
WORKDIR /app
COPY . .

# Construir el proyecto
RUN mvn clean package -DskipTests

# Etapa 2: Imagen de ejecución
FROM openjdk:17-jdk-slim

# Copiar el JAR construido desde la etapa de construcción
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto en el que corre la aplicación
EXPOSE 8090

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
