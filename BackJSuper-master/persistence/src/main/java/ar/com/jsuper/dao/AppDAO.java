package ar.com.jsuper.dao;

import ar.com.jsuper.domain.App;
import ar.com.jsuper.domain.Configuracion;
import ar.com.jsuper.domain.PuntoVenta;
import java.util.Map;
import java.util.Set;

public interface AppDAO extends GenericDAO<App, Integer> {

    App get();

    App getAppAndConfig();

    Configuracion getConfiguracion();

    Configuracion getConfiguracionFromAppId(Integer id);

    Configuracion updateConfiguracion(Configuracion config);

    Configuracion updateConfiguracionBasica(Configuracion config);

    Map<String, Object> getEncabezadoReporte();

    Configuracion enabledOrdisabledProdAfip(boolean value);

    Configuracion setBackup(Configuracion config);

    void saveUpdateOrDelete(Configuracion configuracion, Set<PuntoVenta> puntosVenta, Set<PuntoVenta> puntosVentaDB);

    void deleteVentasByApp(Integer idApp);

}
