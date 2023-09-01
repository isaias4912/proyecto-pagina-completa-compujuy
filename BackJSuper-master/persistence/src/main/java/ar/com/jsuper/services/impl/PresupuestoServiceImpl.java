package ar.com.jsuper.services.impl;

import ar.com.jsuper.dao.*;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.ComparatorDetalleVentas;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.*;
import ar.com.jsuper.domain.utils.DataSummaryVentas;
import ar.com.jsuper.domain.utils.FilterCbte;
import ar.com.jsuper.domain.utils.FilterGeneric;
import ar.com.jsuper.domain.utils.PaginationRequestDTO;
import ar.com.jsuper.dto.*;
import ar.com.jsuper.dto.reportes.ReportesVentaProductoDTO;
import ar.com.jsuper.exceptions.JSuperException;
import ar.com.jsuper.services.ParametricasService;
import ar.com.jsuper.services.PresupuestoService;
import ar.com.jsuper.services.UtilService;
import ar.com.jsuper.services.VentasService;
import ar.com.jsuper.services.async.VentasAsyncService;
import ar.com.jsuper.services.factElec.FacturaElectronicaService;
import ar.com.jsuper.services.factElec.ws.FECAECabResponse;
import ar.com.jsuper.services.factElec.ws.FECAEDetRequest;
import ar.com.jsuper.services.factElec.ws.FECAEDetResponse;
import ar.com.jsuper.services.factElec.ws.FECAEResponse;
import ar.com.jsuper.services.websockets.Message;
import ar.com.jsuper.services.websockets.VentaWSService;
import ar.com.jsuper.utils.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PresupuestoServiceImpl extends BaseService implements PresupuestoService {

    @Autowired
    PresupuestoDAO presupuestoDAO;
    @Autowired
    private ListaPreciosDAO listaPreciosDAO;
    @Autowired
    private UtilService utilService;
    @Autowired
    private ParametricasService parametricasService;

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public Pagination<CbtePresEncMinDTO> getByPage(FilterCbte filterCbte, PaginationRequestDTO paginationRequestDTO) {
        return this.getPagination(presupuestoDAO.getPresupuestoByPage(filterCbte, paginationRequestDTO), CbtePresEncMinDTO.class);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public List<CbtePresEncMinDTO> getPresupuestos(FilterCbte filterCbte, PaginationRequestDTO paginationRequestDTO) {
        paginationRequestDTO.setPage(0);
        paginationRequestDTO.setPageSize(0);
        Pagination<CbtePresEncMinDTO> pagination = this.getByPage(filterCbte, paginationRequestDTO);
        return pagination.getData();
    }

    @Override
    @Transactional()
    public Boolean updateEstadoCbte(Integer id, Integer value) {
        CbteEncPresupuesto cbteEncPresupuesto = this.presupuestoDAO.get(id);
        this.presupuestoDAO.updateEstadoCbte(cbteEncPresupuesto, EstadoCbte.value(value));
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public CbtePresupuestoEncDTO get(Integer id) {
        return modelMapper.map(presupuestoDAO.get(id), CbtePresupuestoEncDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public CbtePresupuestoEncVenDTO getToVen(Integer id) {
        return modelMapper.map(presupuestoDAO.get(id), CbtePresupuestoEncVenDTO.class);
    }

    @Override
    @Transactional()
    public CbtePresupuestoEncDTO insertPresupuesto(CbtePresupuestoEncDTO cbtePresupuestoEncDTO) {
        CbteEncPresupuesto cbteEncPresupuesto = modelMapper.map(cbtePresupuestoEncDTO, CbteEncPresupuesto.class);
        try {
            cbteEncPresupuesto = presupuestoDAO.insertPresupuesto(cbteEncPresupuesto);
        } catch (BussinessException ex) {
            throw new DataIntegrityViolationException("No se pudo registrar la venta");
        } catch (Exception ex) {
            throw new DataIntegrityViolationException("No se pudo registrar la venta");
        }
        cbtePresupuestoEncDTO = modelMapper.map(cbteEncPresupuesto, CbtePresupuestoEncDTO.class);
        return cbtePresupuestoEncDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Map getDataPresupuestos() {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> empresa = new HashMap<>();
        ConfiguracionDTO config = utilService.getConfiguracionFromApp();
        ArrayNode tiposComprobantes = this.getTpoCbtesPresupuesto();
        List<ListaPreciosDTO> listaPreciosDTO = modelMapper.mapAsList(listaPreciosDAO.getAllListActiveId(), ListaPreciosDTO.class);
        data.put("formasPago", this.parametricasService.getListAllActiveForcliente());
        data.put("conceptos", ConstanstAfipTest.getTpoConceptos());
        data.put("docs", ConstanstAfipTest.getTpoDocs());
        data.put("tributos", ConstanstAfipTest.getTpoTributos());
        data.put("ivas", ConstanstAfipTest.getTpoIvas());
        data.put("facturas", ConstanstAfipTest.getTpoFacturas(1));
        data.put("enabled", config.getEnabledVenta());
        data.put("comprobantes", tiposComprobantes);
        data.put("listaPrecios", listaPreciosDTO);
        // datos empresa
        empresa.put("tipo", config.getTipoEmpresa());
        empresa.put("razonSocial", config.getRazonSocial());
        empresa.put("cuit", config.getCuitEmpresa());
        data.put("empresa", empresa);
        return data;
    }

    public ArrayNode getTpoCbtesPresupuesto() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode temp = mapper.createObjectNode();
        ArrayNode arrayNode = mapper.createArrayNode();
        temp = mapper.createObjectNode();
        temp.put("id", 11);
        temp.put("descripcion", "Presupuesto");
        temp.put("nombre", Comprobante.PR.toString());
        arrayNode.add(temp);
        return arrayNode;
    }
}
