package pe.gob.minsa.farmacia.services.impl;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.IciDetalleDao;
import pe.gob.minsa.farmacia.domain.dto.IciDetalleDto;
import pe.gob.minsa.farmacia.util.BusinessException;

public class IciDetalleService {

    @Autowired
    IciDetalleDao iciDetalleDao;

    public List<IciDetalleDto> listarPorIci(int idIci) {
        return iciDetalleDao.listarPorIdIci(idIci);
    }
    
    public List<IciDetalleDto> listarPorFechas(Timestamp fechaDesde, Timestamp fechaHasta, int idAlmacen, int idTipoSuministro) {
        return iciDetalleDao.listarPorFechas(fechaDesde, fechaHasta, idAlmacen, idTipoSuministro);
    }

    public void procesar(int idAlmacen, int idPeriodo/**/, int idTipoSuministro) throws BusinessException {

        int idPeriodoAnt = idPeriodo - 1;

        if (String.valueOf(idPeriodoAnt).substring(4, 6).equalsIgnoreCase("00")) {
            idPeriodoAnt = idPeriodoAnt - 100;
            idPeriodoAnt = idPeriodoAnt + 12;
        }

        iciDetalleDao.procesar(idAlmacen, idPeriodo, idPeriodoAnt/**/, idTipoSuministro);
    }
}