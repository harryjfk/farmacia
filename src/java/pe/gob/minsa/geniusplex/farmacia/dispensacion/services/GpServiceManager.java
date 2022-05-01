/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.BaseEntity;
import pe.gob.minsa.geniusplex.web.objects.FilterData;

/**
 *
 * @param <T>
 */
public abstract class GpServiceManager<T extends BaseEntity> implements GServiceManager<T> {

    protected Class<T> entityClass;
    protected EntityManager em;

    public GpServiceManager(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public List<T> listar() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> p = cb.createQuery(entityClass);
        p.from(entityClass);
        return em.createQuery(p).getResultList();
    }

    @Override
    public T obtenerPorId(Object id) {
        return em.find(entityClass, id);
    }

    @Override
    public boolean insertar(T t) {
        boolean result = true;
        try {
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            em.getTransaction().rollback();
            result = false;
        }
        return result;
    }

    @Override
    public boolean actualizar(T t) {
        boolean result = true;
        try {
            em.getTransaction().begin();
            em.merge(t);
            em.getTransaction().commit();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            em.getTransaction().rollback();
            result = false;
        }
        return result;
    }

    @Override
    public boolean eliminar(Object id) {
        T t = obtenerPorId(id);
        boolean result = true;
        if (t != null) {
            try {
                em.getTransaction().begin();
                em.remove(em.merge(t));
                em.getTransaction().commit();
            } catch (Exception ex) {
                result = false;
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                em.getTransaction().rollback();
            }
        } else {
            result = false;
        }

        return result;
    }
    
    @Override
    public boolean cambiarEstado(Object id) {
        T t = em.find(entityClass, id);
        boolean result = true;
        if (t != null) {
            int estado = t.getActivo() == 1 ? 0 : 1;
            t.setActivo(estado);
            try {
                em.getTransaction().begin();
                em.merge(t);
                em.getTransaction().commit();
            } catch (Exception ex) {
                em.getTransaction().rollback();
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                result = false;
            }
        } else {
            result = false;
        }
        return result;
    }
    
    @Override
    public int countar() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb
                .createQuery();
        
        Root<T> rt = cq.from(entityClass);
        cq.select(cb.count(rt));
        Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult())
                .intValue();
    }
    
    /**
     * Sincroniza la entidad t con la base de datos (trae los datos reales de dicha entidad)
     * Levanta una exepcion si la entidad no existe.
     * @param t la entidad a sincronizar
     */
    public void sincronizar(T t) {
        em.refresh(t);
    }

    /**
     * This method uses java reflection API and the javax.persistance.criteria
     * API to build a dynamic query. If the field you want to apply the filter on is a java.util.Date, its value has to be the concatenation of 
     * the date, a semi-colon (:), and the date format. To apply the filter on relationships you have to use period (.), example lote.description to filter by the product's lote description. 
     *
     * @param fData is a FilterData object (useful for json), that holds the
     * following data:
     * <b>params</b> Filter Parameters. A HashMap<String, Object> where keys
     * represent a field declared in T and value the value to filter by
     * <b>start</b> Result start position
     * <b>v</b> Result countar
 <b>orderFields</b> Query order fields
     * <b>dirs</b> order directions (length must be the same as the
     * orderFields'.)
     * @return List of T
     * @throws NoSuchFieldException if a field in params or orderFields does not
     * belong to T class
     * @throws ParseException if a java.util.Date field is provided but its
     * value cannot be parse to a Date
     */
    public List<T> dynamicQuery(FilterData fData) throws NoSuchFieldException, ParseException {
        HashMap<String, Object> params = fData.getParams();
        int start = fData.getStart();
        int count = fData.getCount();
        String[] orderFields = fData.getOrderFields();
        String[] dirs = fData.getDirs();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root from = cq.from(entityClass);

        ArrayList<ParameterExpression> paramList = new ArrayList<ParameterExpression>();//lista de parametros
        ArrayList<Object> values = new ArrayList<Object>();//lista de los valores a filtrar
        Predicate[] restrictions = new Predicate[0];//condiciones de la clausula where
        if (params != null) {
            for (Map.Entry<String, Object> en : params.entrySet()) {//recorrer filtros
                String attrName = en.getKey();//nombre del filtro (debe coincidir con el nombre del atributo en la entidad)
                Object value = en.getValue();//valor del filtro
                Field field;

                //<editor-fold defaultstate="collapsed" desc="verificar si es una relacion">
                String[] tmp = null;
                if (attrName.contains(":")) {
                    tmp = attrName.split(":");
                    attrName = tmp[0];
                }
                Path path = from.get(attrName);//campo a filtrar de la entidad (criteria query)
                field = entityClass.getDeclaredField(attrName);//campo a filtrar de la entidad
                if (null != tmp) {
                    path = from.get(attrName).get(tmp[1]);
                    field = field.getType().getDeclaredField(tmp[1]);
                    for (int i = 2; i < tmp.length; i++) {//navegar hacia el campo en la otra entidad
                        path = path.get(tmp[i]);//(criteria query)
                        field = field.getType().getDeclaredField(tmp[i]);//(reflection)
                    }
                }
                //</editor-fold>
                String paramName = field.getDeclaringClass().getSimpleName() + field.getName();
                ParameterExpression param = cb.parameter(field.getType(), paramName.toLowerCase());//creamos un parametro.
                Predicate predicate;//declaramos un predicado para usar en la clausula where

                if (field.getType().getName().equalsIgnoreCase("java.lang.string")) {
                    predicate = cb.like(path, param);//si el campo es un string, usamos like en el where
                } else {
                    boolean isDate = field.getType().getName().equalsIgnoreCase("java.util.date")
                            || field.getType().getName().equalsIgnoreCase("java.sql.date");
                    if (isDate) {//si el campo es de tipo fecha, parsear el value a java.util.Date (no se como va a funcionar con java.sql.Date)
                        String[] tmpValue = value.toString().split(":");
                        value = new SimpleDateFormat(tmpValue[1]).parse(tmpValue[0]);
                    }
                    predicate = cb.equal(path, param);//si no, simplemente equal (esto es una relacion)
                }

                restrictions = Arrays.copyOf(restrictions, restrictions.length + 1);//se aumenta el length del arreglo de restricciones
                restrictions[restrictions.length - 1] = predicate;//se agrega el nuevo predicado (nueva resctriccion o condicion)

                paramList.add(param);//se agrega el parametro a la lista de parametros para ser utulizado despues
                values.add(value);//lo mismo con los valores de los filtros
            }
        }

        ArrayList<Order> orderList = new ArrayList<Order>();//Ahora vamos a orderar
        if (orderFields != null && orderFields.length > 0 && orderFields.length == dirs.length) {// si no hay order fields o no coinciden los lengths no se ordena
            for (int i = 0; i < orderFields.length; i++) {
                String fldName = orderFields[i];

                //<editor-fold defaultstate="collapsed" desc="verificar si es una relacion">
                String[] tmp = null;
                if (fldName.contains(":")) {
                    tmp = fldName.split(":");
                    fldName = tmp[0];
                }
                Path path = from.get(fldName);
                Field fld = entityClass.getDeclaredField(fldName);//Se obtiene el campo de la entidad
                if (null != tmp) {
                    path = from.get(fldName).get(tmp[1]);
                    fld = fld.getType().getDeclaredField(tmp[1]);
                    for (int j = 2; j < tmp.length; j++) {//navegar hacia el campo en la otra entidad
                        path = path.get(tmp[j]);//(criteria query)
                        fld = fld.getType().getDeclaredField(tmp[j]);//(reflection)
                    }
                }
                //</editor-fold>

                Order order = dirs[i].equalsIgnoreCase("asc") ? cb.asc(path) : cb.desc(path);//se obtiene la direccion
                orderList.add(order);//se agrega a la lista
            }
            cq.orderBy(orderList);//se le aplica a la consulta
        }

        TypedQuery<T> q;//la consulta que se va a ejecutar
        if (restrictions.length > 0) {//si hay restricciones...
            cq.where(restrictions);//se agregan a la clausula where
            q = em.createQuery(cq);//se crea la consulta con las restricciones agregadas
            for (int i = 0; i < values.size(); i++) {
                Object val = values.get(i);
                ParameterExpression param = paramList.get(i);
                if (param.getJavaType().getName().equalsIgnoreCase("java.lang.string")) {
                    val = "%" + val.toString() + "%";
                }
                q.setParameter(param.getName(), val);//se le pasan los valores de los parametros
            }
        } else {//si no...
            q = em.createQuery(cq);//simplemente se crea la consulta
        }

        //Paginado (se verifica que countar y start tengan valores validos)
        if (Integer.signum(count) < 0) {
            count = Integer.MAX_VALUE;
        }
        if (Integer.signum(start) < 0) {
            start = 0;
        }
        q.setMaxResults(count);
        q.setFirstResult(start);

        List<T> rs = q.getResultList();
        return rs;
    }
public T obtenerReferencia(Object id){
        return em.getReference(entityClass, id);
    }
}
