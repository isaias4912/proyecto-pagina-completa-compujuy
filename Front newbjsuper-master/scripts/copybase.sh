#!/bin/bash
#Actualizacion de las instancias de gestion al ciudadano OEM, y WS para clientes externos

RED='\033[0;31m'
NC='\033[0m' # No Color
GREEN='\033[0;32m'
YELLOW="\033[0;33m"
BGREEN="\033[1;32m" 
echo -e "${GREEN}-------------------------------------------------------------------------------------"
echo -e "${GREEN}Copiando los archivos${NC}"
pathinicial=$(pwd)
echo $pathinicial
mkdir $2'/'$1
echo $2'/'$1
cp $pathinicial'/scripts/'$1'/'$1'-xxx.component.ts' $2'/'$1'/'$1'-'$3'.component.ts'
cp $pathinicial'/scripts/'$1'/'$1'-xxx.component.html' $2'/'$1'/'$1'-'$3'.component.html'
echo -e "${GREEN}Cambiando nombres a los archivos${NC}"
sudo sed -i "s%xxx%$3%g" $2'/'$1'/'$1'-'$3'.component.ts'
sudo sed -i "s%zzz%$1$3%g" $2'/'$1'/'$1'-'$3'.component.ts'
chmod 777 -R $pathinicial/src/app/modules



