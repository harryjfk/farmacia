/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services;

import java.util.List;

/**
 *
 * @param <T>
 */
public interface GServiceManager<T> {

    public List<T> listar();

    public T obtenerPorId(Object id);

    public boolean insertar(T t);

    public boolean actualizar(T t);

    public boolean cambiarEstado(Object id);

    public boolean eliminar(Object id);

    public int countar();

}
