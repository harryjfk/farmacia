package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.PeriodoDao;
import pe.gob.minsa.farmacia.domain.Periodo;
import pe.gob.minsa.farmacia.util.BusinessException;

public class PeriodoService  {

    @Autowired
    PeriodoDao periodoDao;
    
    @Autowired
    MovimientoService movimientoService;
    
    @Autowired
    MovimientoProductoService movimientoProductoService;
    
    public List<Periodo> listar() {
        return periodoDao.listar();
    }   
    
    public List<Periodo> listarPorAnio(int anio) {
        return periodoDao.listarPorAnio(anio);
    }
    
    public List<Integer> listarAnios(){
        List<Integer> anios = new ArrayList<Integer>();
        
        for(Periodo p : periodoDao.listar()){
            if(anios.isEmpty()){
                anios.add(p.getAnio());
            }else{
                boolean agregado = false;
                for(Integer anio : anios){
                    if(anio == p.getAnio()){
                        agregado = true;
                    }
                }
                
                if(agregado == false){
                    anios.add(p.getAnio());
                }
            }            
        }
        
        return anios;
    }
    
    public List<Integer> AperturarAlmacen(int idPeriodoCerrar){
        return periodoDao.AperturarAlmacen(idPeriodoCerrar);
    }
    
    public void insertar(Periodo periodo) throws BusinessException{
        List<String> errores = new ArrayList<String>();
        
        if(periodoDao.Existe(periodo.getIdPeriodo())){
            errores.add("Ya existe este periodo");
        }
        
        if(errores.isEmpty() == false){
            throw new BusinessException(errores);
        }else{
            periodo.setActivo(1);
            periodoDao.insertar(periodo);
        }
    }
    
    public void aperturarAnio(int anio, int UsuarioCreacion) throws BusinessException{
        
        if(this.listarPorAnio(anio).size() > 0){
            throw new BusinessException(Arrays.asList("El " + String.valueOf(anio) + " ya se encuentra aperturado"));
        }        
        
        Periodo periodoActivo = this.obtenerPeriodoActivo();
        
        if(periodoActivo.getAnio() != anio - 1 || periodoActivo.getMesEntero() != 12){
            throw new BusinessException(Arrays.asList("No puede aperturar un año sin llegar a diciembre del año anterior"));
        }else{            
            periodoActivo.setUsuarioModificacion(UsuarioCreacion);
            periodoDao.cambiarEstado(periodoActivo);
        }
        
        for (int i = 1; i <= 12; ++i) {
            Periodo periodo = new Periodo();
            String idPeriodo = String.valueOf(anio) + ((String.valueOf(i).length() == 1) ? "0" : "") + String.valueOf(i);
            periodo.setIdPeriodo(Integer.parseInt(idPeriodo));
            periodo.setUsuarioCreacion(UsuarioCreacion);
            
            if(i == 1) {
                periodo.setActivo(1);
            }else {
                periodo.setActivo(0);
            }
            
            periodoDao.insertar(periodo);
        }
    }
    
    public void abrirMes(Periodo periodo) throws BusinessException{
        
        List<String> errores = new ArrayList<String>();
        
        Periodo periodoAnterior = new Periodo();
        
        String mesAnterior = String.valueOf(periodo.getMesEntero() - 1);
        mesAnterior = ((mesAnterior.length() == 1) ? "0" : "") + mesAnterior;
       
        periodoAnterior.setIdPeriodo(
                Integer.parseInt(
                        String.valueOf(periodo.getAnio()) + mesAnterior
                )
        );
        
        periodoAnterior.setUsuarioModificacion(periodo.getUsuarioModificacion());
        
        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            periodoDao.cambiarEstado(periodoAnterior);
            periodoDao.cambiarEstado(periodo);
            
            List<Integer> idsAlmacenes = periodoDao.AperturarAlmacen(periodoAnterior.getIdPeriodo());
         
            for(Integer idAlmacen : idsAlmacenes){
                int idMovimiento = movimientoService.ingresoInicial(idAlmacen, periodo.getIdPeriodo(), periodo.getUsuarioModificacion());
                movimientoProductoService.ingresoInicial(idMovimiento, periodoAnterior.getIdPeriodo(), idAlmacen, periodo.getUsuarioModificacion());
            }
        }
    }
    
    public Periodo obtenerPeriodoActivo(){
        return periodoDao.obtenerPeriodoActivo();
    }
}
