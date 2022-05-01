package pe.gob.minsa.geniusplex.farmacia.dispensacion.viewExcel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import pe.gob.minsa.farmacia.util.UtilExportExcel;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.IntervProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Intervencion;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Paciente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Prescriptor;

public class IntervencionExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        UtilExportExcel utilExportExcel = new UtilExportExcel();
        SimpleDateFormat dateFormat = new SimpleDateFormat(utilExportExcel.getFormatoFecha());
        Calendar grego = new GregorianCalendar();

        Intervencion intervencion = (Intervencion) model.get("Data");
        List<IntervProducto> productos = intervencion.getIntervProductos();
        Paciente paciente = intervencion.getPaciente();
        Prescriptor medico = intervencion.getMedico();
        String fechaInt = dateFormat.format(intervencion.getFechaIntervencion());
        String atendida = intervencion.getAtendida() == 0 ? "No" : "Si";
        String programada = intervencion.getProgramada() == 0 ? "No" : "Si";

        String titulo = "Reporte de Intervención";
        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        HSSFRow pacienteRow = sheet.createRow(0);
        pacienteRow.createCell(0).setCellValue("Paciente: ");
        pacienteRow.getCell(0).setCellStyle(getCellHeadStyle(workbook));
        pacienteRow.createCell(1).setCellValue(String.format("%s %s %s", paciente.getNombre(), paciente.getPaterno(), paciente.getMaterno()));
        pacienteRow.getCell(1).setCellStyle(rowStyle);

        HSSFRow medicoRow = sheet.createRow(1);
        medicoRow.createCell(0).setCellValue("Médico: ");
        medicoRow.getCell(0).setCellStyle(getCellHeadStyle(workbook));
        medicoRow.createCell(1).setCellValue(String.format("%s %s %s", medico.getNombre(), medico.getPaterno(), medico.getMaterno()));
        medicoRow.getCell(1).setCellStyle(rowStyle);

        HSSFRow especialidadRow = sheet.createRow(2);
        especialidadRow.createCell(0).setCellValue("Especialidad: ");
        especialidadRow.getCell(0).setCellStyle(getCellHeadStyle(workbook));
        especialidadRow.createCell(1).setCellValue(intervencion.getEspecialidad());
        especialidadRow.getCell(1).setCellStyle(rowStyle);

        HSSFRow fechaIntRow = sheet.createRow(3);
        fechaIntRow.createCell(0).setCellValue("Fecha de Intervención: ");
        fechaIntRow.getCell(0).setCellStyle(getCellHeadStyle(workbook));
        fechaIntRow.createCell(1).setCellValue(fechaInt);
        fechaIntRow.getCell(1).setCellStyle(rowStyle);

        HSSFRow atendidaRow = sheet.createRow(4);
        atendidaRow.createCell(0).setCellValue("Atendida: ");
        atendidaRow.getCell(0).setCellStyle(getCellHeadStyle(workbook));
        atendidaRow.createCell(1).setCellValue(atendida);
        atendidaRow.getCell(1).setCellStyle(rowStyle);

        HSSFRow programadaRow = sheet.createRow(5);
        programadaRow.createCell(0).setCellValue("Programada: ");
        programadaRow.getCell(0).setCellStyle(getCellHeadStyle(workbook));
        programadaRow.createCell(1).setCellValue(programada);
        programadaRow.getCell(1).setCellStyle(rowStyle);

        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Código");
        cabeceras.add("Descripción del Medicamento/Material");
        cabeceras.add("Cantidad");
        addRowHead(cabeceras, sheet, workbook, 7);

        int rowCount = 8;

        for (IntervProducto producto : productos) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            String desc = producto.getProducto().getDescripcion() != null ? producto.getProducto().getDescripcion() : "";

            aRow.createCell(0).setCellValue(producto.getProducto().getIdProducto());
            aRow.getCell(0).setCellStyle(rowStyle);
            aRow.createCell(1).setCellValue(desc);
            aRow.getCell(1).setCellStyle(rowStyle);
            aRow.createCell(2).setCellValue(producto.getCantidad());
            aRow.getCell(2).setCellStyle(rowStyle);
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }

    private void addRowHead(List<String> cabeceras, HSSFSheet sheet, HSSFWorkbook workbook, int rowPos) {

        HSSFRow header = sheet.createRow(rowPos);

        CellStyle style = getCellHeadStyle(workbook);
        int index = 0;

        for (String cabecera : cabeceras) {
            header.createCell(index).setCellValue(cabecera);
            header.getCell(index).setCellStyle(style);
            index++;
        }
    }

    private CellStyle getCellHeadStyle(HSSFWorkbook workbook) {

        HSSFPalette palette = workbook.getCustomPalette();
        palette.setColorAtIndex(HSSFColor.LIME.index, (byte) 235, (byte) 242, (byte) 246);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 11);
        style.setFillForegroundColor(HSSFColor.LIME.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderLeft((short) 1);
        style.setBorderRight((short) 1);
        style.setBorderTop((short) 1);
        style.setBorderBottom((short) 1);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.BLACK.index);
        style.setFont(font);

        return style;
    }

}
