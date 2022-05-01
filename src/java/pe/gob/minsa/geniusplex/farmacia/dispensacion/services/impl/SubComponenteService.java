/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Componente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.KitAtencion;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.SubComponente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.SubCompKitPg;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.SubCompProdPg;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;

/**
 * Servicios para gestionar SubComponentes
 *
 */
public class SubComponenteService extends GpServiceManager<SubComponente> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    private SubCompProdPg paginadorProductos;
    private SubCompKitPg paginadorKits;

    public SubComponenteService() {
        super(SubComponente.class);
    }

    private void crearPaginadores() {
        paginadorProductos = new SubCompProdPg();
        String[] columns = new String[]{"idProducto", "descripcion", "idFormaFarmaceutica.nombreFormaFarmaceutica", "idUnidadMedida.nombreUnidadMedida", "concentracion"};
        paginadorProductos.setColumns(columns);
        paginadorProductos.setEntityClass(GpProducto.class);
        
        paginadorKits = new SubCompKitPg();
        paginadorKits.setEntityClass(KitAtencion.class);
        String[] columns2 = new String[]{"id", "descripcion"};
        paginadorKits.setColumns(columns2);
    }

    @PostConstruct
    public void init() {
        em = entityManagerFactory.createEntityManager();
        setEntityManager(em);
        crearPaginadores();
        paginadorProductos.setEntityManager(em);
        paginadorKits.setEntityManager(em);
    }

    public List<SubComponente> listarActivos() {
        TypedQuery<SubComponente> query = em.createQuery("SELECT sc FROM SubComponente sc WHERE sc.activo = :activo", entityClass);
        return query.setParameter("activo", 1).getResultList();
    }

    public List<GpProducto> getProductos(Long idSubComponente) throws BusinessException {
        List<GpProducto> productos = new ArrayList<GpProducto>();
        TypedQuery<SubComponente> query = em.createQuery("SELECT sc FROM SubComponente sc WHERE sc.id = :id", entityClass);
        SubComponente subComponente;
        try {
            subComponente = query.setParameter("id", idSubComponente).getSingleResult();
        } catch (NoResultException e) {
            java.util.logging.Logger.getLogger(SubComponenteService.class.getName()).log(Level.INFO, null, e);
            throw new BusinessException(Arrays.asList("No existe el Subcomponente seleccionado"));
        }
        if (!subComponente.getProductos().isEmpty()) {
            productos = subComponente.getProductos();
        }
        return productos;
    }

    public List<KitAtencion> getKits(Long idSubComponente) throws BusinessException {
        List<KitAtencion> kits = new ArrayList<KitAtencion>();
        TypedQuery<SubComponente> query = em.createQuery("SELECT sc FROM SubComponente sc WHERE sc.id = :id", entityClass);
        SubComponente subComponente;
        try {
            subComponente = query.setParameter("id", idSubComponente).getSingleResult();
        } catch (NoResultException e) {
            java.util.logging.Logger.getLogger(SubComponenteService.class.getName()).log(Level.INFO, null, e);
            throw new BusinessException(Arrays.asList("No existe el Subcomponente seleccionado"));
        }
        if (!subComponente.getProductos().isEmpty()) {
            kits = subComponente.getKits();
        }
        return kits;
    }

    public List<GpProducto> listarRango(int[] range, String search, Object[] sort, long idSubComponente) {
        paginadorProductos.setIdSubComponente(idSubComponente);
        return paginadorProductos.listarRango(0, range, search, sort);
    }

    public int contarPaginado(String search, long idSubComponente) {
        paginadorProductos.setIdSubComponente(idSubComponente);
        return paginadorProductos.contarPaginado(0, search);
    }
    
    public List<KitAtencion> listarRangoKitAtencions(int [] range, String search, Object[] sort, long idSubComponente, long idModulo) {
        paginadorKits.setIdSubComponente(idSubComponente);
        return paginadorKits.listarRango(idModulo, range, search, sort);
    }
    
    public int contarRangoKitsAtencion(String search, long idSubComponente, long idModulo) {
        paginadorKits.setIdSubComponente(idSubComponente);
        return paginadorKits.contarPaginado(idModulo, search);
    }
    
    public boolean agregarProducto(int idProducto, long idSubComponente) throws BusinessException {
        GpProducto producto = em.getReference(GpProducto.class, idProducto);
        if (producto != null) {
            SubComponente subComponente = em.getReference(SubComponente.class, idSubComponente);
            if (subComponente != null) {
                if (!subComponente.getProductos().contains(producto)) {
                    subComponente.getProductos().add(producto);
                    try {
                        em.getTransaction().begin();
                        em.merge(subComponente);
                        em.getTransaction().commit();
                        return true;
                    } catch (Exception e) {
                        java.util.logging.Logger.getLogger(SubComponenteService.class.getName()).log(Level.INFO, null, e);
                        throw new BusinessException(Arrays.asList("Ha ocurrido un error del sistema al agregar este producto"));
                    }
                } else {
                    throw new BusinessException(Arrays.asList("Este producto ya esta asociado al subcomponente seleccionado"));
                }
            } else {
                throw new BusinessException(Arrays.asList("No se ha encontrado el subcomponente seleccionado"));
            }
        } else {
            throw new BusinessException(Arrays.asList("No se ha encontrado el producto seleccionado"));
        }
    }

    public boolean eliminarProducto(int idProducto, long idSubComponente) throws BusinessException {
        GpProducto producto = em.getReference(GpProducto.class, idProducto);
        if (producto != null) {
            SubComponente subComponente = em.getReference(SubComponente.class, idSubComponente);
            if (subComponente != null) {
                if (subComponente.getProductos().contains(producto)) {
                    subComponente.getProductos().remove(producto);
                    try {
                        em.getTransaction().begin();
                        em.merge(subComponente);
                        em.getTransaction().commit();
                        return true;
                    } catch (Exception e) {
                        java.util.logging.Logger.getLogger(SubComponenteService.class.getName()).log(Level.INFO, null, e);
                        throw new BusinessException(Arrays.asList("Ha ocurrido un error del sistema al agregar este producto"));
                    }
                } else {
                    throw new BusinessException(Arrays.asList("Este producto no esta asociado al subcomponente seleccionado"));
                }
            } else {
                throw new BusinessException(Arrays.asList("No se ha encontrado el subcomponente seleccionado"));
            }
        } else {
            throw new BusinessException(Arrays.asList("No se ha encontrado el producto seleccionado"));
        }
    }

    public boolean agregarKit(long id, long idSubComponente) throws BusinessException {
        KitAtencion kit = em.getReference(KitAtencion.class, id);
        if (kit != null) {
            SubComponente subComponente = em.getReference(SubComponente.class, idSubComponente);
            if (subComponente != null) {
                if (!subComponente.getKits().contains(kit)) {
                    subComponente.getKits().add(kit);
                    try {
                        em.getTransaction().begin();
                        em.merge(subComponente);
                        em.getTransaction().commit();
                        return true;
                    } catch (Exception e) {
                        java.util.logging.Logger.getLogger(SubComponenteService.class.getName()).log(Level.INFO, null, e);
                        throw new BusinessException(Arrays.asList("Ha ocurrido un error del sistema al agregar este Kit"));
                    }
                } else {
                    throw new BusinessException(Arrays.asList("Este Kit ya esta asociado al subcomponente seleccionado"));
                }
            } else {
                throw new BusinessException(Arrays.asList("No se ha encontrado el subcomponente seleccionado"));
            }
        } else {
            throw new BusinessException(Arrays.asList("No se ha encontrado el Kit seleccionado"));
        }
    }
    
    public boolean eliminarKit(long id, long idSubComponente) throws BusinessException {
        KitAtencion kit = em.getReference(KitAtencion.class, id);
        if (kit != null) {
            SubComponente subComponente = em.getReference(SubComponente.class, idSubComponente);
            if (subComponente != null) {
                if (subComponente.getKits().contains(kit)) {
                    subComponente.getKits().remove(kit);
                    try {
                        em.getTransaction().begin();
                        em.merge(subComponente);
                        em.getTransaction().commit();
                        return true;
                    } catch (Exception e) {
                        java.util.logging.Logger.getLogger(SubComponenteService.class.getName()).log(Level.INFO, null, e);
                        throw new BusinessException(Arrays.asList("Ha ocurrido un error del sistema al agregar este Kit"));
                    }
                } else {
                    throw new BusinessException(Arrays.asList("Este Kit ya esta asociado al subcomponente seleccionado"));
                }
            } else {
                throw new BusinessException(Arrays.asList("No se ha encontrado el subcomponente seleccionado"));
            }
        } else {
            throw new BusinessException(Arrays.asList("No se ha encontrado el Kit seleccionado"));
        }
    }

    public List<SubComponente> listar(Long componenteId) {
        Componente componente = em.getReference(Componente.class, componenteId);
        if(componente != null) {
            em.refresh(componente);
            return componente.getSubComponentes();
        } else {
            return this.listar();
        }
    }

}
