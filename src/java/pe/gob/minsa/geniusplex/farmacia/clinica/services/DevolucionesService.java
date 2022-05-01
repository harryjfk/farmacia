/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.clinica.services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.Devolucion;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.RecetaProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.ListaModulo;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.helpers.ProductoHelper;

/**
 *
 * @author stark
 */
@Service("devolucionesService")
public class DevolucionesService extends GpServiceManager<Devolucion> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;
    private EntityManager em;

    

    public DevolucionesService() {
        super(Devolucion.class);
    }

    @PostConstruct
    public void init() {
        em = entityManagerFactory.createEntityManager();
        setEntityManager(em);
    }
    
    public List<Devolucion> listarModulo(Object idModulo){
        ListaModulo<Devolucion> listaModulo = new ListaModulo<Devolucion>(Devolucion.class);
        listaModulo.setEntityManager(entityManagerFactory.createEntityManager());
        return listaModulo.listarPorModulo(idModulo);
        
    }
 

    public List<Devolucion> listarDevolucionesPaciente(Object idPaciente,long idModulo) {
        TypedQuery<Devolucion> q = em.createQuery("SELECT d FROM Devolucion d WHERE d.paciente = :idPaciente AND d.IdModulo=:idModulo",Devolucion.class);
        q.setParameter("idModulo", idModulo);
        List<Devolucion> lista = q.setParameter("idPaciente", idPaciente).getResultList();  
        return lista;
    }

    

   

}
