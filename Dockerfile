FROM maven:3.8.7-eclipse-temurin-17-alpine

WORKDIR /app

COPY . .

RUN mvn package -f src/simulador-emprestimo/pom.xml 

EXPOSE 8080

CMD ["java", "-jar", "src/simulador-emprestimo/target/simulador-emprestimo-1.0.0.jar", "--spring.profiles.active=prod"]

