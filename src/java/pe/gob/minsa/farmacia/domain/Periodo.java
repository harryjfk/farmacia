package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;

public class Periodo extends BaseDomain implements Serializable {
    private int idPeriodo;    

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }
    
    public int getAnio(){
        return Integer.parseInt(String.valueOf(idPeriodo).substring(0, 4));
    }
    
    public int getMesEntero(){
        return Integer.parseInt(this.getMes());
    }
    
    public String getMes(){
        return String.valueOf(idPeriodo).substring(4, 6);
    }
    
    public String getNombreMes(){
        String mes = getMes();
        
        if(mes.equalsIgnoreCase("01")){
            mes = "Enero";
        }else if(mes.equalsIgnoreCase("02")){
            mes = "Febrero";
        }else if(mes.equalsIgnoreCase("03")){
            mes = "Marzo";
        }else if(mes.equalsIgnoreCase("04")){
            mes = "Abril";
        }else if(mes.equalsIgnoreCase("05")){
            mes = "Mayo";
        }else if(mes.equalsIgnoreCase("06")){
            mes = "Junio";
        }else if(mes.equalsIgnoreCase("07")){
            mes = "Julio";
        }else if(mes.equalsIgnoreCase("08")){
            mes = "Agosto";
        }else if(mes.equalsIgnoreCase("09")){
            mes = "Setiembre";
        }else if(mes.equalsIgnoreCase("10")){
            mes = "Octubre";
        }else if(mes.equalsIgnoreCase("11")){
            mes = "Noviembre";
        }else if(mes.equalsIgnoreCase("12")){
            mes = "Diciembre";
        }
        
        return mes;
    }
}