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
import pe.gob.minsa.farmacia.domain.dto.ProductoAlertaVencimientoDto;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class ProductosVencimientoExcel extends AbstractExcelView  {
    
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        List<ProductoAlertaVencimientoDto> productos = (List<ProductoAlertaVencimientoDto>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Reporte de Producto Vencimiento";

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Almacén");
        cabeceras.add("Código");        
        cabeceras.add("Descripción");
        cabeceras.add("Tipo");
        cabeceras.add("Lote");
        cabeceras.add("Estado");
        cabeceras.add("Vence");
        cabeceras.add("Precio");
        cabeceras.add("Stock");
        cabeceras.add("Total");

        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        int rowCount = UtilExportExcel.rowDataCount;        
        SimpleDateFormat dateFormat = new SimpleDateFormat(utilExportExcel.getFormatoFecha());
        Calendar grego = new GregorianCalendar();
        
        for (ProductoAlertaVencimientoDto producto : productos) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            
            aRow.createCell(0).setCellValue(producto.getAlmacen());            
            aRow.createCell(1).setCellValue(producto.getCodigoSismed());            
            aRow.createCell(2).setCellValue(producto.getProducto());
            aRow.createCell(3).setCellValue(producto.getTipoProducto());
            aRow.createCell(4).setCellValue(producto.getLote());
            aRow.createCell(5).setCellValue(producto.getEstado());
            grego.setTimeInMillis(producto.getFechaVencimiento().getTime());
            aRow.createCell(6).setCellValue(dateFormat.format(grego.getTime()));
            aRow.createCell(7).setCellValue(producto.getPrecio().doubleValue());
            aRow.createCell(8).setCellValue(producto.getStock());
            aRow.createCell(9).setCellValue(producto.getTotal().doubleValue());
            
            for(int i = 0; i<cabeceras.size(); ++i){
                aRow.getCell(i).setCellStyle(rowStyle);
            }            
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }
}
