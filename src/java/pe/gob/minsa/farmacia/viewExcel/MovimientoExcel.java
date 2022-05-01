package pe.gob.minsa.farmacia.viewExcel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import pe.gob.minsa.farmacia.domain.dto.MovimientoDto;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class MovimientoExcel extends AbstractExcelView {
    
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {

        List<MovimientoDto> movimientos = (List<MovimientoDto>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Reporte de Movimientos";

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("F. Registro");
        cabeceras.add("Tipo");
        cabeceras.add("N° Mov.");
        cabeceras.add("Almacén Origen");
        cabeceras.add("Almacén Destino");
        cabeceras.add("Concepto");
        cabeceras.add("Importe");
        cabeceras.add("F. Recepción");
        cabeceras.add("Tipo Doc.");
        cabeceras.add("N° Doc.");
        cabeceras.add("Proveedor");
        
        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        int rowCount = UtilExportExcel.rowDataCount;
        
        SimpleDateFormat dateFormat = new SimpleDateFormat(utilExportExcel.getFormatoFecha());
        Calendar grego = new GregorianCalendar();

        for (MovimientoDto movimiento : movimientos) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            
            grego.setTimeInMillis(movimiento.getFechaRegistro().getTime());
            aRow.createCell(0).setCellValue(dateFormat.format(grego.getTime()));
            aRow.createCell(1).setCellValue(movimiento.getTipoMovimiento().toString());    
            aRow.createCell(2).setCellValue(movimiento.getNumeroMovimiento());
            aRow.createCell(3).setCellValue(movimiento.getAlmacenOrigen());
            aRow.createCell(4).setCellValue(movimiento.getAlmacenDestino());
            aRow.createCell(5).setCellValue(movimiento.getNombreConcepto());
            aRow.createCell(6).setCellValue(movimiento.getTotal().doubleValue());
            if(movimiento.getFechaRecepcion() == null){
                aRow.createCell(7).setCellValue("");
            }else{
                grego.setTimeInMillis(movimiento.getFechaRecepcion().getTime());
                aRow.createCell(7).setCellValue(dateFormat.format(grego.getTime()));
            }            
            aRow.createCell(8).setCellValue(movimiento.getNombreTipoDocumento());
            aRow.createCell(9).setCellValue(movimiento.getNumeroDocumentoMov());
            aRow.createCell(10).setCellValue(movimiento.getRazonSocial());
            
            for(int i = 0; i < cabeceras.size(); ++i){
                aRow.getCell(i).setCellStyle(rowStyle);
            }
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
        
    }
    
}
