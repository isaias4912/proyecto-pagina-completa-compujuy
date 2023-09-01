/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.async;

import ar.com.jsuper.dto.CbteVenDetSinEncabDTO;
import ar.com.jsuper.dto.CbteVenEncDTO;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.List;

/**
 * @author rafa
 */
public interface VentasAsyncService {

    void extraTasksVenta(CbteVenEncDTO cbteVenEncDTO, Authentication authentication);
}
