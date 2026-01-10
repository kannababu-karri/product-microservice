FROM eclipse-temurin:17-jre-alpine
WORKDIR /product
COPY target/*.jar product.jar
EXPOSE 8092
ENTRYPOINT ["java","-jar","product.jar"]