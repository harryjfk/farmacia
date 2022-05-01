/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.viewExcel;

import java.text.DecimalFormat;
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
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Cliente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.IntervSanitaria;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.IntervSanitariaProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Prescriptor;

/**
 *
 * @author armando
 */
public class IntervSalidaExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UtilExportExcel utilExportExcel = new UtilExportExcel();
        SimpleDateFormat dateFormat = new SimpleDateFormat(utilExportExcel.getFormatoFecha());
        Calendar grego = new GregorianCalendar();

        IntervSanitaria interv = (IntervSanitaria) model.get("Data");
        List<IntervSanitariaProducto> productos = interv.getIntervSanitariaProductos();
        String nroSalida = interv.getNroSalida();
        String nroHistClinica = interv.getNroHistClinica();
        Cliente cliente = interv.getCliente();
        String nombreCliente = String.format("%s %s %s", cliente.getNombre(), cliente.getApellidoPaterno(), cliente.getApellidoMaterno());
        Prescriptor prescriptor = interv.getPrescriptor();
        String nombrePresc = String.format("%s %s %s", prescriptor.getNombre(), prescriptor.getPaterno(), prescriptor.getMaterno());
        String diagnostico = (interv.getDiagnostico() != null) ? interv.getDiagnostico().getDescripcion(): "";
        String fechaRegistro = dateFormat.format(interv.getFechaRegistro());
        String componente = interv.getComponente().getDescripcion();
        String subComponente = interv.getSubComponente().getDescripcion();
        String proceso = interv.getProceso().getDescripcion();
         String coordinador = "";
        if(interv.getCoordinador() != null) {
            coordinador = String.format("%s %s %s", interv.getDatosCoor().get("nombre"), interv.getDatosCoor().get("apellidoPaterno"), interv.getDatosCoor().get("apellidoMaterno"));
        }
        String referencia = interv.getReferencia();

        String titulo = "Salida por Intervención Sanitaria";
        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        HSSFRow row = sheet.createRow(0);
        addRowData(row, workbook, nroSalida, rowStyle, fechaRegistro, "Nro. Salida: ", "Fecha de Registro: ");

        HSSFRow row1 = sheet.createRow(1);
        addRowData(row1, workbook, nombreCliente, rowStyle, componente, "Cliente: ", "Componente: ");

        HSSFRow row2 = sheet.createRow(2);
        addRowData(row2, workbook, nroHistClinica, rowStyle, subComponente, "Nro. Hist. Clínica: ", "Sub Componente: ");

        HSSFRow row3 = sheet.createRow(3);
        addRowData(row3, workbook, nombrePresc, rowStyle, proceso, "Prescriptor: ", "Proceso: ");

        HSSFRow row4 = sheet.createRow(4);
        addRowData(row4, workbook, diagnostico, rowStyle, "", "Diagnostico: ", "Coordinador: ");

        HSSFRow row5 = sheet.createRow(5);
        addRowData(row5, workbook, "Coordinador", rowStyle, "Referencia: ", coordinador, referencia);

        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Producto");
        cabeceras.add("Código");
        cabeceras.add("Cantidad");
        cabeceras.add("Precio");
        cabeceras.add("Imp. Parcial");
        addRowHead(cabeceras, sheet, workbook, 7);

        int rowCount = 8;
        DecimalFormat df = new DecimalFormat("#.####");
        
        for (IntervSanitariaProducto producto : productos) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            String desc = producto.getProducto().getDescripcion() != null ? producto.getProducto().getDescripcion() : "";
            
            aRow.createCell(0).setCellValue(desc);
            aRow.getCell(0).setCellStyle(rowStyle);
            aRow.createCell(1).setCellValue(producto.getProducto().getIdProducto());
            aRow.getCell(1).setCellStyle(rowStyle);
            aRow.createCell(2).setCellValue(producto.getCantidad());
            aRow.getCell(2).setCellStyle(rowStyle);
            aRow.createCell(3).setCellValue(df.format(producto.getPrecio()));
            aRow.getCell(3).setCellStyle(rowStyle);
            aRow.createCell(4).setCellValue(df.format(producto.getImporteParcial()));
            aRow.getCell(4).setCellStyle(rowStyle);
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

    private void addRowData(HSSFRow row, HSSFWorkbook workbook, String nroSalida, CellStyle rowStyle, String fechaRegistro, String labelRgt, String labelLft) {
        row.createCell(0).setCellValue(labelRgt);
        row.getCell(0).setCellStyle(getCellHeadStyle(workbook));
        row.createCell(1).setCellValue(nroSalida);
        row.getCell(1).setCellStyle(rowStyle);
        row.createCell(3).setCellValue(labelLft);
        row.getCell(3).setCellStyle(getCellHeadStyle(workbook));
        row.createCell(4).setCellValue(fechaRegistro);
        row.getCell(4).setCellStyle(rowStyle);
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
