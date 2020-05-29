#!/bin/sh
#to build on linux
mvn clean install -DskipTests=true

mv target/swagger-jaxrs-resteasy-server-1.0.0.war target/fims2.war


#Deploy @ tomcat
sudo cp target/fims2.war $CATALINA_HOME/webapps


#Restart tomcat clean with new app
sudo $CATALINA_HOME/bin/shutdown.sh
sudo rm -rf $CATALINA_HOME/webapps/fims2
sudo $CATALINA_HOME/bin/startup.sh

#Check debug output in another console window
#sudo tail -f $CATALINA_HOME/logs/catalina.out

