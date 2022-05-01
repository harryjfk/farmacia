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
import pe.gob.minsa.farmacia.domain.Historico;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class ConsultaHistoricoExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
            HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        List<Historico> balances = (List<Historico>) model.get("Data");
        
        UtilExportExcel utilExportExcel = new UtilExportExcel();
        
        String titulo = titulo = "Consulta de consumo histórico";
        
        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Fecha Adq.");
        cabeceras.add("Medicamento");
        cabeceras.add("Concentración");
        cabeceras.add("Forma Presentación");
        cabeceras.add("Forma Farmaceútica");
        cabeceras.add("Año");
        cabeceras.add("Enero");
        cabeceras.add("Febrero");
        cabeceras.add("Marzo");
        cabeceras.add("Abril");
        cabeceras.add("Mayo");
        cabeceras.add("Junio");
        cabeceras.add("Julio");
        cabeceras.add("Agosto");
        cabeceras.add("Setiembre");
        cabeceras.add("Octubre");
        cabeceras.add("Noviembre");
        cabeceras.add("Diciembre");
        
        utilExportExcel.addRowHead(cabeceras, sheet, workbook);
        
        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);
        
        int rowCount = UtilExportExcel.rowDataCount;
        
        SimpleDateFormat dateFormat = new SimpleDateFormat(utilExportExcel.getFormatoFecha());
        Calendar grego = new GregorianCalendar();
        for (Historico balance : balances) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            if(balance.getFechaAprobacion() != null){
                grego.setTimeInMillis(balance.getFechaAprobacion().getTime());
                aRow.createCell(0).setCellValue(dateFormat.format(grego.getTime()));
            }else{
                aRow.createCell(0).setCellValue("");
            }
            aRow.getCell(0).setCellStyle(rowStyle);
            aRow.createCell(1).setCellValue(balance.getDescripcionProducto());
            aRow.getCell(1).setCellStyle(rowStyle);
            aRow.createCell(2).setCellValue(balance.getConcentracion());
            aRow.getCell(2).setCellStyle(rowStyle);
            aRow.createCell(3).setCellValue(balance.getFormaPresentacion());
            aRow.getCell(3).setCellStyle(rowStyle);
            aRow.createCell(4).setCellValue(balance.getNombreFormaFarmaceutica());
            aRow.getCell(4).setCellStyle(rowStyle);
            aRow.createCell(5).setCellValue(balance.getAnio());
            aRow.getCell(5).setCellStyle(rowStyle);
            aRow.createCell(6).setCellValue(balance.getCantEnero());
            aRow.getCell(6).setCellStyle(rowStyle);
            aRow.createCell(7).setCellValue(balance.getCantFebrero());
            aRow.getCell(7).setCellStyle(rowStyle);
            aRow.createCell(8).setCellValue(balance.getCantMarzo());
            aRow.getCell(8).setCellStyle(rowStyle);
            aRow.createCell(9).setCellValue(balance.getCantAbril());
            aRow.getCell(9).setCellStyle(rowStyle);
            aRow.createCell(10).setCellValue(balance.getCantMayo());
            aRow.getCell(10).setCellStyle(rowStyle);
            aRow.createCell(11).setCellValue(balance.getCantJunio());
            aRow.getCell(11).setCellStyle(rowStyle);
            aRow.createCell(12).setCellValue(balance.getCantJulio());
            aRow.getCell(12).setCellStyle(rowStyle);
            aRow.createCell(13).setCellValue(balance.getCantAgosto());
            aRow.getCell(13).setCellStyle(rowStyle);
            aRow.createCell(14).setCellValue(balance.getCantSetiembre());
            aRow.getCell(14).setCellStyle(rowStyle);
            aRow.createCell(15).setCellValue(balance.getCantOctubre());
            aRow.getCell(15).setCellStyle(rowStyle);
            aRow.createCell(16).setCellValue(balance.getCantNoviembre());
            aRow.getCell(16).setCellStyle(rowStyle);
            aRow.createCell(17).setCellValue(balance.getCantDiciembre());
            aRow.getCell(17).setCellStyle(rowStyle);
        }
        
        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }
    
}