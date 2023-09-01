package ar.com.jsuper.services.async.impl;

import ar.com.jsuper.dao.MovimientosDAO;
import ar.com.jsuper.dao.VentasDAO;
import ar.com.jsuper.domain.CbteDet;
import ar.com.jsuper.dto.CbteVenEncDTO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.services.async.VentasAsyncService;

import ar.com.jsuper.services.websockets.Message;
import ar.com.jsuper.services.websockets.VentaWSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VentasAsyncServiceImpl implements VentasAsyncService {

	@Autowired
	private OrikaBeanMapper modelMapper;
	@Autowired
	private MovimientosDAO movimientosDAO;
	@Autowired
	private VentaWSService ventaWSService;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Async("taskExecutor")
	public void extraTasksVenta(CbteVenEncDTO cbteVenEncDTO, Authentication authentication) {
		this.ventaWSService.sendMsgNuevaVenta(new Message(cbteVenEncDTO.getId(), "Nueva venta"), authentication.getName());
	}
}
