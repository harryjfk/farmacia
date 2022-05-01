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
import pe.gob.minsa.farmacia.domain.dto.ProductoComp;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class ProductoExcel extends AbstractExcelView {
    
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        
        List<ProductoComp> productos = (List<ProductoComp>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Reporte de Productos";

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();        
        cabeceras.add("Descripción");
        cabeceras.add("Presentación");
        cabeceras.add("Concentración");
        cabeceras.add("Forma Farmacéutica");
        cabeceras.add("Tipo de Producto");
        cabeceras.add("Unidad de Medida");
        cabeceras.add("Estado");

        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        int rowCount = UtilExportExcel.rowDataCount;        

        for (ProductoComp producto : productos) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            
            aRow.createCell(0).setCellValue(producto.getDescripcion());            
            aRow.createCell(1).setCellValue(producto.getPresentacion());            
            aRow.createCell(2).setCellValue(producto.getConcentracion());            
            aRow.createCell(3).setCellValue(producto.getFormaFarmaceutica());
            aRow.createCell(4).setCellValue(producto.getTipoProducto());
            aRow.createCell(5).setCellValue(producto.getUnidadMedida());
            aRow.createCell(6).setCellValue(producto.getActivoTexto());
            
            for(int i = 0; i < cabeceras.size(); ++i){
                aRow.getCell(i).setCellStyle(rowStyle);
            }
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }
}
