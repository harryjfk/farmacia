package pe.gob.minsa.geniusplex.farmacia.dispensacion.viewExcel;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import pe.gob.minsa.farmacia.viewExcel.*;
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
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.DemandaInsatisfecha;

public class DemandaInsatisfechaExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        List<DemandaInsatisfecha> demandas = (List<DemandaInsatisfecha>) model.get("Data");
        Cliente cliente = (Cliente) model.get("Cliente");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Reporte de Demandas Insatisfechas";

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        ArrayList<String> clienteCabeceras = new ArrayList<String>();
        clienteCabeceras.add("Codigo");
        clienteCabeceras.add("Nombre");
        utilExportExcel.addRowHead(clienteCabeceras, sheet, workbook);

        HSSFRow clienteRow = sheet.createRow(1);
        clienteRow.createCell(0).setCellValue(cliente.getCodigo());
        clienteRow.getCell(0).setCellStyle(rowStyle);
        clienteRow.createCell(1).setCellValue(cliente.getNombreCliente());
        clienteRow.getCell(1).setCellStyle(rowStyle);

        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Fecha");
        cabeceras.add("Código");
        cabeceras.add("Descripción");
        cabeceras.add("Tipo");
        cabeceras.add("F.F");
        cabeceras.add("Lote");
        cabeceras.add("Precio");
        cabeceras.add("F.V.");
        cabeceras.add("Saldo");

        this.addRowHead(cabeceras, sheet, workbook, 3);
        int rowCount = 4;

        SimpleDateFormat dateFormat = new SimpleDateFormat(utilExportExcel.getFormatoFecha());
        Calendar grego = new GregorianCalendar();

        for (DemandaInsatisfecha demanda : demandas) {
            HSSFRow aRow = sheet.createRow(rowCount++);

            aRow.createCell(0).setCellValue(dateFormat.format(demanda.getFecha()));
            aRow.getCell(0).setCellStyle(rowStyle);
            aRow.createCell(1).setCellValue(demanda.getId().toString());
            aRow.getCell(1).setCellStyle(rowStyle);
            aRow.createCell(2).setCellValue(demanda.getProducto().getProducto().getDescripcion());
            aRow.getCell(2).setCellStyle(rowStyle);
            aRow.createCell(3).setCellValue(demanda.getProducto().getProducto().getIdTipoProducto().getNombreTipoProducto());
            aRow.getCell(3).setCellStyle(rowStyle);
            aRow.createCell(4).setCellValue(demanda.getProducto().getProducto().getIdFormaFarmaceutica().getNombreFormaFarmaceutica());
            aRow.getCell(4).setCellStyle(rowStyle);
            aRow.createCell(5).setCellValue(demanda.getProducto().getLote().getDescripcion());
            aRow.getCell(5).setCellStyle(rowStyle);

            DecimalFormat df = new DecimalFormat("0.00########");
            BigDecimal precio = demanda.getProducto().getPrecio().stripTrailingZeros();
            aRow.createCell(6).setCellValue(df.format(precio));
            
            aRow.getCell(6).setCellStyle(rowStyle);
            aRow.createCell(7).setCellValue(String.valueOf(dateFormat.format(demanda.getProducto().getFechaVencimiento())));
            aRow.getCell(7).setCellStyle(rowStyle);
            
            BigDecimal saldo = demanda.getSaldo().stripTrailingZeros();

            aRow.createCell(8).setCellValue(df.format(saldo));
            aRow.getCell(8).setCellStyle(rowStyle);
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }

    private void addRowHead(List<String> cabeceras, HSSFSheet sheet, HSSFWorkbook workbook, int rowHead) {
        HSSFRow header = sheet.createRow(rowHead);

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
