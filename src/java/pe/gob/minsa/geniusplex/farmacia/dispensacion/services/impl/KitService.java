/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Componente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.KitAtencion;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.KitAtencionProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.KitAtencionProductoPk;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Proceso;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.SubComponente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;
import pe.gob.minsa.geniusplex.farmacia.helpers.JpaCriteriaHelper;
import pe.gob.minsa.geniusplex.web.objects.FilterData;

/**
 *
 * @author armando
 */
public class KitService extends GpServiceManager<KitAtencion> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;

    @Autowired
    private SubComponenteService subComponenteService;

    public KitService() {
        super(KitAtencion.class);
    }

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
        setEntityManager(em);
    }

    public KitAtencion encontrarPorCompSubPer(Componente componente, SubComponente subcomponente, Proceso proceso, Date periodo, long idModulo)
            throws BusinessException {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<KitAtencion> cq = cb.createQuery(KitAtencion.class);
        Root<KitAtencion> ka = cq.from(KitAtencion.class);
        cq.where(
                cb.and(
                        cb.equal(ka.get("subComponente"), subcomponente),
                        cb.equal(ka.get("IdModulo"), idModulo)
                ));
        TypedQuery<KitAtencion> q = em.createQuery(cq);
        try {
            KitAtencion result = q.getSingleResult();
            return result;
        } catch (NoResultException nRex) {
            return null;
        } catch (NonUniqueResultException nURex) {
            ArrayList<String> errors = new ArrayList<String>();
            errors.add("Hay m&aacute;s de un Kit de Atenci&iacute;n con el Per&iacute;odo, Componente y Subcomponente especificados.");
            throw new BusinessException(errors);
        }
    }

    /**
     * Agrega un producto, con una cantidad determinada a un kit de atencion, si
     * el producto ya existe, solo se actualiza la cantidad.
     *
     * @param ka Kit de Atencion
     * @param prod Producto
     * @param cant Cantidad
     * @return Boolean si se inserto/actualizo correctamente
     */
    public boolean crearProducto(KitAtencion ka, GpProducto prod, double cant) {

        KitAtencionProducto kaProducto;
        boolean tieneProductos = false;
        boolean esNuevo;
        if (ka.getProductos() != null && !ka.getProductos().isEmpty()) {
            tieneProductos = true;
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<KitAtencionProducto> cq = cb.createQuery(KitAtencionProducto.class);
            Root<KitAtencionProducto> kaProd = cq.from(KitAtencionProducto.class);

            cq.where(
                    cb.and(
                            cb.equal(kaProd.get("kitAtencion"), ka),
                            cb.equal(kaProd.get("producto"), prod)
                    ));
            TypedQuery<KitAtencionProducto> q = em.createQuery(cq);

            try {
                kaProducto = q.getSingleResult();
                esNuevo = false;
            } catch (NoResultException ex) {
                kaProducto = new KitAtencionProducto();
                kaProducto.setProducto(prod);
                kaProducto.setKitAtencion(ka);
                esNuevo = true;
            }
        } else {
            kaProducto = new KitAtencionProducto();
            kaProducto.setProducto(prod);
            kaProducto.setKitAtencion(ka);
            esNuevo = true;
        }

        kaProducto.setCantidad(cant);
        //asociar el producto al kit
        boolean ok = true;
        try {
            em.getTransaction().begin();
            if (!esNuevo) {
                em.merge(kaProducto);
            } else {
                em.persist(kaProducto);
            }
            em.getTransaction().commit();
            em.refresh(kaProducto);
        } catch (Exception ex) {
            ok = false;
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            em.getTransaction().rollback();
        }

        //actualizar la lista de productos del kit
        if (tieneProductos) {
            List<KitAtencionProducto> productos = ka.getProductos();
            boolean existe = false;
            for (int i = 0; i < productos.size(); i++) {
                KitAtencionProducto producto = productos.get(i);
                if (producto.equals(kaProducto)) {
                    existe = true;
                    productos.set(i, kaProducto);
                    break;
                }
            }
            if (!existe) {
                ka.getProductos().add(kaProducto);
            }
        } else {
            ka.setProductos(new ArrayList<KitAtencionProducto>());
            ka.getProductos().add(kaProducto);
        }
        //actualizar kit en base de datos
        return ok && actualizar(ka);
    }

    public KitAtencionProducto obtenerProductoPorId(String id) {
        KitAtencionProductoPk pk = new KitAtencionProductoPk();
        String[] llaves = id.split("-");
        pk.setIdKitAtencion(Long.parseLong(llaves[0]));
        pk.setIdProducto(Integer.parseInt(llaves[1]));
        KitAtencionProducto producto = em.find(KitAtencionProducto.class, pk);
        return producto;
    }

    public boolean actualizarProducto(KitAtencionProducto producto) {
        boolean result = true;

        try {
            em.getTransaction().begin();
            em.merge(producto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            result = false;
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, String.format("Error actualizando la cantidad para el producto: %d del kit %d, ver mensaje de error anterior", producto.getProducto().getIdProducto(), producto.getKitAtencion().getId()));
        }

        return result;
    }

    public boolean eliminarProducto(String id) {
        boolean result = true;
        try {
            em.getTransaction().begin();
            KitAtencionProducto kitProducto = obtenerProductoPorId(id);
            em.remove(kitProducto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            em.getTransaction().rollback();
        }
        return result;
    }

    public KitAtencion cambiarNombre(String nombreNuevo, Componente componente, SubComponente subcomponente, Proceso proceso, Date periodo, long idModulo) {
        try {
            KitAtencion kit = encontrarPorCompSubPer(componente, subcomponente, proceso, periodo, idModulo);
            if (nombreNuevo != null && nombreNuevo.length() > 0) {
                if (kit != null) {
                    kit.setDescripcion(nombreNuevo);
                    actualizar(kit);
                } else {
                    kit = crearKit(componente, subcomponente, proceso, periodo, idModulo);
                    kit.setDescripcion(nombreNuevo);
                    insertar(kit);
                }
            }
            return kit;
        } catch (BusinessException ex) {
            Logger.getLogger(KitService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public KitAtencion crearKit(Componente componente, SubComponente subcomponente, Proceso proceso, Date periodo, long idModulo) {
        KitAtencion ka = new KitAtencion();
        ka.setProceso(proceso);
        ka.setComponente(componente);
        ka.setSubComponente(subcomponente);
        ka.setIdModulo(idModulo);
        ka.setPeriodo(periodo);
        return ka;
    }

    public List<KitAtencion> encontrarPorProcesoPeriodo(long proceso, long idModulo, Date per) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<KitAtencion> cq = cb.createQuery(KitAtencion.class);
        Root<KitAtencion> ka = cq.from(KitAtencion.class);
        cq.where(
                cb.and(
                        cb.equal(ka.get("proceso").get("id"), proceso),
                        //                        cb.equal(ka.get("periodo"), per),
                        cb.equal(ka.get("IdModulo"), idModulo)
                ));
        TypedQuery<KitAtencion> q = em.createQuery(cq);
        return q.getResultList();
    }

    public List<KitAtencion> listarProceso(FilterData fData) {
        Long subComponenteId = null;
        if (fData.getParams().containsKey("subComponente:id")) {
            subComponenteId = Long.parseLong(fData.getParams().get("subComponente:id").toString());
        }
        long idModulo = Long.parseLong(fData.getParams().get("IdModulo").toString());
        Proceso proceso = null;
        if (fData.getParams().containsKey("proceso:id")) {
            long procesoId = Long.parseLong(fData.getParams().get("proceso:id").toString());
            proceso = em.getReference(Proceso.class, procesoId);
        }
        Componente componente = null;
        if (fData.getParams().containsKey("componente:id")) {
            long componenteId = Long.parseLong(fData.getParams().get("componente:id").toString());
            componente = em.getReference(Componente.class, componenteId);
        }
        SubComponente subComponente = null;
        if (subComponenteId != null) {
            subComponente = em.find(SubComponente.class, subComponenteId);
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<KitAtencion> cq = cb.createQuery(entityClass);
        Root<KitAtencion> fromKit = cq.from(entityClass);

        Predicate[] predicates = new Predicate[]{cb.equal(fromKit.get("IdModulo"), idModulo)};
        if(proceso != null) {
            predicates = JpaCriteriaHelper.agregarPredicado(predicates, cb.equal(fromKit.get("proceso"), proceso));
        }
        if(componente != null) {
            predicates = JpaCriteriaHelper.agregarPredicado(predicates, cb.equal(fromKit.get("componente"), componente));
        }
        if(subComponente != null) {
            predicates = JpaCriteriaHelper.agregarPredicado(predicates, cb.equal(fromKit.get("subComponente"), subComponente));
        }
        cq.where(predicates);
        TypedQuery<KitAtencion> query = em.createQuery(cq);
        return query.getResultList();
    }

    public List<KitAtencion> listarModuloRango(long idModulo, long idComp, long idSub, long idProc, Date periodo, String desc, int[] range, Object[] sort) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<KitAtencion> cq = cb.createQuery(entityClass);

        Root<KitAtencion> KIT = cq.from(entityClass);
        Predicate[] conds = new Predicate[]{
            cb.equal(KIT.<Long>get("IdModulo"), idModulo)
        };
        if (desc != null && desc.length() > 0) {
            conds = JpaCriteriaHelper.agregarPredicado(conds, cb.like(KIT.<String>get("descripcion"), "%" + desc + "%"));
        }
        if (idComp > 0) {
            conds = JpaCriteriaHelper.agregarPredicado(conds, cb.equal(KIT.get("componente").get("id"), idComp));
        }
        if (idSub > 0) {
            conds = JpaCriteriaHelper.agregarPredicado(conds, cb.equal(KIT.get("subComponente").get("id"), idSub));
        }
        if (idProc > 0) {
            conds = JpaCriteriaHelper.agregarPredicado(conds, cb.equal(KIT.get("proceso").get("id"), idProc));
        } if(periodo != null) {
            conds = JpaCriteriaHelper.agregarPredicado(conds, cb.equal(KIT.<Date>get("periodo"), periodo));
        }
        cq.where(conds);
        if (sort[0] != null) {
            String fld = "id";
            if (Integer.parseInt(sort[1].toString()) == 1) {
                fld = "descripcion";
            }
            Order order = cb.asc(KIT.get(fld));
            if (sort[2].toString().equalsIgnoreCase("desc")) {
                order = cb.desc(KIT.get(fld));
            }
            cq.orderBy(order);
        }
        TypedQuery<KitAtencion> query = em.createQuery(cq);
        return query
                .setFirstResult(range[0])
                .setMaxResults(range[1]).getResultList();
    }

    public List<KitAtencion> listarModulo(long idModulo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<KitAtencion> cq = cb.createQuery(entityClass);

        Root<KitAtencion> KIT = cq.from(entityClass);
        Predicate[] conds = new Predicate[]{cb.equal(KIT.<Long>get("IdModulo"), idModulo)};

        cq.where(conds);
        TypedQuery<KitAtencion> query = em.createQuery(cq);
        return query.getResultList();
    }

    public List<KitAtencionProducto> listarProductosModuloRango(long idKit, long idModulo, String search, int[] range, Object[] sort) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<KitAtencionProducto> cq = cb.createQuery(KitAtencionProducto.class);

        Root<KitAtencionProducto> KIT_PRO = cq.from(KitAtencionProducto.class);
        Path<KitAtencion> KIT = KIT_PRO.<KitAtencion>get("kitAtencion");
        Path<GpProducto> PRO = KIT_PRO.<GpProducto>get("producto");
        Predicate[] conds = new Predicate[]{
            cb.equal(KIT.<Long>get("IdModulo"), idModulo),
            cb.equal(KIT.<Long>get("id"), idKit)
        };
        if (search != null && search.length() > 0) {
            conds = JpaCriteriaHelper.agregarPredicado(conds, cb.like(KIT_PRO.<GpProducto>get("producto").<String>get("descripcion"), "%" + search + "%"));
        }
        cq.where(conds);
        if (sort[0] != null) {
            String[] clns = new String[]{"idProducto", "descripcion", "idFormaFarmaceutica", "idUnidadMedida", "concentracion"};
            int sIndex = Integer.parseInt(sort[1].toString());
            String fld = clns[sIndex];
            Order order;
            if (!fld.equalsIgnoreCase("idFormaFarmaceutica") && !fld.equalsIgnoreCase("idUnidadMedida")) {
                order = cb.asc(PRO.get(fld));
            } else if (fld.equalsIgnoreCase("idUnidadMedida")) {
                order = cb.asc(PRO.get(fld).get("nombreUnidadMedida"));
            } else {
                order = cb.asc(PRO.get(fld).get("nombreFormaFarmaceutica"));
            }
            if (sort[2].toString().equalsIgnoreCase("desc")) {
                order = cb.desc(PRO.get(fld));
            }
            cq.orderBy(order);
        }
        TypedQuery<KitAtencionProducto> query = em.createQuery(cq);
        return query
                .setFirstResult(range[0])
                .setMaxResults(range[1]).getResultList();
    }

    public boolean agregarProducto(long kitid, int idProducto, int cantidad) {
        KitAtencion kit = this.obtenerPorId(kitid);
        GpProducto producto = em.getReference(GpProducto.class, idProducto);
        return this.crearProducto(kit, producto, cantidad);
    }

    public boolean insertar(KitAtencion kit, long compId, long subId, long procId) {
        Componente componente = em.getReference(Componente.class, compId);
        SubComponente subComponente = em.getReference(SubComponente.class, subId);
        Proceso proceso = em.getReference(Proceso.class, procId);

        kit.setComponente(componente);
        kit.setSubComponente(subComponente);
        kit.setProceso(proceso);
        boolean ok = super.insertar(kit);

        try {
            em.getTransaction().begin();
            subComponente.getKits().add(kit);
            em.merge(subComponente);
            em.getTransaction().commit();
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(KitService.class.getName()).log(Level.INFO, null, e);
            ok = false;
            eliminar(kit.getId());
            em.getTransaction().rollback();
        }

        return ok;
    }

    public boolean actualizar(KitAtencion kit, long compId, long subId, long procId) {
        Componente componente = em.getReference(Componente.class, compId);
        SubComponente subComponente = em.getReference(SubComponente.class, subId);
        Proceso proceso = em.getReference(Proceso.class, procId);

        kit.setComponente(componente);
        kit.setSubComponente(subComponente);
        kit.setProceso(proceso);
        boolean ok = super.actualizar(kit);

        if (!subComponente.getKits().contains(kit)) {
            try {

                em.getTransaction().begin();
                subComponente.getKits().add(kit);
                em.merge(subComponente);
                em.getTransaction().commit();
            } catch (Exception e) {
                java.util.logging.Logger.getLogger(KitService.class.getName()).log(Level.INFO, null, e);
                ok = false;
                eliminar(kit.getId());
                em.getTransaction().rollback();
            }
        }

        return ok;
    }
}
