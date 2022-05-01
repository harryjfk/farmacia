/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Farmacia;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;


/**
 *
 * @author stark
 */
public class FarmaciaService extends GpServiceManager<Farmacia> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;
    
    public FarmaciaService() {
        super(Farmacia.class);
    }
    
    @PostConstruct
    public void init(){
        EntityManager em = emf.createEntityManager();
        setEntityManager(em);
    }
    
    @Override
    public List<Farmacia> listar() {
        ArrayList<Farmacia> resultados = new ArrayList<Farmacia>();
        Farmacia a = new Farmacia();
        Farmacia b = new Farmacia();
        a.setFarmacia(1);
        a.setNombre("Farmacia de analgesicos");
        resultados.add(a);
        b.setFarmacia(2);
        b.setNombre("Farmacia de emergencia");
        resultados.add(b);
        return resultados;
    }
}
