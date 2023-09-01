#!/bin/bash
#Actualizacion de las instancias de gestion al ciudadano OEM, y WS para clientes externos
RED='\033[0;31m'
NC='\033[0m' # No Color
GREEN='\033[0;32m'
iter=0


echo -e "${GREEN}##########################################################"
echo -e "${GREEN}       Actualizando y desplegando"
echo -e "${GREEN}##########################################################${NC}"
                        echo "Parando servicios"
                        sudo service api-jsuper-test stop
                        sudo service api-jsuper stop

                        echo "Cambiando de directorio"
                        cd /root/repo/BackJSuper

                        echo "Descargando desde git lab"
                        git pull

			echo "Copiamos reportes"
                        cp -r /root/repo/BackJSuper/reportesJSuper /root/repo/reportesJSuper

			echo "Eliminamos jar existentes"
                        rm -rdf /root/repo/web/*        
 
			echo "Copiamos archivos de configuracion para produccion"
			cp -r /root/jsuper/config/* /root/repo/BackJSuper/api-jsuper/src/main/resources

			echo "Construimos con maven"
			mvn clean install -f /root/repo/BackJSuper

                        cp -r /root/repo/BackJSuper/api-jsuper/target/api-jsuper.jar /root/repo/web/api-jsuper.jar
                        sudo chown root:root /root/repo/web/api-jsuper.jar
                        sudo chmod 500 /root/repo/web/api-jsuper.jar
			echo "Copiamos archivos de configuracion para test"

                        #---------------------------------------------------------    

			cp -r /root/jsuper/config-test/* /root/repo/BackJSuper/api-jsuper/src/main/resources
			echo "Construimos con maven"
			mvn clean install -f /root/repo/BackJSuper

                        cp -r /root/repo/BackJSuper/api-jsuper/target/api-jsuper.jar /root/repo/web/api-jsuper-test.jar
                        sudo chown root:root /root/repo/web/api-jsuper-test.jar
                        sudo chmod 500 /root/repo/web/api-jsuper-test.jar
                        echo -e "${GREEN}========================================================"
			echo -e "${GREEN} Se actualizó correctamente"
                        echo -e "${GREEN}========================================================${NC}"
			echo "Iniciando los servicios"
                        sudo service api-jsuper-test start
                        sudo service api-jsuper start

