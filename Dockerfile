# 1. Base image 선택 (JDK 21)
FROM openjdk:21-jdk

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. Gradle 빌드 실행 후 JAR 파일 복사
COPY build/libs/*.jar app.jar

# 4. 포트 설정 (Spring Boot 기본 8080)
EXPOSE 8080

# 5. 실행 명령어
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]