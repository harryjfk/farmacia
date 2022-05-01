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
import pe.gob.minsa.farmacia.domain.TipoDocumento;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class TipoDocumentoExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<TipoDocumento> tiposDocumentos = (List<TipoDocumento>) model.get("Data");
        
        UtilExportExcel utilExportExcel = new UtilExportExcel();
        
        String titulo = "Reporte de Tipos de Documentos";
        
        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Código");
        cabeceras.add("Tipo de Documento");
        cabeceras.add("Estado");
        
        utilExportExcel.addRowHead(cabeceras, sheet, workbook);
        
        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);
        
        int rowCount = UtilExportExcel.rowDataCount;
         
        for (TipoDocumento tipoDocumento : tiposDocumentos) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(tipoDocumento.getIdTipoDocumento());
            aRow.getCell(0).setCellStyle(rowStyle);
            aRow.createCell(1).setCellValue(tipoDocumento.getNombreTipoDocumento());
            aRow.getCell(1).setCellStyle(rowStyle);
            aRow.createCell(2).setCellValue(tipoDocumento.getActivoTexto());
            aRow.getCell(2).setCellStyle(rowStyle);
        }
        
        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }    
}
