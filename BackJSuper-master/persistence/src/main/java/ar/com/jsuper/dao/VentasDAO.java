package ar.com.jsuper.dao;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.CbteDet;
import ar.com.jsuper.domain.Dinero;
import ar.com.jsuper.domain.CbteEncVenta;
import ar.com.jsuper.domain.Producto;
import ar.com.jsuper.domain.utils.*;
import ar.com.jsuper.dto.FilterVentasDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface VentasDAO extends GenericDAO<CbteEncVenta, Integer> {

    CbteEncVenta insertVenta(CbteEncVenta cbteEncVenta, Boolean checkStock);

    CbteEncVenta updateVenta(CbteEncVenta cbteEncVenta) throws BussinessException;

    Long getTotalVentasToday() throws BussinessException;

    Long getTotalVentasYesterday() throws BussinessException;

    Long getTotalVentasByMonthLater(Integer monthLater) throws BussinessException;

    BigDecimal getTotalPesosVentasByMonthLater(Integer monthLater);

    BigDecimal getTotalPesosVentasByDayLater(Integer dayLater);

    List<CbteDet> getDetalleVentaByProducto(Producto producto);

    Long getTotalVentasByDayLater(Integer dayLater) throws BussinessException;

    Pagination<CbteEncVenta> getVentasByPage(FilterCbte filterCbte, int page, int pageSize, String fieldOrder, boolean reverse);

    List<CbteEncVenta> getAllVentasFromTransaccionCaja(Integer idTransaccionCaja) throws BussinessException;

    Dinero getTotalVentasFromTransaccion(Integer idTransaccion) throws BussinessException;

    DataSummaryVentas getSummaryVentasFromTransaccion(Integer idTransaccion) throws BussinessException;

    DataSummaryVentas getSummaryVentasFromFilter(FilterCbte filterCbte);

    BigDecimal getTotalPesosVentasByDateTime(String dateTimeInicial, String dateTimeFinal);

    List<ReportesVentaProducto> getProductosMasVendidos(FilterGeneric filterGeneric);

    List<ReportesVentaProducto> getProductosDeMasEntradaMonetaria(FilterGeneric filterGeneric);

    Ganancias getGanancias(FilterGeneric filterGeneric);

    int deleteVentaAdminMayor(Integer idVenta);

    Pagination<CbteEncVenta> getLibroIvaByPage(FilterCbte filterCbte, Integer page, Integer pageSize, String fieldOrder, Boolean reverse);

    Map getTotalesLibroIva(FilterCbte filterCbte);

    CbteEncVenta getByUUID(String uuid);

    Boolean existByUUID(String uuid);

}
