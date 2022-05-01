/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.gob.minsa.geniusplex.web.objects;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * 
 */
public class FilterData implements Serializable {
    private HashMap<String, Object> params = new HashMap<String, Object>();
    private int start;
    private int count;
    private String[] orderFields;
    private String[] dirs;

    /**
     * @return the params
     */
    public HashMap<String, Object> getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }

    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the orderFields
     */
    public String[] getOrderFields() {
        return orderFields;
    }

    /**
     * @param orderFields the orderFields to set
     */
    public void setOrderFields(String[] orderFields) {
        this.orderFields = orderFields;
    }

    /**
     * @return the dirs
     */
    public String[] getDirs() {
        return dirs;
    }

    /**
     * @param dirs the dirs to set
     */
    public void setDirs(String[] dirs) {
        this.dirs = dirs;
    }
}
