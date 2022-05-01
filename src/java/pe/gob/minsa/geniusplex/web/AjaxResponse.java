/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.web;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T>
 */
public class AjaxResponse<T> {

    private T data;
    private List<String> mssg = new ArrayList<String>();
    private List<String> tableProperties = new ArrayList<String>();
    private boolean hasError;

    public AjaxResponse() {hasError = false;}

    public AjaxResponse(T d) {
        data = d;
        hasError = false;
    }

    public AjaxResponse(List<String> ms) {
        mssg = ms;
        hasError = false;
    }

    public AjaxResponse(List<String> ms, T d) {
        data = d;
        mssg = ms;
        hasError = false;
    }
    
    public AjaxResponse(List<String> ms, T d, boolean tieneError) {
        data = d;
        mssg = ms;
        this.hasError = tieneError;
    }

    public void addMensaje(String msg) {
        mssg.add(msg);
    }

    
    /**
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * @return the mssg
     */
    public List<String> getMssg() {
        return mssg;
    }

    /**
     * @param mssg the mssg to set
     */
    public void setMssg(List<String> mssg) {
        this.mssg = mssg;
    }

    /**
     * @return the hasError
     */
    public boolean isHasError() {
        return hasError;
    }

    /**
     * @param hasError the hasError to set
     */
    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public List<String> getTableProperties() {
        return tableProperties;
    }

    public void setTableProperties(List<String> tableProperties) {
        this.tableProperties = tableProperties;
    }
     
}
