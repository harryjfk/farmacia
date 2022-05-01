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
import pe.gob.minsa.farmacia.domain.BalanceSemestral;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class ConsultaSolicitudExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
            HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        List<BalanceSemestral> balances = (List<BalanceSemestral>) model.get("Data");
        
        UtilExportExcel utilExportExcel = new UtilExportExcel();
        
        String titulo = "";
        switch(Integer.valueOf(request.getParameter("tipoConsulta")!=null?request.getParameter("tipoConsulta"):"0")){
            case 1: titulo = "Consulta de Medicamentos Adquiridos"; break;
            case 2: titulo = "Consulta de Medicamentos no aprobados"; break;
        }
        
        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Establecimiento");
        cabeceras.add("Institución");
        cabeceras.add("Fecha");
        cabeceras.add("Tipo Medicamento");
        cabeceras.add("Medicamento");
        cabeceras.add("Concentración");
        cabeceras.add("Forma Farmaceútica");
        cabeceras.add("Forma Presentación");
        cabeceras.add("Dosis diaria");
        cabeceras.add("Precio adq.");
        cabeceras.add("Estado");
        
        utilExportExcel.addRowHead(cabeceras, sheet, workbook);
        
        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);
        
        int rowCount = UtilExportExcel.rowDataCount;
        
        SimpleDateFormat dateFormat = new SimpleDateFormat(utilExportExcel.getFormatoFecha());
        Calendar grego = new GregorianCalendar();
        for (BalanceSemestral balance : balances) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(balance.getEstablecimiento());
            aRow.getCell(0).setCellStyle(rowStyle);
            aRow.createCell(1).setCellValue(balance.getInstitucion());
            aRow.getCell(1).setCellStyle(rowStyle);
            if(balance.getFechaAprobacion() != null){
                grego.setTimeInMillis(balance.getFechaAprobacion().getTime());
                aRow.createCell(2).setCellValue(dateFormat.format(grego.getTime()));
            }else{
                aRow.createCell(2).setCellValue("");
            }
            aRow.getCell(2).setCellStyle(rowStyle);
            aRow.createCell(3).setCellValue(balance.getTipoMedicamento());
            aRow.getCell(3).setCellStyle(rowStyle);
            aRow.createCell(4).setCellValue(balance.getDescripcionProducto());
            aRow.getCell(4).setCellStyle(rowStyle);
            aRow.createCell(5).setCellValue(balance.getConcentracion());
            aRow.getCell(5).setCellStyle(rowStyle);
            aRow.createCell(6).setCellValue(balance.getNombreFormaFarmaceutica());
            aRow.getCell(6).setCellStyle(rowStyle);
            aRow.createCell(7).setCellValue(balance.getFormaPresentacion());
            aRow.getCell(7).setCellStyle(rowStyle);
            aRow.createCell(8).setCellValue(balance.getCantidadSol());
            aRow.getCell(8).setCellStyle(rowStyle);
            aRow.createCell(9).setCellValue(balance.getPrecio());
            aRow.getCell(9).setCellStyle(rowStyle);
            aRow.createCell(10).setCellValue(balance.getAprobado());
            aRow.getCell(10).setCellStyle(rowStyle);
        }
        
        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }
    
}