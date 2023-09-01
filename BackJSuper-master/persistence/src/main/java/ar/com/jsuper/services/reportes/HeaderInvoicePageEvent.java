package ar.com.jsuper.services.reportes;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.TableRenderer;
import org.springframework.lang.NonNull;

import java.util.List;

public class HeaderInvoicePageEvent implements IEventHandler {

    private Document doc;
    private Table tableHeader;
    private List<Table> tablesSubHeader;
    private float heightHeader;

    public HeaderInvoicePageEvent(@NonNull Document doc, @NonNull Table tableHeader, @NonNull List<Table> tablesSubHeader) {
        this.tableHeader = tableHeader;
        this.tablesSubHeader = tablesSubHeader;
        this.doc = doc;
        this.init();
    }

    private void init() {
        this.heightHeader = ReportUtil.getHeight(this.doc, this.tableHeader);
        this.heightHeader = this.heightHeader + ReportUtil.getTotalHeight(this.doc, this.tablesSubHeader);
        float topMargin = 36f + this.heightHeader;
        doc.setMargins(topMargin, 36, 36, 36);
    }

    @Override
    public void handleEvent(Event currentEvent) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) currentEvent;
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();
        PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
        PageSize pageSize = pdfDoc.getDefaultPageSize();
        PdfPage lastPage = pdfDoc.getLastPage();
        float coordX = pageSize.getX() + doc.getLeftMargin();
        float coordY = pageSize.getTop() - doc.getTopMargin();
        float width = pageSize.getWidth() - doc.getRightMargin() - doc.getLeftMargin();
        Rectangle rect = new Rectangle(coordX, coordY, width, this.heightHeader);
        Canvas cHeader = new Canvas(canvas, pdfDoc, rect)
                .add(this.tableHeader);
        this.tablesSubHeader.forEach(x -> {
            cHeader.add(x);
        });
        cHeader.close();
    }

    public float getHeightHeader() {
        return heightHeader;
    }
}