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
import pe.gob.minsa.farmacia.domain.dto.DocumentoComp;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class DespachoExcel extends AbstractExcelView {
    
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        List<DocumentoComp> documentos = (List<DocumentoComp>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Reporte de Despachos";

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Num. Interna");
        cabeceras.add("Fecha Documento");
        cabeceras.add("Tipo Documento");
        cabeceras.add("Nro Documento");
        cabeceras.add("Destino");
        cabeceras.add("Num. Dirección");
        cabeceras.add("Fecha Despacho");

        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        int rowCount = UtilExportExcel.rowDataCount;

        SimpleDateFormat dateFormat = new SimpleDateFormat(utilExportExcel.getFormatoFecha());
        Calendar grego = new GregorianCalendar();

        for (DocumentoComp documento : documentos) {
            HSSFRow aRow = sheet.createRow(rowCount++);

            aRow.createCell(0).setCellValue(documento.getNumeracionInterna());
            grego.setTimeInMillis(documento.getFechaDocumento().getTime());
            aRow.createCell(1).setCellValue(dateFormat.format(grego.getTime()));            
            aRow.createCell(2).setCellValue(documento.getNombreTipoDocumento());
            aRow.createCell(3).setCellValue(documento.getNroDocumento());     
            aRow.createCell(4).setCellValue(documento.getDestino());
            aRow.createCell(5).setCellValue(documento.getNumeracionDireccion());
            
            if(documento.getFechaDespacho()!= null){
                grego.setTimeInMillis(documento.getFechaDespacho().getTime());
                aRow.createCell(6).setCellValue(dateFormat.format(grego.getTime()));
            }else{
                aRow.createCell(6).setCellValue("");
            }
            
            for (int i = 0; i < cabeceras.size(); ++i) {
                aRow.getCell(i).setCellStyle(rowStyle);
            }
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }
}
