/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.helpers;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.stereotype.Component;

/**
 *
 * @author armando
 */
@Component("ventaHelper")
public class VentaHelper {
    public HashMap<Integer, String> listarDocumentos(EntityManager em) {
        String q = ""
                + "select IdTipoDocumentoMov, NombreTipoDocumentoMov "
                + "from Far_Tipo_Documento_Mov "
                + "where NombreTipoDocumentoMov like '%FACTURA%' "
                + "OR NombreTipoDocumentoMov like '%BOLETA%'";
        Query query = em.createNativeQuery(q);
        List<Object[]> res = query.getResultList();
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        for (Object[] re : res) {
            Integer idDocumento = (Integer) re[0];
            String nombre = (String) re[1];
            map.put(idDocumento, nombre);
        }
        return map;
    }
    
    public String getNombreDocumento(long idDoc, EntityManager em) {
        String q = ""
                + "select NombreTipoDocumentoMov "
                + "from Far_Tipo_Documento_Mov "
                + "where IdTipoDocumentoMov = " + idDoc;
        Query query = em.createNativeQuery(q);
        String res = null;
        try {
            res = (String) query.getSingleResult();
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(VentaHelper.class.getName()).log(Level.INFO, "Venta: no seencontro el documento con id " + idDoc, e);
        }
        return res;
    }
    
}
