package ar.com.jsuper.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ar.com.jsuper.api.utils.Response;
import ar.com.jsuper.dto.MessageDTO;
import ar.com.jsuper.services.SysService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/public")
public class PublicController {

	@Autowired
	SysService sysService;

	/**
	 * Guarda un mensaje enviado
	 *
	 * @param messageDTO mensaje a guardar
	 * @return response
	 */
	@RequestMapping(value = "/message", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> insertMessage(@RequestBody MessageDTO messageDTO) {
		return new ResponseEntity<>(Response.insert(sysService.insert(messageDTO)), HttpStatus.CREATED);
	}

}
