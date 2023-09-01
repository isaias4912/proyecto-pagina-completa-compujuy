#!/bin/bash
#Actualizacion de las instancias de gestion al ciudadano OEM, y WS para clientes externos
RED='\033[0;31m'
NC='\033[0m' # No Color
GREEN='\033[0;32m'
iter=0


echo -e "${GREEN}##########################################################"
echo -e "${GREEN}	Actualizando y desplegando"
echo -e "${GREEN}##########################################################${NC}"

                        cd /root/repo/CliWebJSuper
                        echo "Descargando desde git lab"
                        git pull

                        echo "Grunt"
                        grunt --base /root/repo/CliWebJSuper prod

                        echo "Removiendo app inicial"
                        rm -rdf /var/www/html/jsuper/*

                        echo "Copiamos archivos"
                        cp -r /root/repo/CliWebJSuper/dist/prod/* /var/www/html/jsuper/

                        echo "Copiamos archivos"
                        cp -r /root/repo/CliWebJSuper/dist/prod/* /var/www/html/jsuper-test/

                        echo "Cambiamos base url..."
                        sudo sed -i "s%localhost:8080%138.36.237.254:8080%g" /var/www/html/jsuper/js/js.min.js
                        sudo sed -i "s%localhost:8080%138.36.237.254:8081%g" /var/www/html/jsuper-test/js/js.min.js

                        echo "Cambiamos parametros..."
                        md5=`echo -n 'prod_jsuper' | md5sum`
			md5final=`echo $md5 | cut -c1-10`
			echo 'md5final prod jsuper:' $md5final
                        sudo sed -i 's%version="dev"%version="prod"%g' /var/www/html/jsuper/js/js.min.js
                        sudo sed -i 's%version="dev"%version="test"%g' /var/www/html/jsuper-test/js/js.min.js

                        sudo sed -i 's%token_prefix%token_prod_js%g' /var/www/html/jsuper/js/js.min.js
                        sudo sed -i 's%token_prefix%token_test_js%g' /var/www/html/jsuper-test/js/js.min.js

                        sudo sed -i 's%6LfqvzEUAAAAAGLr2EQIjSfrN_fUBPOYaRsq7jCb%6LefGpoUAAAAAPuumRg-zxstqpj_zxlXAfuCBn1O%g' /var/www/html/jsuper/pages/login/register.html
                        sudo sed -i 's%6LfqvzEUAAAAAGLr2EQIjSfrN_fUBPOYaRsq7jCb%6LefGpoUAAAAAPuumRg-zxstqpj_zxlXAfuCBn1O%g' /var/www/html/jsuper-test/pages/login/register.html

                        echo "Dando permisos correctos"
                        chmod -R 755 /var/www/html/jsuper
                        chmod -R 755 /var/www/html/jsuper-test

                        echo -e "${GREEN}========================================================"
                        echo -e "${GREEN} Se actualizó correctamente"
                        echo -e "${GREEN}========================================================${NC}"

