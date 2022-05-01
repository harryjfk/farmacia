package pe.gob.minsa.farmacia.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.ConceptoTipoDocumentoMovDao;
import pe.gob.minsa.farmacia.domain.ConceptoTipoDocumentoMov;
import pe.gob.minsa.farmacia.util.BusinessException;

public class ConceptoTipoDocumentoMovService {

    @Autowired
    ConceptoTipoDocumentoMovDao conceptoTipoDocumentoMovDao;

    public List<ConceptoTipoDocumentoMov> listar(int idConcepto) {
        return conceptoTipoDocumentoMovDao.listar(idConcepto);
    }

    public void insertar(ConceptoTipoDocumentoMov conceptoTipoDocumentoMov) throws BusinessException {
        conceptoTipoDocumentoMovDao.insertar(conceptoTipoDocumentoMov);
    }

    public void eliminar(int idConceptoTipoDocumentoMov) throws BusinessException {
        conceptoTipoDocumentoMovDao.eliminar(idConceptoTipoDocumentoMov);
    }
}
