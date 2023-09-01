/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.api;

import ar.com.jsuper.api.utils.FileMeta;
import ar.com.jsuper.api.utils.Response;
import ar.com.jsuper.aspect.CheckPermission;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dto.AppDTO;
import ar.com.jsuper.services.FileService2;
import ar.com.jsuper.services.UtilService;
import java.util.LinkedList;
import javax.servlet.http.HttpServletResponse;

import ar.com.jsuper.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 *
 * @author rafa22
 */
@RestController
@RequestMapping("api/v1/app")
public class AppController {

    LinkedList<FileMeta> files = new LinkedList<FileMeta>();

    @Autowired
    FileService2 fileService;
    @Autowired
    UtilService utilService;

    @Autowired
    private FileUtils fileUtil;
    @CheckPermission
    @RequestMapping(value = "/upload/image", headers = "content-type=multipart/*", method = RequestMethod.POST, produces = "application/json")
    public LinkedList<FileMeta> uploadImage(MultipartHttpServletRequest request, HttpServletResponse response) {
        this.files = fileService.generateFileImage(request, this.fileUtil.getDirImagenesAPP(), false);
        return files;
    }

    /**
     * devuelve los datos de la app
     *
     */
    @CheckPermission
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity get() throws BussinessException {
        AppDTO appDTO = utilService.getDataApp();
        return new ResponseEntity<>(Response.ok(appDTO), HttpStatus.OK);
    }

    /**
     * Modifica los datos de una app mas la sucursal principalwww
     *
     * @param appDTO
     * @return
     * @throws BussinessException
     */
    @CheckPermission
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> updateApp(@RequestBody AppDTO appDTO) throws BussinessException {
        return new ResponseEntity<>(Response.insert(utilService.saveDataApp(appDTO)), HttpStatus.OK);
    }
}
