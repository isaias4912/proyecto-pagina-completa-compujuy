package ar.com.jsuper.services.reportes.impl;

import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.dto.reportes.ColumnGeneric;
import ar.com.jsuper.dto.reportes.DataReportGeneric;
import ar.com.jsuper.services.reportes.HeaderDefaultPageEvent;
import ar.com.jsuper.services.reportes.ReportGeneric;
import ar.com.jsuper.services.reportes.ReportUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.csv.CSVFormat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ReportGenericImpl extends ReportesImpl implements ReportGeneric {
    @Autowired
    ObjectMapper mapper;
    @Autowired
    protected OrikaBeanMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(ReportGenericImpl.class);

    public byte[] printPresupuesto() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        // Creating a Document object
        Document doc = new Document(pdf);
        return null;
    }

    public byte[] printTable(DataReportGeneric reportGeneric, String typePrint) {
        if (typePrint.equals("pdf")) {
            return this.printTablePdf(reportGeneric);
        }
        if (typePrint.equals("csv")) {
            return this.printTableCsv(reportGeneric);
        }
        if (typePrint.equals("xls")) {
            return this.printTableXls(reportGeneric);
        }
        return null;
    }

    public byte[] printTablePdf(DataReportGeneric reportGeneric) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdf = new PdfDocument(writer);
            // Creating a Document object
            Document doc = new Document(pdf);
            Map<String, Object> parameters = this.getBaseData();
            parameters.put("titulo", reportGeneric.getTitle());
            HeaderDefaultPageEvent handlerHeader = new HeaderDefaultPageEvent(doc, ReportUtil.getHeaderDefault(parameters.get("logo"), parameters), Collections.emptyList());
            pdf.addEventHandler(PdfDocumentEvent.START_PAGE, handlerHeader);
            Table table = ReportUtil.getTableGeneric(reportGeneric);
//            Table table = new Table(UnitValue.createPercentArray(reportGeneric.getWidths())).useAllAvailableWidth();
//            table.setWidth(UnitValue.createPercentValue(100)).setFixedLayout();
//            // Header
//            reportGeneric.getColumnGenerics().forEach(x -> {
//                table.addCell(
//                        new Cell().setFontSize(10)
//                                .setTextAlignment(TextAlignment.CENTER)
//                                .setBackgroundColor(new DeviceGray(0.75f))
//                                .add(new Paragraph(x.getLabelHeader()))
//                );
//            });
//            // detail
//            reportGeneric.getValues().forEach(x -> {
//                table.addCell(
//                        new Cell()
//                                .add(new Paragraph(x)
//                                        .setMultipliedLeading(1.2f)
//                                        .setFontSize(8)
//                                )
//                );
//            });
            doc.add(new LineSeparator(new SolidLine(0.5f)));
            table.setMarginTop(5f);
            doc.add(table);
            doc.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] printTableCsv(DataReportGeneric reportGeneric) {
        CSVFormat csvFormat = CSVFormat.Builder
                .create()
                .setHeader(reportGeneric.getColumnGenerics().stream().map(ColumnGeneric::getLabelHeader).toArray(String[]::new))
                .setDelimiter(";")
                .build();
        try (ByteArrayOutputStream output = new ByteArrayOutputStream();
             CSVPrinter printer = new CSVPrinter(new OutputStreamWriter(output), csvFormat)) {
            for (List<String> x : reportGeneric.getValuesRow()) {
                printer.printRecord(x);
            }
            printer.flush();
            return output.toByteArray();
        } catch (IOException e) {
            logger.error("Error al convertir csv", e);
        }
        return null;
    }

    public byte[] printTableXls(DataReportGeneric reportGeneric) {
        try (
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                HSSFWorkbook libro = new HSSFWorkbook();
        ) {
            //            // Se crea una hoja dentro del libro
            HSSFSheet hoja = libro.createSheet();
            HSSFRow fila = hoja.createRow(0);
            AtomicInteger i = new AtomicInteger(0);
            reportGeneric.getColumnGenerics().forEach(x -> {
                HSSFCell celda = fila.createCell(i.getAndIncrement());
                celda.setCellValue(new HSSFRichTextString(x.getLabelHeader()));
            });

            AtomicInteger j = new AtomicInteger(1);

            reportGeneric.getValuesRow().forEach(x -> {
                HSSFRow filax = hoja.createRow(j.getAndIncrement());
                AtomicInteger z = new AtomicInteger(0);
                x.forEach(y -> {
                    HSSFCell celda = filax.createCell(z.getAndIncrement());
                    celda.setCellValue(new HSSFRichTextString(y));
                });
            });
//            rowDataTemplates.forEach(x -> {
//                HSSFRow filax = hoja.createRow(j.getAndIncrement());
//                AtomicInteger z = new AtomicInteger(0);
//                x.getRow().forEach(y -> {
//                    HSSFCell celda = filax.createCell(z.getAndIncrement());
//                    celda.setCellValue(new HSSFRichTextString(y.getValue()));
//                });
//            });
            libro.write(bos);
            return bos.toByteArray();
        } catch (IOException e) {
            logger.error("Error al convertir xls", e);
        }
        return null;
    }
}
