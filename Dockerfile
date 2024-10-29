FROM openjdk:20-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} flight-booking.jar
EXPOSE 8080
ENTRYPOINT ["java","-cp","app:app/lib/*","/flight-booking.jar"]