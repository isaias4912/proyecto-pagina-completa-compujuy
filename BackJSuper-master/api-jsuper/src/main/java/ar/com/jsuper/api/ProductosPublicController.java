package ar.com.jsuper.api;

import ar.com.jsuper.api.utils.FileMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ar.com.jsuper.api.utils.Response;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.services.ProductosService;
import ar.com.jsuper.services.ProductoPadreService;
import java.util.LinkedList;
import ar.com.jsuper.services.FileService2;

@RestController
@RequestMapping("/public/v1/productos")
public class ProductosPublicController {

    @Autowired
    ProductosService productosService;
    @Autowired
    ProductoPadreService productoPadreService;
    @Autowired
    FileService2 pdfService;
    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;


    @RequestMapping(value = "/padre/", params = {"name"}, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getProductoPadreByName(@RequestParam(value = "name", required = false) String name)
            throws BussinessException {
        return new ResponseEntity<Object>(new Response(productoPadreService.getByName(name), HttpStatus.OK.value(), ""),
                HttpStatus.OK);
    }
    @RequestMapping(value = "/codigobarra/{query}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getProductoByCode(@PathVariable String query)
            throws BussinessException {
        return new ResponseEntity<Object>(new Response(productosService.getProductoByBarCode(query, 1, 1,1), HttpStatus.OK.value(), ""),
                HttpStatus.OK);
    }
//    @RequestMapping(value = "/codigobarraorname", method = RequestMethod.POST, produces = "application/json")
//    public ResponseEntity<Object> getProductoByBarCodeOrName(@RequestBody  SimpleRequest request)throws BussinessException {
//        return new ResponseEntity<Object>(new Response(productosService.getProductoByBarCodeOrName(request.getData(), 1, 1,1), HttpStatus.OK.value(), ""),
//                HttpStatus.OK);
//    }


}
