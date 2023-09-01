package ar.com.jsuper.services.reportes;

import ar.com.jsuper.dto.ClienteMinDTO;
import ar.com.jsuper.dto.reportes.DataReportGeneric;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.canvas.draw.DashedLine;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.property.BorderRadius;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.TableRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ReportUtil {

    private static final float SIZE_FONT_TICKET_DETAIL = 8;

    private static byte[] getImage(Object logo) {
        BufferedImage image = (BufferedImage) logo;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LineSeparator getLineSeparatorFinish(float marginBottom) {
        SolidLine solidLine = new SolidLine(1f);
        solidLine.setColor(ColorConstants.WHITE);
        return new LineSeparator(solidLine)
                .setStrokeColor(ColorConstants.WHITE)
                .setBackgroundColor(ColorConstants.WHITE)
                .setPadding(0f)
                .setMarginTop(0f)
                .setMarginRight(0f)
                .setMarginLeft(0f)
                .setMarginBottom(marginBottom);
    }

    public static LineSeparator getDashedLineSeparator() {
        DashedLine dashedLine = new DashedLine(0.5f);
        dashedLine.setColor(ColorConstants.BLACK);
        return new LineSeparator(dashedLine)
                .setStrokeColor(ColorConstants.BLACK)
                .setPadding(0f)
                .setMarginTop(0f)
                .setMarginRight(0f)
                .setMarginLeft(0f);
    }

    public static Table getHeaderDefault(Object imageObj, Map<String, Object> data) {
        Border border = Border.NO_BORDER;
        Style style = new Style();
        style.setBold();
        style.setFontSize(12f);
        style.setMarginTop(0.5f);
        Table tableHeader = new Table(new float[]{15, 31, 8, 46}, false) // in points
                .setWidth(UnitValue.createPercentValue(100))
                .setFixedLayout();
        tableHeader.setMarginBottom(15f);
        Cell cell0 = new Cell(2, 1);
        byte[] imageLogo = getImage(imageObj);
        if (Objects.nonNull(imageLogo)) {
            Image image = new Image(ImageDataFactory.create(imageLogo));
            image.setAutoScale(true);
            image.setTextAlignment(TextAlignment.LEFT);
            cell0.setBorder(border);
            cell0.add(image);
        }
        tableHeader.addCell(cell0);
        Cell cell1 = new Cell(2, 1);
        cell1.add(new Paragraph(data.get("razonSocial").toString())
                .setBold()
                .setFontSize(15)
                .setWidth(UnitValue.createPercentValue(100))
                .setMultipliedLeading(1.4f).setTextAlignment(TextAlignment.LEFT)
        );
        cell1.add(new Paragraph(String.format("Domicilio: %s", data.get("domicilioComercial").toString()))
                .setFontSize(9)
                .setWidth(UnitValue.createPercentValue(100))
                .setMultipliedLeading(0.5f).setTextAlignment(TextAlignment.LEFT)
        );
        cell1.setBorder(border);
        tableHeader.addCell(cell1);
        Cell cell2 = new Cell();
        cell2.add(new Paragraph(""));
        cell2.setBorder(border);
        tableHeader.addCell(cell2);
        Cell cell3 = new Cell(2, 1);
        cell3.add(new Paragraph(new Text(data.get("titulo").toString()))
                .setBold()
                .setFontSize(12)
                .setWidth(UnitValue.createPercentValue(100))
                .setMultipliedLeading(1.4f).setTextAlignment(TextAlignment.RIGHT)
        );
        cell3.setBorder(border);
        tableHeader.addCell(cell3);
        Cell cell4 = new Cell();
        cell4.add(new Paragraph(""));
        cell4.setBorder(border);
        tableHeader.addCell(cell4);
        tableHeader.setMargin(0f);
        tableHeader.setPadding(0f);
        return tableHeader;
    }

    public static Table getHeaderInvoice(Object imageObj, Map<String, Object> data) {
        Border border = Border.NO_BORDER;
        Style style = new Style();
        style.setBold();
        style.setFontSize(12f);
        style.setMarginTop(0.5f);
        Table tableHeader = new Table(new float[]{15, 31, 4, 4, 46}, false) // in points
                .setWidth(UnitValue.createPercentValue(100))
                .setFixedLayout();
//        tableHeader.setMarginBottom(15f);
        Cell cell0 = new Cell(2, 1);
        byte[] imageLogo = getImage(imageObj);
        if (Objects.nonNull(imageLogo)) {
            Image image = new Image(ImageDataFactory.create(imageLogo));
            image.setAutoScale(true);
            image.setTextAlignment(TextAlignment.LEFT);
            cell0.setBorder(border);
            cell0.add(image);
        }
        tableHeader.addCell(cell0);
        Cell cell1 = new Cell(2, 1);
        cell1.add(new Paragraph(getValue(data.get("razonSocial")))
                .setBold()
                .setFontSize(15)
                .setWidth(UnitValue.createPercentValue(100))
                .setMultipliedLeading(1.4f).setTextAlignment(TextAlignment.LEFT)
        );
        cell1.add(new Paragraph(String.format("Domicilio: %s", getValue("domicilioComercial")))
                .setFontSize(9)
                .setWidth(UnitValue.createPercentValue(100))
                .setMultipliedLeading(0.5f).setTextAlignment(TextAlignment.LEFT)
        );
        cell1.setBorderLeft(Border.NO_BORDER);
        cell1.setBorder(Border.NO_BORDER);
        tableHeader.addCell(cell1);
        Cell cell2 = new Cell(1, 2);
        cell2.add(new Paragraph(getValue("tipoCbte"))
                .setBold()
                .setFontSize(14)
                .setMultipliedLeading(1f)
        );
        cell2.add(new Paragraph(getValue("codCbte"))
                .setFontSize(6f)
        );
        cell2.setTextAlignment(TextAlignment.CENTER);
//        cell2.setBorder(border);
        tableHeader.addCell(cell2);
        Cell cell3 = new Cell(2, 1);
        cell3.add(getParagraph(String.format("Pto. venta: %s  Cbte. Nro: %s", getValue(data.get("ptoVenta")), getValue(data.get("cbteNro"))), 8f, 1.2f));
        cell3.add(getParagraph(String.format("Fecha de emisión: %s", getValue(data.get("fechaCbte"))), 8f, 1f));
        cell3.add(getParagraph(String.format("CUIT: %s", getValue(data.get("cuit"))), 8f, 1f));
        cell3.add(getParagraph(String.format("Ingreso brutos: %s", getValue(data.get("ingresosBrutos"))), 8f, 1f));
        cell3.add(getParagraph(String.format("Fecha de inicio de act: %s", getValue(data.get("inicioActividad"))), 8f, 1f));
        cell3.setTextAlignment(TextAlignment.RIGHT);
        cell3.setBorder(border);
        tableHeader.addCell(cell3);
        Cell cell4 = new Cell();
        cell4.add(new Paragraph(""));
        cell4.setBorder(border);
        cell4.setBorderRight(new SolidBorder(1f));
        tableHeader.addCell(cell4);
        Cell cell5 = new Cell();
        cell5.add(new Paragraph(""));
        cell5.setBorder(border);
        tableHeader.addCell(cell5);
        tableHeader.setMargin(0f);
        tableHeader.setPadding(0f);
        tableHeader.setBorder(new SolidBorder(1f));
//        tableHeader.setBorderRadius(new BorderRadius(20f));
        return tableHeader;
    }

    public static Table getHeaderInvoiceTicket(Map<String, Object> data) {
        Border border = Border.NO_BORDER;
        Table tableHeader = new Table(new float[]{100}, false) // in points
                .setWidth(UnitValue.createPercentValue(100))
                .setFixedLayout();

        Cell cell1 = new Cell();
        cell1.add(new Paragraph(getValue(data.get("razonSocial")))
                .setBold()
                .setFontSize(10)
                .setWidth(UnitValue.createPercentValue(100))
                .setMultipliedLeading(1.4f).setTextAlignment(TextAlignment.CENTER)
        );
        cell1.add(new Paragraph(getValue(data.get("domicilioComercial")))
                .setFontSize(SIZE_FONT_TICKET_DETAIL)
                .setWidth(UnitValue.createPercentValue(100))
                .setMultipliedLeading(0.5f).setTextAlignment(TextAlignment.CENTER)
        );
        cell1.setBorderLeft(Border.NO_BORDER);
        cell1.setBorder(Border.NO_BORDER);
        tableHeader.addCell(cell1);
        Cell cell2 = new Cell();
        cell2.add(getDashedLineSeparator());
        cell2.add(new Paragraph(getValue(data.get("tipoCbte")))
                .setBold()
                .setFontSize(10)
                .setMultipliedLeading(1f)
        );
        cell2.add(getDashedLineSeparator());
        cell2.setTextAlignment(TextAlignment.CENTER);
        cell2.setBorder(border);
        tableHeader.addCell(cell2);
        Cell cell3 = new Cell();
        cell3.add(getParagraph(String.format("P. V: %s  Cbte.: %s", getValue(data.get("ptoVenta")), getValue(data.get("cbteNro"))), SIZE_FONT_TICKET_DETAIL, 1.2f));
        cell3.add(getParagraph(String.format("T: %s  F.: %s", getValue(data.get("transaccion")), getValue(data.get("fechaCbte"))), SIZE_FONT_TICKET_DETAIL, 1f));
        cell3.add(getParagraph(String.format("F. carga: %s %s", getValue(data.get("fechaCarga")), getValue(data.get("horaCarga"))), SIZE_FONT_TICKET_DETAIL, 1f));
        cell3.setTextAlignment(TextAlignment.JUSTIFIED_ALL);
        cell3.add(getDashedLineSeparator());
        cell3.setBorder(border);
        tableHeader.addCell(cell3);

        Cell cell4 = new Cell();
        String docClie = getValue(data.get("nroDocCliente"));
        String nomClie = getValue(data.get("nombreCliente"));
        if (!docClie.equals("")) {
            cell4.add(new Paragraph(String.format("%s: %S", getValue(data.get("tipoDocCliente")), docClie))
                    .setBold()
                    .setFontSize(SIZE_FONT_TICKET_DETAIL)
                    .setMultipliedLeading(1f)
            );
        }
        if (!nomClie.equals("")) {
            cell4.add(new Paragraph(String.format("Cliente: %S", nomClie))
                    .setBold()
                    .setFontSize(SIZE_FONT_TICKET_DETAIL)
                    .setMultipliedLeading(1f)
            );
        }
        if (nomClie.equals("") && docClie.equals("")) {
            cell4.add(new Paragraph("A consumidor final")
                    .setFontSize(SIZE_FONT_TICKET_DETAIL)
                    .setMultipliedLeading(1f)
            );
        }
        cell4.setBorder(Border.NO_BORDER);
        cell4.add(getDashedLineSeparator());
        tableHeader.addCell(cell4);
        tableHeader.setMargin(0f);
        tableHeader.setPadding(0f);
        tableHeader.setBorder(Border.NO_BORDER);
        return tableHeader;
    }

    public static Table getTableGeneric(DataReportGeneric reportGeneric){
        Table table = new Table(UnitValue.createPercentArray(reportGeneric.getWidths())).useAllAvailableWidth();
        table.setWidth(UnitValue.createPercentValue(100)).setFixedLayout();
        // Header
        reportGeneric.getColumnGenerics().forEach(x -> {
            table.addCell(
                    new Cell().setFontSize(10)
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBackgroundColor(new DeviceGray(0.75f))
                            .add(new Paragraph(x.getLabelHeader()))
            );
        });
        // detail
        reportGeneric.getValues().forEach(x -> {
            table.addCell(
                    new Cell()
                            .add(new Paragraph(x)
                                    .setMultipliedLeading(1.2f)
                                    .setFontSize(8)
                            )
            );
        });
        return table;
    }

    public static Table getTableCliente(ClienteMinDTO clienteMinDTO) {
        Map<String, Object> parametersCli =new HashMap<>();
        if (Objects.nonNull(clienteMinDTO)){
            parametersCli.put("nombreCliente", clienteMinDTO.getNombreCompleto());
            parametersCli.put("condicionIvaCliente", clienteMinDTO.getTipoCliente().name());
            parametersCli.put("tipoDocCliente", clienteMinDTO.getTipoDocCliente());
            parametersCli.put("nroDocCliente", clienteMinDTO.getNroDocCliente());
            parametersCli.put("direccionCliente", "");
        }else{
            return null;
        }
        return getTableCliente(parametersCli);
    }
    public static Table getTableCliente(Map<String, Object> parameters) {
        Table tableCli = new Table(new float[]{50, 50}, false) // in points
                .setWidth(UnitValue.createPercentValue(100))
                .setFixedLayout();
        Cell cell0 = new Cell();
        cell0.add(getParagraphSubHeader("Nombre Cliente", parameters.get("nombreCliente")));
        cell0.add(getParagraphSubHeader("Condición frente al IVA", parameters.get("condicionIvaCliente")));
        cell0.setBorder(Border.NO_BORDER);
        tableCli.addCell(cell0);
        Cell cell1 = new Cell();
        String tipoDocCliente = "Doc";
        if (Objects.nonNull(parameters.get("tipoDocCliente"))) {
            tipoDocCliente = parameters.get(("tipoDocCliente")).toString();
        }
        cell1.add(getParagraphSubHeader(tipoDocCliente, parameters.get("nroDocCliente")));
        cell1.add(getParagraphSubHeader("Dirección", parameters.get("direccionCliente")));
        cell1.setBorder(Border.NO_BORDER);
        tableCli.addCell(cell1);
        tableCli.setMargin(0f);
        tableCli.setPadding(0f);
        return tableCli;
    }
    public static Paragraph getParagraphSubHeader(String label, Object value) {
        return new Paragraph(label + ": " + (Objects.nonNull(value) ? value.toString() : ""))
                .setFontSize(10)
                .setWidth(UnitValue.createPercentValue(100))
                .setMultipliedLeading(1.0f).setTextAlignment(TextAlignment.LEFT);
    }
    public static String getValue(Object value) {
        return Objects.nonNull(value) ? value.toString() : "";
    }

    public static Paragraph getParagraph(String value, float fontSize, float multipleLeading) {
        return new Paragraph(value)
                .setPadding(0f)
                .setMargin(0f)
                .setFontSize(fontSize)
                .setMultipliedLeading(multipleLeading);
    }

    public static float getHeight(Document doc, Table element) {
        TableRenderer renderer = (TableRenderer) element.createRendererSubTree();
        renderer.setParent(new DocumentRenderer(doc));
        LayoutResult result = renderer.layout(new LayoutContext(new LayoutArea(0, PageSize.A4)));
        return result.getOccupiedArea().getBBox().getHeight();
    }

    public static float getTotalHeight(Document doc, List<Table> tables) {
        float height = 0f;
        for (Table x : tables) {
            height = height + getHeight(doc, x);
        }
        return height;
    }
}
