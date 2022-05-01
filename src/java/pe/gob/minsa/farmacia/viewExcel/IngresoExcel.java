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
import pe.gob.minsa.farmacia.domain.dto.IngresoDto;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class IngresoExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        List<IngresoDto> movimientos = (List<IngresoDto>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Reporte de Ingresos";

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Fecha de Registro");
        cabeceras.add("Número");        
        cabeceras.add("Almacén Origen");
        cabeceras.add("Almacén Destino");
        cabeceras.add("Tipo Documento");
        cabeceras.add("Nro. Documento");
        cabeceras.add("Doc. Origen");
        cabeceras.add("Nro. Doc. Origen");        

        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        int rowCount = UtilExportExcel.rowDataCount;

        SimpleDateFormat dateFormat = new SimpleDateFormat(utilExportExcel.getFormatoFecha());
        Calendar grego = new GregorianCalendar();

        for (IngresoDto movimiento : movimientos) {
            HSSFRow aRow = sheet.createRow(rowCount++);

            grego.setTimeInMillis(movimiento.getFechaRegistro().getTime());
            aRow.createCell(0).setCellValue(dateFormat.format(grego.getTime()));
            aRow.createCell(1).setCellValue(movimiento.getNumeroMovimiento());
            aRow.createCell(2).setCellValue(movimiento.getAlmacenOrigen());
            aRow.createCell(3).setCellValue(movimiento.getAlmacenDestino());
            aRow.createCell(4).setCellValue(movimiento.getTipoDocumento());
            aRow.createCell(5).setCellValue(movimiento.getNumeroDocumento());
            aRow.createCell(6).setCellValue(movimiento.getDocumentoOrigen());
            aRow.createCell(7).setCellValue(movimiento.getNumeroDocumentoOrigen());

            for (int i = 0; i < cabeceras.size(); ++i) {
                aRow.getCell(i).setCellStyle(rowStyle);
            }
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }
}
