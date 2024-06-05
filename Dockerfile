# Etapa 1: Construir el JAR usando Maven
FROM maven:3.8.6-eclipse-temurin-17 AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos del proyecto al contenedor
COPY . .

# Ejecuta Maven para construir el JAR
RUN mvn clean package -DskipTests

# Etapa 2: Ejecutar la aplicación
FROM eclipse-temurin:17-jdk-alpine

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR generado desde la imagen de construcción
COPY --from=build /app/target/challenge-0.0.1-SNAPSHOT.jar /app/app.jar

# Expone el puerto en el que tu aplicación se ejecuta
EXPOSE 8080

# Define el comando para ejecutar tu aplicación
ENTRYPOINT ["java", "-jar", "/app/app.jar"]