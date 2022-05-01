package pe.gob.minsa.farmacia.util;

import java.util.HashMap;
import java.util.Map;

public class ManagerDatatables {
    
    private String sEcho;
    private int iTotalRecords;
    private int iTotalDisplayRecords;
    private Object aaData;
    private Map<String, Object> extraData;
    
    public ManagerDatatables(){
        extraData = new HashMap<String, Object>();
    }
    
    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public int getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public Object getAaData() {
        return aaData;
    }

    public void setAaData(Object aaData) {
        this.aaData = aaData;
    }

    public Map<String, Object> getExtraData() {
        return extraData;
    }

    public void setExtraData(Map<String, Object> extraData) {
        this.extraData = extraData;
    }
}
