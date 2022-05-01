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
import pe.gob.minsa.farmacia.domain.Concepto;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class ConceptoExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        List<Concepto> conceptos = (List<Concepto>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Reporte de Conceptos";

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Código");        
        cabeceras.add("Concepto");        
        cabeceras.add("Estado");

        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        int rowCount = UtilExportExcel.rowDataCount;        

        for (Concepto concepto : conceptos) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            
            aRow.createCell(0).setCellValue(concepto.getIdConcepto());
            aRow.getCell(0).setCellStyle(rowStyle);
            aRow.createCell(1).setCellValue(concepto.getNombreConcepto());
            aRow.getCell(1).setCellStyle(rowStyle);
            aRow.createCell(2).setCellValue(concepto.getActivoTexto());
            aRow.getCell(2).setCellStyle(rowStyle);

        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }
}
