package ar.com.jsuper.services.reportes;

import com.itextpdf.kernel.colors.DeviceGray;
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
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;

import java.util.List;

import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.TableRenderer;
import org.springframework.lang.NonNull;

public class HeaderDefaultPageEvent implements IEventHandler {

    private Document doc;
    private Table tableHeader;
    private List<Table> tablesSubHeader;
    private float heightHeader;
    private float marginTop;
    private float marginRight;
    private float marginBottom;
    private float marginLeft;

    private static final float MARGIN_TOP = 36;
    private static final float MARGIN_LEFT = 36;
    private static final float MARGIN_BOTTOM = 36;
    private static final float MARGIN_RIGHT = 36;

    public HeaderDefaultPageEvent(@NonNull Document doc, @NonNull Table tableHeader, @NonNull List<Table> tablesSubHeader) {
        this.tableHeader = tableHeader;
        this.tablesSubHeader = tablesSubHeader;
        this.doc = doc;
        this.marginLeft= MARGIN_LEFT;
        this.marginBottom= MARGIN_BOTTOM;
        this.marginRight= MARGIN_RIGHT;
        this.marginTop= MARGIN_TOP;
        this.init();
    }

    public HeaderDefaultPageEvent(@NonNull Document doc, @NonNull Table tableHeader, @NonNull List<Table> tablesSubHeader, @NonNull float margin) {
        this.tableHeader = tableHeader;
        this.tablesSubHeader = tablesSubHeader;
        this.doc = doc;
        this.marginTop=margin;
        this.marginLeft=margin;
        this.marginRight=margin;
        this.marginBottom=margin;
        this.init();
    }

    private void init() {
        this.heightHeader = this.getHeight(this.tableHeader);
        this.heightHeader = this.heightHeader + this.getTotalHeight(this.tablesSubHeader);
        float topMargin = this.heightHeader + this.marginTop;
        doc.setMargins(topMargin, this.marginRight, this.marginBottom, this.marginLeft);
    }

    @Override
    public void handleEvent(Event currentEvent) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) currentEvent;
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();
        PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
        PageSize pageSize = pdfDoc.getDefaultPageSize();
        float coordX = pageSize.getX() + doc.getLeftMargin();
        float coordY = pageSize.getTop() - doc.getTopMargin() - this.marginTop;
        float width = pageSize.getWidth() - doc.getRightMargin() - doc.getLeftMargin();
        Rectangle rect = new Rectangle(coordX, coordY, width, this.heightHeader + this.marginTop);
        Canvas cHeader = new Canvas(canvas, pdfDoc, rect)
                .add(this.tableHeader);
        this.tablesSubHeader.forEach(x -> {
            cHeader.add(x);
        });
        cHeader.setBorder(new SolidBorder(1f));
        cHeader.setBackgroundColor(new DeviceGray(1f));
        cHeader.close();
    }

    private float getHeight(Table element) {
        TableRenderer renderer = (TableRenderer) element.createRendererSubTree();
        renderer.setParent(new DocumentRenderer(this.doc));
        LayoutResult result = renderer.layout(new LayoutContext(new LayoutArea(0, PageSize.A4)));
        return result.getOccupiedArea().getBBox().getHeight();
    }

    private float getTotalHeight(List<Table> tables) {
        float height = 0f;
        for (Table x : tables) {
            height = height + this.getHeight(x);
        }
        return height;
    }

    public float getHeightHeader() {
        return heightHeader;
    }

}