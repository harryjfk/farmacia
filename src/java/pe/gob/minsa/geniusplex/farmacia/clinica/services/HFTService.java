/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.clinica.services;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.HFT;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Paciente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.ListaModulo;

/**
 *
 * @author stark
 */
@Service("hftService")
public class HFTService  extends GpServiceManager<HFT>{

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;

    public HFTService() {
        super(HFT.class);
    }
    
    @PostConstruct
    public void init(){
        em = emf.createEntityManager();
        setEntityManager(em);
    }

    @Override
    public boolean insertar(HFT t) {
        if (t.getPaciente() != null) {
            Paciente paciente = em.find(Paciente.class, t.getPaciente().getPaciente());
            t.setPaciente(paciente);
        }
        return super.insertar(t); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<HFT> listarModulo(Object idModulo){
        ListaModulo<HFT> listaModulo = new ListaModulo<HFT>(HFT.class);
        listaModulo.setEntityManager(emf.createEntityManager());
        return listaModulo.listarPorModulo(idModulo);
        
    }
    
    public List<HFT> listarPorModuloPaciente(Object idModulo,Object paciente) {
        TypedQuery<HFT> q = em.createQuery("SELECT h FROM HFT h WHERE h.IdModulo = :idModulo AND h.paciente = :paciente",HFT.class);
        q.setParameter("paciente", paciente);
        List<HFT> lista = q.setParameter("idModulo", idModulo).getResultList();
        return lista;
    }
}
