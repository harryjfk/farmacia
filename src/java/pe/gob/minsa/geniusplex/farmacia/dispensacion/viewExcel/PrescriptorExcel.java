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
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Prescriptor;

public class PrescriptorExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        List<Prescriptor> prescriptores = (List<Prescriptor>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Reporte de Prescriptores";

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Prescripor");
        cabeceras.add("Código");
        cabeceras.add("Dirección");
        cabeceras.add("Telefono");
        cabeceras.add("Código de Personal");
        cabeceras.add("Colegiatura");
        cabeceras.add("Tipo de Profesión");
        

        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        int rowCount = UtilExportExcel.rowDataCount;

        SimpleDateFormat dateFormat = new SimpleDateFormat(utilExportExcel.getFormatoFecha());
        Calendar grego = new GregorianCalendar();

        for (Prescriptor prescriptor : prescriptores  ) {
            HSSFRow aRow = sheet.createRow(rowCount++);

            aRow.createCell(0).setCellValue(prescriptor.getNombrePrescriptor());
            aRow.getCell(0).setCellStyle(rowStyle);
            aRow.createCell(1).setCellValue(prescriptor.getIdMedico());
            aRow.getCell(1).setCellStyle(rowStyle);
            aRow.createCell(2).setCellValue(prescriptor.getDireccion());
            aRow.getCell(2).setCellStyle(rowStyle);
            aRow.createCell(3).setCellValue(prescriptor.getTelefonoUno());
            aRow.getCell(3).setCellStyle(rowStyle);
            aRow.createCell(4).setCellValue(prescriptor.getPersonal());
            aRow.getCell(4).setCellStyle(rowStyle);
            aRow.createCell(5).setCellValue(prescriptor.getColegio());
            aRow.getCell(5).setCellStyle(rowStyle);
            aRow.createCell(6).setCellValue(prescriptor.getProfesion());
            aRow.getCell(6).setCellStyle(rowStyle);
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }

}
