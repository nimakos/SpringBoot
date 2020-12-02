FROM openjdk:11
ADD web/target/*.jar spring-boot-mvc.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "spring-boot-mvc.jar"]
