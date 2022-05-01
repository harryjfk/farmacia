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
import pe.gob.minsa.farmacia.domain.TipoAlmacen;
import pe.gob.minsa.farmacia.domain.TipoCompra;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class TipoCompraExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        List<TipoCompra> tiposCompras = (List<TipoCompra>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Reporte de Tipos de Compras";

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Código");        
        cabeceras.add("Tipo de Compra");        
        cabeceras.add("Estado");

        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        int rowCount = UtilExportExcel.rowDataCount;        

        for (TipoCompra tipoCompra : tiposCompras) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            
            aRow.createCell(0).setCellValue(tipoCompra.getIdTipoCompra());
            aRow.getCell(0).setCellStyle(rowStyle);
            aRow.createCell(1).setCellValue(tipoCompra.getNombreTipoCompra());
            aRow.getCell(1).setCellStyle(rowStyle);
            aRow.createCell(2).setCellValue(tipoCompra.getActivoTexto());
            aRow.getCell(2).setCellStyle(rowStyle);

        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }
    
}
