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
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.UploadFile;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.ListaModulo;

/**
 *
 * @author stark
 */
@Service("uploadFileService")
public class UploadFileService  extends GpServiceManager<UploadFile>{

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;

    public UploadFileService() {
        super(UploadFile.class);
    }
    
    @PostConstruct
    public void init(){
        em = emf.createEntityManager();
        setEntityManager(em);
    }
    
    public List<UploadFile> listarModulo(Object idModulo){
        ListaModulo<UploadFile> listaModulo = new ListaModulo<UploadFile>(UploadFile.class);
        listaModulo.setEntityManager(emf.createEntityManager());
        return listaModulo.listarPorModulo(idModulo);
        
    }
    
    public List<UploadFile> listarPorModuloSubModulo(Object idModulo,String submodulo) {
        TypedQuery<UploadFile> q = em.createQuery("SELECT f FROM UploadFile f WHERE f.IdModulo = :idModulo AND f.submodulo =:submodulo ",UploadFile.class);
        q.setParameter("submodulo", submodulo);
        List<UploadFile> lista = q.setParameter("idModulo", idModulo).getResultList();
        return lista;
    }
    public List<UploadFile> existe(Object idModulo,String submodulo,String name) {
        TypedQuery<UploadFile> q = em.createQuery("SELECT f FROM UploadFile f WHERE f.IdModulo = :idModulo AND f.submodulo =:submodulo AND f.nombre=:name ",UploadFile.class);
        q.setParameter("submodulo", submodulo);
        q.setParameter("name",name);
        List<UploadFile> lista = q.setParameter("idModulo", idModulo).getResultList();
        return lista;
    }
}
