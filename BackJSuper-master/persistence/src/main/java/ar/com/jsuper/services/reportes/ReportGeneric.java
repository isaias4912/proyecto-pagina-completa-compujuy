package ar.com.jsuper.services.reportes;

import ar.com.jsuper.dto.reportes.ColumnGeneric;
import ar.com.jsuper.dto.reportes.DataReportGeneric;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ReportGeneric {
    byte[] printTablePdf(DataReportGeneric reportGeneric);

    byte[] printTable(DataReportGeneric reportGeneric, String typePrint);

    byte[] printTableCsv(DataReportGeneric reportGeneric) throws IOException;

}
