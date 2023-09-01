package ar.com.jsuper.dao;

import ar.com.jsuper.domain.AfipPtoVenta;
import ar.com.jsuper.domain.AfipTpoCbte;
import ar.com.jsuper.domain.AfipTpoConcepto;
import ar.com.jsuper.domain.AfipTpoDoc;
import ar.com.jsuper.domain.AfipTpoIva;
import ar.com.jsuper.domain.AfipTpoTributo;
import ar.com.jsuper.domain.App;
import java.util.List;

public interface AfipDAO extends GenericDAO< App, Integer> {

    List<AfipTpoCbte> getTpoCbtes();

    public AfipTpoCbte saveOrUpdateTpoCbtes(AfipTpoCbte afipTpoCbte);

    public int deleteAllTpoCbtes();

    public int deleteAllTpoIva();

    public int deleteAllTpoConcepto();

    public int deleteAllPtoVenta();

    public int deleteAllTpoDoc();

    public int deleteAllTpoTributo();

    List<AfipTpoDoc> getTpoDocs();

    public AfipTpoDoc saveOrUpdateTpoDoc(AfipTpoDoc afipTpoDoc);

    List<AfipTpoConcepto> getTpoConceptos();

    public AfipTpoConcepto saveOrUpdateTpoConcepto(AfipTpoConcepto afipTpoConcepto);

    List<AfipTpoIva> getTpoIvas();

    public AfipTpoIva saveOrUpdateTpoIva(AfipTpoIva afipTpoIva);

    List<AfipPtoVenta> getPtoVentas();

    public AfipPtoVenta saveOrUpdatePtoVenta(AfipPtoVenta afipPtoVenta);

    public AfipTpoTributo saveOrUpdateTpoTributo(AfipTpoTributo afipTpoTributo);

    public List<AfipTpoTributo> getTpoTributos();
}
