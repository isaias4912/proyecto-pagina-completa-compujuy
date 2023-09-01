package ar.com.jsuper.dto;

import java.util.Set;
import java.io.Serializable;

public class FilterProductoPadreDTO implements Serializable {

    private Set<FamiliaDTO> familias;
    private ProductoPadreDTO productoPadre;

    public FilterProductoPadreDTO() {
    }

    public Set<FamiliaDTO> getFamilias() {
        return familias;
    }

    public void setFamilias(Set<FamiliaDTO> familias) {
        this.familias = familias;
    }

    public ProductoPadreDTO getProductoPadre() {
        return productoPadre;
    }

    public void setProductoPadre(ProductoPadreDTO productoPadre) {
        this.productoPadre = productoPadre;
    }

  
}
