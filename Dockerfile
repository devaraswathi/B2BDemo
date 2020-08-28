FROM scrscontainerregistry.azurecr.io/java-openjre-11:latest

EXPOSE 8080
WORKDIR /opt/app

# Deployment pipeline should override with appropriate environment profile
ENV SPRING_PROFILES_ACTIVE=dev

COPY service/target/*.jar /opt/app/service.jar

CMD ["java","-jar","/opt/app/service.jar"]
