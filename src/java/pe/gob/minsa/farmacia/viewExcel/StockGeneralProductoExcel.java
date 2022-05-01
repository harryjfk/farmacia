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
import pe.gob.minsa.farmacia.domain.dto.StockGeneralProductoDto;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class StockGeneralProductoExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        List<StockGeneralProductoDto> stockGeneralProductos = (List<StockGeneralProductoDto>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Stock General de Productos";

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Código");
        cabeceras.add("Descripción");
        cabeceras.add("Stock");
        cabeceras.add("Forma Farmacéutica");
        cabeceras.add("Presentación");
        cabeceras.add("Concentración");
        cabeceras.add("Precio Ref.");
        cabeceras.add("Stock Min");
        cabeceras.add("Stock Max");

        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        int rowCount = UtilExportExcel.rowDataCount;

        for (StockGeneralProductoDto stockGeneralProducto : stockGeneralProductos) {
            HSSFRow aRow = sheet.createRow(rowCount++);

            aRow.createCell(0).setCellValue(stockGeneralProducto.getCodigoSismed());
            aRow.createCell(1).setCellValue(stockGeneralProducto.getDescripcion());
            aRow.createCell(2).setCellValue(stockGeneralProducto.getCantidad());
            aRow.createCell(3).setCellValue(stockGeneralProducto.getNombreFormaFarmaceutica());
            aRow.createCell(4).setCellValue(stockGeneralProducto.getPresentacion());
            aRow.createCell(5).setCellValue(stockGeneralProducto.getConcentracion());
            aRow.createCell(6).setCellValue(stockGeneralProducto.getPrecioRef().doubleValue());
            aRow.createCell(7).setCellValue(stockGeneralProducto.getStockMin());
            aRow.createCell(8).setCellValue(stockGeneralProducto.getStockMax());

            for (int i = 0; i < cabeceras.size(); ++i) {
                aRow.getCell(i).setCellStyle(rowStyle);
            }
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }    
}
