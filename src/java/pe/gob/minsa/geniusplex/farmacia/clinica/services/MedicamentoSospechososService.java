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
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.HFTMedicamento;
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.MedicamentoSospechoso;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;

/**
 *
 * @author stark
 */
@Service("medicamentoSospechosoService")
public class MedicamentoSospechososService  extends GpServiceManager<MedicamentoSospechoso>{

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;

    public MedicamentoSospechososService() {
        super(MedicamentoSospechoso.class);
    }
    
    @PostConstruct
    public void init(){
        em = emf.createEntityManager();
        setEntityManager(em);
    }
    
   
}
