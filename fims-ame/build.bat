call mvn clean install -DskipTests=true
rem 1.0.1 contains the model/update endpoint
rename target\swagger-jaxrs-resteasy-server-1.0.1.war fims2.war