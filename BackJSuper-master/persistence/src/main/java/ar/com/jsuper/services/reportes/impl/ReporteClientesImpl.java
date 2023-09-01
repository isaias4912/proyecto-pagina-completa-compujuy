/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.reportes.impl;

import ar.com.jsuper.dao.utils.PaginationData;
import ar.com.jsuper.dto.*;
import ar.com.jsuper.dto.reportes.ColumnGeneric;
import ar.com.jsuper.dto.reportes.DataReportGeneric;
import ar.com.jsuper.services.reportes.*;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.List;

/**
 * @author rafael
 */
@Service
public class ReporteClientesImpl extends ReportesImpl implements ReporteClientes {

    @Autowired
    ReportGeneric reportGeneric;

    @Override
    public byte[] printMovimientosCtaCte(PaginationData<MovimientosCtaCteDTO, InfoCtaCteDTO> data) {
        DataReportGeneric reportGeneric = new DataReportGeneric(data.getData(), ColumnGeneric.getColumns(
                new String[]{"Id", "Tipo", "Fecha", "Deuda", "Interes", "Deuda total", "Saldo total"},
                new String[]{"id", "tipo", "fecha", "monto", "interes", "montoTotal", "saldo"},
                new int[]{10, 5, 12, 12, 12, 12, 12}), "Detalle de Cta. Cte.");
        // table client
//        Map<String, Object> parametersCli = this.getBaseData();
        InfoCtaCteDTO infoCtaCteDTO = data.getDataAditional();
//        ClienteMinDTO clienteMinDTO=infoCtaCteDTO.getCliente();
        List<Table> tablesSubHeader = new ArrayList<>();
        if (Objects.nonNull(infoCtaCteDTO)) {
            Table tableCli = ReportUtil.getTableCliente(infoCtaCteDTO.getCliente());
            if (Objects.nonNull(tableCli)) {
                tablesSubHeader.add(tableCli);
            }
        }
        Table table = ReportUtil.getTableGeneric(reportGeneric);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        // Creating a Document object
        Document doc = new Document(pdf, PageSize.A4);
        Map<String, Object> parameters = this.getBaseData();
        parameters.put("titulo", reportGeneric.getTitle());
        HeaderDefaultPageEvent handlerHeader = new HeaderDefaultPageEvent(doc, ReportUtil.getHeaderDefault(parameters.get("logo"), parameters), tablesSubHeader);
        pdf.addEventHandler(PdfDocumentEvent.START_PAGE, handlerHeader);
        doc.add(new LineSeparator(new SolidLine(0.5f)));
        table.setMarginTop(5f);
        doc.add(table);
        doc.close();
        return byteArrayOutputStream.toByteArray();
    }


}
