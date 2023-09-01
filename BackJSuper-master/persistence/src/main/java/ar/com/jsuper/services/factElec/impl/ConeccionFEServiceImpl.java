/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.factElec.impl;

import ar.com.jsuper.dao.AppDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Configuracion;
import ar.com.jsuper.dto.AfipDTO;
import ar.com.jsuper.security.TenantContext;
import ar.com.jsuper.services.UtilService;
import ar.com.jsuper.services.factElec.ConeccionFEService;
import ar.com.jsuper.services.factElec.ws.FEAuthRequest;
import ar.com.jsuper.services.impl.BaseService;
import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rafa
 */
@Service
public class ConeccionFEServiceImpl extends BaseService implements ConeccionFEService {

	@Value("${afip.modo}")
	protected String afipModo;
	@Value("${afip.homo.endpoint}")
	protected String homoEndpoint;
	@Value("${afip.prod.endpoint}")
	protected String prodEndpoint;
	@Value("${afip.service}")
	protected String service;
	@Value("${afip.prod.dstDN}")
	protected String prodDstDN;
	@Value("${afip.homo.dstDN}")
	protected String homoDstDN;
	@Value("${afip.homo.p12file}")
	protected String homoP12file;
	@Value("${afip.prod.signer}")
	protected String prodSigner;
	@Value("${afip.homo.signer}")
	protected String homoSigner;
	@Value("${afip.p12pass}")
	protected String p12pass;
	@Value("${afip.ticketTime}")
	protected Long ticketTime;
	@Autowired
	private UtilService utilService;
	@Autowired
	AppDAO appDAO;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public FEAuthRequest getToken() throws BussinessException {
		Configuracion configuracion = appDAO.getConfiguracion();
		return this.getToken(configuracion);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public FEAuthRequest getToken(Configuracion configuracion) throws BussinessException {
		//verificamos que no exista un token en la bd
//		Configuracion configuracion = appDAO.getConfiguracion();
		String path = this.getFullPathApp() + "afip" + File.separator;
		FEAuthRequest authRequest = null;
		if (utilService.getAfipModo().equals("homo")) {
			authRequest = this.getTokenTestFromApp();
		} else {
			authRequest = this.getTokenFromApp();
		}
		if (Objects.nonNull(authRequest)) {
			return authRequest;
		} else {
			String LoginTicketResponse = null;
			String dstDN = "";
			String pass = "";
			String signer = "";
			String endpoint = "";

			if (utilService.getAfipModo().equals("homo")) {
				pass = "rafael";
				dstDN = homoDstDN;
				path = homoP12file;
				signer = homoSigner;
				endpoint = homoEndpoint;
			} else {
				pass = configuracion.getCertPassword();
				dstDN = prodDstDN;
				path = path + "certificatePFX.pfx";
				signer = prodSigner;
				endpoint = prodEndpoint;
			}
			byte[] LoginTicketRequest_xml_cms = afip_wsaa_client.create_cms(path, pass,
					signer, dstDN, service, ticketTime);
			LoginTicketResponse = afip_wsaa_client.invoke_wsaa(LoginTicketRequest_xml_cms, endpoint);
			Reader tokenReader = new StringReader(LoginTicketResponse);
			Document tokenDoc;
			try {
				tokenDoc = new SAXReader(false).read(tokenReader);
			} catch (DocumentException ex) {
				throw new BussinessException(ex);
			}
			String token = tokenDoc.valueOf("/loginTicketResponse/credentials/token");
			String sign = tokenDoc.valueOf("/loginTicketResponse/credentials/sign");
			String fechaInit = tokenDoc.valueOf("/loginTicketResponse/header/generationTime");
			String fechaEnd = tokenDoc.valueOf("/loginTicketResponse/header/expirationTime");
			System.out.println("TOKEN: " + token);
			System.out.println("SIGN: " + sign);
			System.out.println("INIT: " + fechaInit);
			System.out.println("END: " + fechaEnd);

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
			Date dateInit;
			try {
				dateInit = dateFormat.parse(fechaInit); //You will get date object relative to server/client timezone wherever it is parsed
			} catch (ParseException ex) {
				throw new BussinessException(ex);
			}
			Date dateEnd;
			try {
				dateEnd = dateFormat.parse(fechaEnd); //You will get date object relative to server/client timezone wherever it is parsed
			} catch (ParseException ex) {
				throw new BussinessException(ex);
			}

			// guardamos el token para que no vueva a ser consultado
			// le resto una hora tambien para no ser tan justo
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateEnd);
			cal.add(Calendar.HOUR, -1);
			if (utilService.getAfipModo().equals("homo")) {
				utilService.setDataAppAfipTest(TenantContext.getCurrentTenant(), token, sign, dateInit, cal.getTime());
			} else {
				utilService.setDataAppAfip(TenantContext.getCurrentTenant(), token, sign, dateInit, cal.getTime());
			}
			//------------------------------------
			authRequest = new FEAuthRequest();
			authRequest.setCuit(new Long(configuracion.getCuitEmpresa())); //CUIT 
			authRequest.setSign(sign);
			authRequest.setToken(token);
			return authRequest;
		}
	}

	/**
	 * Verifica si existe un token valido de afip, caso contrario devuelve null
	 *
	 * @return
	 */
	private FEAuthRequest getTokenFromApp() {
		AfipDTO afipDTO = utilService.getDataAppAfip();
		Date fechaInit = afipDTO.getAfipFechaInitToken();
		Date fechaEnd = afipDTO.getAfipFechaEndToken();
		Date hoy = new Date();
		// si es null retorna null
		if (Objects.isNull(afipDTO.getAfipToken()) || afipDTO.getAfipToken().trim().equals("")) {
			return null;
		}
		if (Objects.isNull(afipDTO.getAfipSign()) || afipDTO.getAfipSign().trim().equals("")) {
			return null;
		}
		if (Objects.isNull(fechaEnd)) {
			return null;
		}
		if (fechaEnd.compareTo(hoy) > 0) {
			FEAuthRequest authRequest = new FEAuthRequest();
			if (!Objects.isNull(afipDTO.getCuitEmpresa()) && !afipDTO.getCuitEmpresa().trim().equals("")) {
				authRequest.setCuit(new Long(afipDTO.getCuitEmpresa()));
			}
			authRequest.setSign(afipDTO.getAfipSign());
			authRequest.setToken(afipDTO.getAfipToken());
			return authRequest;
		} else {
			return null;
		}
	}

	/**
	 * Verifica si existe un token valido de afip, caso contrario devuelve null
	 *
	 * @return
	 */
	private FEAuthRequest getTokenTestFromApp() {
		AfipDTO afipDTO = utilService.getDataAppAfip();
		Date fechaInit = afipDTO.getAfipFechaInitTokenTest();
		Date fechaEnd = afipDTO.getAfipFechaEndTokenTest();
		Date hoy = new Date();
		// si es null retorna null
		if (Objects.isNull(afipDTO.getAfipTokenTest()) || afipDTO.getAfipTokenTest().trim().equals("")) {
			return null;
		}
		if (Objects.isNull(afipDTO.getAfipSignTest()) || afipDTO.getAfipSignTest().trim().equals("")) {
			return null;
		}
		if (Objects.isNull(fechaEnd)) {
			return null;
		}
		if (fechaEnd.compareTo(hoy) > 0) {
			FEAuthRequest authRequest = new FEAuthRequest();
			if (!Objects.isNull(afipDTO.getCuitEmpresa()) && !afipDTO.getCuitEmpresa().trim().equals("")) {
				authRequest.setCuit(new Long(afipDTO.getCuitEmpresa()));
			}
			authRequest.setSign(afipDTO.getAfipSignTest());
			authRequest.setToken(afipDTO.getAfipTokenTest());
			return authRequest;
		} else {
			return null;
		}
	}

}
