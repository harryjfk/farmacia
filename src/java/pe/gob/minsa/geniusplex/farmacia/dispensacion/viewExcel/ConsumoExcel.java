package pe.gob.minsa.geniusplex.farmacia.dispensacion.viewExcel;

import java.util.ArrayList;
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
import pe.gob.minsa.geniusplex.web.Consumo;

public class ConsumoExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        List<Consumo> consumos = (List<Consumo>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Reporte de Consumo";

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);

        int rowCount = 0;
        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        for (Consumo consumo : consumos) {
            String[] monthSplit = consumo.getMonthName().split("-");
            String monthName = String.format("%s/%s", monthSplit[0], monthSplit[2]);

            HSSFRow header = sheet.createRow(rowCount);
            rowCount += 2;
            CellStyle style = getCellHeadStyle(workbook);
            header.createCell(0).setCellValue(monthName);
            header.getCell(0).setCellStyle(style);

            List<String> cabeceras = new ArrayList<String>();
            cabeceras.add("Código");
            cabeceras.add("Producto");
            cabeceras.add("Total");
            cabeceras.add("Precio");
            cabeceras.add("Importe");

            utilExportExcel.addRowHead(cabeceras, sheet, workbook, rowCount);
            for (int i = 0; i < consumo.getIds().size(); i++) {
                HSSFRow aRow = sheet.createRow(rowCount++);

                aRow.createCell(0).setCellValue(consumo.getCod().get(i));
                aRow.getCell(0).setCellStyle(rowStyle);
                aRow.createCell(1).setCellValue(consumo.getProducto().get(i));
                aRow.getCell(1).setCellStyle(rowStyle);
                aRow.createCell(2).setCellValue(String.valueOf(consumo.getTotal().get(i)));
                aRow.getCell(2).setCellStyle(rowStyle);
                aRow.createCell(3).setCellValue(String.valueOf(consumo.getPrice().get(i)));
                aRow.getCell(3).setCellStyle(rowStyle);
                aRow.createCell(4).setCellValue(String.valueOf(consumo.getImporte().get(i)));
                aRow.getCell(4).setCellStyle(rowStyle);
            }

            rowCount += 1;
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
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
