@echo off
echo ---------------------Stop Apache---------------------------------
call C:\apache-tomcat-7.0.65\bin\shutdown.bat
echo ---------------------Eliminando war de apache--------------------
RMDIR C:\apache-tomcat-7.0.65\webapps\web-services /S /Q  
DEL /F /Q C:\apache-tomcat-7.0.65\webapps\web-services.war
echo ---------------------Creando el war con Maven--------------------
call mvn clean install
echo ---------------------Moviendo el war ----------------------------
COPY C:\Users\cilo22\Documents\Proyectos\jsuper\web-services\target\web-services.war C:\apache-tomcat-7.0.65\webapps
echo ---------------------Iniciando el apache----------------------------
::call C:\apache-tomcat-7.0.65\bin\startup.bat



