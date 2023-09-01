/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.utils;

import ar.com.jsuper.services.impl.BaseService;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author rafa
 */
@Service
public class FileUtils extends BaseService {

    private final Logger logger = Logger.getLogger(FileUtils.class);
    @Value("${path.general}")
    private String pathGeneral;
    @Value("${path.reportes}")
    private String pathReportes;
    @Value("${pathImagenesProductos}")
    private String pathImagenesProductos;
    @Value("${pathImagenesApp}")
    private String pathImagenesApp;
    @Value("${pathImagenesConfig}")
    private String pathImagenesConfig;
    @Value("${pathImagenesUsers}")
    private String pathImagenesUsers;
    public static final String DIR_MAIN_USER = "main_dir_%s";

    public void createDirInDirApp(String dir, Integer idApp) {
        File file = new File(this.pathGeneral + File.separator + this.getDirFromToken(idApp) + File.separator + dir);
        if (!file.exists()) {
            if (file.mkdirs()) {
                logger.info("Directory is created! - " + dir);
            } else {
                logger.error("Failed to create directory! - " + dir);
            }
        }
    }

    private String getMainDir(Integer idApp) {
        return String.format(DIR_MAIN_USER, idApp);
    }

    public void createDirApp(Integer idApp) {
        File file = new File(this.pathGeneral + File.separator + this.getDirFromToken(idApp));
        if (!file.exists()) {
            if (file.mkdirs()) {
                logger.info(String.format("Directory APP is created - {}", idApp));
            } else {
                logger.info(String.format("Error created directory- {}", idApp));
            }
        }
    }

    public void createDirectorysUser(Integer idApp) {
        this.createDirApp(idApp);
        //creamos dir de AFIP
        this.createDirInDirApp("afip", idApp);
        this.createDirInDirApp("images" + File.separator + "products" + File.separator + "thumbnails", idApp);
        this.createDirInDirApp("images" + File.separator + "app" + File.separator + "thumbnails", idApp);
        this.createDirInDirApp("images" + File.separator + "users" + File.separator + "thumbnails", idApp);
        this.createDirInDirApp("images" + File.separator + "my" + File.separator + "thumbnails", idApp);
        this.createDirInDirApp("images" + File.separator + "config" + File.separator + "thumbnails", idApp);
        this.createDirInDirApp("config", idApp);
        this.createDirInDirApp("extra", idApp);
    }

    public String getDirApp() {
        return this.pathGeneral + this.getDirFromToken();
    }

    public String getDirReportes() {
        return this.pathReportes;
    }

    public String getDirImagenesProd() {
        return this.pathGeneral + File.separator + this.getDirFromToken() + File.separator + "images" + File.separator + "products" + File.separator;
    }

    public String getDirImagenesMy() {
        return this.pathGeneral + File.separator + this.getDirFromToken() + File.separator + "images" + File.separator + "my" + File.separator;
    }

    public String getDirImagenesAPP() {
        return this.pathGeneral + File.separator + this.getDirFromToken() + File.separator + "images" + File.separator + "app" + File.separator;
    }

    public String getDirImagenesConfig() {
        return this.pathGeneral + File.separator + this.getDirFromToken() + File.separator + "images" + File.separator + "config" + File.separator;
    }

    public String getDirImagenesUsers() {
        return this.pathGeneral + File.separator + this.getDirFromToken() + File.separator + "images" + File.separator + "users" + File.separator;
    }

    public static Boolean existFile(String dir) {
        File file = new File(dir);
        return file.exists();
    }

    public static String getNameFile(String path, String nameFile) {
        String sufijo = "";
        int index = 1;
        boolean bandera = true;
        while (bandera) {
            File f = new File(path + nameFile);
            if (f.exists() && !f.isDirectory()) {
                if (index > 1) {
                    int length = (int) (Math.log10(index) + 1);
                    String tempName = FilenameUtils.removeExtension(nameFile);
                    tempName = StringUtils.substring(tempName, 0, tempName.length() - (length + 1));
                    nameFile = tempName + "_" + index + "." + FilenameUtils.getExtension(nameFile);
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
