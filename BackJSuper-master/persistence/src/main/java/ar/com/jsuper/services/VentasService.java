package ar.com.jsuper.services;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Cliente;
import ar.com.jsuper.domain.PagoCbteVen;
import ar.com.jsuper.dto.DataSummaryVentasDTO;
import ar.com.jsuper.dto.DineroDTO;
import ar.com.jsuper.dto.CbteVenEncDTO;
import ar.com.jsuper.dto.CbteVenEncLibDTO;
import ar.com.jsuper.dto.CbteVenEncMinDTO;
import ar.com.jsuper.dto.ConfiguracionDTO;
import ar.com.jsuper.dto.FilterGenericDTO;
import ar.com.jsuper.dto.FilterVentasDTO;
import ar.com.jsuper.dto.GananciasDTO;
import ar.com.jsuper.dto.reportes.ReportesVentaProductoDTO;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface VentasService {

    CbteVenEncDTO insertVenta(CbteVenEncDTO cbteVenEncDTO, ConfiguracionDTO configuracionDTO, Boolean checkStock);

    CbteVenEncDTO insertVentaFE(Integer idCbteVenO, ConfiguracionDTO configuracionDTO);

    CbteVenEncDTO refacturarAFIP(Integer id, ConfiguracionDTO configuracionDTO);

    Set<CbteVenEncDTO> getall() throws BussinessException;

    DataSummaryVentasDTO getSummaryVentasFromFilter(FilterVentasDTO filterVentasDTO);

    Pagination<CbteVenEncMinDTO> getVentasByPage(FilterVentasDTO filterVentasDTO, int page, int pageSize, String fieldOrder, boolean reverse);

    List<CbteVenEncMinDTO> getVentas(FilterVentasDTO filterVentasDTO, String fieldOrder, boolean reverse) throws BussinessException;

    List<CbteVenEncMinDTO> getAllVentasFromTransaccionCaja(Integer idTransaccionCaja) throws BussinessException;

    CbteVenEncDTO get(Integer id) throws BussinessException;

    CbteVenEncDTO get(Integer id, Boolean order);

    DineroDTO getTotalVentasFromTransaccion(Integer idTransaccion) throws BussinessException;

    DataSummaryVentasDTO getSummaryVentasFromTransaccion(Integer idTransaccion) throws BussinessException;

    Map getGraficosVentas(FilterVentasDTO filterVentasDTO);

    List getTotalPesosVentasByDateTime(String dateTimeInicial, String dateTimeFinal);

    List getTotalPesosVentasTodayBydateTime();

    Cliente verifiedClienteAndCtaCte(Set<PagoCbteVen> pagoVentas, Cliente cliente);

    List<ReportesVentaProductoDTO> getProductosMasVendidos(FilterGenericDTO filterGenericDTO);

    List<ReportesVentaProductoDTO> getProductosDeMasEntradaMonetaria(FilterGenericDTO filterGenericDTO);

    GananciasDTO getGanancias(FilterGenericDTO filterGenericDTO);

    int deleteVentaAdminMayor(Integer idVenta);

    ArrayNode getTpoCbtesVenta();

    Map getDataVentas(Integer presupuesto);

    Pagination<CbteVenEncLibDTO> getLibroIvaByPage(FilterVentasDTO filterCbte, Integer page, Integer pageSize, String fieldOrder, Boolean reverse);

    Boolean existByUUID(String uuid);

    CbteVenEncDTO getByUUID(String uuid);
}
