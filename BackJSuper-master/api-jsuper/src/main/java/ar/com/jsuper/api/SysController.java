/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.api;

import ar.com.jsuper.api.utils.Response;
import ar.com.jsuper.services.AppService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rafa22
 */
@RestController
@RequestMapping("api/v1/sys")
public class SysController {

	@Autowired
	AppService appService;

	/**
	 * Elimina toda una app
	 *
	 */
	@RequestMapping(value = "/deleteApp", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity get(@RequestBody Map<String, Integer> data) {
		Integer idApp = data.get("idApp");
//		appService.deleteVentasByApp(idApp);  // descomentar para test
		return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
	}

}
