#!/bin/bash
# -*- ENCODING: UTF-8 -*-
echo "-------------Generando el war------------------"
mvn clean install
echo "-------------Eliminando el war de Apache------------------"
rm /opt/apache-tomcat-8.5.34/webapps/web-services.war
rm -rfd /opt/apache-tomcat-8.5.34/webapps/web-services
echo "-------------Compiando el war en apache------------------"
cp /home/rafa/Proyectos/jsuper/BackJSuper/web-services/target/web-services.war /opt/apache-tomcat-8.5.34/webapps/
exit
