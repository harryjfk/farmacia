/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.IntervProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Intervencion;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Paciente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Prescriptor;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;
import pe.gob.minsa.geniusplex.farmacia.helpers.JpaCriteriaHelper;

/**
 * Servicios para gestionar Intervenciones
 *
 */
@Service("intervService")
public class IntervencionService extends GpServiceManager<Intervencion> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    public IntervencionService() {
        super(Intervencion.class);
    }

    @PostConstruct
    public void init() {
        this.em = entityManagerFactory.createEntityManager();
        setEntityManager(em);
    }

    public boolean guardarCambios(Intervencion intervencion) {

        List<IntervProducto> intervProductos = intervencion.getIntervProductos();
        if (intervencion.getId() > 0) {
            String especialidad = intervencion.getEspecialidad();
            Prescriptor medico = intervencion.getMedico();
            Paciente paciente = em.getReference(Paciente.class, intervencion.getPaciente().getPaciente());
            Date fechaInt = intervencion.getFechaIntervencion();
            Integer programada = intervencion.getProgramada();
            Integer atendida = intervencion.getAtendida();
            int activo = intervencion.getActivo();

            intervencion = obtenerPorId(intervencion.getId());
            borrarProductos(intervencion);
            intervencion.setEspecialidad(especialidad);
            intervencion.setMedico(medico);
            intervencion.setPaciente(paciente);
            intervencion.setFechaIntervencion(fechaInt);
            intervencion.setProgramada(programada);
            intervencion.setAtendida(atendida);
            intervencion.setActivo(activo);
        }
        if (!intervProductos.isEmpty()) {
            intervencion.setIntervProductos(intervProductos);
            for (int i = 0; i < intervencion.getIntervProductos().size(); i++) {
                IntervProducto prod = intervencion.getIntervProductos().get(i);
                GpProducto producto = em.find(GpProducto.class, prod.getProducto().getIdProducto());
                prod.setProducto(producto);
                prod.setIntervencion(intervencion);
                intervencion.getIntervProductos().set(i, prod);
            }
        }
        return (intervencion.getId() > 0) ? actualizar(intervencion) : insertar(intervencion);
    }

    public Intervencion obtenerPorEspecialidad(Intervencion intervencion) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Intervencion> cq = cb.createQuery(Intervencion.class);
        Root<Intervencion> interv = cq.from(Intervencion.class);
        cq.where(
                cb.equal(interv.get("paciente"), intervencion.getPaciente()),
                cb.equal(interv.get("medico"), intervencion.getMedico()),
                cb.equal(interv.get("especialidad"), intervencion.getEspecialidad()),
                cb.equal(interv.get("fechaIntervencion"), intervencion.getFechaIntervencion()));
        TypedQuery<Intervencion> q = em.createQuery(cq);

        try {
            Intervencion tmpInt = q.getSingleResult();
            intervencion = tmpInt;
        } catch (NoResultException ex) {
            return null;
        } catch (NonUniqueResultException ex) {//I hope this never happens but just in case
            intervencion = q.getResultList().get(0);
        }

        return intervencion;
    }

    public Intervencion obtenerPorProgramada(Intervencion intervencion) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Intervencion> cq = cb.createQuery(Intervencion.class);
        Root<Intervencion> interv = cq.from(Intervencion.class);
        cq.where(
                cb.equal(interv.get("paciente"), intervencion.getPaciente()),
                cb.equal(interv.get("medico"), intervencion.getMedico()),
                cb.equal(interv.get("programada"), intervencion.getProgramada()),
                cb.equal(interv.get("especialidad"), intervencion.getEspecialidad()),
                cb.equal(interv.get("fechaIntervencion"), intervencion.getFechaIntervencion()));
        TypedQuery<Intervencion> q = em.createQuery(cq);

        try {
            Intervencion tmpInt = q.getSingleResult();
            intervencion = tmpInt;
        } catch (NoResultException ex) {
            return null;
        } catch (NonUniqueResultException ex) {
            intervencion = q.getResultList().get(0);
        }

        return intervencion;
    }

    public Intervencion obtenerPorAtendida(Intervencion intervencion) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Intervencion> cq = cb.createQuery(Intervencion.class);
        Root<Intervencion> interv = cq.from(Intervencion.class);
        cq.where(
                cb.equal(interv.get("paciente"), intervencion.getPaciente()),
                cb.equal(interv.get("medico"), intervencion.getMedico()),
                cb.equal(interv.get("atendida"), intervencion.getAtendida()),
                cb.equal(interv.get("especialidad"), intervencion.getEspecialidad()),
                cb.equal(interv.get("fechaIntervencion"), intervencion.getFechaIntervencion()));
        TypedQuery<Intervencion> q = em.createQuery(cq);

        try {
            Intervencion tmpInt = q.getSingleResult();
            intervencion = tmpInt;
        } catch (NoResultException ex) {
            return null;
        } catch (NonUniqueResultException ex) {
            intervencion = q.getResultList().get(0);
        }

        return intervencion;
    }

    private void borrarProductos(Intervencion interverncion) {
        for (int i = 0; i < interverncion.getIntervProductos().size(); i++) {
            IntervProducto prod = interverncion.getIntervProductos().get(i);
            try {
                em.getTransaction().begin();
                em.remove(em.merge(prod));
                em.getTransaction().commit();
            } catch (Exception e) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                em.getTransaction().rollback();
            }
        }
    }

    public List<Intervencion> ConsultaIntervencion(Long idMedico, String idPaciente, Integer atendida, Integer programada, String especialidad, Date startDate) {
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Intervencion> cq = cb.createQuery(entityClass);
        Root<Intervencion> fromInterv = cq.from(entityClass);
        Predicate[] predicates = new Predicate[0];
        if(idPaciente != null && idPaciente.length() > 0) {
            predicates = JpaCriteriaHelper.agregarPredicado(predicates, cb.equal(fromInterv.get("paciente").get("paciente"), idPaciente));
        }
        if(idMedico != null) {
            predicates = JpaCriteriaHelper.agregarPredicado(predicates, cb.equal(fromInterv.get("medico").get("idMedico"), idMedico));
        }
        if(atendida != null) {
            predicates = JpaCriteriaHelper.agregarPredicado(predicates, cb.equal(fromInterv.get("atendida"), atendida));
        }
        if(programada != null) {
            predicates = JpaCriteriaHelper.agregarPredicado(predicates, cb.equal(fromInterv.get("programada"), programada));
        }
        if(especialidad != null && especialidad.length() > 0) {
            predicates = JpaCriteriaHelper.agregarPredicado(predicates, cb.like(fromInterv.<String>get("especialidad"), "%" + especialidad + "%"));
        }
        if(startDate != null) {
            predicates = JpaCriteriaHelper.agregarPredicado(predicates, cb.equal(fromInterv.get("fechaIntervencion"), startDate));
        }
        cq.where(predicates);
        TypedQuery<Intervencion> q = em.createQuery(cq);
        return q.getResultList();
    }

}
