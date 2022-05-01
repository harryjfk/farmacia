package pe.gob.minsa.geniusplex.farmacia.dispensacion.viewExcel;

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
import pe.gob.minsa.farmacia.util.UtilExportExcel;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.KitAtencion;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.KitAtencionProducto;

public class KitAtencionExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        List<KitAtencion> kits = (List<KitAtencion>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Reporte de Kits de Atención";

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();

        cabeceras.add("Código");
        cabeceras.add("Descripción");
        cabeceras.add("Productos");

        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        int rowCount = UtilExportExcel.rowDataCount;

        for (KitAtencion kit : kits) {
            HSSFRow aRow = sheet.createRow(rowCount++);

            String desc = kit.getDescripcion() != null ? kit.getDescripcion() : "";
            //0
            aRow.createCell(0).setCellValue(kit.getId());
            aRow.getCell(0).setCellStyle(rowStyle);
            //1
            aRow.createCell(1).setCellValue(desc);
            aRow.getCell(1).setCellStyle(rowStyle);
            
            String productos = "";
            for (KitAtencionProducto kP : kit.getProductos()) {
                productos += kP.getProducto().getDescripcion() + " (" + (int) kP.getCantidad() + ") / ";
            }
            //2
            aRow.createCell(2).setCellValue(productos);
            aRow.getCell(2).setCellStyle(rowStyle);
            
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }

}
