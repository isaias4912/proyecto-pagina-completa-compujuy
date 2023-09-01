/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.reportes.impl;

import ar.com.jsuper.domain.CbteEncPresupuesto;
import ar.com.jsuper.dto.CbteEncDTO;
import ar.com.jsuper.dto.CbtePresupuestoEncDTO;
import ar.com.jsuper.dto.CbteVenEncDTO;
import ar.com.jsuper.dto.ConfiguracionDTO;
import ar.com.jsuper.services.UtilService;
import ar.com.jsuper.services.reportes.Reporte;
import ar.com.jsuper.utils.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.imageio.ImageIO;

//import net.sf.jasperreports.engine.JRAbstractExporter;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JRExporterParameter;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FilenameUtils;
//import org.apache.commons.lang3.StringUtils;

/**
 * @author rafael
 */
public class ReportesImpl implements Reporte {

    @Value("${path.reportes}")
    protected String pathReportes;
    protected ByteArrayOutputStream baos;
    protected File file;
    @Autowired
    UtilService utilService;
    @Autowired
    private FileUtils fileUtil;
    private Logger logger = Logger.getLogger(ReportesImpl.class);

//    @Override
//    public void loadReport(String reporte) throws JRException {
//        this.file = new File(this.pathReportes.trim() + reporte.trim());
//        this.jasperReport = (JasperReport) JRLoader.loadObject(file);
//    }

//    @Override
//    public byte[] getByteArray(JasperPrint jasperPrint, JRAbstractExporter exporter) throws JRException {
//        ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
//        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, xlsReport);
//        exporter.exportReport();
//        return xlsReport.toByteArray();
//    }

    @Override
    public Map<String, Object> getEncabezadoReporte() {
        return utilService.getEncabezadoReporte();
    }

    //    @Override
    public BufferedImage getImageEncabezadoReporte(String html) {
        HtmlImageGeneratorNS generator = new HtmlImageGeneratorNS();
        generator.loadHtml(html);
        return generator.getBufferedImage();
    }

    public Map<String, Object> getData(CbteVenEncDTO cbteVenEnc, ConfiguracionDTO configuracionDTO) {
        Map<String, Object> parameters = new HashMap<>();
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(this.fileUtil.getDirImagenesConfig() + configuracionDTO.getLogoReporte()));
        } catch (IOException e) {
            logger.error("Error en la imagen del reporte---------");
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        if (Objects.isNull(cbteVenEnc.getAfipValida())) { // si es null lo mandamos a false directamente
            cbteVenEnc.setAfipValida(Boolean.FALSE);
        }
        if (!cbteVenEnc.getAfipValida()) {
            parameters.put("cbte", "X");
            parameters.put("codCbte", "COD. 501");
        } else {
            if (cbteVenEnc.getTipoCbte() == Comprobante.C) {
                parameters.put("cbte", "C");
                parameters.put("codCbte", "COD. 11");
            }
            if (cbteVenEnc.getTipoCbte() == Comprobante.B) {
                parameters.put("cbte", "B");
                parameters.put("codCbte", "COD. 6");
            }
            if (cbteVenEnc.getTipoCbte() == Comprobante.A) {
                parameters.put("cbte", "A");
                parameters.put("codCbte", "COD. 1");
            }
        }

        parameters.put("tipoCbte", cbteVenEnc.getTipoCbte().toString());
        parameters.put("cbteNro", cbteVenEnc.getAfipValida() ? cbteVenEnc.getCbteNro() : "-");
        parameters.put("ptoVenta", cbteVenEnc.getAfipValida() ? cbteVenEnc.getPtoVenta() : "-");
        parameters.put("doc", cbteVenEnc.getNroDocCliente());
        parameters.put("condicionIva", this.getLeyendaTipoEmpresa(cbteVenEnc.getCondicionEmpresa()));
        parameters.put("afipModo", cbteVenEnc.getAfipModo());
        if (Objects.nonNull(cbteVenEnc.getTipoCliente())) {
            parameters.put("condicionIvaCliente", this.getLeyendaTipoCliente(cbteVenEnc.getTipoCliente().toString()));
        } else {
            parameters.put("condicionIvaCliente", "-");
        }

        if (cbteVenEnc.getAfipValida()) {
            if (cbteVenEnc.getTipoFactura().equals(Factura.FACTURA_ELECTRONICA)) {
                parameters.put("codigoBarra", this.calculoDigitoVerificador(cbteVenEnc.getCuitEmpresa(), this.getIdCbteForBarcode(cbteVenEnc.getTipoCbte().theState.toString()),
                        this.getPtoVentaCbteForBarcode(cbteVenEnc.getPtoVenta()), cbteVenEnc.getCae(), dateFormat.format(cbteVenEnc.getCaeVenc())));
            }
        }
        parameters.put("logo", img);
        parameters.put("leyenda", "");
        if (Objects.nonNull(cbteVenEnc.getRazonSocialEmpresa())) {
            parameters.put("razonSocial", cbteVenEnc.getRazonSocialEmpresa());
        } else {
            parameters.put("razonSocial", configuracionDTO.getRazonSocial());
        }
        if (Objects.nonNull(cbteVenEnc.getDomComercialEmpresa())) {
            parameters.put("domicilioComercial", cbteVenEnc.getDomComercialEmpresa());
        } else {
            parameters.put("domicilioComercial", configuracionDTO.getDomicilioComercial());
        }
        if (Objects.nonNull(cbteVenEnc.getCuitEmpresa())) {
            parameters.put("cuit", cbteVenEnc.getCuitEmpresa());
        } else {
            parameters.put("cuit", configuracionDTO.getCuitEmpresa());
        }
        if (Objects.nonNull(cbteVenEnc.getIngBrutosEmpresa())) {
            parameters.put("ingresosBrutos", cbteVenEnc.getIngBrutosEmpresa());
        } else {
            parameters.put("ingresosBrutos", configuracionDTO.getIngresosBrutos());
        }
        if (Objects.nonNull(cbteVenEnc.getFechaIniActEmpresa())) {
            parameters.put("inicioActividad", cbteVenEnc.getFechaIniActEmpresa());
        } else {
            parameters.put("inicioActividad", configuracionDTO.getFechaIniAct());
        }
        parameters.put("id", cbteVenEnc.getId().toString());
        parameters.put("tipoFactura", cbteVenEnc.getTipoFactura().toString());
        parameters.put("transaccion", cbteVenEnc.getId());
        parameters.put("total", cbteVenEnc.getTotal());
        parameters.put("totalIva", cbteVenEnc.getTotalIva());
        parameters.put("totalTrib", cbteVenEnc.getTotalTrib());
        parameters.put("totalBI", cbteVenEnc.getTotalBaseImp());
        parameters.put("idUsuario", cbteVenEnc.getUsuario().getId());
        parameters.put("cae", cbteVenEnc.getCae());
        parameters.put("caeVenc", DateUtils.getDateStringFromDate(cbteVenEnc.getCaeVenc(), "dd-MM-yyyy"));
        parameters.put("nombreUsuario", cbteVenEnc.getUsuario().getUsuario());
        parameters.put("fechaCarga", DateUtils.getDateStringFromDate(cbteVenEnc.getFechaCbte(), "dd-MM-yyyy"));
        parameters.put("horaCarga", DateUtils.getDateStringFromDate(cbteVenEnc.getFechaCbte(), "HH:mm:ss"));
        parameters.put("fechaCbte", DateUtils.getDateStringFromDate(cbteVenEnc.getFechaCbte(), "dd-MM-yyyy"));
        parameters.put("fechaDesde", DateUtils.getDateStringFromDate(cbteVenEnc.getFechaDesde(), "dd-MM-yyyy"));
        parameters.put("fechaHasta", DateUtils.getDateStringFromDate(cbteVenEnc.getFechaHasta(), "dd-MM-yyyy"));
        parameters.put("fechaVenPag", DateUtils.getDateStringFromDate(cbteVenEnc.getFechaVenPag(), "dd-MM-yyyy"));
        parameters.put("afipValida", cbteVenEnc.getAfipValida());
        if (Objects.nonNull(cbteVenEnc.getNombreCliente()) && !cbteVenEnc.getNombreCliente().trim().equals("") && Objects.nonNull(cbteVenEnc.getNroDocCliente())) {
            parameters.put("cliente", true);
        }
        if (Objects.nonNull(cbteVenEnc.getConcepto())) {
            parameters.put("concepto", cbteVenEnc.getConcepto().toString());
        } else {
            parameters.put("concepto", "-");
        }
        if (Objects.nonNull(cbteVenEnc.getNroDocCliente())) {
            parameters.put("nroDocCliente", cbteVenEnc.getNroDocCliente().toString());
        } else {
            parameters.put("nroDocCliente", null);
        }
        if (Objects.nonNull(cbteVenEnc.getTipoDocCliente())) {
            parameters.put("tipoDocCliente", cbteVenEnc.getTipoDocCliente().toString());
        } else {
            parameters.put("tipoDocCliente", null);
        }
        parameters.put("nombreCliente", cbteVenEnc.getNombreCliente());
        parameters.put("direccionCliente", cbteVenEnc.getDireccionCliente());

        return parameters;
    }

    public Map<String, Object> getData(CbteEncDTO cbteEnc, ConfiguracionDTO configuracionDTO) {
        Map<String, Object> parameters = new HashMap<>();
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(this.fileUtil.getDirImagenesConfig() + configuracionDTO.getLogoReporte()));
        } catch (IOException e) {
            logger.error("Error en la imagen del reporte---------");
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        parameters.put("tipoCbte", cbteEnc.getTipoCbte().toString());
        parameters.put("doc", cbteEnc.getNroDocCliente());
        parameters.put("condicionIva", this.getLeyendaTipoEmpresa(cbteEnc.getCondicionEmpresa()));
        if (Objects.nonNull(cbteEnc.getTipoCliente())) {
            parameters.put("condicionIvaCliente", this.getLeyendaTipoCliente(cbteEnc.getTipoCliente().toString()));
        } else {
            parameters.put("condicionIvaCliente", "-");
        }
        parameters.put("logo", img);
        parameters.put("leyenda", "");
        if (Objects.nonNull(cbteEnc.getRazonSocialEmpresa())) {
            parameters.put("razonSocial", cbteEnc.getRazonSocialEmpresa());
        } else {
            parameters.put("razonSocial", configuracionDTO.getRazonSocial());
        }
        if (Objects.nonNull(cbteEnc.getDomComercialEmpresa())) {
            parameters.put("domicilioComercial", cbteEnc.getDomComercialEmpresa());
        } else {
            parameters.put("domicilioComercial", configuracionDTO.getDomicilioComercial());
        }
        if (Objects.nonNull(cbteEnc.getCuitEmpresa())) {
            parameters.put("cuit", cbteEnc.getCuitEmpresa());
        } else {
            parameters.put("cuit", configuracionDTO.getCuitEmpresa());
        }
        if (Objects.nonNull(cbteEnc.getIngBrutosEmpresa())) {
            parameters.put("ingresosBrutos", cbteEnc.getIngBrutosEmpresa());
        } else {
            parameters.put("ingresosBrutos", configuracionDTO.getIngresosBrutos());
        }
        if (Objects.nonNull(cbteEnc.getFechaIniActEmpresa())) {
            parameters.put("inicioActividad", cbteEnc.getFechaIniActEmpresa());
        } else {
            parameters.put("inicioActividad", configuracionDTO.getFechaIniAct());
        }
        parameters.put("id", cbteEnc.getId().toString());
        parameters.put("transaccion", cbteEnc.getId());
        parameters.put("total", cbteEnc.getTotal());
        parameters.put("idUsuario", cbteEnc.getUsuario().getId());
        parameters.put("nombreUsuario", cbteEnc.getUsuario().getUsuario());
        parameters.put("fechaCarga", DateUtils.getDateStringFromDate(cbteEnc.getFechaCarga(), "dd-MM-yyyy"));
        parameters.put("fechaDesde", DateUtils.getDateStringFromDate(cbteEnc.getFechaDesde(), "dd-MM-yyyy"));
        parameters.put("fechaHasta", DateUtils.getDateStringFromDate(cbteEnc.getFechaHasta(), "dd-MM-yyyy"));
        if (Objects.nonNull(cbteEnc.getNombreCliente()) && !cbteEnc.getNombreCliente().trim().equals("") && Objects.nonNull(cbteEnc.getNroDocCliente())) {
            parameters.put("cliente", true);
        }
        if (Objects.nonNull(cbteEnc.getConcepto())) {
            parameters.put("concepto", cbteEnc.getConcepto().toString());
        } else {
            parameters.put("concepto", "-");
        }
        if (Objects.nonNull(cbteEnc.getNroDocCliente())) {
            parameters.put("nroDocCliente", cbteEnc.getNroDocCliente().toString());
        } else {
            parameters.put("nroDocCliente", null);
        }
        if (Objects.nonNull(cbteEnc.getTipoDocCliente())) {
            parameters.put("tipoDocCliente", cbteEnc.getTipoDocCliente().toString());
        } else {
            parameters.put("tipoDocCliente", null);
        }
        parameters.put("nombreCliente", cbteEnc.getNombreCliente());
        parameters.put("direccionCliente", cbteEnc.getDireccionCliente());

        return parameters;
    }

    @Override
    public Map<String, Object> getData(CbteVenEncDTO cbteVenEnc) {
        Map<String, Object> parameters = this.getData(cbteVenEnc, this.utilService.getConfiguracionFromApp());
        parameters.put("cbte", cbteVenEnc.getTipoCbte().toString());
        parameters.put("codCbte", "COD. " + cbteVenEnc.getTipoCbte().theState);
        parameters.put("items", cbteVenEnc.getDetalleVentas());
        parameters.put("ivas", cbteVenEnc.getIvas());
        parameters.put("tributos", cbteVenEnc.getTributos());
        parameters.put("pagos", cbteVenEnc.getPagosCbte());
        parameters.put("totalIva", cbteVenEnc.getTotalIva());
        parameters.put("totalTrib", cbteVenEnc.getTotalTrib());
        parameters.put("totalBI", cbteVenEnc.getTotalBaseImp());
        return parameters;
    }

    @Override
    public Map<String, Object> getData(CbtePresupuestoEncDTO cbtePresupuestoEncDTO) {
        Map<String, Object> parameters = this.getData(cbtePresupuestoEncDTO, this.utilService.getConfiguracionFromApp());
        parameters.put("cbte", "PR");
        parameters.put("codCbte", "COD. " + cbtePresupuestoEncDTO.getTipoCbte().theState);
        parameters.put("estadoCbte", cbtePresupuestoEncDTO.getEstadoCbte());
        parameters.put("fechaVigencia", cbtePresupuestoEncDTO.getFechaVigencia());
        parameters.put("tipo", TipoCbte.PRESUPUESTO.toString());
        parameters.put("items", cbtePresupuestoEncDTO.getDetalleVentas());
        parameters.put("ivas", null);
        parameters.put("tributos", null);
        parameters.put("pagos", cbtePresupuestoEncDTO.getPagosCbte());
        parameters.put("totalIva", BigDecimal.ZERO);
        parameters.put("totalTrib", BigDecimal.ZERO);
        parameters.put("totalBI", BigDecimal.ZERO);
        return parameters;
    }

    /**
     * Segun a la afip https://www.afip.gob.ar/genericos/guiavirtual/consultas_detalle.aspx?id=130014 el id de cbt tiene q tener 3 caracteres
     *
     * @param id
     * @return
     */
    private String getIdCbteForBarcode(String id) {
        if (id.length() == 1) {
            return "00" + id;
        }
        if (id.length() == 2) {
            return "0" + id;
        }
        if (id.length() == 3) {
            return id;
        }
        return "000"; // por defecto por el momento le mando 3 ceros, no deberia pasar esto
    }

    /**
     * Segun a la afip https://www.afip.gob.ar/genericos/guiavirtual/consultas_detalle.aspx?id=130014 el id de cbt tiene q tener 3 caracteres
     *
     * @param ptoVenta
     * @return
     */
    private String getPtoVentaCbteForBarcode(String ptoVenta) {
        if (ptoVenta.length() == 1) {
            return "0000" + ptoVenta;
        }
        if (ptoVenta.length() == 2) {
            return "000" + ptoVenta;
        }
        if (ptoVenta.length() == 3) {
            return "00" + ptoVenta;
        }
        if (ptoVenta.length() == 4) {
            return "0" + ptoVenta;
        }
        if (ptoVenta.length() == 5) {
            return ptoVenta;
        }

        return "00000"; // por defecto por el momento le mando 3 ceros, no deberia pasar esto
    }

    /**
     * @param txtCuit    CUIT del EMISOR del comprobante sin guiones
     * @param txtCodComp codigo de comprobantes dos digitos
     * @param txtPtoVta  punto de venta del comprobante 4 digitos
     * @param txtCae     cae
     * @param txtVtoCae  vencimiento del cae en formato yyyyMMdd
     * @return devuelve el string completo para utilizar en el codigo de barras
     */
    public String calculoDigitoVerificador(String txtCuit, String txtCodComp,
                                           String txtPtoVta, String txtCae, String txtVtoCae) {
        int i;
        String cod;
        String txtDigito;
        int impares;
        int pares;
        int total;
        int digito;
        String txtCodBarra;
        cod = txtCuit + txtCodComp + txtPtoVta + txtCae + txtVtoCae;
        txtCodBarra = cod;
//
// Ahora analizo la cadena de caracteres:
// Tengo que sumar todos los caracteres impares y los pares
        pares = 0;
        impares = 0;
        for (i = 1; i < cod.length(); i++) {
//
// If I Mod 2 = 0 Then
            if (i % 2 == 0) {
// es par
// Pares = Pares + CLng(Mid(Cod, I, 1))
                pares += Integer.valueOf(StringUtils.mid(cod, i - 1, 1));
            } else {
// es impar
// Impares = Impares + CLng(Mid(Cod, I, 1))
                impares += Integer.valueOf(StringUtils.mid(cod, i - 1, 1));
            }
        }
//
        impares = 3 * impares;
        total = pares + impares;
        digito = 10 - (total % 10);
//
        if (digito == 10) {
            digito = 0;
        }
        return cod + digito;
    }

    private String getLeyendaTipoEmpresa(String tipoEmpresa) {
        if (TipoEmpresa.RESPONSABLE_INSCRIPTO.toString().equals(tipoEmpresa)) {
            return "Resp. Insc";
        }
        if (TipoEmpresa.EXCENTO.toString().equals(tipoEmpresa)) {
            return "Excento";
        }
        if (TipoEmpresa.MONOTRIBUTO.toString().equals(tipoEmpresa)) {
            return "Monotributo";
        }
        return "-";
    }

    private String getLeyendaTipoCliente(String tipoCliente) {
        if (TipoCliente.RESPONSABLE_INSCRIPTO.toString().equals(tipoCliente)) {
            return "Resp. Insc";
        }
        if (TipoCliente.EXCENTO.toString().equals(tipoCliente)) {
            return "Excento";
        }
        if (TipoCliente.MONOTRIBUTO.toString().equals(tipoCliente)) {
            return "Monotributo";
        }
        if (TipoCliente.CONSUMIDOR_FINAL.toString().equals(tipoCliente)) {
            return "Cons. final";
        }
        if (TipoCliente.SIN_ESPECIFICAR.toString().equals(tipoCliente)) {
            return "-";
        }
        return "-";
    }

    @Override
    public Map<String, Object> getBaseData() {
        Map<String, Object> parameters = new HashMap<>();
        Map<String, Object> encabezado = this.getEncabezadoReporte();
        BufferedImage img = null;
        if (!Objects.isNull(encabezado)) {
            try {
                String name = FilenameUtils.getBaseName(encabezado.get("logo").toString());
                String ext = FilenameUtils.getExtension(encabezado.get("logo").toString());
                String nameFile = String.format("%s.%s", name + "_128", ext);
                try {
                    img = ImageIO.read(new File(this.fileUtil.getDirImagenesConfig()+ "thumbnails/" + nameFile));
                } catch (IOException e) {
                    logger.error("Error en la imagen del reporte---------");
                }
            } catch (NullPointerException npe) {
                logger.error("Error en la imagen del reporte no existe el logo");
            }
//			Agregamos los div para el ancho
            StringBuilder ste = new StringBuilder(encabezado.get("encabezado").toString());
            ste.append("</div></div>");
//			ste.insert(0, "<div style='width: 1000px; border:solid 1px #000000'>");
            ste.insert(0, "<div style='width:2000px;'><div style='width: 100%; '>");
//			parameters.put("encabezado", this.getImageEncabezadoReporte(encabezado.get("encabezado").toString()));
//			parameters.put("encabezado", ste.toString());
            try {
//                parameters.put("encabezado", this.getImageEncabezadoReporte(encabezado.get("encabezado").toString()));
                parameters.put("encabezado", this.getImageEncabezadoReporte(ste.toString()));
            } catch (NullPointerException npe) {
                logger.error("Error en la imagen del reporte no existe el encabezado");
            }
        } else {
            parameters.put("encabezado", null);
        }
        parameters.put("logo", img);
        parameters.put("leyenda", "");
        parameters.put("razonSocial", encabezado.get("razonSocial"));
        parameters.put("domicilioComercial", encabezado.get("domicilioComercial"));
        parameters.put("cuitEmpresa", encabezado.get("cuitEmpresa"));
        return parameters;
    }
}
