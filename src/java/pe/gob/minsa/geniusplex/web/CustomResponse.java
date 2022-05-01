/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.web;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author armando
 */
public class CustomResponse {
    private List<String> mensajesRepuesta;

    public CustomResponse() {
        this.mensajesRepuesta = new ArrayList<String>();
    }

    public List<String> getMensajesRepuesta() {
        return mensajesRepuesta;
    }

    public void setMensajesRepuesta(List<String> mensajesRepuesta) {
        this.mensajesRepuesta = mensajesRepuesta;
    }
}
