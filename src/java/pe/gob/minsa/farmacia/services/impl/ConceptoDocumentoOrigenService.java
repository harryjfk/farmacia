package pe.gob.minsa.farmacia.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.ConceptoDocumentoOrigenDao;
import pe.gob.minsa.farmacia.domain.ConceptoDocumentoOrigen;
import pe.gob.minsa.farmacia.util.BusinessException;

public class ConceptoDocumentoOrigenService {

    @Autowired
    ConceptoDocumentoOrigenDao conceptoDocumentoOrigenDao;

    public List<ConceptoDocumentoOrigen> listar(int idConcepto) {
        return conceptoDocumentoOrigenDao.listar(idConcepto);
    }

    public void insertar(ConceptoDocumentoOrigen conceptoDocumentoOrigen) throws BusinessException {
       conceptoDocumentoOrigenDao.insertar(conceptoDocumentoOrigen);
    }

    public void eliminar(int idConceptoDocumentoOrigen) throws BusinessException  {
        conceptoDocumentoOrigenDao.eliminar(idConceptoDocumentoOrigen);
    }

}
