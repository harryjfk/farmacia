/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.gob.minsa.geniusplex.farmacia.dispensacion.services;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author stark
 * @param <T>
 */
public class ListaModulo<T> implements ModuloListable<T>{

    private Class<T> entityClass;
    private EntityManager em;
    
    public ListaModulo(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public List<T> listarPorModulo(Object idModulo) {
        TypedQuery<T> q = em.createQuery("SELECT t FROM " + entityClass.getSimpleName() + " t WHERE t.IdModulo = :idModulo",entityClass);
        List<T> lista = q.setParameter("idModulo", idModulo).getResultList();
        return lista;
    }
    
}
