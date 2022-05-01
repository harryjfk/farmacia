/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.farmacia.viewExcel;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import pe.gob.minsa.farmacia.domain.Ime;
import pe.gob.minsa.farmacia.domain.Ime_III;
import pe.gob.minsa.farmacia.util.UtilExportExcel;
import static pe.gob.minsa.farmacia.util.AbstractITextPdfView.getFormatoFecha;


/**
 *
 * @author admin
 */
public class ImeExcel extends AbstractExcelView {
    
    /*
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UtilExportExcel utilExportExcel = new UtilExportExcel();
        
        Ime ime = (Ime)model.get("IME");
        
        List<Ime_III> detalle = (List<Ime_III>)model.get("detalle");
        int indiceDetalle=0;
        
        String titulo = "Formato IME";
        String periodoString = "";

        periodoString += "desde "+ime.getFechaDesdeString();
        periodoString += " hasta " +ime.getFechaHastaString();
               
        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);

        CellStyle style = this.getCellStyleBorder(workbook);
        CellStyle styleWithoutBorder = this.getCellHeadStyle(workbook);
        CellStyle styleThick = this.getCellStyleBorderThick(workbook);
        CellStyle styleTop = this.getStyleTopThick(workbook);
        CellStyle styleBottom = this.getStyleBottomThick(workbook);
        CellStyle styleRight = this.getStyleRightBorderThick(workbook);
        CellStyle styleRightTop = this.getStyleRightTopBorderThick(workbook);
        CellStyle styleRightBottom = this.getStyleRightBottomBorderThick(workbook);
        CellStyle styleLeft = this.getStyleLeftBorderThick(workbook);
        CellStyle styleLeftTop = this.getStyleLeftTopBorderThick(workbook);
        CellStyle styleLeftBottom = this.getStyleLeftBottomBorderThick(workbook);
        
       Ime_III imeiii;
        
        for (int i = 0; i <= 9; ++i) {
            sheet.setColumnWidth(i, 5000);
        }
        
        for(int x = 5; x<=39; x++){
            HSSFRow header = sheet.createRow(x);
        
            switch(x){
                case 5:
                    header.createCell(0).setCellValue("Periodo: "+periodoString);
                    header.createCell(1);
                    header.createCell(2);
                    header.createCell(3);

                    header.createCell(4);

                    header.createCell(5).setCellValue("GASTOS ADMINISTRATIVOS DE LA UNIDAD EJECUTORA CON EL 20%");
                    header.createCell(6);
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        if(i!=4){
                            header.getCell(i).setCellStyle(styleThick);
                        }
                    }

                    sheet.addMergedRegion(new CellRangeAddress(5,5,0,3));
                    sheet.addMergedRegion(new CellRangeAddress(5,5,5,9));

                break;
                
                case 6:
                    header.createCell(0).setCellValue("SALIDAS POR VENTA O EXONERACION (Boletas de venta)");
                    header.createCell(1);
                    header.createCell(2);
                    header.createCell(3);

                    header.createCell(4);

                    header.createCell(5).setCellValue("FECHA");
                    header.createCell(6).setCellValue("PARTIDA");
                    header.createCell(7).setCellValue("DETALLE DEL GASTO");
                    header.createCell(8).setCellValue("DOC. FUENTE");
                    header.createCell(9).setCellValue("IMPORTE");

                    for(int i = 0; i <= 9; i++){
                        if(i!=4)
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    for(int i = 0; i<=3; i++){
                        header.getCell(i).setCellStyle(styleThick);
                    }
                    
                    sheet.addMergedRegion(new CellRangeAddress(6,6,0,3));
                    
                break;
                
                case 7:
                    if(indiceDetalle<detalle.size()){
                        imeiii = detalle.get(indiceDetalle);
                        indiceDetalle++;
                    }else{
                        imeiii=new Ime_III();
                    }
                        
                    header.createCell(0).setCellValue("Boletas emitidas");
                    header.createCell(1);
                    header.createCell(2);
                    header.createCell(3).setCellValue("Importe total de Ventas");

                    header.createCell(4);

                    //DETALLE IMEIII
                    header.createCell(5).setCellValue(imeiii.getFechaString());
                    header.createCell(6).setCellValue(imeiii.getPartida());
                    header.createCell(7).setCellValue(imeiii.getDetalleGasto());
                    header.createCell(8).setCellValue(imeiii.getDocFuente());
                    header.createCell(9).setCellValue(getDouble(imeiii.getImporte()));

                    for(int i = 0; i <= 9; i++){
                        if(i!=4)
                        header.getCell(i).setCellStyle(style);
                        
                    }
                    
                    header.getCell(3).setCellStyle(styleThick);
                    header.getCell(0).setCellStyle(styleLeftTop);
                    

                    sheet.addMergedRegion(new CellRangeAddress(7,7, 0, 2));
                    sheet.addMergedRegion(new CellRangeAddress(8,11, 3, 3));
                break;
                
                case 8:
                    
                    if(indiceDetalle<detalle.size()){
                        imeiii = detalle.get(indiceDetalle);
                        indiceDetalle++;
                    }else{
                        imeiii=new Ime_III();
                    }
                    
                    header.createCell(0).setCellValue("Serie:");
                    header.createCell(1).setCellValue(ime.getNumeroSerie());
                    header.createCell(2);
                    header.createCell(3).setCellValue(getDouble(ime.getAVenta()));

                    header.createCell(4);

                    //DETALLE IMEIII
                    header.createCell(5).setCellValue(imeiii.getFechaString());
                    header.createCell(6).setCellValue(imeiii.getPartida());
                    header.createCell(7).setCellValue(imeiii.getDetalleGasto());
                    header.createCell(8).setCellValue(imeiii.getDocFuente());
                    header.createCell(9).setCellValue(getDouble(imeiii.getImporte()));

                    header.getCell(0).setCellStyle(styleLeft);
                    
                    for(int i = 1; i <= 9; i++){
                        if(i!=4)
                        header.getCell(i).setCellStyle(style);
                        if(i==3)
                        header.getCell(i).setCellStyle(styleThick);
                    }
                    
                    header.getCell(3).setCellStyle(styleThick);
                    
                    sheet.addMergedRegion(new CellRangeAddress(8,8,1,2));
                break;
                
                case 9:
                    
                    if(indiceDetalle<detalle.size()){
                        imeiii = detalle.get(indiceDetalle);
                        indiceDetalle++;
                    }else{
                        imeiii=new Ime_III();
                    }
                    
                    header.createCell(0).setCellValue("Del:");
                    header.createCell(1).setCellValue(ime.getNumBoletaDe());
                    header.createCell(2);
                    header.createCell(3);

                    header.createCell(4);

                    //DETALLE IMEIII
                    header.createCell(5).setCellValue(imeiii.getFechaString());
                    header.createCell(6).setCellValue(imeiii.getPartida());
                    header.createCell(7).setCellValue(imeiii.getDetalleGasto());
                    header.createCell(8).setCellValue(imeiii.getDocFuente());
                    header.createCell(9).setCellValue(getDouble(imeiii.getImporte()));

                    header.getCell(0).setCellStyle(styleLeft);
                    
                    for(int i = 1; i <= 9; i++){
                        if(i!=4)
                        header.getCell(i).setCellStyle(style);
                        if(i==3)
                        header.getCell(i).setCellStyle(styleThick);
                    }
                    
                    sheet.addMergedRegion(new CellRangeAddress(9,9,1,2));
                break;
                
                case 10:
                    
                    if(indiceDetalle<detalle.size()){
                        imeiii = detalle.get(indiceDetalle);
                        indiceDetalle++;
                    }
                    else{
                        imeiii=new Ime_III();
                    }
                    header.createCell(0).setCellValue("Al:");
                    header.createCell(1).setCellValue(ime.getNumBoletaA());
                    header.createCell(2);
                    header.createCell(3);

                    header.createCell(4);

                    //DETALLE IMEIII
                    header.createCell(5).setCellValue(imeiii.getFechaString());
                    header.createCell(6).setCellValue(imeiii.getPartida());
                    header.createCell(7).setCellValue(imeiii.getDetalleGasto());
                    header.createCell(8).setCellValue(imeiii.getDocFuente());
                    header.createCell(9).setCellValue(getDouble(imeiii.getImporte()));

                    header.getCell(0).setCellStyle(styleLeft);
                    
                    for(int i = 1; i <= 9; i++){
                        if(i!=4)
                        header.getCell(i).setCellStyle(style);
                        if(i==3)
                        header.getCell(i).setCellStyle(styleThick);
                    }

                    sheet.addMergedRegion(new CellRangeAddress(10,10,1,2));
                    
                break;
                
                case 11:
                    
                    if(indiceDetalle<detalle.size()){
                        imeiii = detalle.get(indiceDetalle);
                        indiceDetalle++;
                    }else{
                        imeiii=new Ime_III();
                    }
                    
                    header.createCell(0).setCellValue("Anuladas");
                    header.createCell(1).setCellValue("");
                    header.createCell(2);
                    header.createCell(3);

                    header.createCell(4);

                    //DETALLE IMEIII
                    header.createCell(5).setCellValue(imeiii.getFechaString());
                    header.createCell(6).setCellValue(imeiii.getPartida());
                    header.createCell(7).setCellValue(imeiii.getDetalleGasto());
                    header.createCell(8).setCellValue(imeiii.getDocFuente());
                    header.createCell(9).setCellValue(getDouble(imeiii.getImporte()));

                    for(int i = 0; i <= 9; i++){
                        if(i==0)
                        header.getCell(i).setCellStyle(styleLeftBottom);
                        if(i<3&&i>0)
                        header.getCell(i).setCellStyle(styleBottom);
                        if(i>4)
                        header.getCell(i).setCellStyle(style);
                        if(i==3)
                        header.getCell(i).setCellStyle(styleThick);
                    }

                    sheet.addMergedRegion(new CellRangeAddress(11,11,1,2));
                break;
                
                case 12:
                    
                    if(indiceDetalle<detalle.size()){
                        imeiii = detalle.get(indiceDetalle);
                        indiceDetalle++;
                    }else{
                        imeiii=new Ime_III();
                    }
                    
                    header.createCell(0);
                    header.createCell(1);
                    header.createCell(2).setCellValue("EMITIDAS");
                    header.createCell(3).setCellValue("DISPENSADAS");

                    header.createCell(4);

                    //DETALLE IMEIII
                    header.createCell(5).setCellValue(imeiii.getFechaString());
                    header.createCell(6).setCellValue(imeiii.getPartida());
                    header.createCell(7).setCellValue(imeiii.getDetalleGasto());
                    header.createCell(8).setCellValue(imeiii.getDocFuente());
                    header.createCell(9).setCellValue(getDouble(imeiii.getImporte()));

                    for(int i = 0; i <= 9; i++){
                        
                        if(i<4)
                            header.getCell(i).setCellStyle(styleWithoutBorder);
                        if(i>4)
                            header.getCell(i).setCellStyle(style);
    
                    }
                break;
                
                case 13:
                    
                    if(indiceDetalle<detalle.size()){
                        imeiii = detalle.get(indiceDetalle);
                        indiceDetalle++;
                    }else{
                        imeiii=new Ime_III();
                    }
                    
                    header.createCell(0).setCellValue("No. De Recetas por Demanda");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getRecetasDemandaEmitidas()));
                    header.createCell(3).setCellValue(getDouble(ime.getRecetasDemandaDispensadas()));

                    header.createCell(4);

                    //DETALLE IMEIII
                    header.createCell(5).setCellValue(imeiii.getFechaString());
                    header.createCell(6).setCellValue(imeiii.getPartida());
                    header.createCell(7).setCellValue(imeiii.getDetalleGasto());
                    header.createCell(8).setCellValue(imeiii.getDocFuente());
                    header.createCell(9).setCellValue(getDouble(imeiii.getImporte()));

                    for(int i = 0; i <= 9; i++){
                        if(i!=4)
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(13,13,0,1));
                break;
                
                case 14:
                    
                    if(indiceDetalle<detalle.size()){
                        imeiii = detalle.get(indiceDetalle);
                        indiceDetalle++;
                    }else{
                        imeiii=new Ime_III();
                    }
                    
                    header.createCell(0).setCellValue("No. De Recetas por SIS");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getRecetasSisEmitidas()));
                    header.createCell(3).setCellValue(getDouble(ime.getRecetasSisDispensadas()));

                    header.createCell(4);

                    //DETALLE IMEIII
                    header.createCell(5).setCellValue(imeiii.getFechaString());
                    header.createCell(6).setCellValue(imeiii.getPartida());
                    header.createCell(7).setCellValue(imeiii.getDetalleGasto());
                    header.createCell(8).setCellValue(imeiii.getDocFuente());
                    header.createCell(9).setCellValue(getDouble(imeiii.getImporte()));

                    for(int i = 0; i <= 9; i++){
                        if(i!=4)
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(14,14,0,1));
                    
                break;
                
                case 15:
                    
                    if(indiceDetalle<detalle.size()){
                        imeiii = detalle.get(indiceDetalle);
                        indiceDetalle++;
                    }else{
                        imeiii=new Ime_III();
                    }
                    
                    header.createCell(0).setCellValue("No. Recetas Interven. Sanit.");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getRecetasIntervSanitEmitidas()));
                    header.createCell(3).setCellValue(getDouble(ime.getRecetasIntervSanitDispensadas()));

                    header.createCell(4);

                    //DETALLE IMEIII
                    header.createCell(5).setCellValue(imeiii.getFechaString());
                    header.createCell(6).setCellValue(imeiii.getPartida());
                    header.createCell(7).setCellValue(imeiii.getDetalleGasto());
                    header.createCell(8).setCellValue(imeiii.getDocFuente());
                    header.createCell(9).setCellValue(getDouble(imeiii.getImporte()));

                    for(int i = 0; i <= 9; i++){
                        if(i!=4)
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(15,15,0,1));
                    
                break;
                
                case 16:
                    
                    if(indiceDetalle<detalle.size()){
                        imeiii = detalle.get(indiceDetalle);
                        indiceDetalle++;
                    }else{
                        imeiii=new Ime_III();
                    }
                    
                    header.createCell(0).setCellValue("No. Recetas por Soat");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getRecetasSoatEmitidas()));
                    header.createCell(3).setCellValue(getDouble(ime.getRecetasSoatDispensadas()));

                    header.createCell(4);

                    //DETALLE IMEIII
                    header.createCell(5).setCellValue(imeiii.getFechaString());
                    header.createCell(6).setCellValue(imeiii.getPartida());
                    header.createCell(7).setCellValue(imeiii.getDetalleGasto());
                    header.createCell(8).setCellValue(imeiii.getDocFuente());
                    header.createCell(9).setCellValue(getDouble(imeiii.getImporte()));

                    for(int i = 0; i <= 9; i++){
                        if(i!=4)
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(16,16,0,1));
                    
                break;
                
                case 17:
                    
                    if(indiceDetalle<detalle.size()){
                        imeiii = detalle.get(indiceDetalle);
                        indiceDetalle++;
                    }else{
                        imeiii=new Ime_III();
                    }
                    
                    header.createCell(0).setCellValue("No. Recetas por Exoneracion");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getRecetasExoneracionEmitidas()));
                    header.createCell(3).setCellValue(getDouble(ime.getRecetasExoneracionDispensadas()));

                    header.createCell(4);

                    //DETALLE IMEIII
                    header.createCell(5).setCellValue(imeiii.getFechaString());
                    header.createCell(6).setCellValue(imeiii.getPartida());
                    header.createCell(7).setCellValue(imeiii.getDetalleGasto());
                    header.createCell(8).setCellValue(imeiii.getDocFuente());
                    header.createCell(9).setCellValue(getDouble(imeiii.getImporte()));

                    for(int i = 0; i <= 9; i++){
                        if(i!=4)
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(17,17,0,1));
                    
                break;
                
                case 18:
                    header.createCell(0).setCellValue("No. Recetas Externas");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getRecetasExternasEmitidas()));
                    header.createCell(3).setCellValue(getDouble(ime.getRecetasExternasDispensadas()));

                    header.createCell(4);

                    header.createCell(5);
                    header.createCell(6);
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9).setCellFormula("SUM(J8:J18)");
                    
                    for(int i = 0; i <= 9; i++){
                        if(i!=4)
                        header.getCell(i).setCellStyle(style);
                    }

                    sheet.addMergedRegion(new CellRangeAddress(18,18,0,1));
                    
                break;
                
                case 19:
                    header.createCell(0).setCellValue("IMPORTE");
                    header.createCell(1);
                    header.createCell(2).setCellValue("IMPORTE DE ATENCIONES\n"
                            + "FARMACIA (FORMATO ICI)");
                    header.createCell(3).setCellValue("CAPTACION O REEMBOLSOS DEL MES");
                    header.createCell(4);
                    header.createCell(5);
                    header.createCell(6).setCellValue("CUENTAS POR COBRAR DEL MES (C=A-B)");
                    header.createCell(7).setCellValue("CUENTAS POR COBRAR ACUMULADAS DEL MES ANTERIOR (D)");
                    header.createCell(8).setCellValue("CUENTAS POR COBRAR ACUMULADAS EN EL MES (E=C+D)");
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    //IMPORTE
                    sheet.addMergedRegion(new CellRangeAddress(19,21,0,1));
                    //IMPORTE DE ATENCIONES, FARMACIA FORMATO ICI
                    sheet.addMergedRegion(new CellRangeAddress(19,21,2,2));
                    //CAPTACION O REEMBOLSOS DEL MES
                    sheet.addMergedRegion(new CellRangeAddress(19,19,3,5));
                    //CUENTAS POR COBRAR DEL MES (C=A-B)
                    sheet.addMergedRegion(new CellRangeAddress(19,21,6,6));
                    //CUENTAS POR COBRAR ACUMULADAS DEL MES ANTERIOR (D)
                    sheet.addMergedRegion(new CellRangeAddress(19,21,7,7));
                    //CUENTAS POR COBRAR ACUMULADAS EN EL MES (E=C+D)
                    sheet.addMergedRegion(new CellRangeAddress(19,21,8,9));
                    
                break;
                
                case 20:
                    header.createCell(0);
                    header.createCell(1);
                    header.createCell(2);
                    header.createCell(3).setCellValue("TOTAL (B)");
                    header.createCell(4).setCellValue("DISTRIBUCION DE LO CAPTADO");
                    header.createCell(5);
                    header.createCell(6);
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    //TOTAL (B)
                    sheet.addMergedRegion(new CellRangeAddress(20,21,3,3));
                    //DISTRIBUCION DE LO CAPTADO       
                    sheet.addMergedRegion(new CellRangeAddress(20,20,4,5));
                    
                break;
                
                case 21:
                    header.createCell(0);
                    header.createCell(1);
                    header.createCell(2);
                    header.createCell(3);
                    header.createCell(4).setCellValue("80%");
                    header.createCell(5).setCellValue("20%");
                    header.createCell(6);
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }
                    
                break;
                
                case 22:
                    header.createCell(0).setCellValue("VENTAS (+)");
                    header.createCell(1);
                    header.createCell(2).setCellFormula("D9");
                    header.createCell(3).setCellFormula("C23");
                    header.createCell(4).setCellFormula("D23*0.8");
                    header.createCell(5).setCellFormula("D23*0.2");
                    header.createCell(6).setCellFormula("C23-D23");
                    header.createCell(7);
                    header.createCell(8).setCellFormula("SUM(G23:H23)");
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(22,22,0,1));
                    sheet.addMergedRegion(new CellRangeAddress(22,22,8,9));
                    
                break;
                
                case 23:
                    header.createCell(0).setCellValue("CREDITO HOSPITALIZADOS (+)");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getACredito()));
                    header.createCell(3).setCellValue(getDouble(ime.getBCredito()));
                    header.createCell(4).setCellFormula("D24*0.8");
                    header.createCell(5).setCellFormula("D24*0.2");
                    header.createCell(6).setCellFormula("C24-D24");
                    header.createCell(7).setCellValue(getDouble(ime.getDCredito()));
                    header.createCell(8).setCellFormula("SUM(G24:H24)");
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(23,23,0,1));
                    sheet.addMergedRegion(new CellRangeAddress(23,23,8,9));
                    
                break;
                
                case 24:
                    header.createCell(0).setCellValue("SOAT (+)");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getASoat()));
                    header.createCell(3).setCellValue(getDouble(ime.getBSoat()));
                    header.createCell(4).setCellFormula("D25*0.8");
                    header.createCell(5).setCellFormula("D25*0.2");
                    header.createCell(6).setCellFormula("C25-D25");
                    header.createCell(7).setCellValue(getDouble(ime.getDSoat()));
                    header.createCell(8).setCellFormula("SUM(G25:H25)");
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(24,24,0,1));
                    sheet.addMergedRegion(new CellRangeAddress(24,24,8,9));
                    
                break;
                
                case 25:
                    header.createCell(0).setCellValue("OTROS CONVENIOS O CREDITOS (+)");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getAOtros()));
                    header.createCell(3).setCellValue(getDouble(ime.getBOtros()));
                    header.createCell(4).setCellFormula("D26*0.8");
                    header.createCell(5).setCellFormula("D26*0.2");
                    header.createCell(6).setCellFormula("C26-D26");
                    header.createCell(7).setCellValue(getDouble(ime.getDOtros()));
                    header.createCell(8).setCellFormula("SUM(G26:H26)");
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(25,25,0,1));
                    sheet.addMergedRegion(new CellRangeAddress(25,25,8,9));
                    
                break;
                
                case 26:
                    header.createCell(0).setCellValue("TOTAL CAPTADO POR RDR");
                    header.createCell(1);
                    header.createCell(2).setCellFormula("SUM(C23:C26)");
                    header.createCell(3).setCellFormula("SUM(D23:D26)");
                    header.createCell(4).setCellFormula("SUM(E23:E26)");
                    header.createCell(5).setCellFormula("SUM(F23:F26)");
                    header.createCell(6).setCellValue("");
                    header.createCell(7).setCellValue("");
                    header.createCell(8).setCellFormula("SUM(I23:J26)");
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(26,26,0,1));
                    sheet.addMergedRegion(new CellRangeAddress(26,26,8,9));
                    
                break;
                
                case 27:
                    header.createCell(0).setCellValue("SIS (+)");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getASis()));
                    header.createCell(3).setCellValue(getDouble(ime.getBSis()));
                    header.createCell(4).setCellFormula("D28*0.8");
                    header.createCell(5).setCellFormula("D28*0.2");
                    header.createCell(6).setCellFormula("C28-D28");
                    header.createCell(7).setCellValue(getDouble(ime.getDSis()));
                    header.createCell(8).setCellFormula("SUM(G28:H28)");
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(27,27,0,1));
                    sheet.addMergedRegion(new CellRangeAddress(27,27,8,9));
                    
                break;
                
                case 28:
                    header.createCell(0).setCellValue("SIS SALUD (+)");
                    header.createCell(1);
                    header.createCell(2);
                    header.createCell(3);
                    header.createCell(4);
                    header.createCell(5);
                    header.createCell(6);
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(28,28,0,1));
                    sheet.addMergedRegion(new CellRangeAddress(28,28,8,9));
                    
                break;
                
                case 29:
                    header.createCell(0).setCellValue("INTERVENCIONES SANITARIAS - SOPORTE (+)");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getAIntSan()));
                    header.createCell(3).setCellValue(getDouble(ime.getBIntSan()));
                    header.createCell(4).setCellFormula("D30*0.8");
                    header.createCell(5).setCellFormula("D30*0.2");
                    header.createCell(6).setCellFormula("C30-D30");
                    header.createCell(7).setCellValue(getDouble(ime.getDIntSan()));
                    header.createCell(8).setCellFormula("SUM(G30:H30)");
                    header.createCell(9);
                    
                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(29,29,0,1));
                    sheet.addMergedRegion(new CellRangeAddress(29,29,8,9));
                    
                break;
                
                case 30:
                    header.createCell(0).setCellValue("DEFENSA NACIONAL (+)");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getAdn()));
                    header.createCell(3).setCellValue(getDouble(ime.getBdn()));
                    header.createCell(4).setCellFormula("D31*0.8");
                    header.createCell(5).setCellFormula("D31*0.2");
                    header.createCell(6).setCellFormula("C31-D31");
                    header.createCell(7).setCellValue(getDouble(ime.getDdn()));
                    header.createCell(8).setCellFormula("SUM(G31:H31)");
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(30,30,0,1));
                    sheet.addMergedRegion(new CellRangeAddress(30,30,8,9));
                    
                break;
                
                case 31:
                    header.createCell(0).setCellValue("F");
                    header.createCell(1).setCellValue("SUBTOTAL EN SOLES");
                    header.createCell(2).setCellFormula("SUM(C27:C31)");
                    header.createCell(3).setCellFormula("SUM(D27:D31)+E36");
                    header.createCell(4).setCellFormula("SUM(E27:E31)");
                    header.createCell(5).setCellFormula("SUM(F27:F31)");
                    header.createCell(6);
                    header.createCell(7);
                    header.createCell(8).setCellFormula("SUM(I27:J31)");
                    header.createCell(9).setCellValue("F");

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    header.getCell(0).setCellStyle(styleLeftTop);
                    header.getCell(9).setCellStyle(styleRightTop);
                    for(int i = 1; i<9; i++){
                        if(i==5){
                            header.getCell(i).setCellStyle(styleLeftTop);
                        }else{
                            header.getCell(i).setCellStyle(styleTop);
                        }
                    }
                    
                break;
                
                case 32:
                    header.createCell(0).setCellValue("G");
                    header.createCell(1).setCellValue("SALDO DISPONIBLE DEL MES ANTERIOR PARA MEDICAMENTOS (+)");
                    header.createCell(2);
                    header.createCell(3);

                    header.createCell(4).setCellValue(getDouble(ime.getSaldoMesAnteriorMed()));
                    header.createCell(5).setCellValue(getDouble(ime.getSaldoMesAnteriorGAdmin()));
                    
                    header.createCell(6).setCellValue("(+) SALDO DISPONIBLE DEL MES PASADO PARA GASTOS ADM");
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9).setCellValue("G");

                    header.getCell(0).setCellStyle(styleLeft);
                    header.getCell(9).setCellStyle(styleRight);
                    for(int i = 1; i < 9; i++){
                        
                        if(i==5){
                            header.getCell(i).setCellStyle(styleLeft);
                        }else{
                            header.getCell(i).setCellStyle(style);
                        }
                    }

                    sheet.addMergedRegion(new CellRangeAddress(32,32,1,3));
                    sheet.addMergedRegion(new CellRangeAddress(32,32,6,8));
                    
                break;
                
                case 33:
                    header.createCell(0).setCellValue("H");
                    header.createCell(1).setCellValue("10% PARA FORTALECIMIENTO (en caso de no tener stock minimo)(+)");
                    header.createCell(2);
                    header.createCell(3);

                    header.createCell(4).setCellValue(getDouble(ime.getH10positivo()));
                    header.createCell(5).setCellValue(0);
                    header.createCell(6).setCellValue("(-) 10% PARA FORTALECIMIENTO");
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9).setCellValue("H");

                    header.getCell(0).setCellStyle(styleLeft);
                    header.getCell(9).setCellStyle(styleRight);
                    for(int i = 1; i < 9; i++){
                        
                        if(i==5){
                            header.getCell(i).setCellStyle(styleLeft);
                        }else{
                            header.getCell(i).setCellStyle(style);
                        }
                    }
                    
                    sheet.addMergedRegion(new CellRangeAddress(33,33,1,3));
                    sheet.addMergedRegion(new CellRangeAddress(33,33,6,8));
                    
                break;
                
                case 34:
                    header.createCell(0);
                    header.createCell(1).setCellValue("PARA FORTALECIMIENTO (en caso de no tener stock minimo x don y transf)");
                    header.createCell(2);
                    header.createCell(3);

                    header.createCell(4);

                    header.createCell(5);
                    header.createCell(6).setCellValue("(-) TRANSFERENCIAS A OTRAS UNIDADES EJECUTORAS");
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9);

                    header.getCell(0).setCellStyle(styleLeft);
                    header.getCell(9).setCellStyle(styleRight);
                    for(int i = 1; i < 9; i++){
                        
                        if(i==5){
                            header.getCell(i).setCellStyle(styleLeft);
                        }else{
                            header.getCell(i).setCellStyle(style);
                        }
                    }

                    sheet.addMergedRegion(new CellRangeAddress(34,34,1,3));
                    sheet.addMergedRegion(new CellRangeAddress(34,34,6,8));
                    
                break;
                
                case 35:
                    header.createCell(0);
                    header.createCell(1).setCellValue("PARA FORTALECIMIENTO (en caso de no tener stock minimo x soat)");
                    header.createCell(2);
                    header.createCell(3);

                    header.createCell(4);

                    header.createCell(5);
                    header.createCell(6);
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9);

                    header.getCell(0).setCellStyle(styleLeft);
                    header.getCell(9).setCellStyle(styleRight);
                    for(int i = 1; i < 9; i++){
                        
                        if(i==5){
                            header.getCell(i).setCellStyle(styleLeft);
                        }else{
                            header.getCell(i).setCellStyle(style);
                        }
                    }

                    sheet.addMergedRegion(new CellRangeAddress(35,35,1,3));
                    sheet.addMergedRegion(new CellRangeAddress(35,35,6,8));
                    
                break;
                
                case 36:
                    header.createCell(0).setCellValue("I");
                    header.createCell(1).setCellValue("EXONERACIONES (+)");
                    header.createCell(2);
                    header.createCell(3);
                    header.createCell(4).setCellValue(getDouble(ime.getExonPositivo()));

                    header.createCell(5).setCellValue(getDouble(ime.getExonNegativo()));
                    header.createCell(6).setCellValue("(-) EXONERACIONES");
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9);

                    header.getCell(0).setCellStyle(styleLeft);
                    header.getCell(9).setCellStyle(styleRight);
                    for(int i = 1; i < 9; i++){
                        
                        if(i==5){
                            header.getCell(i).setCellStyle(styleLeft);
                        }else{
                            header.getCell(i).setCellStyle(style);
                        }
                    }

                    sheet.addMergedRegion(new CellRangeAddress(36,36,1,3));
                    sheet.addMergedRegion(new CellRangeAddress(36,36,6,8));
                    
                break;
                case 37:
                    header.createCell(0);
                    header.createCell(1).setCellValue("TOTAL DE ABASTECIMIENTO EN EL MES TRANSF Y/O DONA (REEMBOLSO SIS)");
                    header.createCell(2);
                    header.createCell(3);

                    header.createCell(4);

                    header.createCell(5);
                    header.createCell(6);
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9);

                    header.getCell(0).setCellStyle(styleLeft);
                    header.getCell(9).setCellStyle(styleRight);
                    for(int i = 1; i < 9; i++){
                        
                        if(i==5){
                            header.getCell(i).setCellStyle(styleLeft);
                        }else{
                            header.getCell(i).setCellStyle(style);
                        }
                    }

                    sheet.addMergedRegion(new CellRangeAddress(37,37,1,3));
                    sheet.addMergedRegion(new CellRangeAddress(37,37,6,8));
                    
                break;
                
                case 38:
                    header.createCell(0).setCellValue("J");
                    header.createCell(1).setCellValue("TOTAL DE ABASTECIMIENTO EN EL MES ("+periodoString+") (-) RDR*");
                    header.createCell(2);
                    header.createCell(3);
                    header.createCell(4).setCellValue(getDouble(ime.getTotalAbastecimientoMed()));

                    header.createCell(5).setCellValue(getDouble(ime.getTotalGAdmin()));
                    header.createCell(6).setCellValue("(-) TOTAL DE GASTOS ADM EN EL MES "+periodoString);
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9).setCellValue("J");

                    header.getCell(0).setCellStyle(styleLeft);
                    header.getCell(9).setCellStyle(styleRight);
                    for(int i = 1; i < 9; i++){
                        
                        if(i==5){
                            header.getCell(i).setCellStyle(styleLeft);
                        }else{
                            header.getCell(i).setCellStyle(style);
                        }
                    }

                    sheet.addMergedRegion(new CellRangeAddress(38,38,1,3));
                    sheet.addMergedRegion(new CellRangeAddress(38,38,6,8));
                    
                break;
                
                case 39:
                    header.createCell(0).setCellValue("F+G+H+I+J");
                    header.createCell(1).setCellValue("SALDO DISPONIBLE PARA COMPRA DE MEDICAMENTOS PARA EL MES SIGUIENTE");
                    header.createCell(2);
                    header.createCell(3);
                    header.createCell(4).setCellFormula("E32+E33+E34-E35+E36+E37-E38-E39");

                    header.createCell(5).setCellFormula("F32+F33-F39-F36-F37");
                    header.createCell(6).setCellValue("SALDO DISPONIBLE PARA GASTOS ADM PARA EL MES SIGUIENTE");
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9).setCellValue("F+G+H+I+J");

                    header.getCell(0).setCellStyle(styleLeftBottom);
                    header.getCell(9).setCellStyle(styleRightBottom);
                    for(int i = 1; i < 9; i++){
                        
                        if(i==5){
                            header.getCell(i).setCellStyle(styleLeftBottom);
                        }else{
                            header.getCell(i).setCellStyle(styleBottom);
                        }
                    }

                    sheet.addMergedRegion(new CellRangeAddress(39,39,1,3));
                    sheet.addMergedRegion(new CellRangeAddress(39,39,6,8));
                    
                break;
                
                
            }
        }
        
        for(int i = 5; i<=39;i++){
           sheet.getRow(i).setHeight((short)330);
        }
        sheet.getRow(6).setHeight((short)650);
        sheet.getRow(7).setHeight((short)650);
        sheet.getRow(39).setHeight((short)650);
        
        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");

    }
    */

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UtilExportExcel utilExportExcel = new UtilExportExcel();
        
        Ime ime = (Ime)model.get("IME");
        
        List<Ime_III> detalle = (List<Ime_III>)model.get("detalle");
        int indiceDetalle=0;
        
        String titulo = "Formato IME";
        String periodoString = "";

        periodoString += "desde "+ime.getFechaDesdeString();
        periodoString += " hasta " +ime.getFechaHastaString();
               
        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);

        CellStyle style = this.getCellStyleBorder(workbook);
        CellStyle styleWithoutBorder = this.getCellHeadStyle(workbook);
        CellStyle styleThick = this.getCellStyleBorderThick(workbook);
        CellStyle styleTop = this.getStyleTopThick(workbook);
        CellStyle styleBottom = this.getStyleBottomThick(workbook);
        CellStyle styleRight = this.getStyleRightBorderThick(workbook);
        CellStyle styleRightTop = this.getStyleRightTopBorderThick(workbook);
        CellStyle styleRightBottom = this.getStyleRightBottomBorderThick(workbook);
        CellStyle styleLeft = this.getStyleLeftBorderThick(workbook);
        CellStyle styleLeftTop = this.getStyleLeftTopBorderThick(workbook);
        CellStyle styleLeftBottom = this.getStyleLeftBottomBorderThick(workbook);
        
       Ime_III imeiii;
        
        for (int i = 0; i <= 9; ++i) {
            sheet.setColumnWidth(i, 5000);
        }
        
        for(int x = 5; x<=39; x++){
            HSSFRow header = sheet.createRow(x);
        
            switch(x){
                case 5:
                    header.createCell(0).setCellValue("Periodo: "+periodoString);
                    header.createCell(1);
                    header.createCell(2);
                    header.createCell(3);

                    header.createCell(4);

                    header.createCell(5).setCellValue("GASTOS ADMINISTRATIVOS DE LA UNIDAD EJECUTORA CON EL 20%");
                    header.createCell(6);
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        if(i!=4){
                            header.getCell(i).setCellStyle(styleThick);
                        }
                    }

                    sheet.addMergedRegion(new CellRangeAddress(5,5,0,3));
                    sheet.addMergedRegion(new CellRangeAddress(5,5,5,9));

                break;
                
                case 6:
                    header.createCell(0).setCellValue("SALIDAS POR VENTA O EXONERACION (Boletas de venta)");
                    header.createCell(1);
                    header.createCell(2);
                    header.createCell(3);

                    header.createCell(4);

                    header.createCell(5).setCellValue("FECHA");
                    header.createCell(6).setCellValue("PARTIDA");
                    header.createCell(7).setCellValue("DETALLE DEL GASTO");
                    header.createCell(8).setCellValue("DOC. FUENTE");
                    header.createCell(9).setCellValue("IMPORTE");

                    for(int i = 0; i <= 9; i++){
                        if(i!=4)
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    for(int i = 0; i<=3; i++){
                        header.getCell(i).setCellStyle(styleThick);
                    }
                    
                    sheet.addMergedRegion(new CellRangeAddress(6,6,0,3));
                    
                break;
                
                case 7:
                    if(indiceDetalle<detalle.size()){
                        imeiii = detalle.get(indiceDetalle);
                        indiceDetalle++;
                    }else{
                        imeiii=new Ime_III();
                    }
                        
                    header.createCell(0).setCellValue("Boletas emitidas");
                    header.createCell(1);
                    header.createCell(2);
                    header.createCell(3).setCellValue("Importe total de Ventas");

                    header.createCell(4);

                    //DETALLE IMEIII
                    header.createCell(5).setCellValue(imeiii.getFechaString());
                    header.createCell(6).setCellValue(imeiii.getPartida());
                    header.createCell(7).setCellValue(imeiii.getDetalleGasto());
                    header.createCell(8).setCellValue(imeiii.getDocFuente());
                    header.createCell(9).setCellValue(getDouble(imeiii.getImporte()));

                    for(int i = 0; i <= 9; i++){
                        if(i!=4)
                        header.getCell(i).setCellStyle(style);
                        
                    }
                    
                    header.getCell(3).setCellStyle(styleThick);
                    header.getCell(0).setCellStyle(styleLeftTop);
                    

                    sheet.addMergedRegion(new CellRangeAddress(7,7, 0, 2));
                    sheet.addMergedRegion(new CellRangeAddress(8,11, 3, 3));
                break;
                
                case 8:
                    
                    if(indiceDetalle<detalle.size()){
                        imeiii = detalle.get(indiceDetalle);
                        indiceDetalle++;
                    }else{
                        imeiii=new Ime_III();
                    }
                    
                    header.createCell(0).setCellValue("Serie:");
                    header.createCell(1).setCellValue(ime.getNumeroSerie());
                    header.createCell(2);
                    header.createCell(3).setCellValue(getDouble(ime.getAVenta()));

                    header.createCell(4);

                    //DETALLE IMEIII
                    header.createCell(5).setCellValue(imeiii.getFechaString());
                    header.createCell(6).setCellValue(imeiii.getPartida());
                    header.createCell(7).setCellValue(imeiii.getDetalleGasto());
                    header.createCell(8).setCellValue(imeiii.getDocFuente());
                    header.createCell(9).setCellValue(getDouble(imeiii.getImporte()));

                    header.getCell(0).setCellStyle(styleLeft);
                    
                    for(int i = 1; i <= 9; i++){
                        if(i!=4)
                        header.getCell(i).setCellStyle(style);
                        if(i==3)
                        header.getCell(i).setCellStyle(styleThick);
                    }
                    
                    header.getCell(3).setCellStyle(styleThick);
                    
                    sheet.addMergedRegion(new CellRangeAddress(8,8,1,2));
                break;
                
                case 9:
                    
                    if(indiceDetalle<detalle.size()){
                        imeiii = detalle.get(indiceDetalle);
                        indiceDetalle++;
                    }else{
                        imeiii=new Ime_III();
                    }
                    
                    header.createCell(0).setCellValue("Del:");
                    header.createCell(1).setCellValue(ime.getNumBoletaDe());
                    header.createCell(2);
                    header.createCell(3);

                    header.createCell(4);

                    //DETALLE IMEIII
                    header.createCell(5).setCellValue(imeiii.getFechaString());
                    header.createCell(6).setCellValue(imeiii.getPartida());
                    header.createCell(7).setCellValue(imeiii.getDetalleGasto());
                    header.createCell(8).setCellValue(imeiii.getDocFuente());
                    header.createCell(9).setCellValue(getDouble(imeiii.getImporte()));

                    header.getCell(0).setCellStyle(styleLeft);
                    
                    for(int i = 1; i <= 9; i++){
                        if(i!=4)
                        header.getCell(i).setCellStyle(style);
                        if(i==3)
                        header.getCell(i).setCellStyle(styleThick);
                    }
                    
                    sheet.addMergedRegion(new CellRangeAddress(9,9,1,2));
                break;
                
                case 10:
                    
                    if(indiceDetalle<detalle.size()){
                        imeiii = detalle.get(indiceDetalle);
                        indiceDetalle++;
                    }
                    else{
                        imeiii=new Ime_III();
                    }
                    header.createCell(0).setCellValue("Al:");
                    header.createCell(1).setCellValue(ime.getNumBoletaA());
                    header.createCell(2);
                    header.createCell(3);

                    header.createCell(4);

                    //DETALLE IMEIII
                    header.createCell(5).setCellValue(imeiii.getFechaString());
                    header.createCell(6).setCellValue(imeiii.getPartida());
                    header.createCell(7).setCellValue(imeiii.getDetalleGasto());
                    header.createCell(8).setCellValue(imeiii.getDocFuente());
                    header.createCell(9).setCellValue(getDouble(imeiii.getImporte()));

                    header.getCell(0).setCellStyle(styleLeft);
                    
                    for(int i = 1; i <= 9; i++){
                        if(i!=4)
                        header.getCell(i).setCellStyle(style);
                        if(i==3)
                        header.getCell(i).setCellStyle(styleThick);
                    }

                    sheet.addMergedRegion(new CellRangeAddress(10,10,1,2));
                    
                break;
                
                case 11:
                    
                    if(indiceDetalle<detalle.size()){
                        imeiii = detalle.get(indiceDetalle);
                        indiceDetalle++;
                    }else{
                        imeiii=new Ime_III();
                    }
                    
                    header.createCell(0).setCellValue("Anuladas");
                    header.createCell(1).setCellValue("");
                    header.createCell(2);
                    header.createCell(3);

                    header.createCell(4);

                    //DETALLE IMEIII
                    header.createCell(5).setCellValue(imeiii.getFechaString());
                    header.createCell(6).setCellValue(imeiii.getPartida());
                    header.createCell(7).setCellValue(imeiii.getDetalleGasto());
                    header.createCell(8).setCellValue(imeiii.getDocFuente());
                    header.createCell(9).setCellValue(getDouble(imeiii.getImporte()));

                    for(int i = 0; i <= 9; i++){
                        if(i==0)
                        header.getCell(i).setCellStyle(styleLeftBottom);
                        if(i<3&&i>0)
                        header.getCell(i).setCellStyle(styleBottom);
                        if(i>4)
                        header.getCell(i).setCellStyle(style);
                        if(i==3)
                        header.getCell(i).setCellStyle(styleThick);
                    }

                    sheet.addMergedRegion(new CellRangeAddress(11,11,1,2));
                break;
                
                case 12:
                    
                    if(indiceDetalle<detalle.size()){
                        imeiii = detalle.get(indiceDetalle);
                        indiceDetalle++;
                    }else{
                        imeiii=new Ime_III();
                    }
                    
                    header.createCell(0);
                    header.createCell(1);
                    header.createCell(2).setCellValue("EMITIDAS");
                    header.createCell(3).setCellValue("DISPENSADAS");

                    header.createCell(4);

                    //DETALLE IMEIII
                    header.createCell(5).setCellValue(imeiii.getFechaString());
                    header.createCell(6).setCellValue(imeiii.getPartida());
                    header.createCell(7).setCellValue(imeiii.getDetalleGasto());
                    header.createCell(8).setCellValue(imeiii.getDocFuente());
                    header.createCell(9).setCellValue(getDouble(imeiii.getImporte()));

                    for(int i = 0; i <= 9; i++){
                        
                        if(i<4)
                            header.getCell(i).setCellStyle(styleWithoutBorder);
                        if(i>4)
                            header.getCell(i).setCellStyle(style);
    
                    }
                break;
                
                case 13:
                    
                    if(indiceDetalle<detalle.size()){
                        imeiii = detalle.get(indiceDetalle);
                        indiceDetalle++;
                    }else{
                        imeiii=new Ime_III();
                    }
                    
                    header.createCell(0).setCellValue("No. De Recetas por Demanda");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getRecetasDemandaEmitidas()));
                    header.createCell(3).setCellValue(getDouble(ime.getRecetasDemandaDispensadas()));

                    header.createCell(4);

                    //DETALLE IMEIII
                    header.createCell(5).setCellValue(imeiii.getFechaString());
                    header.createCell(6).setCellValue(imeiii.getPartida());
                    header.createCell(7).setCellValue(imeiii.getDetalleGasto());
                    header.createCell(8).setCellValue(imeiii.getDocFuente());
                    header.createCell(9).setCellValue(getDouble(imeiii.getImporte()));

                    for(int i = 0; i <= 9; i++){
                        if(i!=4)
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(13,13,0,1));
                break;
                
                case 14:
                    
                    if(indiceDetalle<detalle.size()){
                        imeiii = detalle.get(indiceDetalle);
                        indiceDetalle++;
                    }else{
                        imeiii=new Ime_III();
                    }
                    
                    header.createCell(0).setCellValue("No. De Recetas por SIS");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getRecetasSisEmitidas()));
                    header.createCell(3).setCellValue(getDouble(ime.getRecetasSisDispensadas()));

                    header.createCell(4);

                    //DETALLE IMEIII
                    header.createCell(5).setCellValue(imeiii.getFechaString());
                    header.createCell(6).setCellValue(imeiii.getPartida());
                    header.createCell(7).setCellValue(imeiii.getDetalleGasto());
                    header.createCell(8).setCellValue(imeiii.getDocFuente());
                    header.createCell(9).setCellValue(getDouble(imeiii.getImporte()));

                    for(int i = 0; i <= 9; i++){
                        if(i!=4)
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(14,14,0,1));
                    
                break;
                
                case 15:
                    
                    if(indiceDetalle<detalle.size()){
                        imeiii = detalle.get(indiceDetalle);
                        indiceDetalle++;
                    }else{
                        imeiii=new Ime_III();
                    }
                    
                    header.createCell(0).setCellValue("No. Recetas Interven. Sanit.");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getRecetasIntervSanitEmitidas()));
                    header.createCell(3).setCellValue(getDouble(ime.getRecetasIntervSanitDispensadas()));

                    header.createCell(4);

                    //DETALLE IMEIII
                    header.createCell(5).setCellValue(imeiii.getFechaString());
                    header.createCell(6).setCellValue(imeiii.getPartida());
                    header.createCell(7).setCellValue(imeiii.getDetalleGasto());
                    header.createCell(8).setCellValue(imeiii.getDocFuente());
                    header.createCell(9).setCellValue(getDouble(imeiii.getImporte()));

                    for(int i = 0; i <= 9; i++){
                        if(i!=4)
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(15,15,0,1));
                    
                break;
                
                case 16:
                    
                    if(indiceDetalle<detalle.size()){
                        imeiii = detalle.get(indiceDetalle);
                        indiceDetalle++;
                    }else{
                        imeiii=new Ime_III();
                    }
                    
                    header.createCell(0).setCellValue("No. Recetas por Soat");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getRecetasSoatEmitidas()));
                    header.createCell(3).setCellValue(getDouble(ime.getRecetasSoatDispensadas()));

                    header.createCell(4);

                    //DETALLE IMEIII
                    header.createCell(5).setCellValue(imeiii.getFechaString());
                    header.createCell(6).setCellValue(imeiii.getPartida());
                    header.createCell(7).setCellValue(imeiii.getDetalleGasto());
                    header.createCell(8).setCellValue(imeiii.getDocFuente());
                    header.createCell(9).setCellValue(getDouble(imeiii.getImporte()));

                    for(int i = 0; i <= 9; i++){
                        if(i!=4)
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(16,16,0,1));
                    
                break;
                
                case 17:
                    
                    if(indiceDetalle<detalle.size()){
                        imeiii = detalle.get(indiceDetalle);
                        indiceDetalle++;
                    }else{
                        imeiii=new Ime_III();
                    }
                    
                    header.createCell(0).setCellValue("No. Recetas por Exoneracion");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getRecetasExoneracionEmitidas()));
                    header.createCell(3).setCellValue(getDouble(ime.getRecetasExoneracionDispensadas()));

                    header.createCell(4);

                    //DETALLE IMEIII
                    header.createCell(5).setCellValue(imeiii.getFechaString());
                    header.createCell(6).setCellValue(imeiii.getPartida());
                    header.createCell(7).setCellValue(imeiii.getDetalleGasto());
                    header.createCell(8).setCellValue(imeiii.getDocFuente());
                    header.createCell(9).setCellValue(getDouble(imeiii.getImporte()));

                    for(int i = 0; i <= 9; i++){
                        if(i!=4)
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(17,17,0,1));
                    
                break;
                
                case 18:
                    header.createCell(0).setCellValue("No. Recetas Externas");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getRecetasExternasEmitidas()));
                    header.createCell(3).setCellValue(getDouble(ime.getRecetasExternasDispensadas()));

                    header.createCell(4);

                    header.createCell(5);
                    header.createCell(6);
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9).setCellFormula("SUM(J8:J18)");
                    
                    for(int i = 0; i <= 9; i++){
                        if(i!=4)
                        header.getCell(i).setCellStyle(style);
                    }

                    sheet.addMergedRegion(new CellRangeAddress(18,18,0,1));
                    
                break;
                
                case 19:
                    header.createCell(0).setCellValue("IMPORTE");
                    header.createCell(1);
                    header.createCell(2).setCellValue("IMPORTE DE ATENCIONES\n"
                            + "FARMACIA (FORMATO ICI)");
                    header.createCell(3).setCellValue("CAPTACION O REEMBOLSOS DEL MES");
                    header.createCell(4);
                    header.createCell(5);
                    header.createCell(6).setCellValue("CUENTAS POR COBRAR DEL MES (C=A-B)");
                    header.createCell(7).setCellValue("CUENTAS POR COBRAR ACUMULADAS DEL MES ANTERIOR (D)");
                    header.createCell(8).setCellValue("CUENTAS POR COBRAR ACUMULADAS EN EL MES (E=C+D)");
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    //IMPORTE
                    sheet.addMergedRegion(new CellRangeAddress(19,21,0,1));
                    //IMPORTE DE ATENCIONES, FARMACIA FORMATO ICI
                    sheet.addMergedRegion(new CellRangeAddress(19,21,2,2));
                    //CAPTACION O REEMBOLSOS DEL MES
                    sheet.addMergedRegion(new CellRangeAddress(19,19,3,5));
                    //CUENTAS POR COBRAR DEL MES (C=A-B)
                    sheet.addMergedRegion(new CellRangeAddress(19,21,6,6));
                    //CUENTAS POR COBRAR ACUMULADAS DEL MES ANTERIOR (D)
                    sheet.addMergedRegion(new CellRangeAddress(19,21,7,7));
                    //CUENTAS POR COBRAR ACUMULADAS EN EL MES (E=C+D)
                    sheet.addMergedRegion(new CellRangeAddress(19,21,8,9));
                    
                break;
                
                case 20:
                    header.createCell(0);
                    header.createCell(1);
                    header.createCell(2);
                    header.createCell(3).setCellValue("TOTAL (B)");
                    header.createCell(4).setCellValue("DISTRIBUCION DE LO CAPTADO");
                    header.createCell(5);
                    header.createCell(6);
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    //TOTAL (B)
                    sheet.addMergedRegion(new CellRangeAddress(20,21,3,3));
                    //DISTRIBUCION DE LO CAPTADO       
                    sheet.addMergedRegion(new CellRangeAddress(20,20,4,5));
                    
                break;
                
                case 21:
                    header.createCell(0);
                    header.createCell(1);
                    header.createCell(2);
                    header.createCell(3);
                    header.createCell(4).setCellValue("80%");
                    header.createCell(5).setCellValue("20%");
                    header.createCell(6);
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }
                    
                break;
                
                case 22:
                    header.createCell(0).setCellValue("VENTAS (+)");
                    header.createCell(1);
                    header.createCell(2).setCellFormula("D9");
                    header.createCell(3).setCellFormula("C23");
                    header.createCell(4).setCellFormula("D23*0.8");
                    header.createCell(5).setCellFormula("D23*0.2");
                    header.createCell(6).setCellFormula("C23-D23");
                    //header.createCell(7);
                    header.createCell(7).setCellValue(ime.getCuentaCobrarAcumMesAnt_Ventas());
                    header.createCell(8).setCellFormula("SUM(G23:H23)");
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(22,22,0,1));
                    sheet.addMergedRegion(new CellRangeAddress(22,22,8,9));
                    
                break;
                
                case 23:
                    header.createCell(0).setCellValue("CREDITO HOSPITALIZADOS (+)");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getACredito()));
                    header.createCell(3).setCellValue(getDouble(ime.getBCredito()));
                    header.createCell(4).setCellFormula("D24*0.8");
                    header.createCell(5).setCellFormula("D24*0.2");
                    header.createCell(6).setCellFormula("C24-D24");
                    header.createCell(7).setCellValue(getDouble(ime.getDCredito()));
                    header.createCell(8).setCellFormula("SUM(G24:H24)");
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(23,23,0,1));
                    sheet.addMergedRegion(new CellRangeAddress(23,23,8,9));
                    
                break;
                
                case 24:
                    header.createCell(0).setCellValue("SOAT (+)");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getDSoat()));
                    header.createCell(3).setCellValue(getDouble(ime.getESoat()));
                    //header.createCell(2).setCellValue(getDouble(ime.getASoat()));
                    //header.createCell(3).setCellValue(getDouble(ime.getBSoat()));
                    header.createCell(4).setCellFormula("D25*0.8");
                    header.createCell(5).setCellFormula("D25*0.2");
                    header.createCell(6).setCellFormula("C25-D25");
                    header.createCell(7).setCellValue(ime.getCuentaCobrarAcumMesAnt_Soat());
                    //header.createCell(7).setCellValue(getDouble(ime.getDSoat()));
                    header.createCell(8).setCellFormula("SUM(G25:H25)");
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(24,24,0,1));
                    sheet.addMergedRegion(new CellRangeAddress(24,24,8,9));
                    
                break;
                
                case 25:
                    header.createCell(0).setCellValue("OTROS CONVENIOS O CREDITOS (+)");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getAOtros()));
                    header.createCell(3).setCellValue(getDouble(ime.getBOtros()));
                    header.createCell(4).setCellFormula("D26*0.8");
                    header.createCell(5).setCellFormula("D26*0.2");
                    header.createCell(6).setCellFormula("C26-D26");
                    header.createCell(7).setCellValue(getDouble(ime.getDOtros()));
                    header.createCell(8).setCellFormula("SUM(G26:H26)");
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(25,25,0,1));
                    sheet.addMergedRegion(new CellRangeAddress(25,25,8,9));
                    
                break;
                
                case 26:
                    header.createCell(0).setCellValue("TOTAL CAPTADO POR RDR");
                    header.createCell(1);
                    header.createCell(2).setCellFormula("SUM(C23:C26)");
                    header.createCell(3).setCellFormula("SUM(D23:D26)");
                    header.createCell(4).setCellFormula("SUM(E23:E26)");
                    header.createCell(5).setCellFormula("SUM(F23:F26)");
                    header.createCell(6).setCellValue("");
                    header.createCell(7).setCellValue("");
                    header.createCell(8).setCellFormula("SUM(I23:J26)");
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(26,26,0,1));
                    sheet.addMergedRegion(new CellRangeAddress(26,26,8,9));
                    
                break;
                
                case 27:
                    header.createCell(0).setCellValue("SIS (+)");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getDSis()));
                    header.createCell(3).setCellValue(getDouble(ime.getESis()));
                    //header.createCell(2).setCellValue(getDouble(ime.getASis()));
                    //header.createCell(3).setCellValue(getDouble(ime.getBSis()));
                    header.createCell(4).setCellFormula("D28*0.8");
                    header.createCell(5).setCellFormula("D28*0.2");
                    header.createCell(6).setCellFormula("C28-D28");
                    header.createCell(7).setCellValue(ime.getCuentaCobrarAcumMesAnt_Sis());
                    //header.createCell(7).setCellValue(getDouble(ime.getDSis()));
                    header.createCell(8).setCellFormula("SUM(G28:H28)");
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(27,27,0,1));
                    sheet.addMergedRegion(new CellRangeAddress(27,27,8,9));
                    
                break;
                
                case 28:
                    header.createCell(0).setCellValue("SIS SALUD (+)");
                    header.createCell(1);
                    header.createCell(2);
                    header.createCell(3);
                    header.createCell(4);
                    header.createCell(5);
                    header.createCell(6);
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(28,28,0,1));
                    sheet.addMergedRegion(new CellRangeAddress(28,28,8,9));
                    
                break;
                
                case 29:
                    header.createCell(0).setCellValue("INTERVENCIONES SANITARIAS - SOPORTE (+)");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getDIntSan()));
                    header.createCell(3).setCellValue(getDouble(ime.getEIntSan()));
                    //header.createCell(2).setCellValue(getDouble(ime.getAIntSan()));
                    //header.createCell(3).setCellValue(getDouble(ime.getBIntSan()));
                    header.createCell(4).setCellFormula("D30*0.8");
                    header.createCell(5).setCellFormula("D30*0.2");
                    header.createCell(6).setCellFormula("C30-D30");
                    header.createCell(7).setCellValue(ime.getCuentaCobrarAcumMesAnt_InterSanit());
                    //header.createCell(7).setCellValue(getDouble(ime.getDIntSan()));
                    header.createCell(8).setCellFormula("SUM(G30:H30)");
                    header.createCell(9);
                    
                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(29,29,0,1));
                    sheet.addMergedRegion(new CellRangeAddress(29,29,8,9));
                    
                break;
                
                case 30:
                    header.createCell(0).setCellValue("DEFENSA NACIONAL (+)");
                    header.createCell(1);
                    header.createCell(2).setCellValue(getDouble(ime.getAdn()));
                    header.createCell(3).setCellValue(getDouble(ime.getBdn()));
                    header.createCell(4).setCellFormula("D31*0.8");
                    header.createCell(5).setCellFormula("D31*0.2");
                    header.createCell(6).setCellFormula("C31-D31");
                    header.createCell(7).setCellValue(getDouble(ime.getDdn()));
                    header.createCell(8).setCellFormula("SUM(G31:H31)");
                    header.createCell(9);

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    sheet.addMergedRegion(new CellRangeAddress(30,30,0,1));
                    sheet.addMergedRegion(new CellRangeAddress(30,30,8,9));
                    
                break;
                
                case 31:
                    header.createCell(0).setCellValue("F");
                    header.createCell(1).setCellValue("SUBTOTAL EN SOLES");
                    header.createCell(2).setCellFormula("SUM(C27:C31)");
                    header.createCell(3).setCellFormula("SUM(D27:D31)+E36");
                    header.createCell(4).setCellFormula("SUM(E27:E31)");
                    header.createCell(5).setCellFormula("SUM(F27:F31)");
                    header.createCell(6);
                    header.createCell(7);
                    header.createCell(8).setCellFormula("SUM(I27:J31)");
                    header.createCell(9).setCellValue("F");

                    for(int i = 0; i <= 9; i++){
                        
                        header.getCell(i).setCellStyle(style);
                        
                    }

                    header.getCell(0).setCellStyle(styleLeftTop);
                    header.getCell(9).setCellStyle(styleRightTop);
                    for(int i = 1; i<9; i++){
                        if(i==5){
                            header.getCell(i).setCellStyle(styleLeftTop);
                        }else{
                            header.getCell(i).setCellStyle(styleTop);
                        }
                    }
                    
                break;
                
                case 32:
                    header.createCell(0).setCellValue("G");
                    header.createCell(1).setCellValue("SALDO DISPONIBLE DEL MES ANTERIOR PARA MEDICAMENTOS (+)");
                    header.createCell(2);
                    header.createCell(3);

                    header.createCell(4).setCellValue(ime.getSaldoDisponibleMesAnt_Medicamentos());
                    header.createCell(5).setCellValue(ime.getSaldoDisponibleMesAnt_GastosAdmin());
                    //header.createCell(4).setCellValue(getDouble(ime.getSaldoMesAnteriorMed()));
                    //header.createCell(5).setCellValue(getDouble(ime.getSaldoMesAnteriorGAdmin()));
                    
                    header.createCell(6).setCellValue("(+) SALDO DISPONIBLE DEL MES PASADO PARA GASTOS ADM");
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9).setCellValue("G");

                    header.getCell(0).setCellStyle(styleLeft);
                    header.getCell(9).setCellStyle(styleRight);
                    for(int i = 1; i < 9; i++){
                        
                        if(i==5){
                            header.getCell(i).setCellStyle(styleLeft);
                        }else{
                            header.getCell(i).setCellStyle(style);
                        }
                    }

                    sheet.addMergedRegion(new CellRangeAddress(32,32,1,3));
                    sheet.addMergedRegion(new CellRangeAddress(32,32,6,8));
                    
                break;
                
                case 33:
                    header.createCell(0).setCellValue("H");
                    header.createCell(1).setCellValue("10% PARA FORTALECIMIENTO (en caso de no tener stock minimo)(+)");
                    header.createCell(2);
                    header.createCell(3);

                    header.createCell(4).setCellValue(getDouble(ime.getH10positivo()));
                    header.createCell(5).setCellValue(0);
                    header.createCell(6).setCellValue("(-) 10% PARA FORTALECIMIENTO");
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9).setCellValue("H");

                    header.getCell(0).setCellStyle(styleLeft);
                    header.getCell(9).setCellStyle(styleRight);
                    for(int i = 1; i < 9; i++){
                        
                        if(i==5){
                            header.getCell(i).setCellStyle(styleLeft);
                        }else{
                            header.getCell(i).setCellStyle(style);
                        }
                    }
                    
                    sheet.addMergedRegion(new CellRangeAddress(33,33,1,3));
                    sheet.addMergedRegion(new CellRangeAddress(33,33,6,8));
                    
                break;
                
                case 34:
                    header.createCell(0);
                    header.createCell(1).setCellValue("PARA FORTALECIMIENTO (en caso de no tener stock minimo x don y transf)");
                    header.createCell(2);
                    header.createCell(3);

                    header.createCell(4);

                    header.createCell(5);
                    header.createCell(6).setCellValue("(-) TRANSFERENCIAS A OTRAS UNIDADES EJECUTORAS");
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9);

                    header.getCell(0).setCellStyle(styleLeft);
                    header.getCell(9).setCellStyle(styleRight);
                    for(int i = 1; i < 9; i++){
                        
                        if(i==5){
                            header.getCell(i).setCellStyle(styleLeft);
                        }else{
                            header.getCell(i).setCellStyle(style);
                        }
                    }

                    sheet.addMergedRegion(new CellRangeAddress(34,34,1,3));
                    sheet.addMergedRegion(new CellRangeAddress(34,34,6,8));
                    
                break;
                
                case 35:
                    header.createCell(0);
                    header.createCell(1).setCellValue("PARA FORTALECIMIENTO (en caso de no tener stock minimo x soat)");
                    header.createCell(2);
                    header.createCell(3);

                    header.createCell(4);

                    header.createCell(5);
                    header.createCell(6);
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9);

                    header.getCell(0).setCellStyle(styleLeft);
                    header.getCell(9).setCellStyle(styleRight);
                    for(int i = 1; i < 9; i++){
                        
                        if(i==5){
                            header.getCell(i).setCellStyle(styleLeft);
                        }else{
                            header.getCell(i).setCellStyle(style);
                        }
                    }

                    sheet.addMergedRegion(new CellRangeAddress(35,35,1,3));
                    sheet.addMergedRegion(new CellRangeAddress(35,35,6,8));
                    
                break;
                
                case 36:
                    header.createCell(0).setCellValue("I");
                    header.createCell(1).setCellValue("EXONERACIONES (+)");
                    header.createCell(2);
                    header.createCell(3);
                    header.createCell(4).setCellValue(getDouble(ime.getExonPositivo()));

                    header.createCell(5).setCellValue(ime.getExoneraciones_negativo());
                    //header.createCell(5).setCellValue(getDouble(ime.getExonNegativo()));
                    header.createCell(6).setCellValue("(-) EXONERACIONES");
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9);

                    header.getCell(0).setCellStyle(styleLeft);
                    header.getCell(9).setCellStyle(styleRight);
                    for(int i = 1; i < 9; i++){
                        
                        if(i==5){
                            header.getCell(i).setCellStyle(styleLeft);
                        }else{
                            header.getCell(i).setCellStyle(style);
                        }
                    }

                    sheet.addMergedRegion(new CellRangeAddress(36,36,1,3));
                    sheet.addMergedRegion(new CellRangeAddress(36,36,6,8));
                    
                break;
                case 37:
                    header.createCell(0);
                    header.createCell(1).setCellValue("TOTAL DE ABASTECIMIENTO EN EL MES TRANSF Y/O DONA (REEMBOLSO SIS)");
                    header.createCell(2);
                    header.createCell(3);

                    header.createCell(4).setCellValue(ime.getTotalAbastecimientoMes());
                    //header.createCell(4);

                    header.createCell(5);
                    header.createCell(6);
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9);

                    header.getCell(0).setCellStyle(styleLeft);
                    header.getCell(9).setCellStyle(styleRight);
                    for(int i = 1; i < 9; i++){
                        
                        if(i==5){
                            header.getCell(i).setCellStyle(styleLeft);
                        }else{
                            header.getCell(i).setCellStyle(style);
                        }
                    }

                    sheet.addMergedRegion(new CellRangeAddress(37,37,1,3));
                    sheet.addMergedRegion(new CellRangeAddress(37,37,6,8));
                    
                break;
                
                case 38:
                    header.createCell(0).setCellValue("J");
                    header.createCell(1).setCellValue("TOTAL DE ABASTECIMIENTO EN EL MES ("+periodoString+") (-) RDR*");
                    header.createCell(2);
                    header.createCell(3);
                    
                    header.createCell(4).setCellValue(getDouble(ime.getTotalAbastecimientoMed()));

                    header.createCell(5).setCellValue(getDouble(ime.getTotalGAdmin()));
                    header.createCell(6).setCellValue("(-) TOTAL DE GASTOS ADM EN EL MES "+periodoString);
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9).setCellValue("J");

                    header.getCell(0).setCellStyle(styleLeft);
                    header.getCell(9).setCellStyle(styleRight);
                    for(int i = 1; i < 9; i++){
                        
                        if(i==5){
                            header.getCell(i).setCellStyle(styleLeft);
                        }else{
                            header.getCell(i).setCellStyle(style);
                        }
                    }

                    sheet.addMergedRegion(new CellRangeAddress(38,38,1,3));
                    sheet.addMergedRegion(new CellRangeAddress(38,38,6,8));
                    
                break;
                
                case 39:
                    header.createCell(0).setCellValue("F+G+H+I+J");
                    header.createCell(1).setCellValue("SALDO DISPONIBLE PARA COMPRA DE MEDICAMENTOS PARA EL MES SIGUIENTE");
                    header.createCell(2);
                    header.createCell(3);
                    header.createCell(4).setCellFormula("E32+E33+E34-E35+E36+E37-E38-E39");

                    header.createCell(5).setCellFormula("F32+F33-F39-F36-F37");
                    header.createCell(6).setCellValue("SALDO DISPONIBLE PARA GASTOS ADM PARA EL MES SIGUIENTE");
                    header.createCell(7);
                    header.createCell(8);
                    header.createCell(9).setCellValue("F+G+H+I+J");

                    header.getCell(0).setCellStyle(styleLeftBottom);
                    header.getCell(9).setCellStyle(styleRightBottom);
                    for(int i = 1; i < 9; i++){
                        
                        if(i==5){
                            header.getCell(i).setCellStyle(styleLeftBottom);
                        }else{
                            header.getCell(i).setCellStyle(styleBottom);
                        }
                    }

                    sheet.addMergedRegion(new CellRangeAddress(39,39,1,3));
                    sheet.addMergedRegion(new CellRangeAddress(39,39,6,8));
                    
                break;
                
                
            }
        }
        
        for(int i = 5; i<=39;i++){
           sheet.getRow(i).setHeight((short)330);
        }
        sheet.getRow(6).setHeight((short)650);
        sheet.getRow(7).setHeight((short)650);
        sheet.getRow(39).setHeight((short)650);
        
        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");

    }
    
    private CellStyle getStyleRightBorderThick(HSSFWorkbook workbook) {

        CellStyle style = this.getCellHeadStyle(workbook);
        
        HSSFDataFormat format = workbook.createDataFormat();
        
        style.setDataFormat(format.getFormat(BuiltinFormats.getBuiltinFormat(0x2b)));
        
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setRightBorderColor(HSSFColor.BLACK.index);

        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THICK);
        return style;
    }
    
    private CellStyle getStyleRightTopBorderThick(HSSFWorkbook workbook) {

        CellStyle style = this.getCellHeadStyle(workbook);
        
        
        HSSFDataFormat format = workbook.createDataFormat();
        
        style.setDataFormat(format.getFormat(BuiltinFormats.getBuiltinFormat(0x2b)));
        
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setRightBorderColor(HSSFColor.BLACK.index);

        style.setBorderTop(HSSFCellStyle.BORDER_THICK);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THICK);
        return style;
    }
    
    private CellStyle getStyleRightBottomBorderThick(HSSFWorkbook workbook) {

        CellStyle style = this.getCellHeadStyle(workbook);
        
        HSSFDataFormat format = workbook.createDataFormat();
        
        style.setDataFormat(format.getFormat(BuiltinFormats.getBuiltinFormat(0x2b)));
        
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setRightBorderColor(HSSFColor.BLACK.index);

        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(HSSFCellStyle.BORDER_THICK);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THICK);
        return style;
    }
        
    private CellStyle getStyleLeftBorderThick(HSSFWorkbook workbook) {

        CellStyle style = this.getCellHeadStyle(workbook);
        
        HSSFDataFormat format = workbook.createDataFormat();
        
        style.setDataFormat(format.getFormat(BuiltinFormats.getBuiltinFormat(0x2b)));
        
        
        
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setRightBorderColor(HSSFColor.BLACK.index);

        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THICK);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        return style;
    }

    private CellStyle getStyleLeftTopBorderThick(HSSFWorkbook workbook) {

        CellStyle style = this.getCellHeadStyle(workbook);
        
        HSSFDataFormat format = workbook.createDataFormat();
        
        style.setDataFormat(format.getFormat(BuiltinFormats.getBuiltinFormat(0x2b)));
        
        
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setRightBorderColor(HSSFColor.BLACK.index);

        style.setBorderTop(HSSFCellStyle.BORDER_THICK);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THICK);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        return style;
    }

    private CellStyle getStyleLeftBottomBorderThick(HSSFWorkbook workbook) {

        CellStyle style = this.getCellHeadStyle(workbook);
        
        HSSFDataFormat format = workbook.createDataFormat();
        
        style.setDataFormat(format.getFormat(BuiltinFormats.getBuiltinFormat(0x2b)));
        
        
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setRightBorderColor(HSSFColor.BLACK.index);

        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(HSSFCellStyle.BORDER_THICK);
        style.setBorderLeft(HSSFCellStyle.BORDER_THICK);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        return style;
    }
    
    
    private CellStyle getStyleBottomThick(HSSFWorkbook workbook){
        CellStyle style = this.getCellHeadStyle(workbook);
        
        HSSFDataFormat format = workbook.createDataFormat();
        
        style.setDataFormat(format.getFormat(BuiltinFormats.getBuiltinFormat(0x2b)));
        
        
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(HSSFCellStyle.BORDER_THICK);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        
        return style;
    }
    
    private CellStyle getStyleTopThick(HSSFWorkbook workbook){
        CellStyle style = this.getCellHeadStyle(workbook);
        
        HSSFDataFormat format = workbook.createDataFormat();
        
        style.setDataFormat(format.getFormat(BuiltinFormats.getBuiltinFormat(0x2b)));
        
        
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        
        style.setBorderTop(HSSFCellStyle.BORDER_THICK);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        
        return style;
    }

    private CellStyle getCellStyleBorder(HSSFWorkbook workbook) {
        CellStyle style = this.getCellHeadStyle(workbook);

        HSSFDataFormat format = workbook.createDataFormat();
        
        style.setDataFormat(format.getFormat(BuiltinFormats.getBuiltinFormat(0x2b)));
        
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

    private CellStyle getCellStyleBorderThick(HSSFWorkbook workbook) {
        CellStyle style = this.getCellHeadStyle(workbook);

        HSSFDataFormat format = workbook.createDataFormat();
        
        style.setDataFormat(format.getFormat(BuiltinFormats.getBuiltinFormat(0x2b)));
        
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setRightBorderColor(HSSFColor.BLACK.index);

        style.setBorderTop(HSSFCellStyle.BORDER_THICK);
        style.setBorderBottom(HSSFCellStyle.BORDER_THICK);
        style.setBorderLeft(HSSFCellStyle.BORDER_THICK);
        style.setBorderRight(HSSFCellStyle.BORDER_THICK);

        return style;
    }
    

    private CellStyle getCellHeadStyle(HSSFWorkbook workbook) {

        HSSFPalette palette = workbook.getCustomPalette();
        palette.setColorAtIndex(HSSFColor.LIME.index, (byte) 235, (byte) 242, (byte) 246);
        HSSFDataFormat format = workbook.createDataFormat();
        
        
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(format.getFormat(BuiltinFormats.getBuiltinFormat(0x2b)));

        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 8);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font.setColor(HSSFColor.BLACK.index);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setWrapText(true);
        style.setFont(font);

        return style;
    }
    
    double getDouble(BigDecimal decimal) throws ParseException{
        DecimalFormat df = new DecimalFormat("0.00");
        String value = df.format(decimal.doubleValue());
        return df.parse(value).doubleValue();
    }
    
    double getDouble(int integer){
        return integer*1.0;
    }
}
