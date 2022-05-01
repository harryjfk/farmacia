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

public class ClienteExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        List<Cliente> clientes = (List<Cliente>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Reporte de Clientes";

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Dni");
        cabeceras.add("Nombre");
        cabeceras.add("A Paterno");
        cabeceras.add("A Materno");
        cabeceras.add("Código");
        cabeceras.add("No Afilicación");
        cabeceras.add("Dirección");
        cabeceras.add("Telefono");
        cabeceras.add("CodPersonal");
        cabeceras.add("TD");
        cabeceras.add("NoDocumento");

        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        int rowCount = UtilExportExcel.rowDataCount;

        SimpleDateFormat dateFormat = new SimpleDateFormat(utilExportExcel.getFormatoFecha());
        Calendar grego = new GregorianCalendar();

        for (Cliente cliente : clientes) {
            HSSFRow aRow = sheet.createRow(rowCount++);

            aRow.createCell(0).setCellValue((cliente.getDni() == null) ? "" : cliente.getDni());
            aRow.getCell(0).setCellStyle(rowStyle);
            aRow.createCell(1).setCellValue((cliente.getNombre() == null) ? "" : cliente.getNombre());
            aRow.getCell(1).setCellStyle(rowStyle);
            aRow.createCell(2).setCellValue((cliente.getApellidoPaterno() == null) ? "" : cliente.getApellidoPaterno());
            aRow.getCell(2).setCellStyle(rowStyle);
            aRow.createCell(3).setCellValue((cliente.getApellidoMaterno() == null) ? "" : cliente.getApellidoMaterno());
            aRow.getCell(3).setCellStyle(rowStyle);
            aRow.createCell(4).setCellValue((cliente.getCodigo() == null) ? "" : cliente.getCodigo());
            aRow.getCell(4).setCellStyle(rowStyle);
            aRow.createCell(5).setCellValue((cliente.getNoAfiliacion() == null) ? "" : cliente.getNoAfiliacion());
            aRow.getCell(5).setCellStyle(rowStyle);
            aRow.createCell(6).setCellValue((cliente.getDireccion() == null) ? "" : cliente.getDireccion());
            aRow.getCell(6).setCellStyle(rowStyle);
            aRow.createCell(7).setCellValue((cliente.getTelefono() == null) ? "" : cliente.getTelefono());
            aRow.getCell(7).setCellStyle(rowStyle);
            aRow.createCell(8).setCellValue((cliente.getCodPersonal() == null) ? "" : cliente.getCodPersonal());
            aRow.getCell(8).setCellStyle(rowStyle);
            aRow.createCell(9).setCellValue((cliente.getTipoDocumento() == null) ? "" : cliente.getTipoDocumento().getNombreTipoDocumento());
            aRow.getCell(9).setCellStyle(rowStyle);
            aRow.createCell(10).setCellValue(cliente.getNoDocumento());
            aRow.getCell(10).setCellStyle(rowStyle);
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }

}
