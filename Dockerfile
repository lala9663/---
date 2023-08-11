FROM adoptopenjdk:11-jre-hotspot

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 프로파일을 분리하여 사용할 경우 아래 주석을 해제하세요
# ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app.jar"]

# 프로파일을 분리하지 않고 사용할 경우 아래 주석을 해제하세요
ENTRYPOINT ["java","-jar","/app.jar"]