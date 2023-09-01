/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.reportes.impl;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Sucursales;
import ar.com.jsuper.dto.CodigoBarrasDTO;
import ar.com.jsuper.dto.FamiliaDTO;
import ar.com.jsuper.dto.FilterProductoDTO;
import ar.com.jsuper.dto.ProductoDTO;
import ar.com.jsuper.dto.reportes.ProductoBarCodeDTO;
import ar.com.jsuper.dto.ProductoListDTO;
import ar.com.jsuper.dto.ProductoListStockDTO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.dto.reportes.BarCodes5ColsDTO;
import ar.com.jsuper.dto.reportes.BarCodesReqDTO;
import ar.com.jsuper.services.ProductosService;
import ar.com.jsuper.services.reportes.ReporteProductos;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.impl.upcean.UPCEANLogicImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rafael
 */
@Service
public class ReporteProductosImpl extends ReportesImpl implements ReporteProductos {

	@Autowired
	ProductosService productosService;
	@Autowired
	private OrikaBeanMapper modelMappero;

	@Override
	public byte[] printListProductosPDF(FilterProductoDTO productoFilter, String fieldOrder, boolean reverse, String type) {
//		List<ProductoListDTO> productoListDTO = productosService.getDetalleProducto(productoFilter, fieldOrder, reverse, type);
//		// esto realizamos para pasar el dato de stock para q no sea nulo
//		List<ProductoListStockDTO> productoListStockDTO = modelMappero.mapAsList(productoListDTO, ProductoListStockDTO.class);
//		this.loadReport("listadoProductosReport.jasper");
		this.baos = new ByteArrayOutputStream();
//		/* Convert List to JRBeanCollectionDataSource */
//		JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(productoListStockDTO);
//		/* Map to hold Jasper report Parameters */
//		Map<String, Object> parameters = this.getBaseData();
//		parameters.put("listDataSource", itemsJRBean);
//		parameters.put("leyenda", this.getLeyendaListadoProductos(productoFilter).toString());
//		jasperPrint = JasperFillManager.fillReport(this.jasperReport, parameters, new JREmptyDataSource());
//		JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
		return this.baos.toByteArray();
	}

	@Override
	public byte[] printListProductos(FilterProductoDTO productoFilter, String fieldOrder, boolean reverse, String type, String typePrint) {
//		List<ProductoListDTO> productoListDTO = productosService.getDetalleProducto(productoFilter, fieldOrder, reverse, type);
//		List<ProductoListStockDTO> productoListStockDTO = modelMappero.mapAsList(productoListDTO, ProductoListStockDTO.class);
//		this.loadReport("listadoProductosReport.jasper");
//		/* Convert List to JRBeanCollectionDataSource */
//		JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(productoListStockDTO);
//		/* Map to hold Jasper report Parameters */
//		Map<String, Object> parameters = this.getBaseData();
//		parameters.put("listDataSource", itemsJRBean);
//		parameters.put("leyenda", this.getLeyendaListadoProductos(productoFilter).toString());
//		jasperPrint = JasperFillManager.fillReport(this.jasperReport, parameters, new JREmptyDataSource());
//		JRAbstractExporter exporter = null;
//		if (typePrint.equals("xls")) {
//			exporter = new JRXlsxExporter();
//		}
//		if (typePrint.equals("html")) {
//			exporter = new HtmlExporter();
//		}
//		if (typePrint.equals("csv")) {
//			exporter = new JRCsvExporter();
//		}
//		if (typePrint.equals("doc")) {
//			exporter = new JRDocxExporter();
//		}
//		return this.getByteArray(jasperPrint, exporter);
		return null;
	}

	@Override
	public byte[] printDetailProductoPDF(Integer idProducto) {
//		ProductoDTO producto = productosService.getProducto(idProducto, false);
//		this.loadReport("detalleProductoReport.jasper");
		this.baos = new ByteArrayOutputStream();
//		/* Map to hold Jasper report Parameters */
//		Map<String, Object> parameters = this.getBaseData();
//		parameters.put("producto", producto);
////        jasperPrint = JasperFillManager.fillReport(this.jasperReport, null, new JRBeanCollectionDataSource(productoListDTO));
//		jasperPrint = JasperFillManager.fillReport(this.jasperReport, parameters, new JRBeanArrayDataSource(new ProductoDTO[]{producto}));
//		JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
		return this.baos.toByteArray();
	}

	@Override
	public byte[] printBarCodesColsPDF(List<BarCodesReqDTO> barCodesReqDTOs, Integer cols, Integer style, String barcode) {
//		JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(this.getListaBarCode(barCodesReqDTOs, cols));
//		this.loadReport("barcode/" + barcode.toLowerCase() + "/listBarCodesReport" + style + ".jasper");
		this.baos = new ByteArrayOutputStream();
//		Map<String, Object> parameters = this.getBaseData();
//		parameters.put("lista", itemsJRBean);
//		jasperPrint = JasperFillManager.fillReport(this.jasperReport, parameters, new JREmptyDataSource());
//		JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
		return this.baos.toByteArray();
	}

	@Override
	public byte[] printBarCodesCols(List<BarCodesReqDTO> barCodesReqDTOs, Integer cols, Integer style, String barcode, String typePrint) {
//		JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(this.getListaBarCode(barCodesReqDTOs, cols));
//		this.loadReport("barcode/" + barcode.toLowerCase() + "/listBarCodesReport" + style + ".jasper");
//		this.baos = new ByteArrayOutputStream();
//		Map<String, Object> parameters = this.getBaseData();
//		parameters.put("lista", itemsJRBean);
//		jasperPrint = JasperFillManager.fillReport(this.jasperReport, parameters, new JREmptyDataSource());
//		JRAbstractExporter exporter = null;
//		if (typePrint.equals("xls")) {
//			exporter = new JRXlsxExporter();
//		}
//		if (typePrint.equals("html")) {
//			exporter = new HtmlExporter();
//		}
//		if (typePrint.equals("csv")) {
//			exporter = new JRCsvExporter();
//		}
//		if (typePrint.equals("doc")) {
//			exporter = new JRDocxExporter();
//		}
//		return this.getByteArray(jasperPrint, exporter);
		return null;
	}

	private List<BarCodes5ColsDTO> getListaBarCode(List<BarCodesReqDTO> barCodesReqDTOs, Integer cols) {
		List<BarCodes5ColsDTO> lista = new ArrayList<>();
		BarCodes5ColsDTO barCodes5ColsDTO = null;
		for (BarCodesReqDTO barCodesReqDTO : barCodesReqDTOs) {
			ProductoBarCodeDTO producto = productosService.getProductoMinBarCode(barCodesReqDTO.getId());
			producto.setDescripcion(barCodesReqDTO.getDescripcion());
			producto.setPrecio(barCodesReqDTO.getPrecio());
			EAN13Bean generator = new EAN13Bean();
			UPCEANLogicImpl impl = generator.createLogicImpl();
			producto.setCodigoBarra(barCodesReqDTO.getCodigo());// por ahora le mandamos el codigo q viene del front, no comprobamos nada
			Integer columnas = barCodesReqDTO.getCantidad();
			Integer columna = 1;
			Double filas = Math.ceil(Double.valueOf(columnas) / cols);
			for (int i = 1; i <= filas; i++) {
				barCodes5ColsDTO = new BarCodes5ColsDTO();
				for (int j = 1; j <= cols; j++) {
					if (columna <= columnas) {
						if (j == 1) {
							barCodes5ColsDTO.setProducto1(producto);
						}
						if (j == 2) {
							barCodes5ColsDTO.setProducto2(producto);
						}
						if (j == 3) {
							barCodes5ColsDTO.setProducto3(producto);
						}
						if (j == 4) {
							barCodes5ColsDTO.setProducto4(producto);
						}
						if (j == 5) {
							barCodes5ColsDTO.setProducto5(producto);
						}
						columna++;
					}
				}
				lista.add(barCodes5ColsDTO);
			}
		}
		return lista;
	}

	private StringBuilder getLeyendaListadoProductos(FilterProductoDTO productoFilter) {
		StringBuilder leyenda = new StringBuilder("");
		if (productoFilter != null) {
			if (productoFilter.getProducto().getNombre() != null && !productoFilter.getProducto().getNombre().isEmpty()) {
				leyenda.append("Nombre/s: ");
				leyenda.append(productoFilter.getProducto().getNombre());
				leyenda.append(" | ");
			}
			if (productoFilter.getProducto().getCodigoBarras() != null && !productoFilter.getProducto().getCodigoBarras().isEmpty()) {
				StringBuilder codigo = new StringBuilder("");
				Boolean entro = false;
				for (CodigoBarrasDTO codigoBarra : productoFilter.getProducto().getCodigoBarras()) {
					codigo.append("Código de barra/s: ");
					if (!codigoBarra.getCodigo().trim().equals("")) {
						codigo.append(codigoBarra.getCodigo());
						codigo.append(" ");
						entro = true;
					}
				}
				if (entro) {
					leyenda.append(codigo);
					leyenda.append(" | ");
				}
			}
			if (productoFilter.getProducto().getCodigoEspecial() != null && !productoFilter.getProducto().getCodigoEspecial().isEmpty()) {
				leyenda.append("Cód Int: ");
				leyenda.append(productoFilter.getProducto().getCodigoEspecial());
				leyenda.append(" | ");
			}
			if (productoFilter.getProducto().getCodigoEspecial() != null && !productoFilter.getProducto().getCodigoEspecial().isEmpty()) {
				leyenda.append("Cód Int: ");
				leyenda.append(productoFilter.getProducto().getCodigoEspecial());
				leyenda.append(" | ");
			}
			if (productoFilter.getFamilias() != null && !productoFilter.getFamilias().isEmpty()) {
				leyenda.append("Familia/s: ");
				for (FamiliaDTO fam : productoFilter.getFamilias()) {
					leyenda.append(fam.getId());
					leyenda.append(" ");
				}
				leyenda.append(" | ");
			}
			if (productoFilter.getTipo() != null) {
				leyenda.append("Tipo: ");
				leyenda.append(productoFilter.getTipo() == 1 ? "Simple" : "Comp.");
				leyenda.append(" | ");
			}
			if (productoFilter.getActivo() != null) {
				leyenda.append("Activos: ");
				leyenda.append(productoFilter.getActivo() == 1 ? "Si" : "No");
				leyenda.append(" | ");
			}
			if (productoFilter.getSucursales() != null && !productoFilter.getSucursales().isEmpty()) {
				leyenda.append("Sucursal/es: ");
				for (Sucursales sucursal : productoFilter.getSucursales()) {
					leyenda.append(sucursal.getId());
					leyenda.append(" ");
				}
				leyenda.append(" | ");
			}
			if (productoFilter.getFilterStock() != null) {
				if (productoFilter.getFilterStock() == 1 || productoFilter.getFilterStock() == 2) {
					leyenda.append("Stock: ");
					leyenda.append(productoFilter.getFilterStock() == 1 ? "Prod. debajo del stock min." : productoFilter.getFilterStock() == 2 ? "Prod. debajo del pto. de repo." : "Todos");
					leyenda.append(" | ");
				}
			}
		}
		if (leyenda.length() > 30) {
			leyenda.substring(leyenda.length() - 2);
		}
		return leyenda;
	}
}
