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
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Vendedor;

public class VendedorExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        List<Vendedor> vendedores = (List<Vendedor>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Reporte de Vendedores";

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Vendedor");
        cabeceras.add("Código");
        cabeceras.add("Código de Personal");
        cabeceras.add("Tipo de Personal");
        cabeceras.add("Dirección");
        cabeceras.add("Telefono");

        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        int rowCount = UtilExportExcel.rowDataCount;

        SimpleDateFormat dateFormat = new SimpleDateFormat(utilExportExcel.getFormatoFecha());
        Calendar grego = new GregorianCalendar();

        for (Vendedor vendedor : vendedores) {
            HSSFRow aRow = sheet.createRow(rowCount++);

            aRow.createCell(0).setCellValue(vendedor.getNombreVendedor());
            aRow.getCell(0).setCellStyle(rowStyle);
            aRow.createCell(1).setCellValue(vendedor.getIdVendedor());
            aRow.getCell(1).setCellStyle(rowStyle);
             aRow.createCell(2).setCellValue(vendedor.getPersonal());
            aRow.getCell(2).setCellStyle(rowStyle);
             aRow.createCell(3).setCellValue(vendedor.getTipoPersonal());
            aRow.getCell(3).setCellStyle(rowStyle);
            aRow.createCell(4).setCellValue(vendedor.getDireccion());
            aRow.getCell(4).setCellStyle(rowStyle);
            aRow.createCell(5).setCellValue(vendedor.getTelefonoUno());
            aRow.getCell(5).setCellStyle(rowStyle);
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }

}
