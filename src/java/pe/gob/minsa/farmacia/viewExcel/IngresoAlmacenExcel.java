package pe.gob.minsa.farmacia.viewExcel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import pe.gob.minsa.farmacia.domain.dto.IngresoAlmacenDto;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class IngresoAlmacenExcel  extends AbstractExcelView {
    
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        List<IngresoAlmacenDto> ingresosAlmacen = (List<IngresoAlmacenDto>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Reporte de Productos Ingresados";

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Fecha de Registro");        
        cabeceras.add("Código");        
        cabeceras.add("Medicamento o Insumo");

        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);
         SimpleDateFormat dateFormat = new SimpleDateFormat(utilExportExcel.getFormatoFecha());
         
        int rowCount = UtilExportExcel.rowDataCount;        
        GregorianCalendar grego = new GregorianCalendar();
        for (IngresoAlmacenDto ingresos : ingresosAlmacen) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            
            grego.setTimeInMillis(ingresos.getFechaRegistro().getTime());            
            aRow.createCell(0).setCellValue(dateFormat.format(grego.getTime()));
            aRow.createCell(1).setCellValue(ingresos.getCodigoSismed());
            aRow.createCell(2).setCellValue(ingresos.getDescripcion());
            
            for(int i = 0; i < cabeceras.size(); ++i){
                aRow.getCell(0).setCellStyle(rowStyle);
            }
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }
}
