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
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.MedicamentoSospechoso;
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.OtrosMedicamentos;
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.RAM;
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.ReaccionesAdversas;

public class RAMExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        RAM ram = (RAM) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Reporte RAM";
        Integer count = 0;

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        setPaciente(utilExportExcel, sheet, workbook, ++count, ram);
        count += 2;
        setObservAdicionales(utilExportExcel, sheet, workbook, count, ram);
        count+=2;
        setMedico(utilExportExcel, sheet, workbook, ++count, ram);
        count += 2;
        setMedicamentos(utilExportExcel, sheet, workbook, ++count, ram);
        count += 2;
        setReacciones(utilExportExcel, sheet, workbook, ++count, ram);
        count += 2;
        setOtros(utilExportExcel, sheet, workbook, ++count, ram);
        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }

    private void setPaciente(UtilExportExcel utilExportExcel, HSSFSheet sheet, HSSFWorkbook workbook, Integer rowCount, RAM ram) {
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Paciente");
        cabeceras.add("H.C");
        cabeceras.add("Establecimiento de Salud");
        cabeceras.add("Edad");
        cabeceras.add("Sexo");
        cabeceras.add("Peso");

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);
        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        SimpleDateFormat dateFormat = new SimpleDateFormat(utilExportExcel.getFormatoFecha());
        Calendar grego = new GregorianCalendar();

        HSSFRow aRow = sheet.createRow(rowCount++);

        aRow.createCell(0).setCellValue(ram.getPaciente());
        aRow.getCell(0).setCellStyle(rowStyle);
        aRow.createCell(1).setCellValue(ram.getHistoria());
        aRow.getCell(1).setCellStyle(rowStyle);
        aRow.createCell(2).setCellValue(ram.getEstablecimiento());
        aRow.getCell(2).setCellStyle(rowStyle);
        aRow.createCell(3).setCellValue(ram.getEdad());
        aRow.getCell(3).setCellStyle(rowStyle);
        aRow.createCell(4).setCellValue(ram.getSexo().equals("0") ? "M" : "F");
        aRow.getCell(4).setCellStyle(rowStyle);
        aRow.createCell(5).setCellValue(ram.getPeso());
        aRow.getCell(5).setCellStyle(rowStyle);
    }

    private void setMedico(UtilExportExcel utilExportExcel, HSSFSheet sheet, HSSFWorkbook workbook, Integer rowCount, RAM ram) {
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Nombre");
        cabeceras.add("Categoria");
        cabeceras.add("Dirección");
        cabeceras.add("Telefono");
        cabeceras.add("Fecha");

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);
        HSSFRow header = sheet.createRow(rowCount++);

        for (int i = 0; i < cabeceras.size(); i++) {
            header.createCell(i).setCellValue(cabeceras.get(i));
            header.getCell(i).setCellStyle(rowStyle);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(utilExportExcel.getFormatoFecha());
        Calendar grego = new GregorianCalendar();

        HSSFRow aRow = sheet.createRow(rowCount++);

        aRow.createCell(0).setCellValue(ram.getMedico());
        aRow.getCell(0).setCellStyle(rowStyle);
        aRow.createCell(1).setCellValue(ram.getCategoria());
        aRow.getCell(1).setCellStyle(rowStyle);
        aRow.createCell(2).setCellValue(ram.getDireccion());
        aRow.getCell(2).setCellStyle(rowStyle);
        aRow.createCell(3).setCellValue(ram.getTelefono());
        aRow.getCell(3).setCellStyle(rowStyle);
        aRow.createCell(4).setCellValue(dateFormat.format(ram.getFecha()));
        aRow.getCell(4).setCellStyle(rowStyle);
    }

    private void setMedicamentos(UtilExportExcel utilExportExcel, HSSFSheet sheet, HSSFWorkbook workbook, Integer rowCount, RAM ram) {
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Nombre Comercial o Generico");
        cabeceras.add("Laboratorio");
        cabeceras.add("Lote");
        cabeceras.add("Dosis Diaria");
        cabeceras.add("Via de Administración");
        cabeceras.add("Fecha de Inicio");
        cabeceras.add("Fecha Final");
        cabeceras.add("Motivo");

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);
        HSSFRow header = sheet.createRow(rowCount++);

        for (int i = 0; i < cabeceras.size(); i++) {
            header.createCell(i).setCellValue(cabeceras.get(i));
            header.getCell(i).setCellStyle(rowStyle);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(utilExportExcel.getFormatoFecha());
        Calendar grego = new GregorianCalendar();

        HSSFRow aRow = sheet.createRow(rowCount++);

        for (MedicamentoSospechoso med : ram.getMedicamentos()) {
            aRow.createCell(0).setCellValue(med.getNombre());
            aRow.getCell(0).setCellStyle(rowStyle);
            aRow.createCell(1).setCellValue(med.getLaboratorio());
            aRow.getCell(1).setCellStyle(rowStyle);
            aRow.createCell(2).setCellValue(med.getLote());
            aRow.getCell(2).setCellStyle(rowStyle);
            aRow.createCell(3).setCellValue(med.getDosis());
            aRow.getCell(3).setCellStyle(rowStyle);
            aRow.createCell(4).setCellValue(med.getVia());
            aRow.getCell(4).setCellStyle(rowStyle);
            aRow.createCell(5).setCellValue(dateFormat.format(med.getFechaInicio()));
            aRow.getCell(5).setCellStyle(rowStyle);
            aRow.createCell(6).setCellValue(dateFormat.format(med.getFechaFinal()));
            aRow.getCell(6).setCellStyle(rowStyle);
            aRow.createCell(7).setCellValue(med.getMotivo());
            aRow.getCell(7).setCellStyle(rowStyle);
        }

    }

    private void setReacciones(UtilExportExcel utilExportExcel, HSSFSheet sheet, HSSFWorkbook workbook, Integer rowCount, RAM ram) {
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Reacción Adversa");
        cabeceras.add("Fecha Inicio");
        cabeceras.add("Fecha Final");
        cabeceras.add("Evolución");

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);
        HSSFRow header = sheet.createRow(rowCount++);

        for (int i = 0; i < cabeceras.size(); i++) {
            header.createCell(i).setCellValue(cabeceras.get(i));
            header.getCell(i).setCellStyle(rowStyle);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(utilExportExcel.getFormatoFecha());
        Calendar grego = new GregorianCalendar();

        HSSFRow aRow = sheet.createRow(rowCount++);

        for (ReaccionesAdversas med : ram.getReacciones()) {
            aRow.createCell(0).setCellValue(med.getReaccion());
            aRow.getCell(0).setCellStyle(rowStyle);
            aRow.createCell(1).setCellValue(dateFormat.format(med.getFechaInicio()));
            aRow.getCell(1).setCellStyle(rowStyle);
            aRow.createCell(2).setCellValue(dateFormat.format(med.getFechaFinal()));
            aRow.getCell(2).setCellStyle(rowStyle);
            aRow.createCell(3).setCellValue(med.getEvolucion());
            aRow.getCell(3).setCellStyle(rowStyle);
        }

    }

    private void setOtros(UtilExportExcel utilExportExcel, HSSFSheet sheet, HSSFWorkbook workbook, Integer rowCount, RAM ram) {
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Nombre Comercial o Generico");
        cabeceras.add("Dosis Diaria");
        cabeceras.add("Via de Administración");
        cabeceras.add("Fecha Inicio");
        cabeceras.add("Fecha Final");
        cabeceras.add("Indicación Terapeutica");

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);
        HSSFRow header = sheet.createRow(rowCount++);

        for (int i = 0; i < cabeceras.size(); i++) {
            header.createCell(i).setCellValue(cabeceras.get(i));
            header.getCell(i).setCellStyle(rowStyle);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(utilExportExcel.getFormatoFecha());
        Calendar grego = new GregorianCalendar();

        HSSFRow aRow = sheet.createRow(rowCount++);

        for (OtrosMedicamentos med : ram.getOtros()) {
            aRow.createCell(0).setCellValue(med.getNombre());
            aRow.getCell(0).setCellStyle(rowStyle);
            aRow.createCell(1).setCellValue(med.getDosis());
            aRow.getCell(1).setCellStyle(rowStyle);
            aRow.createCell(2).setCellValue(med.getVia());
            aRow.getCell(2).setCellStyle(rowStyle);
            aRow.createCell(3).setCellValue(dateFormat.format(med.getFechaInicio()));
            aRow.getCell(3).setCellStyle(rowStyle);
            aRow.createCell(4).setCellValue(dateFormat.format(med.getFechaFinal()));
            aRow.getCell(4).setCellStyle(rowStyle);
            aRow.createCell(5).setCellValue(med.getIndicacion());
            aRow.getCell(5).setCellStyle(rowStyle);
        }

    }

    private void setObservAdicionales(UtilExportExcel utilExportExcel, HSSFSheet sheet, HSSFWorkbook workbook, Integer rowCount, RAM ram) {
        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);
         HSSFRow aRow = sheet.createRow(rowCount++);
        aRow.createCell(0).setCellValue("Observaciones Adicionales");
        aRow.getCell(0).setCellStyle(rowStyle);
        
        HSSFRow aRow2 = sheet.createRow(rowCount++);
        aRow2.createCell(0).setCellValue(ram.getObservaciones());
        aRow2.getCell(0).setCellStyle(rowStyle);
    }
}
