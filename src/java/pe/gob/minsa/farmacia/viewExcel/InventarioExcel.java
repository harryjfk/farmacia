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
import pe.gob.minsa.farmacia.domain.dto.InventarioProductoTotalDto;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class InventarioExcel extends AbstractExcelView {
    
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        
        List<InventarioProductoTotalDto> inventarios = (List<InventarioProductoTotalDto>)model.get("Data");
        
        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Inventario";
        
        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Código");
        cabeceras.add("Descripción");
        cabeceras.add("F. F.");
        cabeceras.add("Valor Actual Cant.");
        cabeceras.add("Valor Actual Precio");
        cabeceras.add("Valor Actual Total");
        cabeceras.add("Conteo Físico");
        cabeceras.add("Faltantes Cant.");
        cabeceras.add("Faltantes Total");
        cabeceras.add("Sobrantes Cant.");
        cabeceras.add("Sobrantes Total");
        cabeceras.add("Total");
        cabeceras.add("Alter./Rotos");
        
        utilExportExcel.addRowHead(cabeceras, sheet, workbook);
        
        int rowCount = UtilExportExcel.rowDataCount;
        
        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);
        
         for (InventarioProductoTotalDto inventario : inventarios) {
            HSSFRow aRow = sheet.createRow(rowCount++);

            aRow.createCell(0).setCellValue(inventario.getIdProducto());            
            aRow.createCell(1).setCellValue(inventario.getDescripcion());
            aRow.createCell(2).setCellValue(inventario.getFormaFarmaceutica());
            aRow.createCell(3).setCellValue(inventario.getCantidad());
            aRow.createCell(4).setCellValue(inventario.getPrecio().doubleValue());
            aRow.createCell(5).setCellValue(inventario.getTotal().doubleValue());
            aRow.createCell(6).setCellValue(inventario.getConteo());
            aRow.createCell(7).setCellValue(inventario.getCantidadFaltante());            
            aRow.createCell(8).setCellValue(inventario.getTotalFaltante().doubleValue());            
            aRow.createCell(9).setCellValue(inventario.getCantidadSobrante());
            aRow.createCell(10).setCellValue(inventario.getTotalSobrante().doubleValue());
            aRow.createCell(11).setCellValue(inventario.getTotalFisico().doubleValue());
            aRow.createCell(12).setCellValue(inventario.getCantidadAlterado());

            for (int i = 0; i < cabeceras.size(); ++i) {
                aRow.getCell(i).setCellStyle(rowStyle);
            }
        }
        
        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }
}
