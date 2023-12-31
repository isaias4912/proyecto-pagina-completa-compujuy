
package ar.com.jsuper.services.factElec.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ServiceSoap", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ServiceSoap {


    /**
     * Solicitud de Código de Autorización Electrónico (CAE)
     * 
     * @param feCAEReq
     * @param auth
     * @return
     *     returns ar.com.compujuy.facelec.ws.FECAEResponse
     */
    @WebMethod(operationName = "FECAESolicitar", action = "http://ar.gov.afip.dif.FEV1/FECAESolicitar")
    @WebResult(name = "FECAESolicitarResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FECAESolicitar", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FECAESolicitar")
    @ResponseWrapper(localName = "FECAESolicitarResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FECAESolicitarResponse")
    public FECAEResponse fecaeSolicitar(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth,
        @WebParam(name = "FeCAEReq", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FECAERequest feCAEReq);

    /**
     * Retorna la cantidad maxima de registros que puede tener una invocacion al metodo FECAESolicitar / FECAEARegInformativo 
     * 
     * @param auth
     * @return
     *     returns ar.com.compujuy.facelec.ws.FERegXReqResponse
     */
    @WebMethod(operationName = "FECompTotXRequest", action = "http://ar.gov.afip.dif.FEV1/FECompTotXRequest")
    @WebResult(name = "FECompTotXRequestResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FECompTotXRequest", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FECompTotXRequest")
    @ResponseWrapper(localName = "FECompTotXRequestResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FECompTotXRequestResponse")
    public FERegXReqResponse feCompTotXRequest(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth);

    /**
     * Metodo dummy para verificacion de funcionamiento
     * 
     * @return
     *     returns ar.com.compujuy.facelec.ws.DummyResponse
     */
    @WebMethod(operationName = "FEDummy", action = "http://ar.gov.afip.dif.FEV1/FEDummy")
    @WebResult(name = "FEDummyResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FEDummy", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FEDummy")
    @ResponseWrapper(localName = "FEDummyResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FEDummyResponse")
    public DummyResponse feDummy();

    /**
     * Retorna el ultimo comprobante autorizado para el tipo de comprobante / cuit / punto de venta ingresado / Tipo de Emisión
     * 
     * @param cbteTipo
     * @param auth
     * @param ptoVta
     * @return
     *     returns ar.com.compujuy.facelec.ws.FERecuperaLastCbteResponse
     */
    @WebMethod(operationName = "FECompUltimoAutorizado", action = "http://ar.gov.afip.dif.FEV1/FECompUltimoAutorizado")
    @WebResult(name = "FECompUltimoAutorizadoResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FECompUltimoAutorizado", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FECompUltimoAutorizado")
    @ResponseWrapper(localName = "FECompUltimoAutorizadoResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FECompUltimoAutorizadoResponse")
    public FERecuperaLastCbteResponse feCompUltimoAutorizado(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth,
        @WebParam(name = "PtoVta", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        int ptoVta,
        @WebParam(name = "CbteTipo", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        int cbteTipo);

    /**
     * Consulta Comprobante emitido y su código.
     * 
     * @param auth
     * @param feCompConsReq
     * @return
     *     returns ar.com.compujuy.facelec.ws.FECompConsultaResponse
     */
    @WebMethod(operationName = "FECompConsultar", action = "http://ar.gov.afip.dif.FEV1/FECompConsultar")
    @WebResult(name = "FECompConsultarResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FECompConsultar", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FECompConsultar")
    @ResponseWrapper(localName = "FECompConsultarResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FECompConsultarResponse")
    public FECompConsultaResponse feCompConsultar(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth,
        @WebParam(name = "FeCompConsReq", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FECompConsultaReq feCompConsReq);

    /**
     * Rendición de comprobantes asociados a un CAEA.
     * 
     * @param auth
     * @param feCAEARegInfReq
     * @return
     *     returns ar.com.compujuy.facelec.ws.FECAEAResponse
     */
    @WebMethod(operationName = "FECAEARegInformativo", action = "http://ar.gov.afip.dif.FEV1/FECAEARegInformativo")
    @WebResult(name = "FECAEARegInformativoResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FECAEARegInformativo", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FECAEARegInformativo")
    @ResponseWrapper(localName = "FECAEARegInformativoResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FECAEARegInformativoResponse")
    public FECAEAResponse fecaeaRegInformativo(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth,
        @WebParam(name = "FeCAEARegInfReq", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FECAEARequest feCAEARegInfReq);

    /**
     * Solicitud de Código de Autorización Electrónico Anticipado (CAEA)
     * 
     * @param auth
     * @param periodo
     * @param orden
     * @return
     *     returns ar.com.compujuy.facelec.ws.FECAEAGetResponse
     */
    @WebMethod(operationName = "FECAEASolicitar", action = "http://ar.gov.afip.dif.FEV1/FECAEASolicitar")
    @WebResult(name = "FECAEASolicitarResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FECAEASolicitar", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FECAEASolicitar")
    @ResponseWrapper(localName = "FECAEASolicitarResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FECAEASolicitarResponse")
    public FECAEAGetResponse fecaeaSolicitar(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth,
        @WebParam(name = "Periodo", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        int periodo,
        @WebParam(name = "Orden", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        short orden);

    /**
     * Consulta CAEA informado como sin movimientos.
     * 
     * @param caea
     * @param auth
     * @param ptoVta
     * @return
     *     returns ar.com.compujuy.facelec.ws.FECAEASinMovConsResponse
     */
    @WebMethod(operationName = "FECAEASinMovimientoConsultar", action = "http://ar.gov.afip.dif.FEV1/FECAEASinMovimientoConsultar")
    @WebResult(name = "FECAEASinMovimientoConsultarResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FECAEASinMovimientoConsultar", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FECAEASinMovimientoConsultar")
    @ResponseWrapper(localName = "FECAEASinMovimientoConsultarResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FECAEASinMovimientoConsultarResponse")
    public FECAEASinMovConsResponse fecaeaSinMovimientoConsultar(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth,
        @WebParam(name = "CAEA", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        String caea,
        @WebParam(name = "PtoVta", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        int ptoVta);

    /**
     * Informa CAEA sin movimientos.
     * 
     * @param caea
     * @param auth
     * @param ptoVta
     * @return
     *     returns ar.com.compujuy.facelec.ws.FECAEASinMovResponse
     */
    @WebMethod(operationName = "FECAEASinMovimientoInformar", action = "http://ar.gov.afip.dif.FEV1/FECAEASinMovimientoInformar")
    @WebResult(name = "FECAEASinMovimientoInformarResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FECAEASinMovimientoInformar", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FECAEASinMovimientoInformar")
    @ResponseWrapper(localName = "FECAEASinMovimientoInformarResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FECAEASinMovimientoInformarResponse")
    public FECAEASinMovResponse fecaeaSinMovimientoInformar(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth,
        @WebParam(name = "PtoVta", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        int ptoVta,
        @WebParam(name = "CAEA", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        String caea);

    /**
     * Consultar CAEA emitidos.
     * 
     * @param auth
     * @param periodo
     * @param orden
     * @return
     *     returns ar.com.compujuy.facelec.ws.FECAEAGetResponse
     */
    @WebMethod(operationName = "FECAEAConsultar", action = "http://ar.gov.afip.dif.FEV1/FECAEAConsultar")
    @WebResult(name = "FECAEAConsultarResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FECAEAConsultar", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FECAEAConsultar")
    @ResponseWrapper(localName = "FECAEAConsultarResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FECAEAConsultarResponse")
    public FECAEAGetResponse fecaeaConsultar(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth,
        @WebParam(name = "Periodo", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        int periodo,
        @WebParam(name = "Orden", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        short orden);

    /**
     * Recupera la cotizacion de la moneda consultada y su  fecha 
     * 
     * @param monId
     * @param auth
     * @return
     *     returns ar.com.compujuy.facelec.ws.FECotizacionResponse
     */
    @WebMethod(operationName = "FEParamGetCotizacion", action = "http://ar.gov.afip.dif.FEV1/FEParamGetCotizacion")
    @WebResult(name = "FEParamGetCotizacionResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FEParamGetCotizacion", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FEParamGetCotizacion")
    @ResponseWrapper(localName = "FEParamGetCotizacionResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FEParamGetCotizacionResponse")
    public FECotizacionResponse feParamGetCotizacion(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth,
        @WebParam(name = "MonId", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        String monId);

    /**
     * Recupera el listado de los diferente tributos que pueden ser utilizados  en el servicio de autorizacion
     * 
     * @param auth
     * @return
     *     returns ar.com.compujuy.facelec.ws.FETributoResponse
     */
    @WebMethod(operationName = "FEParamGetTiposTributos", action = "http://ar.gov.afip.dif.FEV1/FEParamGetTiposTributos")
    @WebResult(name = "FEParamGetTiposTributosResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FEParamGetTiposTributos", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FEParamGetTiposTributos")
    @ResponseWrapper(localName = "FEParamGetTiposTributosResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FEParamGetTiposTributosResponse")
    public FETributoResponse feParamGetTiposTributos(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth);

    /**
     * Recupera el listado de monedas utilizables en servicio de autorización
     * 
     * @param auth
     * @return
     *     returns ar.com.compujuy.facelec.ws.MonedaResponse
     */
    @WebMethod(operationName = "FEParamGetTiposMonedas", action = "http://ar.gov.afip.dif.FEV1/FEParamGetTiposMonedas")
    @WebResult(name = "FEParamGetTiposMonedasResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FEParamGetTiposMonedas", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FEParamGetTiposMonedas")
    @ResponseWrapper(localName = "FEParamGetTiposMonedasResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FEParamGetTiposMonedasResponse")
    public MonedaResponse feParamGetTiposMonedas(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth);

    /**
     * Recupera el listado  de Tipos de Iva utilizables en servicio de autorización.
     * 
     * @param auth
     * @return
     *     returns ar.com.compujuy.facelec.ws.IvaTipoResponse
     */
    @WebMethod(operationName = "FEParamGetTiposIva", action = "http://ar.gov.afip.dif.FEV1/FEParamGetTiposIva")
    @WebResult(name = "FEParamGetTiposIvaResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FEParamGetTiposIva", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FEParamGetTiposIva")
    @ResponseWrapper(localName = "FEParamGetTiposIvaResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FEParamGetTiposIvaResponse")
    public IvaTipoResponse feParamGetTiposIva(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth);

    /**
     * Recupera el listado de identificadores para los campos Opcionales
     * 
     * @param auth
     * @return
     *     returns ar.com.compujuy.facelec.ws.OpcionalTipoResponse
     */
    @WebMethod(operationName = "FEParamGetTiposOpcional", action = "http://ar.gov.afip.dif.FEV1/FEParamGetTiposOpcional")
    @WebResult(name = "FEParamGetTiposOpcionalResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FEParamGetTiposOpcional", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FEParamGetTiposOpcional")
    @ResponseWrapper(localName = "FEParamGetTiposOpcionalResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FEParamGetTiposOpcionalResponse")
    public OpcionalTipoResponse feParamGetTiposOpcional(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth);

    /**
     * Recupera el listado  de identificadores para el campo Concepto.
     * 
     * @param auth
     * @return
     *     returns ar.com.compujuy.facelec.ws.ConceptoTipoResponse
     */
    @WebMethod(operationName = "FEParamGetTiposConcepto", action = "http://ar.gov.afip.dif.FEV1/FEParamGetTiposConcepto")
    @WebResult(name = "FEParamGetTiposConceptoResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FEParamGetTiposConcepto", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FEParamGetTiposConcepto")
    @ResponseWrapper(localName = "FEParamGetTiposConceptoResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FEParamGetTiposConceptoResponse")
    public ConceptoTipoResponse feParamGetTiposConcepto(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth);

    /**
     * Recupera el listado de puntos de venta registrados y su estado
     * 
     * @param auth
     * @return
     *     returns ar.com.compujuy.facelec.ws.FEPtoVentaResponse
     */
    @WebMethod(operationName = "FEParamGetPtosVenta", action = "http://ar.gov.afip.dif.FEV1/FEParamGetPtosVenta")
    @WebResult(name = "FEParamGetPtosVentaResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FEParamGetPtosVenta", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FEParamGetPtosVenta")
    @ResponseWrapper(localName = "FEParamGetPtosVentaResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FEParamGetPtosVentaResponse")
    public FEPtoVentaResponse feParamGetPtosVenta(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth);

    /**
     * Recupera el listado  de Tipos de Comprobantes utilizables en servicio de autorización.
     * 
     * @param auth
     * @return
     *     returns ar.com.compujuy.facelec.ws.CbteTipoResponse
     */
    @WebMethod(operationName = "FEParamGetTiposCbte", action = "http://ar.gov.afip.dif.FEV1/FEParamGetTiposCbte")
    @WebResult(name = "FEParamGetTiposCbteResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FEParamGetTiposCbte", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FEParamGetTiposCbte")
    @ResponseWrapper(localName = "FEParamGetTiposCbteResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FEParamGetTiposCbteResponse")
    public CbteTipoResponse feParamGetTiposCbte(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth);

    /**
     * Recupera el listado  de Tipos de Documentos utilizables en servicio de autorización.
     * 
     * @param auth
     * @return
     *     returns ar.com.compujuy.facelec.ws.DocTipoResponse
     */
    @WebMethod(operationName = "FEParamGetTiposDoc", action = "http://ar.gov.afip.dif.FEV1/FEParamGetTiposDoc")
    @WebResult(name = "FEParamGetTiposDocResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FEParamGetTiposDoc", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FEParamGetTiposDoc")
    @ResponseWrapper(localName = "FEParamGetTiposDocResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FEParamGetTiposDocResponse")
    public DocTipoResponse feParamGetTiposDoc(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth);

    /**
     * Recupera el listado de los diferente paises que pueden ser utilizados  en el servicio de autorizacion
     * 
     * @param auth
     * @return
     *     returns ar.com.compujuy.facelec.ws.FEPaisResponse
     */
    @WebMethod(operationName = "FEParamGetTiposPaises", action = "http://ar.gov.afip.dif.FEV1/FEParamGetTiposPaises")
    @WebResult(name = "FEParamGetTiposPaisesResult", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
    @RequestWrapper(localName = "FEParamGetTiposPaises", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FEParamGetTiposPaises")
    @ResponseWrapper(localName = "FEParamGetTiposPaisesResponse", targetNamespace = "http://ar.gov.afip.dif.FEV1/", className = "ar.com.compujuy.facelec.ws.FEParamGetTiposPaisesResponse")
    public FEPaisResponse feParamGetTiposPaises(
        @WebParam(name = "Auth", targetNamespace = "http://ar.gov.afip.dif.FEV1/")
        FEAuthRequest auth);

}
