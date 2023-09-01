package ar.com.jsuper.services;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.utils.FilterFacturas;
import ar.com.jsuper.dto.CbteCompEncListDTO;
import ar.com.jsuper.dto.ImpuestoDTO;
import ar.com.jsuper.dto.CbteCompEncDTO;
import ar.com.jsuper.dto.CbteCompEncLibDTO;
import ar.com.jsuper.dto.FilterFacturasDTO;
import ar.com.jsuper.dto.MovimientosCtaCteProvDTO;
import java.util.List;

public interface ContabilidadGenService {

    public List<ImpuestoDTO> getAllImpuestosActive() throws BussinessException;

    public CbteCompEncDTO saveFacturaCompra(CbteCompEncDTO facturaCompraDTO);

    public Pagination<CbteCompEncListDTO> getFacturasByPage(FilterFacturasDTO filterFacturasDTO, int page, int pageSize, String fieldOrder, boolean reverse, Integer tipo);

    public CbteCompEncDTO get(Integer id);

    public CbteCompEncListDTO getFacturaByNum(String q);

    public MovimientosCtaCteProvDTO getMovimientoFromFactura(Integer id);

    public CbteCompEncDTO getFacturaByPago(Integer idPago);

    public Pagination<CbteCompEncLibDTO> getLibroIvaByPage(FilterFacturas filterVentas, Integer page, Integer pageSize, String fieldOrder, Boolean reverse);

}
