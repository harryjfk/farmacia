package pe.gob.minsa.geniusplex.farmacia.dispensacion.viewExcel;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import static pe.gob.minsa.farmacia.util.AbstractITextPdfView.getFormatoFecha;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class GenericExcel extends AbstractExcelView {

    private Integer count = 0;

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        String title = (String) model.get("Title");
        UtilExportExcel utilExportExcel = new UtilExportExcel();
        String titulo = title;
        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        setHeaders(model, sheet, workbook, rowStyle);
        count++;
        setContent(model, sheet, workbook, rowStyle);
        count++;
        setSubContent1(model, sheet, workbook, rowStyle);

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }

    private void setContent(Map<String, Object> model, HSSFSheet sheet, HSSFWorkbook workbook, CellStyle rowStyle) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, SecurityException {
        List<String> contentLabels = (List<String>) model.get("ContentLabels");
        List<String> contentFields = (List<String>) model.get("ContentFields");
        List<Object> contentData = (List<Object>) model.get("ContentData");
        List<String> cabeceras = new ArrayList<String>();

        for (String label : contentLabels) {
            cabeceras.add(label);
        }
        addRowHead(cabeceras, sheet, workbook, count++);

        String value;
        Object obj = null;
        if (contentData != null) {
            for (Object item : contentData) {
                int colCount = 0;
                HSSFRow aRow = sheet.createRow(count++);

                for (String field : contentFields) {

                    try {
                        obj = invokeMethod(field, item);
                    } catch (NoSuchFieldException ex) {
                        Logger.getLogger(GenericExcel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    value = getValue(obj);
                    aRow.createCell(colCount).setCellValue(value);
                    aRow.getCell(colCount++).setCellStyle(rowStyle);
                }
            }
        }
    }

    private void setSubContent1(Map<String, Object> model, HSSFSheet sheet, HSSFWorkbook workbook, CellStyle rowStyle) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, SecurityException {
        List<String> contentLabels = (List<String>) model.get("SubContent1Labels");
        List<String> contentFields = (List<String>) model.get("SubContent1Fields");
        List<Object> contentData = (List<Object>) model.get("SubContent1Data");
        List<String> cabeceras = new ArrayList<String>();
        if (contentData != null) {
            for (String label : contentLabels) {
                cabeceras.add(label);
            }
            addRowHead(cabeceras, sheet, workbook, count++);

            String value;
            Object obj = null;
            if (contentData != null) {
                for (Object item : contentData) {
                    int colCount = 0;
                    HSSFRow aRow = sheet.createRow(count++);

                    for (String field : contentFields) {

                        try {
                            obj = invokeMethod(field, item);

                        } catch (NoSuchFieldException ex) {
                            Logger.getLogger(GenericExcel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        value = getValue(obj);
                        aRow.createCell(colCount).setCellValue(value);
                        aRow.getCell(colCount++).setCellStyle(rowStyle);
                    }
                }
            }
        }
    }

    private Integer setHeaders(Map<String, Object> model, HSSFSheet sheet, HSSFWorkbook workbook, CellStyle rowStyle) {
        HashMap<Integer, Integer> headerColumns = (HashMap<Integer, Integer>) model.get("HeaderColumns");
        if (headerColumns != null) {
            HashMap<String, String>[] headerData = (HashMap<String, String>[]) model.get("HeaderData");

            for (HashMap<String, String> map : headerData) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String label = entry.getKey();
                    String value = entry.getValue();
                    HSSFRow row = sheet.createRow(count++);
                    int cellIndex = 0;
                    row.createCell(cellIndex).setCellValue(label + ": ");
                    row.getCell(cellIndex++).setCellStyle(getCellHeadStyle(workbook));
                    row.createCell(cellIndex).setCellValue(value);
                    row.getCell(cellIndex++).setCellStyle(rowStyle);
                }
            }
        }
        return count;
    }

    private Object invokeMethod(String field, Object item) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String[] tmp = null;
        Object obj = item;
        String method = "get" + toCapital(field);
        if (field.contains(":")) {
            tmp = field.split(":");
            field = tmp[0];
            method = "get" + toCapital(field);
        }
        if (obj != null) {
            obj = obj.getClass().getMethod(method, new Class<?>[]{}).invoke(obj, new Object[]{});
            if (null != tmp) {
                for (int i = 1; i < tmp.length; i++) {//navegar hacia el campo en la otra entidad
                    method = "get" + toCapital(tmp[i]);
                    obj = obj.getClass().getMethod(method, new Class<?>[]{}).invoke(obj, new Object[]{});
                }
            }
        } else {
            obj = "";
        }
        return obj;
    }

    private String getValue(Object temp) {
        if (temp != null) {
            if (temp.getClass().getName().equalsIgnoreCase("java.util.Date")) {
                SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
                return dateFormat.format(temp);
            }
            return temp.toString();
        } else {
            return "";
        }
    }

    private String toCapital(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
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

}
