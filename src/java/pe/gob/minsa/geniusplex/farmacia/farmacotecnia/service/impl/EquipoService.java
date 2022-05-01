
package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.service.impl;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.ListaModulo;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.Equipo;

@Service("equipoService")
public class EquipoService extends GpServiceManager<Equipo> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;

    public EquipoService() {
        super(Equipo.class);
    }

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
        setEntityManager(em);
    }

    public List<Equipo> listarModulo(Object idModulo) {
        ListaModulo<Equipo> listaModulo = new ListaModulo<Equipo>(Equipo.class);
        listaModulo.setEntityManager(emf.createEntityManager());
        return listaModulo.listarPorModulo(idModulo);
    }
}
