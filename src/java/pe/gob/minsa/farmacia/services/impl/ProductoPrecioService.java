package pe.gob.minsa.farmacia.services.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.ParametroDao;
import pe.gob.minsa.farmacia.dao.impl.ProductoPrecioDao;
import pe.gob.minsa.farmacia.domain.Parametro;
import pe.gob.minsa.farmacia.domain.ProductoPrecio;
import pe.gob.minsa.farmacia.domain.dto.PrecioUltimoDto;
import pe.gob.minsa.farmacia.util.BusinessException;

public class ProductoPrecioService {

    @Autowired
    ProductoPrecioDao productoPrecioDao;

    @Autowired
    ParametroDao parametroDao;

    private String nombreParametroDistribucion = "DISTRIBUCION";
    private String nombreParametroOperacion = "OPERACION";

    public List<PrecioUltimoDto> listarUltimoPrecio(String descripcion) {
        return productoPrecioDao.listarUltimoPrecio(descripcion);
    }

    public List<ProductoPrecio> listarPorProducto(int idProducto) {
        return productoPrecioDao.listarPorProducto(idProducto);
    }

    public void insertar(ProductoPrecio productoPrecio) throws BusinessException {

        List<String> errores = new ArrayList<String>();
        
        if (productoPrecio.getPrecioAdquisicion().compareTo(BigDecimal.ZERO) <= 0) {
            errores.add("El precio de adquisición es un campo requerido y debe ser mayor a 0");
        }

        if (productoPrecio.getPrecioDistribucion().compareTo(BigDecimal.ZERO) <= 0) {
            errores.add("El precio de distribución es un campo requerido y debe ser mayor a 0");
        }

        if (productoPrecio.getPrecioOperacion().compareTo(BigDecimal.ZERO) <= 0) {
            errores.add("El precio de operación es un campo requerido y debe ser mayor a 0");
        }

        if (productoPrecio.getPrecioAdquisicion().compareTo(BigDecimal.ZERO) > 0
                && productoPrecio.getPrecioDistribucion().compareTo(BigDecimal.ZERO) > 0
                && productoPrecio.getPrecioOperacion().compareTo(BigDecimal.ZERO) > 0) {

            if (productoPrecio.getPrecioOperacion().compareTo(productoPrecio.getPrecioDistribucion()) == -1) {
                errores.add("El precio operación no puede ser menor al precio distribución");
            }

            if (productoPrecio.getPrecioDistribucion().compareTo(productoPrecio.getPrecioAdquisicion()) == -1) {
                errores.add("El precio distribución no puede ser menor al precio adquisición");
            }            
        }

        //Al cumplir las validacion previas puedo continuar con distintas validaciones que necesitan de bd
        if (errores.isEmpty()) {
            Parametro parametroOperacion = parametroDao.obtenerPorNombre(nombreParametroOperacion);
            Parametro parametroDistribucion = parametroDao.obtenerPorNombre(nombreParametroDistribucion);

            BigDecimal precioDistribucion = new BigDecimal(parametroDistribucion.getValor());
            precioDistribucion =  productoPrecio.getPrecioAdquisicion().add(precioDistribucion.multiply(productoPrecio.getPrecioAdquisicion()));
            
            BigDecimal precioOperacion = new BigDecimal(parametroOperacion.getValor());
            precioOperacion =  productoPrecio.getPrecioAdquisicion().add(precioOperacion.multiply(productoPrecio.getPrecioAdquisicion()));
                        
            if (productoPrecio.getPrecioDistribucion().compareTo(precioDistribucion) > 0) {
                errores.add("El precio de distribución no puede ser mayor al precio sugerido");
            }

            if (productoPrecio.getPrecioOperacion().compareTo(precioOperacion) > 0) {
                errores.add("El precio de operación no puede ser mayor al precio sugerido");
            }
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            productoPrecio.setTipoPrecio("M");
            productoPrecio.setFechaRegistro(new Timestamp(new GregorianCalendar().getTimeInMillis()));
            productoPrecio.setFechaVigencia(new Timestamp(new GregorianCalendar().getTimeInMillis()));            
            productoPrecioDao.insertar(productoPrecio);
        }
    }
}
