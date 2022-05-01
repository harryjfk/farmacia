package pe.gob.minsa.farmacia.services.impl;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.IdiDetalleDao;
import pe.gob.minsa.farmacia.domain.dto.IdiDetalleDto;
import pe.gob.minsa.farmacia.util.BusinessException;

public class IdiDetalleService {

    @Autowired
    IdiDetalleDao idiDetalleDao;

    public List<IdiDetalleDto> listarPorIdi(int idIdi) {
        return idiDetalleDao.listarPorIdIdi(idIdi);
    }
    
    public List<IdiDetalleDto> listarPorFechas(Timestamp fechaDesde, Timestamp fechaHasta, int idTipoSuministro, int idTipoProceso) {
        return idiDetalleDao.listarPorFechas(fechaDesde, fechaHasta, idTipoSuministro, idTipoProceso);
    }

    public void procesar(int idPeriodo/**/, int idTipoSuministro, int idTipoProceso) throws BusinessException {

        int idPeriodoAnt = idPeriodo - 1;

        if (String.valueOf(idPeriodoAnt).substring(4, 6).equalsIgnoreCase("00")) {
            idPeriodoAnt = idPeriodoAnt - 100;
            idPeriodoAnt = idPeriodoAnt + 12;
        }

        idiDetalleDao.procesar(idPeriodo, idPeriodoAnt/**/, idTipoSuministro, idTipoProceso);
    }
}
