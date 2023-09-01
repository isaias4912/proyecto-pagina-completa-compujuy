/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author rafael
 */
public class ResumenVentaDTO implements Serializable {

    private BigDecimal total;

    public ResumenVentaDTO() {
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
