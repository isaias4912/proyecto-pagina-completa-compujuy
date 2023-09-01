/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.api;

import ar.com.jsuper.api.utils.FileMeta;
import ar.com.jsuper.api.utils.ImageUtils;
import ar.com.jsuper.api.utils.Response;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dto.*;
import ar.com.jsuper.services.Backup;
import ar.com.jsuper.services.FileService;
import ar.com.jsuper.services.FileService2;
import ar.com.jsuper.services.UtilService;
import ar.com.jsuper.services.factElec.FacturaElectronicaService;
import ar.com.jsuper.utils.FileStorageService;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.com.jsuper.utils.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 *
 * @author rafa22
 */
@RestController
@RequestMapping("api/v1/util")
public class UtilController {

    LinkedList<FileMeta> files = new LinkedList<>();
//    @Autowired
//    FacebookConnectionFactory facebookConnectionFactory;
    @Autowired
    UtilService utilService;
    @Autowired
    Backup backup;
    @Value("${pathImagenesApp}")
    private String pathImageApp;
    private Logger logger = Logger.getLogger(UtilController.class);
    @Autowired
    FileService2 fileService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private FacturaElectronicaService facturaElectronicaService;
    @Autowired
    private FileUtils fileUtil;
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getDataDashBoard() throws BussinessException {
        return new ResponseEntity<>(Response.ok(utilService.getDataDashBoard()), HttpStatus.OK);
    }

    /**
     * devuelve la congiuracion de una app
     *
     * @return
     */
    @RequestMapping(value = "/configuracion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getConfiguracion() {
        return new ResponseEntity<>(Response.ok(utilService.getConfiguracion()), HttpStatus.OK);
    }

    /**
     * genera un backup
     *
     * @return
     */
    @RequestMapping(value = "/backup/generate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> generateBackup() {
        return new ResponseEntity<>(Response.ok(backup.generateBackup()), HttpStatus.OK);
    }

    /**
     * setea los camposd e backup y ejecuta la tarea si es que corresponde
     *
     * @return
     */
    @RequestMapping(value = "/backup", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> setBackup(@RequestBody ConfiguracionDTO configuracionDTO) {
        return new ResponseEntity<>(Response.ok(utilService.setBackup(configuracionDTO)), HttpStatus.OK);
    }

    /**
     * devuelve la congiuracion gral de una app
     *
     * @return
     */
    @RequestMapping(value = "/configuraciongral", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getConfiguracionGral() {
        return new ResponseEntity<>(Response.ok(utilService.getConfiguracionGral()), HttpStatus.OK);
    }

    /**
     * Guarad modificacion de una configuración
     *
     * @param configuracionDTO
     * @return
     */
    @RequestMapping(value = "/configuracion", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> updateConfiguracion(@RequestBody ConfiguracionDTO configuracionDTO) {
        return new ResponseEntity<>(Response.ok(utilService.updateConfiguracion(configuracionDTO)), HttpStatus.OK);
    }

    /**
     * Modificacion de una configuración, pero solo los datos basicos, necesarios para la app
     *
     * @param configuracionDTO
     * @return
     */
    @RequestMapping(value = "/configuracion/basica", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> updateConfiguracionBasica(@RequestBody ConfiguracionDTO configuracionDTO) {
        return new ResponseEntity<>(Response.ok(utilService.updateConfiguracionBasica(configuracionDTO)), HttpStatus.OK);
    }

    /**
     * Guarad modificacion de una configuración
     *
     * @return
     */
    @RequestMapping(value = "/configuracion/upload/image/reporte", headers = "content-type=multipart/*", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public LinkedList<FileMeta> configuracionUploadLogoReporte(MultipartHttpServletRequest request, HttpServletResponse response) {
        this.files = fileService.generateFileImage(request, this.fileUtil.getDirImagenesConfig(), true);
        return files;
    }

    /**
     *
     * @param clienteAppDesktopDTO
     * @param option si es 1 devuelve todos los datos incluyendo los datos de la app, caso contrario no devuelve estos datos
     * @return
     * @throws BussinessException
     */
    @RequestMapping(value = "/dataappdesktop", params = {"option"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getDataAppDesktop(@RequestBody ClienteAppDesktopDTO clienteAppDesktopDTO, @RequestParam(value = "option", required = true) String option, @RequestParam(value = "logos", required = true) String logos) throws BussinessException {
        DataClientDesktop dataClientDesktop = utilService.getDataAppDesktop(clienteAppDesktopDTO, option);
        // enviamos la imagen para el cliente desktop en base64
        if (logos.equals("1")) {
            if (Objects.nonNull(dataClientDesktop.getLogo())) {
                try {
                    // imagen de la aplicacion
                    String name = FilenameUtils.getBaseName(dataClientDesktop.getLogo());
                    String ext = FilenameUtils.getExtension(dataClientDesktop.getLogo());
                    String nameFile = String.format("%s.%s", name + "_24", ext);
                    String imageBase64LogoApp = ImageUtils.getBase64(pathImageApp + "thumbnails/" + nameFile);
                    // imagen de encabezado de cbtes
                    dataClientDesktop.setLogo(imageBase64LogoApp);
                } catch (Exception e) {
                    logger.error("Error con la imagen", e);
                    dataClientDesktop.setLogo(null);
                }
            }
            if (Objects.nonNull(dataClientDesktop.getConfiguracion().getLogoReporte())) {
                try {
                    // imagen de encabezado de cbtes
                    String imageBase64EncCbte = ImageUtils.getBase64(pathImageApp + "configuracion/" + dataClientDesktop.getConfiguracion().getLogoReporte());
                    dataClientDesktop.setLogoCbte(imageBase64EncCbte);
                } catch (Exception e) {
                    logger.error("Error con la imagen", e);
                    dataClientDesktop.setLogoCbte(null);
                }
            }
        }
        return new ResponseEntity<>(Response.ok(dataClientDesktop), HttpStatus.OK);
    }

    /**
     * Guardamos el vencimiento por producto
     *
     * @param vencimientosProductosDTO
     * @return
     */
    @RequestMapping(value = "/vencimiento", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> saveVencimientoProducto(@RequestBody VencimientosProductosDTO vencimientosProductosDTO) {
        return new ResponseEntity<>(Response.ok(utilService.saveVencimientoProducto(vencimientosProductosDTO)), HttpStatus.CREATED);
    }

    /**
     * devuelve paginado los vencimientos
     *
     * @param filterGenericDTO
     * @param pagina
     * @param paginaTamanio
     * @param orden
     * @param reverse
     * @return
     * @throws BussinessException
     */
    @RequestMapping(value = "/vencimiento/all/pagination", params = {"page", "pageSize", "order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getCajaBypage(@RequestBody FilterGenericDTO filterGenericDTO, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) throws BussinessException {
        return new ResponseEntity<>(utilService.getVencimientosByPage(filterGenericDTO, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
    }

    /**
     * Deshabilita un vencimiento o varios,pone el estado en false
     *
     * @param vencimientosDTO
     * @return
     */
    @RequestMapping(value = "/vencimiento/disable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity disableVencimiento(@RequestBody Set<VencimientosProductosDTO> vencimientosDTO) {
        utilService.enabledOrdisabledVencimientos(vencimientosDTO, false);
        return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
    }

    /**
     * Habilita un venci o varios, pone un estado en true
     *
     * @param vencimientosDTO
     * @return
     */
    @RequestMapping(value = "/vencimiento/enable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity enableVencimiento(@RequestBody Set<VencimientosProductosDTO> vencimientosDTO) {
        utilService.enabledOrdisabledVencimientos(vencimientosDTO, true);
        return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
    }

    /**
     * Habilita el alerta de un vencimiento
     *
     * @param vencimientosDTO
     * @return
     */
    @RequestMapping(value = "/vencimiento/alerta/enable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity enableAlertaVencimiento(@RequestBody VencimientosProductosDTO vencimientosDTO) {
        utilService.enabledOrdisabledAlertaVencimientos(vencimientosDTO, true);
        return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
    }

    /**
     * Deshabilita el alerta de un vencimiento
     *
     * @param vencimientosDTO
     * @return
     */
    @RequestMapping(value = "/vencimiento/alerta/disable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity disableAlertaVencimiento(@RequestBody VencimientosProductosDTO vencimientosDTO) {
        utilService.enabledOrdisabledAlertaVencimientos(vencimientosDTO, false);
        return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
    }

    /**
     * Elimina un producto, siempre y cuando no tenga relaciones creaadas, como ser una venta, caso contrario tira un 409
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/vencimiento/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        try {
            utilService.deleteVencimientos(id);
        } catch (Exception ex) {
            logger.error("Error al eliminar un vencimiento", ex);
            throw new DataIntegrityViolationException("No se puede eliminar el vencimiento (" + id + "), porque esta asociado a otros registros.");
        }
        return new ResponseEntity<>(new Response(null, HttpStatus.OK.value(), ""), HttpStatus.OK);
    }

    /**
     * notificaciones para el inicio
     *
     * @return
     */
    @RequestMapping(value = "/notificaciones", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity notificaciones() {
        return new ResponseEntity<>(Response.ok(utilService.notificaciones()), HttpStatus.OK);
    }

    /**
     * Generamso un sert csr y ademas guardamos la configuracion
     *
     * @return
     */
    @RequestMapping(value = "/generate/csr", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity generateCSR(@RequestBody Map<String, String> data) {
        String certO = data.get("certO");
        String certCN = data.get("certCN");
        String certSerialNumber = data.get("certSerialNumber");
        String certPassword = data.get("certPassword");
        utilService.generateCSR(certCN, certO, certSerialNumber, certPassword);
        Map<String, String> response = new HashMap<>();
        response.put("certificateCSR", "ok");
        return new ResponseEntity<>(Response.ok("Certificado creado correctamente"), HttpStatus.OK);
    }

    /**
     * Generamos la clave privada
     *
     * @return
     */
    @RequestMapping(value = "/generate/private", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity generatePrivateKey() {
        utilService.generatePrivateKey();
        return new ResponseEntity<>(Response.ok("Clave generada correctamente"), HttpStatus.OK);
    }

    /**
     * Sube un archivo crt al path de afip
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/upload/crt", headers = "content-type=multipart/*", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public FileMetaDTO uploadFile(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
//        FileMetaDTO FileMetaDTO = fileService.generateFile(request, "crt");
//        utilService.updateNameCRTConfiguracion(FileMetaDTO.getFileName());
//        utilService.generatePkcs12();
//        return fileMeta;
        return null;
    }

    /**
     * Descarga certificado csr
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/download/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadFileSCR(HttpServletRequest request, @PathVariable String type) throws Exception {
        // Load file as Resource
        Resource resource = null;
        if (type.equals("csr")) {
            resource = fileStorageService.loadFileAsResource("afip/certificateCSR");
        }
        if (type.equals("crt")) {
            ConfiguracionDTO cdto = utilService.getConfiguracion();
            resource = fileStorageService.loadFileAsResource("afip/" + cdto.getCertNameCRT());
        }
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .headers(header)
                .body(resource);
    }

    @RequestMapping(value = "/configuracion/enableprodafip", params = {"value"}, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity enableProduccionAfip(@RequestParam(value = "value") Integer value) {
        this.facturaElectronicaService.enableProduccionAfip(value);
        return new ResponseEntity<>(Response.ok("Facturacion electrónica  habilitada en modo de producción correctamente."), HttpStatus.OK);
    }

}
