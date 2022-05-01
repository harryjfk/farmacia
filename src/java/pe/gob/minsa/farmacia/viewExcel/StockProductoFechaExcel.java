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
import pe.gob.minsa.farmacia.domain.dto.ProductoStockFechaDto;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class StockProductoFechaExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        List<ProductoStockFechaDto> productosStockFecha = (List<ProductoStockFechaDto>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Stock Producto a Fecha";

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Código Sismed");
        cabeceras.add("Producto");
        cabeceras.add("Tipo");
        cabeceras.add("F.F.");
        cabeceras.add("Stock");

        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        int rowCount = UtilExportExcel.rowDataCount;

        for (ProductoStockFechaDto productoStockFecha : productosStockFecha) {
            HSSFRow aRow = sheet.createRow(rowCount++);

            aRow.createCell(0).setCellValue(productoStockFecha.getCodigoSismed());
            aRow.createCell(1).setCellValue(productoStockFecha.getDescripcion());
            aRow.createCell(2).setCellValue(productoStockFecha.getTipoProducto());
            aRow.createCell(3).setCellValue(productoStockFecha.getFormaFarmaceutica());
            aRow.createCell(4).setCellValue(productoStockFecha.getStock());
            
            for (int i = 0; i < cabeceras.size(); ++i) {
                aRow.getCell(i).setCellStyle(rowStyle);
            }
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }

    
}
