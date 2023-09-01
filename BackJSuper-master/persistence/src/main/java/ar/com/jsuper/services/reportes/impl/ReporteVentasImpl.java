/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.reportes.impl;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.utils.DetalleVentasTicket;
import ar.com.jsuper.dto.*;
import ar.com.jsuper.services.CajasService;
import ar.com.jsuper.services.UsuariosService;
import ar.com.jsuper.services.UtilService;
import ar.com.jsuper.services.reportes.FooterDefaultPageEvent;
import ar.com.jsuper.services.reportes.HeaderDefaultPageEvent;
import ar.com.jsuper.services.reportes.ReportUtil;
import ar.com.jsuper.services.reportes.ReporteVentas;
import ar.com.jsuper.utils.Comprobante;
import ar.com.jsuper.utils.Constants;
import ar.com.jsuper.utils.DateUtils;
import ar.com.jsuper.utils.NumbersUtils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;

/**
 * @author rafael
 */
@Service
public class ReporteVentasImpl extends ReportesImpl implements ReporteVentas {

    @Autowired
    private UsuariosService usuariosService;
    @Autowired
    private CajasService cajasService;
    @Autowired
    private UtilService utilService;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${afip.qr.url}")
    protected String afipQrUrl;
    private static final float THERMAL_58_PAGE_WIDTH = 140;
    private static final float THERMAL_58_PAGE_HEIGHT = 14400; // max page height size
    private static final float THERMAL_80_PAGE_WIDTH = 190;
    private static final float THERMAL_80_PAGE_HEIGHT = 14400; // max page height size

//    @Override
//    public byte[] printListVentasPDF(List<CbteVenEncMinDTO> ventasListDTO, FilterVentasDTO filterVentasDTO) throws JRException, BussinessException {
////		List<CbteVenEncMinDTO> ventasListDTO = ventasService.getVentas(filterVentasDTO, fieldOrder, reverse);
//        this.loadReport("listadoVentasReport.jasper");
//        this.baos = new ByteArrayOutputStream();
//        /* Convert List to JRBeanCollectionDataSource */
//        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(ventasListDTO);
//        /* Map to hold Jasper report Parameters */
//        Map<String, Object> parameters = this.getBaseData();
//        parameters.put("listDataSource", itemsJRBean);
//        parameters.put("leyenda", this.getLeyendaListadoVentas(filterVentasDTO).toString());
////        jasperPrint = JasperFillManager.fillReport(this.jasperReport, null, new JRBeanCollectionDataSource(productoListDTO));
//        jasperPrint = JasperFillManager.fillReport(this.jasperReport, parameters, new JREmptyDataSource());
//        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
//        return this.baos.toByteArray();
//    }

//    @Override
//    public byte[] printListVentas(List<CbteVenEncMinDTO> ventasListDTO, FilterVentasDTO filterVentasDTO, String typePrint) throws JRException, BussinessException {
////		List<CbteVenEncMinDTO> ventasListDTO = ventasService.getVentas(filterVentasDTO, fieldOrder, reverse);
//        this.loadReport("listadoVentasReport.jasper");
//        /* Convert List to JRBeanCollectionDataSource */
//        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(ventasListDTO);
//        /* Map to hold Jasper report Parameters */
//        Map<String, Object> parameters = this.getBaseData();
//        parameters.put("listDataSource", itemsJRBean);
//        parameters.put("leyenda", this.getLeyendaListadoVentas(filterVentasDTO).toString());
//        jasperPrint = JasperFillManager.fillReport(this.jasperReport, parameters, new JREmptyDataSource());
//        JRAbstractExporter exporter = null;
//        if (typePrint.equals("xls")) {
//            exporter = new JRXlsxExporter();
//        }
//        if (typePrint.equals("html")) {
//            exporter = new HtmlExporter();
//        }
//        if (typePrint.equals("html")) {
//            exporter = new HtmlExporter();
//        }
//        if (typePrint.equals("csv")) {
//            exporter = new JRCsvExporter();
//        }
//        if (typePrint.equals("doc")) {
//            exporter = new JRDocxExporter();
//        }
//        return this.getByteArray(jasperPrint, exporter);
//    }

    @Override
    public byte[] printTicketFactura(String tipo, CbteVenEncDTO cbteVenEncDTO) {
        if (tipo.equals("58mm")) {
            return this.printTicketFacturaPDF(tipo, cbteVenEncDTO);
        }
        if (tipo.equals("80mm")) {
            return this.printTicketFacturaPDF(tipo, cbteVenEncDTO);
        }
        if (tipo.equals("A4")) {
            return this.printCbteVenPDF(cbteVenEncDTO);
        }
        throw new DataIntegrityViolationException("El parametro tipo de ticket no se corresponde con ninguno existente.");

    }

    @Override
    public byte[] printCbtePresupueso(String tipo, CbtePresupuestoEncDTO cbtePresupuestoEncDTO) {
        if (tipo.equals("A4")) {
            return this.printCbtePresupuestoPDF(cbtePresupuestoEncDTO);
        }
        throw new DataIntegrityViolationException("El parametro tipo de ticket no se corresponde con ninguno existente.");

    }

    @Override
    public byte[] printTicketFacturaPDF(String tipo, CbteVenEncDTO cbteVenEncDTO) {
        ConfiguracionDTO configuracion = this.utilService.getConfiguracionFromApp();
        Map<String, Object> parameters = this.getBaseData();
        parameters.putAll(this.getData(cbteVenEncDTO));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        // Creating a Document object
        Document doc = null;
        if (tipo.equals("58mm")) {
            doc = new Document(pdf, new PageSize(THERMAL_58_PAGE_WIDTH, THERMAL_58_PAGE_HEIGHT));
        }
        if (tipo.equals("80mm")) {
            doc = new Document(pdf, new PageSize(THERMAL_80_PAGE_WIDTH, THERMAL_80_PAGE_HEIGHT));
        }
        HeaderDefaultPageEvent handlerHeader = new HeaderDefaultPageEvent(doc, ReportUtil.getHeaderInvoiceTicket(parameters), Collections.emptyList(), 2f);
        pdf.addEventHandler(PdfDocumentEvent.START_PAGE, handlerHeader);
        List<CbteVenDetSinEncabDTO> details = (List<CbteVenDetSinEncabDTO>) parameters.get("items");
        Table tableDetail = this.getTableDetailTicket(parameters.get("tipoCbte").toString(), this.getDetallesTicket(details, configuracion), NumbersUtils.roundForPrint(parameters.get("total")));
        doc.add(tableDetail);
        // add daga afip if belongs
        doc.add(this.getTableAfipDataTicket(cbteVenEncDTO));
        doc.close();
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public byte[] printCbteVenPDF(CbteVenEncDTO cbteVenEncDTO) {
        Map<String, Object> parameters = this.getBaseData();
        parameters.putAll(this.getData(cbteVenEncDTO));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        // Creating a Document object
        Document doc = new Document(pdf, PageSize.A4);
        List<Table> tablesSubHeader = new ArrayList<>();
        tablesSubHeader.add(ReportUtil.getTableCliente(parameters));
        List<Table> tablesSubFooter = new ArrayList<>();
        tablesSubFooter.add(this.getTableTotalFull(parameters).setBorderTop(new SolidBorder(1f)));
        if (cbteVenEncDTO.getAfipValida()) {
            tablesSubFooter.add(this.getTableAfipData(cbteVenEncDTO));
        }
        HeaderDefaultPageEvent handlerHeader = new HeaderDefaultPageEvent(doc, ReportUtil.getHeaderInvoice(parameters.get("logo"), parameters), tablesSubHeader);
        FooterDefaultPageEvent handlerFooter = new FooterDefaultPageEvent(doc, tablesSubFooter);
        pdf.addEventHandler(PdfDocumentEvent.START_PAGE, handlerHeader);
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, handlerFooter);
        List<CbteVenDetSinEncabDTO> details = (List<CbteVenDetSinEncabDTO>) parameters.get("items");
        Table tableDetail = this.getTableDetail(parameters.get("tipoCbte").toString(), details);
        doc.add(tableDetail);
        doc.add(ReportUtil.getLineSeparatorFinish(handlerFooter.getHeightFooter()));
        doc.close();
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] printCbtePresupuestoPDF(CbtePresupuestoEncDTO cbtePresupuestoEncDTO) {
        Map<String, Object> parameters = this.getBaseData();
        parameters.putAll(this.getData(cbtePresupuestoEncDTO));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        // Creating a Document object
        Document doc = new Document(pdf);
        parameters.put("titulo", "Presupuesto");
        List<Table> tablesSubHeader = new ArrayList<>();
        tablesSubHeader.add(ReportUtil.getTableCliente(parameters));
        List<Table> tablesSubFooter = new ArrayList<>();
        tablesSubFooter.add(this.getTableMsj(new String[]{"Precios sujetos a modificación.", "Condición de pago a definir."}).setMarginBottom(5f));
        tablesSubFooter.add(this.getTableTotal(NumbersUtils.roundForPrint(parameters.get("total"))));
        HeaderDefaultPageEvent handlerHeader = new HeaderDefaultPageEvent(doc, ReportUtil.getHeaderDefault(parameters.get("logo"), parameters), tablesSubHeader);
        FooterDefaultPageEvent handlerFooter = new FooterDefaultPageEvent(doc, tablesSubFooter);
        pdf.addEventHandler(PdfDocumentEvent.START_PAGE, handlerHeader);
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, handlerFooter);
        List<CbteVenDetSinEncabDTO> details = (List<CbteVenDetSinEncabDTO>) parameters.get("items");
        Table tableDetail = this.getTableDetail(parameters.get("tipoCbte").toString(), details);
        doc.add(tableDetail);
        doc.add(ReportUtil.getLineSeparatorFinish(handlerFooter.getHeightFooter()));
        doc.close();
        return byteArrayOutputStream.toByteArray();
    }

    private Table getTableMsj(String[] msjs) {
        Table tableMsj = new Table(new float[]{100}, false);
        tableMsj.setWidth(UnitValue.createPercentValue(100)).setFixedLayout();
        for (String msj : msjs) {
            tableMsj.addCell(this.getCellTableGeneric(msj, 8f, 1f));
        }
        return tableMsj;
    }

    private Table getTableDetail(String tipoCbte, List<CbteVenDetSinEncabDTO> details) {
        Table tableDetail;
        if (tipoCbte.equals(Comprobante.A.toString())) {
            tableDetail = new Table(new float[]{11, 42, 9, 9, 9, 9, 11}, false);
        } else {
            tableDetail = new Table(new float[]{12, 45, 10, 10, 10, 13}, false);
        }
        tableDetail.setWidth(UnitValue.createPercentValue(100)).setFixedLayout();
        tableDetail.addCell(this.getCellHeaderTableDetail("Código"));
        tableDetail.addCell(this.getCellHeaderTableDetail("Detalle"));
        tableDetail.addCell(this.getCellHeaderTableDetail("Cantidad"));
        tableDetail.addCell(this.getCellHeaderTableDetail("Precio"));
        if (tipoCbte.equals(Comprobante.A.toString())) {
            tableDetail.addCell(this.getCellHeaderTableDetail("IVA alic."));
        }
        tableDetail.addCell(this.getCellHeaderTableDetail("Desc."));
        tableDetail.addCell(this.getCellHeaderTableDetail("Importe")
                .setTextAlignment(TextAlignment.RIGHT)
        );
        details.stream().forEach(x -> {
            tableDetail.addCell(this.getCellTableDetail(x.getId().toString()));
            tableDetail.addCell(this.getCellTableDetail(this.getDescripcion(x)));
            tableDetail.addCell(this.getCellTableDetail(NumbersUtils.roundForPrint(x.getCantidad())));
            tableDetail.addCell(this.getCellTableDetail(NumbersUtils.roundForPrint(x.getPrecio())));
            if (tipoCbte.equals(Comprobante.A.toString())) {
                tableDetail.addCell(this.getCellTableDetail(NumbersUtils.roundForPrint(x.getIvaDes())));
            }
            tableDetail.addCell(this.getCellTableDetail(NumbersUtils.roundForPrint(x.getDescuento())));
            tableDetail.addCell(this.getCellTableDetail(NumbersUtils.roundForPrint(x.getTotal()))
                    .setTextAlignment(TextAlignment.RIGHT));
        });
        return tableDetail;
    }

    private Table getTableDetailTicket(String tipoCbte, List<DetalleVentasTicket> detalleVentasTickets, String total) {
        Table tableDetail;
        if (tipoCbte.equals(Comprobante.A.toString())) {
            tableDetail = new Table(new float[]{75, 25}, false);
        } else {
            tableDetail = new Table(new float[]{75, 25}, false);
        }
        tableDetail.setWidth(UnitValue.createPercentValue(100)).setFixedLayout();
        detalleVentasTickets.stream().forEach(x -> {
            tableDetail.addCell(this.getCellTableDetailTicket(x.getDescripcion()));
            tableDetail.addCell(this.getCellTableDetailTicket(NumbersUtils.roundForPrint(x.getSubtotal()))
                    .setTextAlignment(TextAlignment.RIGHT));
        });
        // agregamos el total
        tableDetail.addCell(new Cell(1, 2).add(new Paragraph()
                        .add(new Text("Total: ").setFontSize(9f))
                        .add(new Text(total).setBold().setFontSize(9f))
                ).setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER)
                .setPadding(0f)
                .setMargin(0f));
        return tableDetail;
    }

    /**
     * Return desc of product for print
     *
     * @param detail
     * @return
     */
    private String getDescripcion(CbteVenDetSinEncabDTO detail) {
        return detail.getDescripcion() +
                (Objects.nonNull(detail.getNumeroSerie()) ? detail.getNumeroSerie() : "") +
                (Objects.nonNull(detail.getInfoProdAdic()) ? detail.getInfoProdAdic() : "");

    }


    public Table getTableAfipData(CbteVenEncDTO cbteVenEnc) {
        Table tableAfip = new Table(new float[]{50, 50}, false) // in points
                .setWidth(UnitValue.createPercentValue(100))
                .setFixedLayout();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
//            Path filePath = Paths.get("/home/rafa/Descargas/imagenqrrrr.png");
//            MatrixToImageWriter.writeToPath(this.getImageQR(cbteVenEnc), "PNG", filePath);
//            ImageIO.write(MatrixToImageWriter.toBufferedImage(this.getImageQR(cbteVenEnc)), "png", baos);
            MatrixToImageWriter.writeToStream(this.getImageQR(cbteVenEnc), "png", outputStream);
            Image image = new Image(ImageDataFactory.create(outputStream.toByteArray()));
            image.setWidth(80f);
            image.setHeight(80f);
            Cell cell0 = new Cell();
            cell0.add(new Paragraph()
                            .add(image)
                            .setMultipliedLeading(0.5f))
                    .setBorder(Border.NO_BORDER);
            tableAfip.addCell(cell0);
            tableAfip.addCell(new Cell().add(ReportUtil.getParagraph(String.format("CAE: %s", cbteVenEnc.getCae()), 8f, 1.2f))
                    .add(ReportUtil.getParagraph(String.format("Fecha de ven. de CAE: %s", cbteVenEnc.getCaeVenc()), 8f, 1.2f))
                    .setBorder(Border.NO_BORDER));
            tableAfip.setBorder(new SolidBorder(1f));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tableAfip;
    }

    public Table getTableAfipDataTicket(CbteVenEncDTO cbteVenEnc) {
        Table tableAfip = new Table(new float[]{100}, false) // in points
                .setWidth(UnitValue.createPercentValue(100))
                .setFixedLayout();
        if (cbteVenEnc.getAfipValida()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                MatrixToImageWriter.writeToStream(this.getImageQR(cbteVenEnc), "png", outputStream);
                Image image = new Image(ImageDataFactory.create(outputStream.toByteArray()));
                image.setWidth(80f);
                image.setHeight(80f);
                Cell cell0 = new Cell();
                cell0.add(new Paragraph()
                                .add(image)
                                .setMultipliedLeading(0.5f))
                        .setBorder(Border.NO_BORDER)
                        .setTextAlignment(TextAlignment.CENTER);
                tableAfip.addCell(cell0);
                tableAfip.addCell(new Cell().add(ReportUtil.getParagraph(String.format("CAE: %s", cbteVenEnc.getCae()), 8f, 1.2f))
                        .setBorder(Border.NO_BORDER)
                        .setPadding(0f)
                        .setMargin(0f)
                        .setTextAlignment(TextAlignment.CENTER));
                tableAfip.addCell(new Cell().add(ReportUtil.getParagraph(String.format("F. Ven. CAE: %s", DateUtils.getDateStringFromDate(cbteVenEnc.getCaeVenc(), "dd-MM-yyyy")), 8f, 1.2f))
                        .setBorder(Border.NO_BORDER)
                        .setPadding(0f)
                        .setMargin(0f)
                        .setTextAlignment(TextAlignment.CENTER));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        tableAfip.addCell(new Cell().add(ReportUtil.getParagraph(String.format("Cajero: %s", cbteVenEnc.getUsuario().getUsuario()), 8f, 1.2f))
                .setBorder(Border.NO_BORDER)
                .setPadding(0f)
                .setMargin(0f)
                .setMarginTop(20f)
                .setTextAlignment(TextAlignment.CENTER));
        tableAfip.addCell(new Cell().add(ReportUtil.getParagraph("Gracias por su compra", 8f, 1.2f))
                .setBorder(Border.NO_BORDER)
                .setPadding(0f)
                .setMargin(0f)
                .setTextAlignment(TextAlignment.CENTER));
        tableAfip.setBorder(Border.NO_BORDER);
        return tableAfip;
    }

    private BitMatrix getImageQR(CbteVenEncDTO cbteVenEnc) {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = barcodeWriter.encode(this.afipQrUrl + this.getBase64QR(cbteVenEnc), BarcodeFormat.QR_CODE, 200, 200);
            return bitMatrix;
//            return barcodeWriter.encode(this.afipQrUrl + base64Image, BarcodeFormat.QR_CODE, 500, 500);
        } catch (WriterException e) {
            e.printStackTrace();
            // TODO poner log
        }
        return null;
    }

    private String getBase64QR(CbteVenEncDTO cbteVenEnc) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("ver", 1);
        payload.put("fecha", DateUtils.getDateStringFromDate(cbteVenEnc.getFechaCbte(), "yyyy-MM-dd"));
        payload.put("cuit", Long.parseLong(cbteVenEnc.getCuitEmpresa()));
        payload.put("ptoVta", Integer.parseInt(cbteVenEnc.getPtoVenta()));
        payload.put("tipoCmp", cbteVenEnc.getTipoCbte().toInt());
        payload.put("nroCmp", Integer.parseInt(cbteVenEnc.getCbteNro()));
        payload.put("importe", cbteVenEnc.getTotal());
        payload.put("moneda", "PES");
        payload.put("ctz", new BigDecimal("1"));
        if (Objects.nonNull(cbteVenEnc.getTipoDocCliente())) {
            payload.put("tipoDocRec", cbteVenEnc.getTipoDocCliente().toInt());
        }
        if (Objects.nonNull(cbteVenEnc.getNroDocCliente())) {
            if (!cbteVenEnc.getNroDocCliente().trim().equals("")) {
                payload.put("nroDocRec", Long.parseLong(cbteVenEnc.getNroDocCliente()));
            }
        }
        payload.put("tipoCodAut", "E");
        payload.put("codAut", new Long(cbteVenEnc.getCae()));
        try {
            String json = this.objectMapper.writeValueAsString(payload);
            String encodedString = Base64.getEncoder().encodeToString(json.getBytes());
            return encodedString;
        } catch (IOException ex) {
            ex.printStackTrace();
            // TODO poner log
        }
        return null;
    }

    private Table getTableTotal(String total) {
        Table tableTotal = new Table(new float[]{100}, false) // in points
                .setWidth(UnitValue.createPercentValue(100))
                .setFixedLayout();
        tableTotal.addCell(new Cell().add(new Paragraph()
                        .add(new Text("Total: "))
                        .add(new Text(total).setBold().setFontSize(12f))
                ).setTextAlignment(TextAlignment.RIGHT)
                .setBorderLeft(Border.NO_BORDER)
                .setBorderRight(Border.NO_BORDER));
        return tableTotal;
    }

    private Table getTableTotalFull(Map<String, Object> parameters) {
        Table tableTotal = new Table(new float[]{85, 15}, false) // in points
                .setWidth(UnitValue.createPercentValue(100))
                .setFixedLayout();
        tableTotal.addCell(this.getCellTableGeneric("Subtotal B.I.:", 8f, 1f)
                .setTextAlignment(TextAlignment.RIGHT));
        tableTotal.addCell(this.getCellTableGeneric(NumbersUtils.roundForPrint(parameters.get("totalBI")), 9f, 1f)
                .setTextAlignment(TextAlignment.RIGHT));
        tableTotal.addCell(this.getCellTableGeneric("IVA:", 8f, 1f)
                .setTextAlignment(TextAlignment.RIGHT));
        tableTotal.addCell(this.getCellTableGeneric(NumbersUtils.roundForPrint(parameters.get("totalIva")), 9f, 1f)
                .setTextAlignment(TextAlignment.RIGHT));
        tableTotal.addCell(this.getCellTableGeneric("Tributos: ", 8f, 1f)
                .setTextAlignment(TextAlignment.RIGHT));
        tableTotal.addCell(this.getCellTableGeneric(NumbersUtils.roundForPrint(parameters.get("totalTrib")), 9f, 1f)
                .setTextAlignment(TextAlignment.RIGHT));
        tableTotal.addCell(this.getCellTableGeneric("Total:", 8f, 1.2f)
                .setTextAlignment(TextAlignment.RIGHT));
        tableTotal.addCell(new Cell().add(new Paragraph()
                        .add(new Text(NumbersUtils.roundForPrint(parameters.get("total")))
                                .setBold()
                                .setFontSize(10f))
                        .setMultipliedLeading(1.2f)
                )
                .setPadding(0)
                .setMargin(0)
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER));
        return tableTotal;
    }

    private Cell getCellHeaderTableDetail(String value) {
        return new Cell().setFontSize(8)
                .add(new Paragraph(value)
                        .setBold()
                        .setPadding(0f)
                        .setMargin(0f)
                )
                .setBorderLeft(Border.NO_BORDER)
                .setBorderRight(Border.NO_BORDER)
                .setPadding(0f)
                .setMargin(0f)
                ;
    }

    private Cell getCellTableGeneric(String value, float fontSize, float multipleLeading) {
        return new Cell().add(
                        new Paragraph(value)
                                .setPadding(0f)
                                .setMargin(0f)
                                .setFontSize(fontSize)
                                .setMultipliedLeading(multipleLeading)
                )
                .setPadding(0f)
                .setMargin(0f)
                .setBorder(Border.NO_BORDER);
    }

    private Cell getCellTableGeneric(String value, float fontSize) {
        return new Cell().add(
                        new Paragraph(value)
                                .setPadding(0f)
                                .setMargin(0f)
                                .setFontSize(fontSize)
                )
                .setPadding(0f)
                .setMargin(0f)
                .setBorder(Border.NO_BORDER);
    }

    private Cell getCellTableDetail(String value) {
        return new Cell().add(
                        new Paragraph(value)
                                .setPadding(0f)
                                .setMargin(0f)
                                .setFontSize(9f)
                                .setMultipliedLeading(1.2f)
                )
                .setBorder(Border.NO_BORDER)
                .setPadding(0f)
                .setMargin(0f);
    }

    private Cell getCellTableDetailTicket(String value) {
        return new Cell().add(
                        new Paragraph(value)
                                .setPadding(0f)
                                .setMargin(0f)
                                .setFontSize(8f)
                                .setMultipliedLeading(1.2f)
                )
                .setBorder(Border.NO_BORDER)
                .setPadding(0f)
                .setMargin(0f);
    }

    public Paragraph getParagraphSubHeader(String label, Object value) {
        return new Paragraph(label + ": " + (Objects.nonNull(value) ? value.toString() : ""))
                .setFontSize(10)
                .setWidth(UnitValue.createPercentValue(100))
                .setMultipliedLeading(1.0f).setTextAlignment(TextAlignment.LEFT);
    }

    private StringBuilder getLeyendaListadoVentas(FilterVentasDTO filterVentasDTO) {
        StringBuilder leyenda = new StringBuilder("");
        if (filterVentasDTO != null) {
            if (filterVentasDTO.getFechaInicial() != null && !filterVentasDTO.getFechaInicial().isEmpty()) {
                leyenda.append("Desde: ");
                leyenda.append(filterVentasDTO.getFechaInicial());
                leyenda.append(" | ");
            }
            if (filterVentasDTO.getFechaFinal() != null && !filterVentasDTO.getFechaFinal().isEmpty()) {
                leyenda.append("Hasta: ");
                leyenda.append(filterVentasDTO.getFechaFinal());
                leyenda.append(" | ");
            }

            if (filterVentasDTO.getPagada() != null) {
                if (filterVentasDTO.getPagada() == 1 || filterVentasDTO.getPagada() == 0) {
                    leyenda.append("Estado: ");
                    leyenda.append(filterVentasDTO.getPagada() == 1 ? "Pagadas" : "Impagas");
                    leyenda.append(" | ");
                }
            }
            if (filterVentasDTO.getUsuario() != null) {
                leyenda.append("Usuario: ");
                leyenda.append(filterVentasDTO.getUsuario().getId());
                leyenda.append(" | ");
            }
            if (filterVentasDTO.getSucursales() != null && !filterVentasDTO.getSucursales().isEmpty()) {
                leyenda.append("Sucursal: ");
                for (SimpleObjectDTO sucursal : filterVentasDTO.getSucursales()) {
                    leyenda.append(sucursal.getId());
                    leyenda.append(" ");
                }
                leyenda.append(" | ");
            }
            if (filterVentasDTO.getCaja() != null) {
                leyenda.append("Caja: ");
                leyenda.append(filterVentasDTO.getCaja().getId());
                leyenda.append(" | ");
            }
            if (filterVentasDTO.getTotalMinimo() != null) {
                if (filterVentasDTO.getTotalMinimo().compareTo(BigDecimal.ZERO) > 0) {
                    leyenda.append("Monto desde: ");
                    leyenda.append(filterVentasDTO.getTotalMinimo().toString());
                    leyenda.append(" | ");
                }
            }
            if (filterVentasDTO.getTotalMaximo() != null) {
                if (filterVentasDTO.getTotalMinimo().compareTo(BigDecimal.ZERO) > 0) {
                    leyenda.append("Monto hasta: ");
                    leyenda.append(filterVentasDTO.getTotalMaximo().toString());
                    leyenda.append(" | ");
                }
            }
        }
        if (leyenda.length() > 30) {
            leyenda.substring(leyenda.length() - 2);
        }
        return leyenda;
    }

    public static List<DetalleVentasTicket> getDetallesTicket
            (List<CbteVenDetSinEncabDTO> detalleVentas, ConfiguracionDTO configuracion) {
        List<DetalleVentasTicket> detalleVentasTickets = new ArrayList<>();
        for (CbteVenDetSinEncabDTO detalleVenta : detalleVentas) {
            DetalleVentasTicket detalleVentasTicket = new DetalleVentasTicket();
            String descripcion = "";
            if (configuracion.getCutDescTicket() != null && configuracion.getCutDescTicket()) {
                if (configuracion.getSizeDescTicket() != null && configuracion.getSizeDescTicket() > 2) {
                    descripcion = cutDescripcion(detalleVenta.getDescripcion().trim(), configuracion.getSizeDescTicket());
                } else {
                    descripcion = detalleVenta.getDescripcion().trim();
                }
            } else {
                descripcion = detalleVenta.getDescripcion().trim();
            }
            if (!Objects.isNull(detalleVenta.getNumeroSerie()) && !detalleVenta.getNumeroSerie().equals("")) {
                descripcion = descripcion + " - " + detalleVenta.getNumeroSerie();
            }
            if (!Objects.isNull(detalleVenta.getInfoProdAdic()) && !detalleVenta.getInfoProdAdic().equals("")) {
                descripcion = descripcion + " - " + detalleVenta.getInfoProdAdic();
            }
            detalleVentasTicket.setDescripcion(descripcion + " - " + NumbersUtils.roundCutTwoDec(detalleVenta.getCantidad()) + " x " + NumbersUtils.roundCutTwoDec(detalleVenta.getPrecio()));
            if (!Objects.isNull(detalleVenta.getOferta())) {
                if (detalleVenta.getOferta()) {
                    if (!Objects.isNull(detalleVenta.getOfertaDescuento())) {
                        if (detalleVenta.getOfertaDescuento().compareTo(BigDecimal.ZERO) > 0) {
                            detalleVentasTicket.setDescripcion(detalleVentasTicket.getDescripcion().trim() + "(-" + NumbersUtils.roundCutTwoDec(detalleVenta.getOfertaDescuento()) + ")");
                        }
                    }
                }
            }
            if (!Objects.isNull(detalleVenta.getDescuento())) {
                if (detalleVenta.getDescuento().compareTo(BigDecimal.ZERO) > 0) {
                    detalleVentasTicket.setDescripcion(detalleVentasTicket.getDescripcion().trim() + "(-" + NumbersUtils.roundCutTwoDec(detalleVenta.getDescuento()) + ")");
                }
            }
            if (!Objects.isNull(detalleVenta.getAdicional())) {
                if (detalleVenta.getAdicional().compareTo(BigDecimal.ZERO) > 0) {
                    detalleVentasTicket.setDescripcion(detalleVentasTicket.getDescripcion().trim() + "(+" + NumbersUtils.roundCutTwoDec(detalleVenta.getAdicional()) + ")");
                }
            }
            detalleVentasTicket.setSubtotal(detalleVenta.getTotal());
            detalleVentasTickets.add(detalleVentasTicket);
        }
        return detalleVentasTickets;
    }

    public static String cutDescripcion(String descripcion, Integer lenghtCut) {
        String[] splited = descripcion.split("\\s+");
        StringBuilder st = new StringBuilder("");
        for (int i = 0; i < splited.length; i++) {
            if (splited[i].length() > lenghtCut) {
                splited[i] = splited[i].substring(0, lenghtCut);
                try {
                    Double.parseDouble(splited[i].trim());
                } catch (NumberFormatException e) {
                    splited[i] = splited[i].substring(0, lenghtCut);
                }
            }
            st.append(splited[i]).append(" ");
        }
        return st.toString().trim();
    }

    private BigDecimal getPagoTotal(Set<PagoVentasSinEncabDTO> pagos) {
        Boolean onlyEfectivo = true;
        BigDecimal pagoTotal = BigDecimal.ZERO;
        if (!Objects.isNull(pagos) && pagos.size() > 0) {
            for (PagoVentasSinEncabDTO pago : pagos) {
                if (!Objects.isNull(pago) && !Objects.isNull(pago.getFormaPago())) {
                    if (pago.getFormaPago().getId() != Constants.ID_EFECTIVO) {
                        onlyEfectivo = false;

                    }
                } else {
                    onlyEfectivo = false;
                }
            }
        }
        if (onlyEfectivo) {
            for (PagoVentasSinEncabDTO pago : pagos) {
                if (!Objects.isNull(pago) && !Objects.isNull(pago.getFormaPago())) {
                    if (!Objects.isNull(pago.getPagoCon())) {
                        pagoTotal = pagoTotal.add(pago.getPagoCon());
                    } else {
                        if (!Objects.isNull(pago.getMonto())) {
                            pagoTotal = pagoTotal.add(pago.getMonto());
                        }
                    }
                }
            }
        } else {
            for (PagoVentasSinEncabDTO pago : pagos) {
                if (!Objects.isNull(pago.getMonto())) {
                    pagoTotal = pagoTotal.add(pago.getMonto());
                }
            }
        }
        return pagoTotal;
    }
}
