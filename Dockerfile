FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY src ./src

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

RUN curl -L -o mysql-connector-j.jar \
    https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.4.0/mysql-connector-j-8.4.0.jar

RUN mkdir out && javac -cp mysql-connector-j.jar -d out $(find src -name "*.java")

CMD ["java", "-cp", "out:mysql-connector-j.jar", "Main"]