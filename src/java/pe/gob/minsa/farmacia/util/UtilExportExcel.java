package pe.gob.minsa.farmacia.util;

import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;

public class UtilExportExcel {

    public static final int rowDataCount = 1;

    public UtilExportExcel() {
    }

    public HSSFSheet getSheet(String titulo, HSSFWorkbook workbook) {
        HSSFSheet sheet = workbook.createSheet(titulo);
        Header header = sheet.getHeader();
        header.setLeft("");
        header.setCenter(HSSFHeader.font("Calibri", "Bold")
                + HSSFHeader.fontSize((short) 11) + titulo.toUpperCase());

        Footer footer = sheet.getFooter();
        footer.setRight(HSSFHeader.font("Calibri", "Normal") + 
                HSSFHeader.fontSize((short) 11) +
                "Página " + HeaderFooter.page() + " de "
                + HeaderFooter.numPages());
        return sheet;
    }

    public void addRowHead(List<String> cabeceras, HSSFSheet sheet, HSSFWorkbook workbook, int rowHead) {
        HSSFRow header = sheet.createRow(rowHead);

        CellStyle style = getCellHeadStyle(workbook);
        int index = 0;

        for (String cabecera : cabeceras) {
            header.createCell(index).setCellValue(cabecera);
            header.getCell(index).setCellStyle(style);
            index++;
        }
    }
    
    public void addRowHead(List<String> cabeceras, HSSFSheet sheet, HSSFWorkbook workbook) {
        addRowHead(cabeceras, sheet, workbook, 0);
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

    public CellStyle getCellRowStyle(HSSFWorkbook workbook) {

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 11);
        style.setFillForegroundColor(HSSFColor.WHITE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderLeft((short) 1);
        style.setBorderRight((short) 1);
        style.setBorderTop((short) 1);
        style.setBorderBottom((short) 1);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font.setColor(HSSFColor.BLACK.index);
        style.setFont(font);

        return style;
    }

    public String getFormatoFecha(){
        return "dd/MM/yyyy";
    }
}
