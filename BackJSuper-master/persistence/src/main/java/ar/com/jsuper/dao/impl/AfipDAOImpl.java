package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.AfipDAO;
import ar.com.jsuper.domain.AfipPtoVenta;
import ar.com.jsuper.domain.AfipTpoCbte;
import ar.com.jsuper.domain.AfipTpoConcepto;
import ar.com.jsuper.domain.AfipTpoDoc;
import ar.com.jsuper.domain.AfipTpoIva;
import ar.com.jsuper.domain.AfipTpoTributo;
import ar.com.jsuper.domain.App;
import ar.com.jsuper.security.TenantContext;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class AfipDAOImpl extends GenericDAOImpl<App, Integer> implements AfipDAO {

    @Override
    public List<AfipTpoCbte> getTpoCbtes() {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(AfipTpoCbte.class, "app");
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app", TenantContext.getCurrentTenant());
        c.add(c1);
        /*######################################################################*/
        return c.list();
    }

    @Override
    public AfipTpoCbte saveOrUpdateTpoCbtes(AfipTpoCbte afipTpoCbte) {
        Session session = sessionFactory.getCurrentSession();
        afipTpoCbte.setApp(TenantContext.getCurrentTenant());
        session.saveOrUpdate(afipTpoCbte);
        return afipTpoCbte;
    }

    @Override
    public int deleteAllTpoCbtes() {
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery("DELETE FROM afip_tpo_cbte where app_id=:idApp")
                .setInteger("idApp", TenantContext.getCurrentTenant())
                .executeUpdate();
    }

    @Override
    public int deleteAllTpoIva() {
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery("DELETE FROM afip_tpo_iva where app_id=:idApp")
                .setInteger("idApp", TenantContext.getCurrentTenant())
                .executeUpdate();
    }

    @Override
    public int deleteAllTpoConcepto() {
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery("DELETE FROM afip_tpo_concepto where app_id=:idApp")
                .setInteger("idApp", TenantContext.getCurrentTenant())
                .executeUpdate();
    }

    @Override
    public int deleteAllTpoDoc() {
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery("DELETE FROM afip_tpo_doc where app_id=:idApp")
                .setInteger("idApp", TenantContext.getCurrentTenant())
                .executeUpdate();
    }

    @Override
    public int deleteAllTpoTributo() {
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery("DELETE FROM afip_tpo_tributo where app_id=:idApp")
                .setInteger("idApp", TenantContext.getCurrentTenant())
                .executeUpdate();
    }

    @Override
    public int deleteAllPtoVenta() {
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery("DELETE FROM afip_pto_venta where app_id=:idApp")
                .setInteger("idApp", TenantContext.getCurrentTenant())
                .executeUpdate();
    }

    @Override
    public List<AfipTpoConcepto> getTpoConceptos() {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(AfipTpoConcepto.class, "app");
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app", TenantContext.getCurrentTenant());
        c.add(c1);
        /*######################################################################*/
        return c.list();
    }

    @Override
    public AfipTpoConcepto saveOrUpdateTpoConcepto(AfipTpoConcepto afipTpoConcepto) {
        Session session = sessionFactory.getCurrentSession();
        afipTpoConcepto.setApp(TenantContext.getCurrentTenant());
        session.saveOrUpdate(afipTpoConcepto);
        return afipTpoConcepto;
    }

    @Override
    public List<AfipTpoIva> getTpoIvas() {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(AfipTpoIva.class, "app");
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app", TenantContext.getCurrentTenant());
        c.add(c1);
        /*######################################################################*/
        return c.list();
    }

    @Override
    public List<AfipTpoDoc> getTpoDocs() {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(AfipTpoDoc.class, "app");
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app", TenantContext.getCurrentTenant());
        c.add(c1);
        /*######################################################################*/
        return c.list();
    }

    @Override
    public List<AfipTpoTributo> getTpoTributos() {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(AfipTpoTributo.class, "app");
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app", TenantContext.getCurrentTenant());
        c.add(c1);
        /*######################################################################*/
        return c.list();
    }

    @Override
    public AfipTpoIva saveOrUpdateTpoIva(AfipTpoIva afipTpoIva) {
        Session session = sessionFactory.getCurrentSession();
        afipTpoIva.setApp(TenantContext.getCurrentTenant());
        session.saveOrUpdate(afipTpoIva);
        return afipTpoIva;
    }

    @Override
    public AfipTpoDoc saveOrUpdateTpoDoc(AfipTpoDoc afipTpoDoc) {
        Session session = sessionFactory.getCurrentSession();
        afipTpoDoc.setApp(TenantContext.getCurrentTenant());
        session.saveOrUpdate(afipTpoDoc);
        return afipTpoDoc;
    }

    @Override
    public AfipTpoTributo saveOrUpdateTpoTributo(AfipTpoTributo afipTpoTributo) {
        Session session = sessionFactory.getCurrentSession();
        afipTpoTributo.setApp(TenantContext.getCurrentTenant());
        session.saveOrUpdate(afipTpoTributo);
        return afipTpoTributo;
    }

    @Override
    public List<AfipPtoVenta> getPtoVentas() {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(AfipPtoVenta.class, "app");
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app", TenantContext.getCurrentTenant());
        c.add(c1);
        /*######################################################################*/
        return c.list();
    }

    @Override
    public AfipPtoVenta saveOrUpdatePtoVenta(AfipPtoVenta afipPtoVenta) {
        Session session = sessionFactory.getCurrentSession();
        afipPtoVenta.setApp(TenantContext.getCurrentTenant());
        session.saveOrUpdate(afipPtoVenta);
        return afipPtoVenta;
    }

}
