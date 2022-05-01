package pe.gob.minsa.geniusplex.farmacia.dispensacion.viewExcel;

import pe.gob.minsa.farmacia.viewExcel.*;
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
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Cliente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Turno;

public class TurnoExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        List<Turno> turnos = (List<Turno>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Reporte de Turnos";

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Código");
        cabeceras.add("Descripción");
        cabeceras.add("Hora Inicio");
        cabeceras.add("Hora Termino");
        

        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        int rowCount = UtilExportExcel.rowDataCount;

        SimpleDateFormat format = new SimpleDateFormat("H:mm");
        Calendar grego = new GregorianCalendar();

        String inicio = "";
        String termino ="";
        for (Turno turno : turnos  ) {
            HSSFRow aRow = sheet.createRow(rowCount++);

            aRow.createCell(0).setCellValue(turno.getCodigo());
            aRow.getCell(0).setCellStyle(rowStyle);
            aRow.createCell(1).setCellValue(turno.getDescripcion());
            aRow.getCell(1).setCellStyle(rowStyle);
            inicio = format.format(turno.getHoraInicio());
            aRow.createCell(2).setCellValue(inicio);
            aRow.getCell(2).setCellStyle(rowStyle);
            termino = format.format(turno.getHoraFinal());
            aRow.createCell(3).setCellValue(termino);
            aRow.getCell(3).setCellStyle(rowStyle);
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }

}
