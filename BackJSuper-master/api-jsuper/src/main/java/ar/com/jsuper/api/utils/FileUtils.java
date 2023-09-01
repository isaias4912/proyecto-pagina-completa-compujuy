/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.api.utils;

import java.io.File;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author rafa22
 */
public class FileUtils {

    public FileUtils() {
    }

    /*
    Devuelve un nombre ademas verifica si el archivo existe y lo renombra
     */
    public static String getNameFile(String path, String nameFile) {
        String sufijo = "";
        int index = 1;
        boolean bandera = true;
        while (bandera) {
            File f = new File(path + nameFile);
            if (f.exists() && !f.isDirectory()) {
                if (index > 1) {
                    int length = (int) (Math.log10(index) + 1);
                    String  tempName=FilenameUtils.removeExtension(nameFile);
                    tempName= StringUtils.substring(tempName, 0 , tempName.length() - (length+1));
                    nameFile= tempName+ "_" + index + "." + FilenameUtils.getExtension(nameFile);
                } else {
                    nameFile = FilenameUtils.removeExtension(nameFile) + "_" + index + "." + FilenameUtils.getExtension(nameFile);
                }
                index++;
            } else {
                bandera = false;
            }
        }
        return nameFile;
    }
}
