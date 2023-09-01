/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author rafa22
 */
public class DashBoardDTO {

    private Long cantidadProductos;
    private Long cantidadVentasHoy;
    private Long cantidadVentasAyer;
    private Long cantidadProdBajoPuntoRepo;
    private Long cantidadProdBajoStockMin;
    private Date ultimoAcceso;
    private List<LinkedHashMap<String, Object>> cantidadPorMes;
    private List<LinkedHashMap<String, Object>> pesosPorMes;
    private List<LinkedHashMap<String, Object>> cantidadPorDia;
    private List<LinkedHashMap<String, Object>> pesosPorDia;

    public DashBoardDTO() {
    }

    public Long getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(Long cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    public Long getCantidadVentasHoy() {
        return cantidadVentasHoy;
    }

    public void setCantidadVentasHoy(Long cantidadVentasHoy) {
        this.cantidadVentasHoy = cantidadVentasHoy;
    }

    public List<LinkedHashMap<String, Object>> getCantidadPorMes() {
        return cantidadPorMes;
    }

    public void setCantidadPorMes(List<LinkedHashMap<String, Object>> cantidadPorMes) {
        this.cantidadPorMes = cantidadPorMes;
    }

    public List<LinkedHashMap<String, Object>> getCantidadPorDia() {
        return cantidadPorDia;
    }

    public void setCantidadPorDia(List<LinkedHashMap<String, Object>> cantidadPorDia) {
        this.cantidadPorDia = cantidadPorDia;
    }

    public Date getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(Date ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public Long getCantidadProdBajoPuntoRepo() {
        return cantidadProdBajoPuntoRepo;
    }

    public void setCantidadProdBajoPuntoRepo(Long cantidadProdBajoPuntoRepo) {
        this.cantidadProdBajoPuntoRepo = cantidadProdBajoPuntoRepo;
    }

    public Long getCantidadProdBajoStockMin() {
        return cantidadProdBajoStockMin;
    }

    public void setCantidadProdBajoStockMin(Long cantidadProdBajoStockMin) {
        this.cantidadProdBajoStockMin = cantidadProdBajoStockMin;
    }

    public Long getCantidadVentasAyer() {
        return cantidadVentasAyer;
    }

    public void setCantidadVentasAyer(Long cantidadVentasAyer) {
        this.cantidadVentasAyer = cantidadVentasAyer;
    }

    public List<LinkedHashMap<String, Object>> getPesosPorMes() {
        return pesosPorMes;
    }

    public void setPesosPorMes(List<LinkedHashMap<String, Object>> pesosPorMes) {
        this.pesosPorMes = pesosPorMes;
    }

    public List<LinkedHashMap<String, Object>> getPesosPorDia() {
        return pesosPorDia;
    }

    public void setPesosPorDia(List<LinkedHashMap<String, Object>> pesosPorDia) {
        this.pesosPorDia = pesosPorDia;
    }

}
