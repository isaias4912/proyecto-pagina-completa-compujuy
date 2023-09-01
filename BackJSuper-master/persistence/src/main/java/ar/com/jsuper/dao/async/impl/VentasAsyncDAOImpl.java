package ar.com.jsuper.dao.async.impl;

import ar.com.jsuper.dao.MovimientosDAO;
import ar.com.jsuper.dao.async.VentasAsyncDAO;
import ar.com.jsuper.domain.CbteEnc;
import ar.com.jsuper.domain.StockSucursal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VentasAsyncDAOImpl implements VentasAsyncDAO {

	@Autowired
	private MovimientosDAO movimientosDAO;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Async("taskExecutor")
	public void saveMovimientoProducto(CbteEnc cbteEnc, StockSucursal ss) {
		this.movimientosDAO.saveMovimientoProducto(cbteEnc.getDetalleVentas(), cbteEnc.getFechaCarga(), ss);
	}
}
