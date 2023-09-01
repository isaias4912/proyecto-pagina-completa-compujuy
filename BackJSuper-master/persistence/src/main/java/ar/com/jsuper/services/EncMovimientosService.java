package ar.com.jsuper.services;

import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.dao.utils.PaginationExtraData;
import ar.com.jsuper.domain.StockSucursal;
import ar.com.jsuper.dto.*;

import java.util.List;

public interface EncMovimientosService {

	EncMovimientosDTO insertMovimiento(EncMovimientosDTO facturaDTO);

	PaseMovimientosDTO insertMovimientoPase(PaseMovimientosDTO movimientoPaseDTO);

	void removePase(PaseMovimientosDTO paseDTO);

	public PaseMovimientosDTO getPase(Integer idPase);

	Pagination<PaseMovimientosDTO> getPasesBypage(FilterGenericDTO filterGenericDTO, int page, int pageSize, String fieldOrder, boolean reverse);

	PaseMovimientosDTO confirmPase(PaseMovimientosDTO movimientoPaseDTO);

	EncMovimientosDTO removeMovimiento(EncMovimientosDTO facturaDTO);

	Pagination<DetMovimientosMinDTO> getMovimientosByProducto(Integer idProducto, int page, int pageSize, String fieldOrder, boolean reverse, boolean ventas, boolean asociados);

	PaginationExtraData<MovimientoProductoDTO, List<StockSucursalMinDTO>> getMovimientosProductoByProducto(Integer idProducto, int page, int pageSize, String fieldOrder, boolean reverse, FilterGenericDTO filterGenericDTO);

	Pagination<HistPreciosDTO> getMovimientosPrecioByProducto(Integer idProducto, int page, int pageSize, String fieldOrder, boolean reverse);

	StockFromFacturaEncDTO addStockFromFacturaPreview(Integer idFactura);

	EncMovimientosDTO addStockFromFactura(StockFromFacturaEncDTO stockFromFacturaEncDTO);
}
