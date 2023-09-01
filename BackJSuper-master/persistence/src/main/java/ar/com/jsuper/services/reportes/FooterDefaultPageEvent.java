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
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.TableRenderer;
import org.springframework.lang.NonNull;

import java.util.List;

public class FooterDefaultPageEvent implements IEventHandler {

    private Document doc;
    private float heightFooter;
    private List<Table> tablesSubFooter;
    private static final float MARGIN_TOP = 36;
    private static final float MARGIN_LEFT = 36;
    private static final float MARGIN_BOTTOM = 36;
    private static final float MARGIN_RIGHT = 36;
    private static final float SIZE_REFACTORY = 0.1f;// Sin este valos las imagenes no se muestran, es cmo un excedente

    public FooterDefaultPageEvent(@NonNull Document doc, @NonNull List<Table> tablesSubFooter) {
        this.tablesSubFooter = tablesSubFooter;
        this.doc = doc;
        this.init();
    }

    private void init() {
//        this.heightFooter = this.getTotalHeight() + MARGIN_BOTTOM;
        this.heightFooter = this.getTotalHeight();
    }


    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage lastPage = pdfDoc.getLastPage();
        PdfPage page = docEvent.getPage();
        if (lastPage.equals(page)) {
            PageSize pageSize = pdfDoc.getDefaultPageSize();
            float coordX = pageSize.getX() + this.doc.getLeftMargin();
            float coordY = pageSize.getBottom() + MARGIN_BOTTOM ;
            float width = pageSize.getWidth() - this.doc.getRightMargin() - doc.getLeftMargin();
            PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
            Canvas canvas = new Canvas(pdfCanvas, pdfDoc, new Rectangle(coordX, coordY, width, this.heightFooter + SIZE_REFACTORY));
            this.tablesSubFooter.stream().forEach(x -> {
                canvas.add(x);
            });
        }
    }

    private float getHeight(Table element) {
        TableRenderer renderer = (TableRenderer) element.createRendererSubTree();
        renderer.setParent(new DocumentRenderer(this.doc));
        LayoutResult result = renderer.layout(new LayoutContext(new LayoutArea(0, PageSize.A4)));
        return result.getOccupiedArea().getBBox().getHeight();
    }

    private float getTotalHeight() {
        float height = 0f;
        for (Table x : this.tablesSubFooter) {
            height = height + this.getHeight(x);
        }
        return height;
    }

    public float getHeightFooter() {
        return heightFooter;
    }

}