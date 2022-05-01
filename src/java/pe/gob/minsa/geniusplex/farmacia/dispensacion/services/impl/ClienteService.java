/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Cliente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Paciente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.ListaModulo;

/**
 *
 * @author stark
 */
public class ClienteService extends GpServiceManager<Cliente> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;

    public ClienteService() {
        super(Cliente.class);
    }

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
        setEntityManager(em);
    }

    public List<Cliente> listarModulo(Object idModulo) {
        ListaModulo<Cliente> listaModulo = new ListaModulo<Cliente>(Cliente.class);
        listaModulo.setEntityManager(emf.createEntityManager());
        return listaModulo.listarPorModulo(idModulo);
    }

    public Cliente obtenerPorCodPaciente(String codPaciente, long idModulo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cliente> cq = cb.createQuery(entityClass);
        Root<Cliente> clienteFrom = cq.from(entityClass);
        if (idModulo > 0) {
            cq.where(cb.equal(clienteFrom.get("CodPersonal"), codPaciente),
                    cb.equal(clienteFrom.get("IdModulo"), idModulo));
        } else {
            cq.where(cb.equal(clienteFrom.get("CodPersonal"), codPaciente));
        }
        TypedQuery<Cliente> query = em.createQuery(cq);
        List<Cliente> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    public String obtenerHistoriaClinica(long idModulo, long idCliente) {
        Cliente cliente = obtenerPorId(idCliente);

        if (cliente != null && cliente.getCodPersonal() != null && cliente.getCodPersonal().length() > 0) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Paciente> cq = cb.createQuery(Paciente.class);
            cq.where(cb.equal(cq.from(Paciente.class).get("paciente"), cliente.getCodPersonal()));
            TypedQuery<Paciente> query = em.createQuery(cq);
            Paciente paciente = null;
            try {
                paciente = query.getSingleResult();
            } catch (Exception e) {
                return "";
            }
            return paciente.getHistoria();
        } else {
            return "";
        }
    }

}
