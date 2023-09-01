/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Configuracion;
import ar.com.jsuper.dto.AfipDTO;
import ar.com.jsuper.dto.AppDTO;
import ar.com.jsuper.dto.ClienteAppDesktopDTO;
import ar.com.jsuper.dto.ConfiguracionDTO;
import ar.com.jsuper.dto.DashBoardDTO;
import ar.com.jsuper.dto.DataClientDesktop;
import ar.com.jsuper.dto.FilterGenericDTO;
import ar.com.jsuper.dto.NotificacionesDTO;
import ar.com.jsuper.dto.VencimientosProductosDTO;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author rafa22
 */
public interface UtilService {

    DashBoardDTO getDataDashBoard() throws BussinessException;

    ConfiguracionDTO getConfiguracionFromApp();

    DataClientDesktop getDataAppDesktop(ClienteAppDesktopDTO clienteAppDesktop, String option) throws BussinessException;

    AppDTO getDataApp();

    Map getConfiguracionGral();

    AppDTO saveDataApp(AppDTO appDTO) throws BussinessException;

    ConfiguracionDTO getConfiguracion();

    ConfiguracionDTO getConfiguracionFromId(Integer idApp);

    ConfiguracionDTO updateConfiguracion(ConfiguracionDTO configuracionDTO);

    ConfiguracionDTO updateConfiguracionBasica(ConfiguracionDTO configuracionDTO);

    VencimientosProductosDTO saveVencimientoProducto(VencimientosProductosDTO vencimientosProductoDTO);

    Pagination<VencimientosProductosDTO> getVencimientosByPage(FilterGenericDTO filterGenericDTO, int page, int pageSize, String fieldOrder,
            boolean reverse);

    void enabledOrdisabledVencimientos(Set<VencimientosProductosDTO> vencimientosProductosDTO, boolean value);

    void enabledOrdisabledAlertaVencimientos(VencimientosProductosDTO vencimientosProductosDTO, boolean value);

    void deleteVencimientos(Integer id);

    List<NotificacionesDTO> notificaciones();

    Map<String, Object> getEncabezadoReporte();

    void setDataAppAfip(Integer idApp, String token, String sign, Date init, Date end);

    void setDataAppAfipTest(Integer idApp, String token, String sign, Date init, Date end);

    AfipDTO getDataAppAfip();

    String readStringPrivateKey();

    void generateCSR(String CN, String O, String cuit, String password);

    void generatePkcs12();

    void generatePrivateKey();

    Boolean existFileInAppDir(String file);

    void updateNameCRTConfiguracion(String name);

    Boolean isDataProd();

    String getAfipModo();

    String getRealAfipModo(Configuracion configuracion);

    ConfiguracionDTO setBackup(ConfiguracionDTO config);

}
