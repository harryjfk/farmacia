package pe.gob.minsa.farmacia.viewExcel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import pe.gob.minsa.farmacia.domain.TipoAccion;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class TipoAccionExcel extends AbstractExcelView  {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<TipoAccion> tiposAcciones = (List<TipoAccion>) model.get("Data");
        
        UtilExportExcel utilExportExcel = new UtilExportExcel();
        
        String titulo = "Reporte de Tipos de Acciones";
        
        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Código");
        cabeceras.add("Tipo de Acción");
        cabeceras.add("Estado");
        
        utilExportExcel.addRowHead(cabeceras, sheet, workbook);
        
        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);
        
        int rowCount = UtilExportExcel.rowDataCount;
         
        for (TipoAccion tipoAccion : tiposAcciones) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(tipoAccion.getIdTipoAccion());
            aRow.getCell(0).setCellStyle(rowStyle);
            aRow.createCell(1).setCellValue(tipoAccion.getNombreTipoAccion());
            aRow.getCell(1).setCellStyle(rowStyle);
            aRow.createCell(2).setCellValue(tipoAccion.getActivoTexto());
            aRow.getCell(2).setCellStyle(rowStyle);
        }
        
        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }
    
}