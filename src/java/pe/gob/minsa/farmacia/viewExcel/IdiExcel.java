package pe.gob.minsa.farmacia.viewExcel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import pe.gob.minsa.farmacia.domain.Periodo;
import pe.gob.minsa.farmacia.domain.dto.IdiDetalleDto;
import static pe.gob.minsa.farmacia.util.AbstractITextPdfView.getFormatoFecha;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class IdiExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<IdiDetalleDto> idiDetalles = (List<IdiDetalleDto>) model.get("Data");
        String titulo = "Formato IDI";
                
        if(request.getParameter("idPeriodo") != null){
            Periodo periodo = new Periodo();
            periodo.setIdPeriodo(Integer.parseInt(request.getParameter("idPeriodo")));
            titulo = titulo + " - " + periodo.getNombreMes() + " " + String.valueOf(periodo.getAnio());
        }

        UtilExportExcel utilExportExcel = new UtilExportExcel();
        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha()); 

        CellStyle style = this.getCellStyleBorder(workbook);
        CellStyle styleRight = this.getStyleRightBorderDouble(workbook);
        CellStyle styleWithoutBorder = this.getCellHeadStyle(workbook);
        CellStyle styleOnlyRightBorder = this.getStyleOnlyRightBorderDouble(workbook);        

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        
        for(int i = 1; i<18; i++){
            if(i==3){
                sheet.setColumnWidth(i, 10000);
            }else{
                sheet.setColumnWidth(i, 4000);
            }
        }
        
        
        HSSFRow header1 = sheet.createRow(4);
        header1.createCell(0);
        header1.createCell(1).setCellValue("PRODUCTO FARMACEUTICO");
        header1.createCell(2);
        header1.createCell(3);
        header1.createCell(4);
        header1.createCell(5).setCellValue("PRECIO DE OPERACION");
        header1.createCell(6).setCellValue("SALDO MES ANTERIOR");
        header1.createCell(7).setCellValue("INGRESOS");
        header1.createCell(8).setCellValue("REINGRESOS");
        header1.createCell(9).setCellValue("SALIDAS");
        header1.createCell(10);
        header1.createCell(11);
        header1.createCell(12);
        header1.createCell(13);
        header1.createCell(14);
        header1.createCell(15);
        header1.createCell(16).setCellValue("SALDO FINAL DISPONIBLE");
        header1.createCell(17).setCellValue("FECHA EXPIRACION MAS PRÓXIMA");

        sheet.addMergedRegion(new CellRangeAddress(4,4,1,4));
        sheet.addMergedRegion(new CellRangeAddress(4,4,9,15));
        
        sheet.addMergedRegion(new CellRangeAddress(4,6,5,5));
        sheet.addMergedRegion(new CellRangeAddress(4,6,6,6));
        sheet.addMergedRegion(new CellRangeAddress(4,6,7,7));
        sheet.addMergedRegion(new CellRangeAddress(4,6,8,8));
        
        sheet.addMergedRegion(new CellRangeAddress(4,5,16,16));
        sheet.addMergedRegion(new CellRangeAddress(4,6,17,17));
        
        for(int i = 1; i < 18;i++){
            header1.getCell(i).setCellStyle(style);
            if(i==8){
                header1.getCell(i).setCellStyle(styleRight);
            }
            if(i==15){
                header1.getCell(i).setCellStyle(styleRight);
            }
        }
        
        
        
        HSSFRow header2 = sheet.createRow(5);
        header2.createCell(0);
        header2.createCell(1).setCellValue("CODIGO");
        header2.createCell(2).setCellValue("TIPO PRODUCTO");
        header2.createCell(3).setCellValue("DESCRIPCION");
        header2.createCell(4).setCellValue("UNIDAD DE CONSUMO");
        header2.createCell(5);
        header2.createCell(6);
        header2.createCell(7);
        header2.createCell(8);
        header2.createCell(9).setCellValue("DISTRIBUCION");
        header2.createCell(10).setCellValue("TRANSFERENCIA");
        header2.createCell(11).setCellValue("DEVOLUCIONS ALMACEN LOGISTICA");
        header2.createCell(12);
        header2.createCell(13).setCellValue("VENTAS");
        header2.createCell(14).setCellValue("EXONERACION");
        header2.createCell(15).setCellValue("TOTAL SALIDAS\n SUMA(I..N)");
        header2.createCell(16).setCellValue("SALDO FINAL DISPONIBLE");
        header2.createCell(17).setCellValue("FECHA EXPIRACION MAS PRÓXIMA");

        sheet.addMergedRegion(new CellRangeAddress(5,6,1,1));
        sheet.addMergedRegion(new CellRangeAddress(5,6,2,2));
        sheet.addMergedRegion(new CellRangeAddress(5,6,3,3));
        sheet.addMergedRegion(new CellRangeAddress(5,6,4,4));
        
        sheet.addMergedRegion(new CellRangeAddress(5,6,10,10));
        sheet.addMergedRegion(new CellRangeAddress(5,6,15,15));
        
        for(int i = 1; i < 18;i++){
            header2.getCell(i).setCellStyle(style);
            if(i==8){
                header2.getCell(i).setCellStyle(styleRight);
            }
            
            if(i>11&&i<16){
                header2.getCell(i).setCellStyle(styleRight);
            }
        }
        
        HSSFRow header3 = sheet.createRow(6);
        header3.createCell(0);
        header3.createCell(1);
        header3.createCell(2);
        header3.createCell(3);
        header3.createCell(4);
        header3.createCell(5);
        header3.createCell(6);
        header3.createCell(7);
        header3.createCell(8);
        header3.createCell(9).setCellValue("UNIDADES");
        header3.createCell(10);
        header3.createCell(11).setCellValue("VENCIDO");
        header3.createCell(12).setCellValue("MERMA");
        header3.createCell(13).setCellValue("UNIDADES");
        header3.createCell(14).setCellValue("UNIDADES");
        header3.createCell(15);
        header3.createCell(16).setCellValue("(F+G+H)-O");
        header3.createCell(17);
        
        
        
        for(int i = 1; i < 18;i++){
            header3.getCell(i).setCellStyle(style);
            if(i==8){
                header3.getCell(i).setCellStyle(styleRight);
            }
            if(i>11&&i<16){
                header3.getCell(i).setCellStyle(styleRight);
            }
        }
        
        
        HSSFRow header4 = sheet.createRow(6);
        header4.createCell(0);
        header4.createCell(1).setCellValue("A");
        header4.createCell(2).setCellValue("B");
        header4.createCell(3).setCellValue("C");
        header4.createCell(4).setCellValue("D");
        header4.createCell(5).setCellValue("E");
        header4.createCell(6).setCellValue("F");
        header4.createCell(7).setCellValue("G");
        header4.createCell(8).setCellValue("H");
        header4.createCell(9).setCellValue("I");
        header4.createCell(10).setCellValue("J");
        header4.createCell(11).setCellValue("K");
        header4.createCell(12).setCellValue("L");
        header4.createCell(13).setCellValue("M");
        header4.createCell(14).setCellValue("N");
        header4.createCell(15).setCellValue("O");
        header4.createCell(16).setCellValue("P");
        header4.createCell(17).setCellValue("R");
        
        
        
        for(int i = 1; i < 18;i++){
            header4.getCell(i).setCellStyle(style);
            if(i==8){
                header4.getCell(i).setCellStyle(styleRight);
            }
            if(i>11&&i<16){
                header4.getCell(i).setCellStyle(styleRight);
            }
        }
        
        int rowCount = UtilExportExcel.rowDataCount, contador = 0;
        rowCount+=7;
        
        for (IdiDetalleDto idiDetalleDto : idiDetalles) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            contador++;
            
            aRow.createCell(0).setCellValue(contador);
            aRow.createCell(1).setCellValue(idiDetalleDto.getIdProducto());
            aRow.createCell(2).setCellValue(idiDetalleDto.getTipoProducto());
            aRow.createCell(3).setCellValue(idiDetalleDto.getDescripcionProducto());
            aRow.createCell(4).setCellValue(idiDetalleDto.getFormaFarmaceutica());
            aRow.createCell(5).setCellValue(idiDetalleDto.getPrecioOperacion().doubleValue());
            aRow.createCell(6).setCellValue(idiDetalleDto.getSaldoAnterior().doubleValue());
            aRow.createCell(7).setCellValue(idiDetalleDto.getIngresos().doubleValue());
            aRow.createCell(8).setCellValue(idiDetalleDto.getReIngresos().doubleValue());
            aRow.createCell(9).setCellValue(idiDetalleDto.getDistribucion().doubleValue());
            aRow.createCell(10).setCellValue(idiDetalleDto.getTransferencia().doubleValue());
            aRow.createCell(11).setCellValue(idiDetalleDto.getVencido().doubleValue());
            aRow.createCell(12).setCellValue(idiDetalleDto.getMerma().doubleValue());
            aRow.createCell(13).setCellValue(idiDetalleDto.getVenta().doubleValue());
            aRow.createCell(14).setCellValue(idiDetalleDto.getExoneracion().doubleValue());
            aRow.createCell(15).setCellValue(idiDetalleDto.getTotalSalidas());
            aRow.createCell(16).setCellValue(idiDetalleDto.getSaldoFinal().doubleValue());
            Calendar grego = new GregorianCalendar();
            grego.setTime(idiDetalleDto.getVencimiento());            
            aRow.createCell(17).setCellValue((dateFormat.format(grego.getTime())));

            for (int i = 1; i < 18; ++i) {
                aRow.getCell(i).setCellStyle(style);
                
                if(i==8){
                    aRow.getCell(i).setCellStyle(styleRight);
                }
                
                if(i==15){
                    aRow.getCell(i).setCellStyle(styleRight);
                }
            }
        }

        
        sheet.getRow(5).setHeight((short)700);
        
        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }
    
    private CellStyle getStyleOnlyRightBorderDouble(HSSFWorkbook workbook) {

        CellStyle style = this.getCellHeadStyle(workbook);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        style.setBorderRight(HSSFCellStyle.BORDER_DOUBLE);
        return style;
    }

    private CellStyle getStyleRightBorderDouble(HSSFWorkbook workbook) {

        CellStyle style = this.getCellHeadStyle(workbook);
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setRightBorderColor(HSSFColor.BLACK.index);

        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_DOUBLE);
        return style;
    }

    private CellStyle getCellStyleBorder(HSSFWorkbook workbook) {
        CellStyle style = this.getCellHeadStyle(workbook);

        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setRightBorderColor(HSSFColor.BLACK.index);

        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);

        return style;
    }

    private CellStyle getCellHeadStyle(HSSFWorkbook workbook) {

        HSSFPalette palette = workbook.getCustomPalette();
        palette.setColorAtIndex(HSSFColor.LIME.index, (byte) 235, (byte) 242, (byte) 246);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 8);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setWrapText(true);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.BLACK.index);
        style.setFont(font);

        return style;
    }
}
