package ar.com.jsuper.dto;

import ar.com.jsuper.utils.Doc;


public class AfipClienteDTO {

    private String doc;
    private String nombre;
    private Doc tipoDoc;

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Doc getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(Doc tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

}
