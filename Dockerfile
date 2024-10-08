# 기반 이미지 선택
FROM eclipse-temurin:17

# 작업 디렉토리 생성
WORKDIR /app

# 빌드된 JAR 파일을 Docker 이미지 내 /app 디렉토리로 복사
COPY build/libs/what-the-weather-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

# JAR 파일 실행
CMD ["java", "-jar", "app.jar"]