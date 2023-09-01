/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.factElec.impl;

import ar.com.jsuper.dao.AfipDAO;
import ar.com.jsuper.dao.AppDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.AfipPtoVenta;
import ar.com.jsuper.domain.AfipTpoCbte;
import ar.com.jsuper.domain.AfipTpoConcepto;
import ar.com.jsuper.domain.AfipTpoDoc;
import ar.com.jsuper.domain.AfipTpoIva;
import ar.com.jsuper.domain.AfipTpoTributo;
import ar.com.jsuper.domain.Configuracion;
import ar.com.jsuper.domain.CbteEncVenta;
import ar.com.jsuper.dto.AfipDetFacturaDTO;
import ar.com.jsuper.dto.AfipEncFacturaDTO;
import ar.com.jsuper.dto.CbteVenDetSinEncabDTO;
import ar.com.jsuper.dto.CbteVenEncDTO;
import ar.com.jsuper.dto.ProductoMinDTO;
import ar.com.jsuper.dto.SimpleObjectDTO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.security.TenantContext;
import ar.com.jsuper.services.ParametricasService;
import ar.com.jsuper.services.UtilService;
import ar.com.jsuper.services.factElec.ConeccionFEService;
import ar.com.jsuper.services.factElec.FacturaElectronicaService;
import ar.com.jsuper.services.factElec.ws.AlicIva;
import ar.com.jsuper.services.factElec.ws.ArrayOfAlicIva;
import ar.com.jsuper.services.factElec.ws.ArrayOfCbteTipo;
import ar.com.jsuper.services.factElec.ws.ArrayOfConceptoTipo;
import ar.com.jsuper.services.factElec.ws.ArrayOfDocTipo;
import ar.com.jsuper.services.factElec.ws.ArrayOfFECAEDetRequest;
import ar.com.jsuper.services.factElec.ws.ArrayOfIvaTipo;
import ar.com.jsuper.services.factElec.ws.ArrayOfPtoVenta;
import ar.com.jsuper.services.factElec.ws.ArrayOfTributo;
import ar.com.jsuper.services.factElec.ws.ArrayOfTributoTipo;
import ar.com.jsuper.services.factElec.ws.CbteTipo;
import ar.com.jsuper.services.factElec.ws.CbteTipoResponse;
import ar.com.jsuper.services.factElec.ws.ConceptoTipo;
import ar.com.jsuper.services.factElec.ws.ConceptoTipoResponse;
import ar.com.jsuper.services.factElec.ws.DocTipo;
import ar.com.jsuper.services.factElec.ws.DocTipoResponse;
import ar.com.jsuper.services.factElec.ws.FEAuthRequest;
import ar.com.jsuper.services.factElec.ws.FECAECabRequest;
import ar.com.jsuper.services.factElec.ws.FECAEDetRequest;
import ar.com.jsuper.services.factElec.ws.FECAERequest;
import ar.com.jsuper.services.factElec.ws.FECAEResponse;
import ar.com.jsuper.services.factElec.ws.FEPtoVentaResponse;
import ar.com.jsuper.services.factElec.ws.FERecuperaLastCbteResponse;
import ar.com.jsuper.services.factElec.ws.FETributoResponse;
import ar.com.jsuper.services.factElec.ws.IvaTipo;
import ar.com.jsuper.services.factElec.ws.IvaTipoResponse;
import ar.com.jsuper.services.factElec.ws.PtoVenta;
import ar.com.jsuper.services.factElec.ws.ServiceSoap;
import ar.com.jsuper.services.factElec.ws.Tributo;
import ar.com.jsuper.services.factElec.ws.TributoTipo;
import ar.com.jsuper.services.impl.BaseService;
import ar.com.jsuper.utils.Comprobante;
import ar.com.jsuper.utils.Concepto;
import ar.com.jsuper.utils.ConstanstAfipTest;
import ar.com.jsuper.utils.Constants;
import ar.com.jsuper.utils.Doc;
import ar.com.jsuper.utils.FileUtils;
import ar.com.jsuper.utils.ResponseSolCae;
import ar.com.jsuper.utils.TipoEmpresa;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.WebServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rafa
 */
@Service
public class FacturaElectronicaServiceImpl extends BaseService implements FacturaElectronicaService {

	@Autowired
	ConeccionFEService coneccionFEService;
	@Autowired
	AfipDAO afipDAO;
	@Autowired
	private OrikaBeanMapper modelMapper;
	@Autowired
	private AppDAO appDAO;
	@Autowired
	private UtilService utilService;
	@Autowired
	private ParametricasService parametricasService;
	private ar.com.jsuper.services.factElec.ws.Service service;
//	@Value("classpath:wsfev1.wsdl")
//	Resource resourceWSDLProd;
//	@Value("classpath:wsfev1-homo.wsdl")
//	Resource resourceWSDLHomo;
	@Value("${afip.prod.wsdl}")
	protected String afipProdWsdl;
	@Value("${afip.homo.wsdl}")
	protected String afipHomoWsdl;

	@Bean
	public URL urlWSDLAfip(@Value("${afip.modo}") String afipModo) {
		System.out.println("afip modo***********************************:" + afipModo);
		URL url = null;
		WebServiceException e = null;
		try {
			if (afipModo.equals("homo")) {
				Resource resource = new FileSystemResource(afipHomoWsdl);
				url = resource.getURL();
			} else {
				Resource resource = new FileSystemResource(afipProdWsdl);
				url = resource.getURL();
//				url = new URL("https://servicios1.afip.gov.ar/wsfev1/service.asmx?WSDL");
			}
		} catch (MalformedURLException ex) {
			e = new WebServiceException(ex);
		} catch (IOException ex) {
			Logger.getLogger(FacturaElectronicaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
		return url;
	}

	@Autowired
	public void configure(URL urlWSDLAfip) {
		this.service = new ar.com.jsuper.services.factElec.ws.Service(urlWSDLAfip);
	}

	@Override
	public List<CbteTipo> getTipoComprobantes() throws BussinessException {
		FEAuthRequest authRequest = coneccionFEService.getToken();
		ServiceSoap serviceSoap = this.service.getServiceSoap12();
		CbteTipoResponse response = serviceSoap.feParamGetTiposCbte(authRequest);
		ArrayOfCbteTipo arrayOfCbteTipo = response.getResultGet();
		return arrayOfCbteTipo.getCbteTipo();
	}

	private Integer getUltimoCbteFromAfip(Integer puntoVenta, Integer tipoCbte) throws BussinessException {
		FEAuthRequest authRequest = coneccionFEService.getToken();
		ServiceSoap serviceSoap = this.service.getServiceSoap12();
		FERecuperaLastCbteResponse response = serviceSoap.feCompUltimoAutorizado(authRequest, puntoVenta, tipoCbte);
		return response.getCbteNro();
	}

	private Integer getUltimoCbteFromAfip(Integer puntoVenta, Integer tipoCbte, FEAuthRequest authRequest) {
		ServiceSoap serviceSoap = this.service.getServiceSoap12();
		FERecuperaLastCbteResponse response = serviceSoap.feCompUltimoAutorizado(authRequest, puntoVenta, tipoCbte);
		return response.getCbteNro();
	}

	private Map getDataFEFromAfip() throws BussinessException {
		FEAuthRequest authRequest = coneccionFEService.getToken();
		ServiceSoap serviceSoap = this.service.getServiceSoap12();
		CbteTipoResponse response = serviceSoap.feParamGetTiposCbte(authRequest);
		ArrayOfCbteTipo arrayOfCbteTipo = response.getResultGet();
		Map<String, List> map = new HashMap<>();
		try {
			map.put("comprobantes", arrayOfCbteTipo.getCbteTipo());
		} catch (NullPointerException e) {
			map.put("comprobantes", new ArrayList<>());
		}
		IvaTipoResponse responseIva = serviceSoap.feParamGetTiposIva(authRequest);
		ArrayOfIvaTipo arrayOfIvaTipo = responseIva.getResultGet();
		try {
			map.put("ivas", arrayOfIvaTipo.getIvaTipo());
		} catch (NullPointerException e) {
			map.put("ivas", new ArrayList<>());
		}
		FEPtoVentaResponse fePtoVentaResponse = serviceSoap.feParamGetPtosVenta(authRequest);
		ArrayOfPtoVenta arrayOfPtoVenta = fePtoVentaResponse.getResultGet();
		try {
			map.put("puntosventa", arrayOfPtoVenta.getPtoVenta());
		} catch (NullPointerException e) {
			map.put("puntosventa", new ArrayList<>());
		}
		ConceptoTipoResponse feParamGetTiposConcepto = serviceSoap.feParamGetTiposConcepto(authRequest);
		ArrayOfConceptoTipo arrayOfConceptoTipo = feParamGetTiposConcepto.getResultGet();
		try {
			map.put("conceptos", arrayOfConceptoTipo.getConceptoTipo());
		} catch (NullPointerException e) {
			map.put("conceptos", new ArrayList<>());
		}
		DocTipoResponse feParamGetTiposDocResponse = serviceSoap.feParamGetTiposDoc(authRequest);
		ArrayOfDocTipo arrayOfDocTipo = feParamGetTiposDocResponse.getResultGet();
		try {
			map.put("docs", arrayOfDocTipo.getDocTipo());
		} catch (NullPointerException e) {
			map.put("docs", new ArrayList<>());
		}
		FETributoResponse feTributoResponse = serviceSoap.feParamGetTiposTributos(authRequest);
		ArrayOfTributoTipo arrayOfTributo = feTributoResponse.getResultGet();
		try {
			map.put("tributos", arrayOfTributo.getTributoTipo());
		} catch (NullPointerException e) {
			map.put("tributos", new ArrayList<>());
		}
		return map;
	}

	@Override
	@Transactional(readOnly = true)
	public FEAuthRequest getToken() throws BussinessException {
		return coneccionFEService.getToken();
	}

	@Override
	@Transactional(readOnly = true)
	public Map getPuntoVentas() {
		Map<String, Object> data = new HashMap<>();
		Configuracion configuracion = this.appDAO.getConfiguracion();
		if (utilService.getAfipModo().equals("prod")) {
			if (configuracion.getAfipProduccion()) {
				data.put("puntosventa", afipDAO.getPtoVentas());
			} else {
				data.put("puntosventa", ConstanstAfipTest.getPtoVentas());
			}
		}
		if (utilService.getAfipModo().equals("homo")) {
			data.put("puntosventa", ConstanstAfipTest.getPtoVentas());
		}
		if (utilService.getAfipModo().equals("test")) {
			data.put("puntosventa", ConstanstAfipTest.getPtoVentas());
		}
		return data;
	}

	@Override
	@Transactional(readOnly = true)
	public Map getDataFE() {
		// verificamos si esta en dev, si esta en prod verificamos si existe el certificado
		Map<String, Object> data = new HashMap<>();
		Configuracion configuracion = this.appDAO.getConfiguracion();
		if (utilService.getAfipModo().equals("prod")) {
			if (configuracion.getAfipProduccion()) {
				data.put("comprobantes", afipDAO.getTpoCbtes());
				data.put("conceptos", afipDAO.getTpoConceptos());
				data.put("puntosventa", afipDAO.getPtoVentas());
				data.put("ivas", afipDAO.getTpoIvas());
				data.put("docs", afipDAO.getTpoDocs());
				data.put("tributos", afipDAO.getTpoTributos());
			} else {
				data.put("comprobantes", ConstanstAfipTest.getTpoCbtes());
				data.put("conceptos", ConstanstAfipTest.getTpoConceptos());
				data.put("puntosventa", ConstanstAfipTest.getPtoVentas());
				data.put("ivas", ConstanstAfipTest.getTpoIvas());
				data.put("docs", ConstanstAfipTest.getTpoDocs());
				data.put("tributos", ConstanstAfipTest.getTpoTributos());
			}
		}
		if (utilService.getAfipModo().equals("homo")) {
			data.put("comprobantes", afipDAO.getTpoCbtes());
			data.put("conceptos", afipDAO.getTpoConceptos());
			data.put("ivas", afipDAO.getTpoIvas());
			data.put("docs", afipDAO.getTpoDocs());
			data.put("tributos", afipDAO.getTpoTributos());
			data.put("puntosventa", ConstanstAfipTest.getPtoVentas());
		}
		if (utilService.getAfipModo().equals("test")) {
			data.put("comprobantes", ConstanstAfipTest.getTpoCbtes());
			data.put("conceptos", ConstanstAfipTest.getTpoConceptos());
			data.put("puntosventa", ConstanstAfipTest.getPtoVentas());
			data.put("ivas", ConstanstAfipTest.getTpoIvas());
			data.put("docs", ConstanstAfipTest.getTpoDocs());
			data.put("tributos", ConstanstAfipTest.getTpoTributos());
		}
		data.put("formaPagos", parametricasService.getFormaPagosForFacElec());
		data.put("valid", this.isValidConfig(configuracion));
		data.put("errors", this.getErrorCongifFE(configuracion));
		return data;
	}

	/**
	 * Se checkea de que exista la configuracion necesaria para realizar una facturacion electronica, ya sea por test o prod
	 *
	 * @param configuracion
	 * @return
	 */
	private Set getErrorCongifFE(Configuracion configuracion) {
		Set<String> msj = new HashSet<>();
		if (Objects.isNull(configuracion.getCuitEmpresa())) {
			msj.add("El CUIT de la empresa debe ser válido/completado");
		}
		if (Objects.isNull(configuracion.getRazonSocial())) {
			msj.add("La razon social de la empresa debe ser válida/completada");
		}
		if (Objects.isNull(configuracion.getDomicilioComercial())) {
			msj.add("El domicilio comercial de la empresa debe ser válido/completado");
		}
		if (Objects.isNull(configuracion.getTipoEmpresa())) {
			msj.add("La condicion de la empresa frente a  la AFIP debe ser válido/completado");
		}
		if (Objects.isNull(configuracion.getFechaIniAct())) {
			msj.add("La fecha de inicio de actividades debe ser válida/completada");
		}
		return msj;
	}

	private Boolean isValidConfig(Configuracion configuracion) {
		if (Objects.isNull(configuracion.getCuitEmpresa()) || configuracion.getCuitEmpresa().trim().equals("")) {
			return false;
		}
		if (Objects.isNull(configuracion.getRazonSocial()) || configuracion.getRazonSocial().trim().equals("")) {
			return false;
		}
		if (Objects.isNull(configuracion.getDomicilioComercial()) || configuracion.getDomicilioComercial().trim().equals("")) {
			return false;
		}
		if (Objects.isNull(configuracion.getTipoEmpresa())) {
			return false;
		}
		if (Objects.isNull(configuracion.getFechaIniAct()) || configuracion.getFechaIniAct().toString().trim().equals("")) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional()
	public Boolean actualizarDataFE() throws BussinessException {
		// Eliminamos primero todos los comprobantes
		Configuracion configuracion = this.appDAO.getConfiguracion();
		if (utilService.getAfipModo().equals("prod") || utilService.getAfipModo().equals("homo")) {
			if (configuracion.getAfipProduccion()) {
				afipDAO.deleteAllTpoCbtes();
				afipDAO.deleteAllTpoIva();
				afipDAO.deleteAllTpoConcepto();
				afipDAO.deleteAllPtoVenta();
				afipDAO.deleteAllTpoDoc();
				afipDAO.deleteAllTpoTributo();
				Map<String, List> map = this.getDataFEFromAfip();
				// traemos  y actulizamos datos de cbte
				List<CbteTipo> cbteTipos = map.get("comprobantes");
				AfipTpoCbte afipTpoCbte = null;
				for (CbteTipo cbteTipo : cbteTipos) {
					afipTpoCbte = new AfipTpoCbte();
					afipTpoCbte.setId(cbteTipo.getId());
					afipTpoCbte.setApp(TenantContext.getCurrentTenant());
					afipTpoCbte.setDescripcion(cbteTipo.getDesc());
					afipDAO.saveOrUpdateTpoCbtes(afipTpoCbte);
				}
				List<IvaTipo> ivaTipos = map.get("ivas");
				AfipTpoIva afipTpoIva = null;
				for (IvaTipo ivaTipo : ivaTipos) {
					afipTpoIva = new AfipTpoIva();
					afipTpoIva.setApp(TenantContext.getCurrentTenant());
					afipTpoIva.setDescripcion(ivaTipo.getDesc());
					afipTpoIva.setId(new Integer(ivaTipo.getId().trim()));
					afipDAO.saveOrUpdateTpoIva(afipTpoIva);
				}
				List<PtoVenta> ptoVentas = map.get("puntosventa");
				AfipPtoVenta afipPtoVenta = null;
				for (PtoVenta ptoVenta : ptoVentas) {
					afipPtoVenta = new AfipPtoVenta();
					afipPtoVenta.setApp(TenantContext.getCurrentTenant());
					afipPtoVenta.setEmisionTipo(ptoVenta.getEmisionTipo());
					afipPtoVenta.setNro(ptoVenta.getNro());
					afipPtoVenta.setBloqueado(ptoVenta.getBloqueado());
					afipDAO.saveOrUpdatePtoVenta(afipPtoVenta);
				}
				List<ConceptoTipo> conceptoTipos = map.get("conceptos");
				AfipTpoConcepto afipTpoConcepto = null;
				for (ConceptoTipo conceptoTipo : conceptoTipos) {
					afipTpoConcepto = new AfipTpoConcepto();
					afipTpoConcepto.setApp(TenantContext.getCurrentTenant());
					afipTpoConcepto.setDescripcion(conceptoTipo.getDesc());
					afipTpoConcepto.setId(conceptoTipo.getId());
					afipDAO.saveOrUpdateTpoConcepto(afipTpoConcepto);
				}
				List<DocTipo> docTipos = map.get("docs");
				AfipTpoDoc afipTpoDoc = null;
				for (DocTipo docTipo : docTipos) {
					afipTpoDoc = new AfipTpoDoc();
					afipTpoDoc.setApp(TenantContext.getCurrentTenant());
					afipTpoDoc.setDescripcion(docTipo.getDesc());
					afipTpoDoc.setId(docTipo.getId());
					afipDAO.saveOrUpdateTpoDoc(afipTpoDoc);
				}
				List<TributoTipo> tributoTipos = map.get("tributos");
				AfipTpoTributo afipTpoTributo = null;
				for (TributoTipo tributoTipo : tributoTipos) {
					afipTpoTributo = new AfipTpoTributo();
					afipTpoTributo.setApp(TenantContext.getCurrentTenant());
					afipTpoTributo.setDescripcion(tributoTipo.getDesc());
					afipTpoTributo.setId(tributoTipo.getId());
					afipDAO.saveOrUpdateTpoTributo(afipTpoTributo);
				}
			}
		}
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<AfipTpoCbte> getTpoCbtes() {
		return afipDAO.getTpoCbtes();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseSolCae solicitarCAEFromEncVentas(CbteEncVenta encVenta, Configuracion configuracion) throws BussinessException {
		// traemos datos para saber si es monotributista u otra cosa
		FEAuthRequest authRequest = null;
		if (utilService.getAfipModo().equals("homo")) {
			authRequest = coneccionFEService.getToken(configuracion);
		}
		if (utilService.getAfipModo().equals("prod")) {
			if (configuracion.getAfipProduccion()) {
				authRequest = coneccionFEService.getToken(configuracion);
			}
		}
		Integer puntoVenta = new Integer(encVenta.getPtoVenta());
		ServiceSoap serviceSoap = this.service.getServiceSoap12();
		FECAERequest feCAERequest = new FECAERequest();
		FECAECabRequest feCAECabRequest = new FECAECabRequest();
		feCAECabRequest.setCantReg(1); // se envia una factura nada mas por ahora
		feCAECabRequest.setCbteTipo(encVenta.getTipoCbte().theState);
		feCAECabRequest.setPtoVta(puntoVenta); // punto de venta
		feCAERequest.setFeCabReq(feCAECabRequest);
		FECAEDetRequest feCAEDetRequest = null;
		ArrayOfFECAEDetRequest aofecaedr = new ArrayOfFECAEDetRequest();
		// traemos el ultimo comprobante
		Integer ultCbte = this.getUltimoCbteAfip(configuracion.getAfipProduccion(), authRequest, puntoVenta, encVenta.getTipoCbte().theState);
		//////para el caso de monotributista
		if (configuracion.getTipoEmpresa() == TipoEmpresa.MONOTRIBUTO) {//monotributista, en teoria solo puede solictar facturas c
			feCAEDetRequest = this.getComprobanteTipoC(encVenta, feCAECabRequest.getCbteTipo(), ultCbte);
			aofecaedr.getFECAEDetRequest().add(feCAEDetRequest);
		}
		// para responsables inscriptos
		if (configuracion.getTipoEmpresa() == TipoEmpresa.RESPONSABLE_INSCRIPTO) {
			if (encVenta.getTipoCbte() == Comprobante.A) {
				feCAEDetRequest = this.getComprobanteTipoA(encVenta, feCAECabRequest.getCbteTipo(), ultCbte);
			}
			if (encVenta.getTipoCbte() == Comprobante.B) {
				feCAEDetRequest = this.getComprobanteTipoB(encVenta, feCAECabRequest.getCbteTipo(), ultCbte);
			}
			aofecaedr.getFECAEDetRequest().add(feCAEDetRequest);
		}
		feCAERequest.setFeDetReq(aofecaedr);
		FECAEResponse response = null;
		if (utilService.getAfipModo().equals("prod")) {
			if (configuracion.getAfipProduccion()) {
				response = serviceSoap.fecaeSolicitar(authRequest, feCAERequest);
			} else {
				response = ConstanstAfipTest.fecaeSolicitarTest(feCAERequest, new Long(configuracion.getCuitEmpresa())); // mandamos el cuit de la empresa
			}
		}
		if (utilService.getAfipModo().equals("homo")) {
			response = serviceSoap.fecaeSolicitar(authRequest, feCAERequest);
		}
		if (utilService.getAfipModo().equals("test")) {
			response = ConstanstAfipTest.fecaeSolicitarTest(feCAERequest, new Long(configuracion.getCuitEmpresa())); // mandamos el cuit de la empresa
		}
		// preparamos la respuesta al cliente
		ResponseSolCae resp = new ResponseSolCae();
		resp.setRequest(feCAEDetRequest);
		resp.setResponse(response);
		resp.setResultado(false);
		if (Objects.nonNull(response)) {
			if (Objects.nonNull(response.getFeCabResp())) {
				if (response.getFeCabResp().getResultado().trim().equals("A")) {
					resp.setResultado(true);
				}
			}
		}
		return resp;
	}

	private Integer getUltimoCbteAfip(Boolean produccion, FEAuthRequest authRequest, Integer ptoVenta, Integer idCbte) {
		Integer ultCbte = null;
		if (utilService.getAfipModo().equals("prod")) {
			if (produccion != null && produccion) {
				ultCbte = this.getUltimoCbteFromAfip(ptoVenta, idCbte, authRequest);
			} else {
				ultCbte = 0;
			}
		}
		if (utilService.getAfipModo().equals("homo")) {
			ultCbte = this.getUltimoCbteFromAfip(ptoVenta, idCbte, authRequest);
		}
		if (utilService.getAfipModo().equals("test")) {
			ultCbte = 0;
		}
		return ultCbte;
	}

	private FECAEDetRequest getComprobanteTipoC(CbteEncVenta factura, Integer cbte, long ultCbte) throws BussinessException {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String fechaCbte = dateFormat.format(factura.getFechaCbte());
		FECAEDetRequest feCAEDetRequest = new FECAEDetRequest();
		feCAEDetRequest.setConcepto(factura.getConcepto().theState);
		feCAEDetRequest.setImpTotConc(0); // segun msj de afip para tipos c debe ser igual a 0
		if (feCAEDetRequest.getConcepto() == 1) {// Producto
		}
		feCAEDetRequest.setCbteDesde(ultCbte + 1);
		feCAEDetRequest.setCbteHasta(ultCbte + 1);
		feCAEDetRequest.setCbteFch(fechaCbte);
		feCAEDetRequest.setImpTotal(factura.getTotal().doubleValue()); // base imp igual al importe neto
		feCAEDetRequest.setImpOpEx(0);
		feCAEDetRequest.setImpIVA(0);
		feCAEDetRequest.setImpTrib(0);
		// el importe neto es el importe total, menos ivas, tributos
		BigDecimal importeNeto = factura.getTotal();
		if (Objects.nonNull(factura.getTotalTrib())) {
			importeNeto = factura.getTotal().subtract(factura.getTotalTrib());
		}
		feCAEDetRequest.setImpNeto(importeNeto.doubleValue());
		this.setClienteForFE(feCAEDetRequest, factura, cbte);
		this.setMoneda(feCAEDetRequest, factura);

		//tributos
		this.setTributosForFE(feCAEDetRequest, factura);
		return feCAEDetRequest;
	}

	private FECAEDetRequest getComprobanteTipoB(CbteEncVenta factura, Integer cbte, long ultCbte) throws BussinessException {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String fechaCbte = dateFormat.format(factura.getFechaCbte());
		FECAEDetRequest feCAEDetRequest = new FECAEDetRequest();
		feCAEDetRequest.setConcepto(factura.getConcepto().theState);
		if (feCAEDetRequest.getConcepto() == Concepto.PRODUCTOS.theState) {// Producto
		}
		if (feCAEDetRequest.getConcepto() == Concepto.SERVICIOS.theState || feCAEDetRequest.getConcepto() == Concepto.PRODUCTOS_SERVICIOS.theState) {// servicios y productos
			String fechaVtoPago = dateFormat.format(factura.getFechaVenPag());
			String fechaServDesde = dateFormat.format(factura.getFechaDesde());
			String fechaServHasta = dateFormat.format(factura.getFechaHasta());
			feCAEDetRequest.setFchServDesde(fechaServDesde);
			feCAEDetRequest.setFchServHasta(fechaServHasta);
			feCAEDetRequest.setFchVtoPago(fechaVtoPago);
		}
		feCAEDetRequest.setCbteDesde(ultCbte + 1);
		feCAEDetRequest.setCbteHasta(ultCbte + 1);
		feCAEDetRequest.setCbteFch(fechaCbte);
		feCAEDetRequest.setImpTotConc(0); // por ahora no  hay importes no gravados, para futuro
		feCAEDetRequest.setImpTotal(factura.getTotal().doubleValue());
		feCAEDetRequest.setImpOpEx(0);
		feCAEDetRequest.setImpIVA(0);
		feCAEDetRequest.setImpNeto(factura.getTotalBaseImp().doubleValue());
		feCAEDetRequest.setImpTrib(0);
		this.setClienteForFE(feCAEDetRequest, factura, cbte);
		this.setMoneda(feCAEDetRequest, factura);

		//tributos
		this.setTributosForFE(feCAEDetRequest, factura);
		//ivas
		this.setIvasForFE(feCAEDetRequest, factura);

		return feCAEDetRequest;
	}

	private FECAEDetRequest getComprobanteTipoA(CbteEncVenta factura, Integer cbte, long ultCbte) throws BussinessException {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String fechaCbte = dateFormat.format(factura.getFechaCbte());
		FECAEDetRequest feCAEDetRequest = new FECAEDetRequest();
		feCAEDetRequest.setConcepto(factura.getConcepto().theState);
		feCAEDetRequest.setImpNeto(factura.getTotalBaseImp().doubleValue()); // en teoria un resp inc puede emitir fac tipo b
		if (feCAEDetRequest.getConcepto() == Concepto.PRODUCTOS.theState) {// Producto
		}
		if (feCAEDetRequest.getConcepto() == Concepto.SERVICIOS.theState || feCAEDetRequest.getConcepto() == Concepto.PRODUCTOS_SERVICIOS.theState) {// servicios y productos
			String fechaVtoPago = dateFormat.format(factura.getFechaVenPag());
			String fechaServDesde = dateFormat.format(factura.getFechaDesde());
			String fechaServHasta = dateFormat.format(factura.getFechaHasta());
			feCAEDetRequest.setFchServDesde(fechaServDesde);
			feCAEDetRequest.setFchServHasta(fechaServHasta);
			feCAEDetRequest.setFchVtoPago(fechaVtoPago);
		}
		feCAEDetRequest.setCbteDesde(ultCbte + 1);
		feCAEDetRequest.setCbteHasta(ultCbte + 1);
		feCAEDetRequest.setCbteFch(fechaCbte);
		feCAEDetRequest.setImpTotConc(0); // por ahora no  hay importes no gravados, para futuro
		feCAEDetRequest.setImpTotal(factura.getTotal().doubleValue());
		feCAEDetRequest.setImpOpEx(0);
		feCAEDetRequest.setImpIVA(0);
		feCAEDetRequest.setImpTrib(0);
		this.setClienteForFE(feCAEDetRequest, factura, cbte);
		this.setMoneda(feCAEDetRequest, factura);

		//tributos
		this.setTributosForFE(feCAEDetRequest, factura);
		//ivas
		this.setIvasForFE(feCAEDetRequest, factura);

		return feCAEDetRequest;
	}

	private void setMoneda(FECAEDetRequest feCAEDetRequest, CbteEncVenta factura) {
		feCAEDetRequest.setMonId("PES");
		feCAEDetRequest.setMonCotiz(1);
	}

	private void setIvasForFE(FECAEDetRequest feCAEDetRequest, CbteEncVenta factura) {
		ArrayOfAlicIva arrayOfAlicIva = this.getIVAsFromEncVenta(factura);
		if (Objects.nonNull(arrayOfAlicIva)) {
			feCAEDetRequest.setImpIVA(factura.getTotalIva().doubleValue());
			feCAEDetRequest.setIva(arrayOfAlicIva);
		}
	}

	private void setTributosForFE(FECAEDetRequest feCAEDetRequest, CbteEncVenta factura) {
		//tributos
		ArrayOfTributo arrayOfTributo = this.getTributosFromEncVenta(factura);
		if (Objects.nonNull(arrayOfTributo)) {
			if (Objects.nonNull(factura.getTotalTrib())) {
				feCAEDetRequest.setImpTrib(factura.getTotalTrib().doubleValue());
				feCAEDetRequest.setTributos(arrayOfTributo);
			}
		}
	}

	/**
	 * Seteamos el cliente realizando las validaciones correspondientes para FE
	 */
	private void setClienteForFE(FECAEDetRequest feCAEDetRequest, CbteEncVenta factura, Integer cbte) {
		Boolean setCli = false;
		//para comprobantes tipo c o b se puede aceptar clientes nullos, siempre y cuando no sobrepasen el limite en el total
		if (cbte == Comprobante.B.theState || cbte == Comprobante.C.theState) {
			if (Objects.nonNull(factura.getCliente())) {
				if (Objects.nonNull(factura.getNroDocCliente()) && Objects.nonNull(factura.getTipoDocCliente())) {
					feCAEDetRequest.setDocNro(new Long(factura.getNroDocCliente()));
					feCAEDetRequest.setDocTipo(factura.getTipoDocCliente().theState);
					setCli = true;
				}
			}
			if (!setCli) {
				if (factura.getTotal().compareTo(new BigDecimal("10000")) > 0) {
					throw new DataIntegrityViolationException("Supero el limite de facturación establecido por la AFIP, debe ingresar un cliente valido.");
				} else {
					feCAEDetRequest.setDocNro(new Long("0"));
					feCAEDetRequest.setDocTipo(99);
				}
			}
		}
		// para cbte tipo a se exige que tipo doc sea cuit (80)
		if (cbte == Comprobante.A.theState) {
			if (Objects.nonNull(factura.getCliente())) {
				if (Objects.nonNull(factura.getNroDocCliente()) && Objects.nonNull(factura.getTipoDocCliente())) {
					if (factura.getTipoDocCliente().equals(Doc.CUIT)) {
						feCAEDetRequest.setDocNro(new Long(factura.getNroDocCliente()));
						feCAEDetRequest.setDocTipo(factura.getTipoDocCliente().theState);
					} else {
						throw new DataIntegrityViolationException("Para comprobantes tipo A se debe ingresar un cliente valido, el tipo de documento debe ser un CUIT");
					}
				} else {
					throw new DataIntegrityViolationException("Para comprobantes tipo A se debe ingresar un cliente valido, con tipo y nro. de documento, y debe ser un CUIT");
				}
			} else {
				throw new DataIntegrityViolationException("Para comprobantes tipo A se debe ingresar un cliente valido");
			}
		}

	}

	private ArrayOfAlicIva getIVAsFromEncVenta(CbteEncVenta cbteEncVenta) {
		if (Objects.isNull(cbteEncVenta.getTributos())) {
			return null;
		} else {
			ArrayOfAlicIva arrayOfAlicIva = new ArrayOfAlicIva();
			cbteEncVenta.getIvas().stream().forEach(x -> {
				AlicIva i = new AlicIva();
				i.setId(x.getIdAfipIva());
				i.setImporte(x.getImporte());
				i.setBaseImp(x.getBaseImponible());
				arrayOfAlicIva.getAlicIva().add(i);
			});
			return arrayOfAlicIva.getAlicIva().size() > 0 ? arrayOfAlicIva : null;
		}
	}

	private ArrayOfTributo getTributosFromEncVenta(CbteEncVenta cbteEncVenta) {
		if (Objects.isNull(cbteEncVenta.getTributos())) {
			return null;
		} else {
			ArrayOfTributo arrayOfTributo = new ArrayOfTributo();
			cbteEncVenta.getTributos().stream().forEach(x -> {
				Tributo t = new Tributo();
				t.setAlic(x.getAlicuota());
				t.setBaseImp(x.getBaseImponible());
				t.setDesc(x.getDescAfipTpoTributo());
				t.setImporte(x.getImporte());
				t.setId(new Short(x.getIdAfipTpoTributo().toString()));
				arrayOfTributo.getTributo().add(t);
			});
			return arrayOfTributo.getTributo().size() > 0 ? arrayOfTributo : null;
		}
	}

	@Override
	@Transactional()
	public CbteVenEncDTO previewFE(AfipEncFacturaDTO factura) {
		Configuracion configuracion = appDAO.getConfiguracion();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		DateFormat dateFormatFI = new SimpleDateFormat("dd-MM-yyyy");
		String fechaCbte = dateFormat.format(factura.getFechaFactura());
		Date fechaVtoCae = null;
		CbteVenEncDTO ev = new CbteVenEncDTO();
		ev.setFechaCarga(new Date());
		if (!Objects.isNull(factura.getClienteApp())) {
			if (!Objects.isNull(factura.getClienteApp().getId())) {
				ev.setCliente(new SimpleObjectDTO(factura.getClienteApp().getId()));
				ev.setNombreCliente(new StringBuilder(factura.getClienteApp().getApellido()).append(" ").append(factura.getClienteApp().getNombre()).toString());
				if (!Objects.isNull(factura.getCliente())) {
					if (factura.getCliente().getTipoDoc() == Doc.DNI) { // el DNI por ahora
						ev.setTipoDocCliente(Doc.DNI);
						ev.setNroDocCliente(factura.getClienteApp().getDni());
					}
					// cuil o cuit
					if (factura.getCliente().getTipoDoc() == Doc.CUIL || factura.getCliente().getTipoDoc() == Doc.CUIT) { // el DNI por ahora
						ev.setTipoDocCliente(Doc.CUIL);
						ev.setNroDocCliente(factura.getClienteApp().getCuil());
					}
				}
			}
		}
//		ev.setFe(true);
		ev.setCae("xxxxxxxxxxxxxxxxxxxxxxx");
		ev.setCaeVenc(new Date());
		ev.setConcepto(factura.getConcepto());
		ev.setFechaDesde(factura.getFechaServDesde());
		ev.setFechaHasta(factura.getFechaServHasta());
		ev.setFechaVenPag(factura.getFechaVenPag());
		ev.setFechaCbte(factura.getFechaFactura());
		ev.setPtoVenta(factura.getPuntoVenta().toString());
		ev.setTipoCbte(factura.getComprobante());
		ev.setTotalBaseImp(new BigDecimal(factura.getTotalBaseImp()));
		ev.setTotalIva(new BigDecimal(factura.getTotalIVAs()));
		ev.setTotalTrib(new BigDecimal(factura.getTotalTributos()));
		ev.setRazonSocialEmpresa(configuracion.getRazonSocial());
		ev.setDomComercialEmpresa(configuracion.getDomicilioComercial());
		ev.setCondicionEmpresa(Constants.CONDICION_AFIP.get(configuracion.getTipoEmpresa()));
		ev.setCuitEmpresa(configuracion.getCuitEmpresa());
		ev.setIngBrutosEmpresa(configuracion.getIngresosBrutos());
		ev.setFechaIniActEmpresa(dateFormatFI.format(configuracion.getFechaIniAct()));
		ev.setCbteNro("X");
		ev.setDomComercialEmpresa(configuracion.getDomicilioComercial());
		ev.setNombreCliente(factura.getCliente().getNombre());
		if (!Objects.isNull(factura.getCliente())) {
			ev.setNroDocCliente(factura.getCliente().getDoc());
			ev.setTipoDocCliente(factura.getCliente().getTipoDoc());
		}
		if (!Objects.isNull(factura.getTipoCliente())) {
			ev.setAfipCondicionCli(factura.getTipoCliente().getName());
		}
//        ev.set
		Set<AfipDetFacturaDTO> items = factura.getItems();
		List<CbteVenDetSinEncabDTO> detalleVentas = new ArrayList<>();
		for (AfipDetFacturaDTO item : items) {
			CbteVenDetSinEncabDTO dv = new CbteVenDetSinEncabDTO();
			dv.setAdicional(BigDecimal.ZERO);
			if (!Objects.isNull(item.getIva())) {
				dv.setIvaDes(item.getIva().getDescripcion());
				dv.setIvaId(item.getIva().getId());
			}
			if (!Objects.isNull(item.getBaseImponible())) {
				dv.setBaseImponible(new BigDecimal(item.getBaseImponible()));
			}
			dv.setCantidad(new BigDecimal(item.getCantidad()));
			dv.setDescuento(BigDecimal.ZERO);
			dv.setOfertaDescuento(BigDecimal.ZERO);
			if (!Objects.isNull(item.getImporteIva())) {
				dv.setImporteIva(new BigDecimal(item.getImporteIva()));
			}
			dv.setOfertaData("");
			if (!Objects.isNull(item.getPrecioSinIva())) {
				dv.setPrecio(new BigDecimal(item.getPrecioSinIva()));
			}
			if (!Objects.isNull(item.getPrecioSinIva())) {
				dv.setPrecioSinIva(new BigDecimal(item.getPrecioSinIva()));
			}
			if (!Objects.isNull(item.getPrecioConIva())) {
				dv.setPrecioConIva(new BigDecimal(item.getPrecioConIva()));
			}
			if (factura.getConcepto() == Concepto.PRODUCTOS) {// si es producto cargamos el producto
				dv.setProducto(modelMapper.map(item.getProducto(), ProductoMinDTO.class
				));
				dv.setDescripcion(item.getNombreProducto());
			} else {
				dv.setDescripcion(item.getServicio());
				dv.setProducto(null);
			}
			dv.setTotal(new BigDecimal(item.getSubtotal()));
			detalleVentas.add(dv);
		}
		ev.setSucursal(factura.getSucursal());
		ev.setDetalleVentas(detalleVentas);
		if (ev.getTipoCbte() == Comprobante.A || ev.getTipoCbte() == Comprobante.B) {
			ev.setIvas(factura.getIvas());
		} else {
			ev.setIvas(null);
		}
		ev.setTributos(factura.getTributos());
		ev.setPagosCbte(factura.getPagoVentas());
		ev.setTotal(new BigDecimal(factura.getTotal()));
		return ev;
	}

	@Override
	@Transactional()
	public void enableProduccionAfip(Integer value) {
		if (value == 1) {
			//1-verificamos que exista el certificado primero para habilitar el modo produccion de afip
			String path = this.getFullPathApp() + "afip" + File.separator;
			if (FileUtils.existFile(path + "certificatePFX.pfx")) {
			} else {
				throw new DataIntegrityViolationException("Error - No existe el certificado CRT");
			}
			try {
				//2- hacemos un test a afip llamando a los tipos de comprobantes para ver que este todo ok
				this.getTipoComprobantes();
			} catch (BussinessException ex) {
				throw new DataIntegrityViolationException("Error - No se puede habilitar el modo en produccion en AFIP, verifique las configuraciones." + ex.getMessage());
			}
			this.appDAO.enabledOrdisabledProdAfip(true);
		}
		if (value == 0) {
			this.appDAO.enabledOrdisabledProdAfip(false);
		}
	}

	@Override
	@Transactional()
	public Map getDataVentasWeb() {
		Map<String, Object> map = new HashMap<>();
		Map mapDataFE = this.getDataFE();
		ArrayNode tiposComprobantes = Constants.getTpoCbtesCompra();
		Map<String, ArrayNode> mapDataVentasTicket = new HashMap<>();
		mapDataVentasTicket.put("comprobantes", tiposComprobantes);
		map.put("dataFE", mapDataFE);
		map.put("dataVentaTicket", mapDataVentasTicket);
		return map;
	}
}
