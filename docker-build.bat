docker build --build-arg JAR_FILE=executable/*.jar -t scalable-assignment-1/auth-service .
docker save -o auth-service.tar scalable-assignment-1/auth-service