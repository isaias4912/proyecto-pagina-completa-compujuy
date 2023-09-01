package ar.com.jsuper.domain.utils;

import java.util.Set;

import ar.com.jsuper.domain.Familias;
import ar.com.jsuper.domain.ProductoPadre;

public class FilterProductoPadre {

    private ProductoPadre productoPadre;
    private Set<Familias> familias;

    public ProductoPadre getProductoPadre() {
        return productoPadre;
    }

    public void setProductoPadre(ProductoPadre productoPadre) {
        this.productoPadre = productoPadre;
    }

    public Set<Familias> getFamilias() {
        return familias;
    }

    public void setFamilias(Set<Familias> familias) {
        this.familias = familias;
    }

}
