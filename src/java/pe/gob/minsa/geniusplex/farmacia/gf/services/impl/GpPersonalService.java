
package pe.gob.minsa.geniusplex.farmacia.gf.services.impl;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.PersonalPaginador;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.RemoteGpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpPersonal;

@Service("gpPersonalService")
public class GpPersonalService extends RemoteGpServiceManager<GpPersonal> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    
    public GpPersonalService() {
        super(GpPersonal.class);
    }
    
    @PostConstruct
    public void init() {
        em = entityManagerFactory.createEntityManager();
        setEntityManager(em);
    }
    
    public List<GpPersonal> listarRango(int[] range, String sSearch, Object[] sort) {
        final String[] cls = new String[]{"personal", "nombres", "apellidoPaterno", "apellidoMaterno"};
        PersonalPaginador paginador = new PersonalPaginador(cls);
        paginador.setEntityManager(em);
        return paginador.listarRango(cls, range, sSearch, sort);
    }

    public int contarPaginado(String sSearch) {
        final String[] cls = new String[]{"personal", "nombres", "apellidoPaterno", "apellidoMaterno"};
        PersonalPaginador paginador = new PersonalPaginador(cls);
        paginador.setEntityManager(em);
        return paginador.contarPaginado(0, sSearch);
    }
}
