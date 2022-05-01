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
import pe.gob.minsa.farmacia.domain.UnidadMedida;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class UnidadMedidaExcel  extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        List<UnidadMedida> unidadesMedida = (List<UnidadMedida>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Reporte de Unidades de Medida";

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Código");
        cabeceras.add("Unidad de Medida");
        cabeceras.add("Abreviatura");
        cabeceras.add("Estado");

        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        int rowCount = UtilExportExcel.rowDataCount;        

        for (UnidadMedida unidadMedida : unidadesMedida) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            
            aRow.createCell(0).setCellValue(unidadMedida.getIdUnidadMedida());
            aRow.createCell(1).setCellValue(unidadMedida.getNombreUnidadMedida());
            aRow.createCell(2).setCellValue(unidadMedida.getAbreviatura());
            aRow.createCell(3).setCellValue(unidadMedida.getActivoTexto());
            
            for(int i = 0; i < cabeceras.size(); ++i){
                aRow.getCell(i).setCellStyle(rowStyle);
            }            
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }
    
}
