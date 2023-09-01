package ar.com.jsuper.services.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.com.jsuper.dao.RubrosDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Rubros;
import ar.com.jsuper.dto.CbteVenEncDTO;
import ar.com.jsuper.dto.RubrosDTO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.services.RubrosService;

@Service
public class RubrosServiceImpl implements RubrosService {

    @Autowired
    private OrikaBeanMapper modelMapper;
    @Autowired
    private RubrosDAO rubrosDAO;

    @Transactional(rollbackFor = BussinessException.class)
    public void insertRubro(Rubros rubro) throws BussinessException {
        try {
            rubrosDAO.insert(rubro);
        } catch (Exception e) {
            throw new BussinessException(e);
        }
    }

    @Transactional(rollbackFor = BussinessException.class)
    public Rubros getRubro(Integer id) throws BussinessException {
        try {
            return rubrosDAO.get(id);
        } catch (Exception e) {
            throw new BussinessException(e);
        }
    }

    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public Set<RubrosDTO> getall() throws BussinessException {
        try {
            Set<Rubros> rubros = rubrosDAO.getAll();
            return modelMapper.mapAsSet(rubros, RubrosDTO.class);
        } catch (Exception e) {
            throw new BussinessException(e);
        }
    }

    @Transactional(rollbackFor = BussinessException.class)
    public void insertr() throws BussinessException {
        try {
            Rubros r = new Rubros();
            r.setDescripcion("una descripcion");
            r.setNombre("Rubro chocolates");
            rubrosDAO.insert(r);
            // return rubrosDAO.getAll();
            // RuntimeException tt = new RuntimeException();

            throw new RuntimeException("ffffffffffffffffff11111188888888888");

            // } catch (RuntimeException e) {
            // System.out.println("entro por el runtime");
            // throw new BussinessException(e);
        } catch (Exception e) {
            throw new BussinessException(e);
        }
        // catch (RuntimeException e) {
        // throw new BussinessException(e);
        // } catch (Exception e) {
        // throw new BussinessException(e);
        // }
    }

}
