/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Paciente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.PaginaModulo;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.RemoteGpServiceManager;

/**
 *
 * @author stark
 */
@Service("pacienteService")
public class PacienteService extends RemoteGpServiceManager<Paciente> {

    @Autowired
    @Qualifier("hospitalEntManFac")
    private EntityManagerFactory emf;

    public PacienteService() {
        super(Paciente.class);
    }

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
        setEntityManager(em);
    }

    public List<Paciente> listarRango(int[] range, String sSearch, Object[] sort) {
        final String[] cls = new String[]{"paciente", "historia", "nombre", "paterno", "materno", "sexo", "fechaNacimiento"};
        PaginaModulo<Paciente> paginaModulo = new PaginaModulo<Paciente>(Paciente.class, cls);
        paginaModulo.setEntityManager(em);
        return paginaModulo.listarRango(0, range, sSearch, sort);
    }

    public int contarPaginado(String sSearch) {
        final String[] cls = new String[]{"paciente", "historia", "nombre", "paterno", "materno", "sexo", "fechaNacimiento"};
        PaginaModulo<Paciente> paginaModulo = new PaginaModulo<Paciente>(Paciente.class, cls);
        paginaModulo.setEntityManager(em);
        return paginaModulo.contarPaginado(0, sSearch);
    }

}
