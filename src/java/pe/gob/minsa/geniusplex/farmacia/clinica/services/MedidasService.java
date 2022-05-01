/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.clinica.services;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.MedidasEducativas;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Paciente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.ListaModulo;

/**
 *
 * @author stark
 */
@Service("medidaService")
public class MedidasService  extends GpServiceManager<MedidasEducativas>{

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;

    public MedidasService() {
        super(MedidasEducativas.class);
    }
    
    @PostConstruct
    public void init(){
        em = emf.createEntityManager();
        setEntityManager(em);
    }
    
    public List<MedidasEducativas> listarModulo(Object idModulo){
        ListaModulo<MedidasEducativas> listaModulo = new ListaModulo<MedidasEducativas>(MedidasEducativas.class);
        listaModulo.setEntityManager(emf.createEntityManager());
        return listaModulo.listarPorModulo(idModulo);
        
    }
    
    public List<MedidasEducativas> listarPorModuloConParams(Object idModulo,String servicio) {
        TypedQuery<MedidasEducativas> q = em.createQuery("SELECT m FROM MedidasEducativas m WHERE m.IdModulo = :idModulo AND m.servicio = :servicio ",MedidasEducativas.class);
        q.setParameter("servicio", servicio);
        List<MedidasEducativas> lista = q.setParameter("idModulo", idModulo).getResultList();
        return lista;
    }
    
    public List<MedidasEducativas> consultar (String paciente,Date startDate,Date endDate,Object idModulo){
         TypedQuery<MedidasEducativas> q = em.createQuery("SELECT m FROM MedidasEducativas m WHERE m.IdModulo = :idModulo AND (m.paciente.paciente LIKE :paciente OR :paciente LIKE 0 ) AND ((m.fecha BETWEEN :start AND :end) OR(:start is null AND :end is null) ) ",MedidasEducativas.class);
        q.setParameter("paciente", paciente);
        q.setParameter("start", startDate);
        q.setParameter("end", endDate);
        List<MedidasEducativas> lista = q.setParameter("idModulo", idModulo).getResultList();
        return lista;
    }
}
