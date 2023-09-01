package ar.com.jsuper.api.mailing;

import ar.com.jsuper.api.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dto.CbteVenEncDTO;
import ar.com.jsuper.dto.EmailDTO;
import ar.com.jsuper.services.VentasService;
import ar.com.jsuper.services.mails.MailAdminService;
import ar.com.jsuper.services.reportes.ReporteVentas;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/mailing/util")
public class UtilMailController {

	@Autowired
	ReporteVentas reporteVentas;
	@Autowired
	private VentasService ventasService;
	@Autowired
	private MailAdminService mailAdminService;

	@RequestMapping(value = "/test", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> sendMailTest(@RequestBody Set<EmailDTO> emails){
        String[] sendMails= emails.stream().map(item->item.getEmail()).toArray(String[]::new);
		mailAdminService.sendMailTest(sendMails);
		return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
	}

}
