package ar.com.jsuper.services.impl;

import ar.com.jsuper.dao.ContImpuestosDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.CbteCompEnc;
import ar.com.jsuper.domain.Impuesto;
import ar.com.jsuper.domain.MovimientosCtaCteProv;
import ar.com.jsuper.domain.utils.FilterFacturas;
import ar.com.jsuper.domain.CbteCompDet;
import ar.com.jsuper.dto.CbteCompEncListDTO;
import ar.com.jsuper.dto.ImpuestoDTO;
import ar.com.jsuper.dto.CbteCompEncDTO;
import ar.com.jsuper.dto.FilterFacturasDTO;
import ar.com.jsuper.dto.MovimientosCtaCteProvDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.services.ContabilidadGenService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.transaction.annotation.Transactional;
import ar.com.jsuper.dao.CbteCompDAO;
import ar.com.jsuper.domain.IvasAfip;
import ar.com.jsuper.dto.CbteCompEncLibDTO;
import ar.com.jsuper.utils.AfipUtil;
import ar.com.jsuper.utils.Comprobante;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class ContabilidadGenServiceImpl implements ContabilidadGenService {

    @Autowired
    private OrikaBeanMapper modelMapper;
    @Autowired
    private ContImpuestosDAO contImpuestosDAO;
    @Autowired
    private CbteCompDAO contFacturasEncDAO;

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public List<ImpuestoDTO> getAllImpuestosActive() throws BussinessException {
        List<Impuesto> contImpuestos = contImpuestosDAO.getAllListActive();
        return modelMapper.mapAsList(contImpuestos, ImpuestoDTO.class);
    }

    /**
     * Guardar una factura de compra
     *
     * @param contFacturasEncDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public CbteCompEncDTO saveFacturaCompra(CbteCompEncDTO cbteCompDTO) {
        CbteCompEnc cbteCompEnc = modelMapper.map(cbteCompDTO, CbteCompEnc.class);
        cbteCompEnc.setActivo(Boolean.TRUE);
        cbteCompEnc.setFechaCarga(new Date());
        cbteCompEnc.setTipo(1); // factura de compra
        this.setCompra(cbteCompEnc);
        cbteCompEnc = contFacturasEncDAO.insert(cbteCompEnc);
        cbteCompDTO = modelMapper.map(cbteCompEnc, CbteCompEncDTO.class);
        return cbteCompDTO;
    }

    /**
     * cargamos los ivas y demas como dcorresponde para la entidades Para los ivas lo agrupamos por id, segun lo requiere la afip
     *
     * @param cbteCompEnc
     */
    private void setCompra(CbteCompEnc cbteCompEnc) {
        Set<CbteCompDet> detalleCompras = cbteCompEnc.getItems();
        if (cbteCompEnc.getTipoCbte().equals(Comprobante.C)) { // para comprobantes tipo C no se informa ni se cargan los ivas
            cbteCompEnc.setIvas(null);
        } else {
            if (Objects.isNull(detalleCompras)) {
                throw new DataIntegrityViolationException("La factura debe tener detalles");
            } else {
                Set<IvasAfip> ivas = new HashSet<>();
                detalleCompras.stream().map(x -> x.getIvaId()).distinct().forEach(x -> {
                    IvasAfip i = new IvasAfip();
                    i.setIdAfipIva(x);
                    BigDecimal ivaTotal = detalleCompras.stream().filter(y -> y.getIvaId() == x).map(z -> z.getIvaTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal baseImpTotal = detalleCompras.stream().filter(y -> y.getIvaId() == x).map(z -> z.getBaseImponibleTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
                    i.setBaseImponible(baseImpTotal.doubleValue());
                    i.setImporte(ivaTotal.doubleValue());
                    i.setAlicuota(AfipUtil.getAlicuotaFromId(x));
                    i.setDescripcion(AfipUtil.getDescripcionFromId(x));
                    ivas.add(i);
                });
                cbteCompEnc.setIvas(ivas);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public Pagination<CbteCompEncListDTO> getFacturasByPage(FilterFacturasDTO filterFacturasDTO, int page, int pageSize, String fieldOrder,
            boolean reverse, Integer tipo
    ) {
        FilterFacturas filterFacturas = modelMapper.map(filterFacturasDTO, FilterFacturas.class);
        // armamos los filtros extras para los daos
        Pagination<CbteCompEnc> paginacionFacturas = contFacturasEncDAO.getFacturasByPage(filterFacturas, page, pageSize,
                fieldOrder, reverse, tipo);
        List<CbteCompEncListDTO> lista = new ArrayList<>();
        if (paginacionFacturas.getData() != null) {
            lista = modelMapper.mapAsList(paginacionFacturas.getData(), CbteCompEncListDTO.class);
        }
        Pagination<CbteCompEncListDTO> pag = new Pagination<>();
        pag.setData(lista);
        pag.setTotal(paginacionFacturas.getTotal());
        pag.setPageSize(paginacionFacturas.getPageSize());
        pag.setPage(paginacionFacturas.getPage());
        return pag;
    }

    @Override
    @Transactional(readOnly = true)
    public CbteCompEncDTO get(Integer id
    ) {
        CbteCompEnc contFacturasEnc = contFacturasEncDAO.getFactura(id);
        return modelMapper.map(contFacturasEnc, CbteCompEncDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public MovimientosCtaCteProvDTO getMovimientoFromFactura(Integer id) {
        MovimientosCtaCteProv movimientosCtaCteProv = contFacturasEncDAO.getMovimientoFromFactura(id);
        return modelMapper.map(movimientosCtaCteProv, MovimientosCtaCteProvDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public CbteCompEncListDTO getFacturaByNum(String q) {
        CbteCompEnc contFacturasEnc = contFacturasEncDAO.getFacturaByNum(q);
        if (Objects.isNull(contFacturasEnc)) {
            return null;
        } else {
            return modelMapper.map(contFacturasEnc, CbteCompEncListDTO.class);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CbteCompEncDTO getFacturaByPago(Integer idPago) {
        CbteCompEnc contFacturasEnc = contFacturasEncDAO.getFacturaByPago(idPago);
        if (Objects.isNull(contFacturasEnc)) {
            return null;
        } else {
            return modelMapper.map(contFacturasEnc, CbteCompEncDTO.class);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Pagination<CbteCompEncLibDTO> getLibroIvaByPage(FilterFacturas filterVentas, Integer page, Integer pageSize, String fieldOrder, Boolean reverse) {
        // armamos los filtros extras para los daos
        Pagination<CbteCompEnc> paginacionVentas = contFacturasEncDAO.getLibroIvaByPage(filterVentas, page, pageSize, fieldOrder, reverse);
        List<CbteCompEncLibDTO> lista = new ArrayList<>();
        if (paginacionVentas.getData() != null) {
            lista = modelMapper.mapAsList(paginacionVentas.getData(), CbteCompEncLibDTO.class);
        }
        // traemos los totales

        Pagination<CbteCompEncLibDTO> pag = new Pagination<>();
        pag.setData(lista);
        pag.setExtraData(contFacturasEncDAO.getTotalesLibroIva(filterVentas));
        pag.setTotal(paginacionVentas.getTotal());
        pag.setPageSize(paginacionVentas.getPageSize());
        pag.setPage(paginacionVentas.getPage());
        return pag;
    }
}
