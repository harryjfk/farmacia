package pe.gob.minsa.farmacia.viewExcel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import pe.gob.minsa.farmacia.domain.Solicitud;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class SolicitudExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
            HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        List<Solicitud> solicitudes = (List<Solicitud>) model.get("Data");
        
        UtilExportExcel utilExportExcel = new UtilExportExcel();
        
        String titulo = "Reporte de Solicitudes";
        
        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Código");
        cabeceras.add("Médico");
        cabeceras.add("Establecimiento");
        cabeceras.add("Insitución");
        cabeceras.add("Fecha");
        cabeceras.add("Estado");
        
        utilExportExcel.addRowHead(cabeceras, sheet, workbook);
        
        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);
        
        int rowCount = UtilExportExcel.rowDataCount;
        
        SimpleDateFormat dateFormat = new SimpleDateFormat(utilExportExcel.getFormatoFecha());
        Calendar grego = new GregorianCalendar();
        for (Solicitud solicitud : solicitudes) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(solicitud.getIdSolicitud());
            aRow.getCell(0).setCellStyle(rowStyle);
            aRow.createCell(1).setCellValue(solicitud.getMedico());
            aRow.getCell(1).setCellStyle(rowStyle);
            aRow.createCell(2).setCellValue(solicitud.getEstablecimiento());
            aRow.getCell(2).setCellStyle(rowStyle);
            aRow.createCell(3).setCellValue(solicitud.getInstitucion());
            aRow.getCell(3).setCellStyle(rowStyle);
            if(solicitud.getFecha() != null){
                grego.setTimeInMillis(solicitud.getFecha().getTime());
                aRow.createCell(4).setCellValue(dateFormat.format(grego.getTime()));
            }else{
                aRow.createCell(4).setCellValue("");
            }
            aRow.getCell(4).setCellStyle(rowStyle);
            aRow.createCell(5).setCellValue(solicitud.getActivoTexto());
            aRow.getCell(5).setCellStyle(rowStyle);
        }
        
        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }
    
}