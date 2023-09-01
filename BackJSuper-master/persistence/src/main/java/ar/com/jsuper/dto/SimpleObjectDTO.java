/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

/**
 *
 * @author rafa22
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleObjectDTO implements Serializable {

    private Integer id;

    public SimpleObjectDTO() {
    }

    public SimpleObjectDTO(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
