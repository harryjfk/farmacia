package pe.gob.minsa.farmacia.viewExcel;

import java.text.SimpleDateFormat;
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
import pe.gob.minsa.farmacia.domain.dto.IciDetalleDto;
import static pe.gob.minsa.farmacia.util.AbstractITextPdfView.getFormatoFecha;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class IciExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<IciDetalleDto> iciDetalles = (List<IciDetalleDto>) model.get("Data");        

        UtilExportExcel utilExportExcel = new UtilExportExcel();
        
        String titulo = "Formato ICI";
        
        if(request.getParameter("idPeriodo") != null){
            Periodo periodo = new Periodo();
            periodo.setIdPeriodo(Integer.parseInt(request.getParameter("idPeriodo")));
            titulo = titulo + " - " + periodo.getNombreMes() + " " + String.valueOf(periodo.getAnio());
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);

        CellStyle style = this.getCellStyleBorder(workbook);
        CellStyle styleRight = this.getStyleRightBorderDouble(workbook);
        CellStyle styleWithoutBorder = this.getCellHeadStyle(workbook);
        CellStyle styleOnlyRightBorder = this.getStyleOnlyRightBorderDouble(workbook);

        HSSFRow header = sheet.createRow(5);
        header.createCell(0).setCellValue("PRODUCTO FARMACEUTICO");
        header.createCell(1);
        header.createCell(2);
        header.createCell(3).setCellValue("PRECIO DE OPERACION");
        header.createCell(4).setCellValue("SALDO MES ANTERIOR");
        header.createCell(5).setCellValue("INGRESOS");
        header.createCell(6).setCellValue("SALIDAS DE MEDICAMENTOS");
        header.createCell(20).setCellValue("SALDO FIN. DISPONIBLE");
        header.createCell(21).setCellValue("FECHA DE EXPIRAC. MAS PROXIMA");
        header.createCell(22).setCellValue("REQUERIMIENTO MES SGTE");

        for (int i = 7; i < 20; ++i) {
            header.createCell(i);
        }

        for (int i = 0; i < 23; ++i) {
            if (i == 5 || i == 19) {
                header.getCell(i).setCellStyle(styleRight);
            } else {
                header.getCell(i).setCellStyle(style);
            }
        }

        header.setHeight((short) 600);

        //PRODUCTO FARMACEUTICO
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 2));

        //SALIDAS DE MEDICAMENTOS
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 6, 19));

        sheet.addMergedRegion(new CellRangeAddress(5,8,3,3));
        sheet.addMergedRegion(new CellRangeAddress(5,8,4,4));
        sheet.addMergedRegion(new CellRangeAddress(5,8,5,5));
        
        sheet.addMergedRegion(new CellRangeAddress(5,8,20,20));
        sheet.addMergedRegion(new CellRangeAddress(5,8,21,21));
        sheet.addMergedRegion(new CellRangeAddress(5,8,22,22));
        
        
        sheet.setColumnWidth(0, 2195);
        sheet.setColumnWidth(1, 10350);
        for (int i = 3; i < 22; ++i) {
            sheet.setColumnWidth(i, 2744);
        }

        HSSFRow subheader = sheet.createRow(6);
        subheader.createCell(0).setCellValue("CODIGO");
        subheader.createCell(1).setCellValue("DESCRIPCION");
        subheader.createCell(2).setCellValue("UNIDAD CONSUMO");
        subheader.createCell(6).setCellValue("CONSUMOS");
        subheader.createCell(15).setCellValue("OTRAS SALIDAS");
        subheader.createCell(19).setCellValue("TOTAL");

        for (int i = 3; i < 6; ++i) {
            subheader.createCell(i);
        }

        for (int i = 7; i < 15; ++i) {
            subheader.createCell(i);
        }

        for (int i = 16; i < 23; ++i) {
            subheader.createCell(i);
        }

        for (int i = 0; i < 23; ++i) {
            if (i == 5 || i == 19) {
                subheader.getCell(i).setCellStyle(styleRight);
            } else {
                subheader.getCell(i).setCellStyle(style);
            }
        }

        sheet.addMergedRegion(new CellRangeAddress(6, 6, 6, 14));

        sheet.addMergedRegion(new CellRangeAddress(6, 6, 15, 18));

        HSSFRow subheaderTwo = sheet.createRow(7);

        for (int i = 0; i < 23; ++i) {
            switch (i) {
                case 6:
                    subheaderTwo.createCell(i).setCellValue("VENTAS");
                    break;
                case 7:
                    subheaderTwo.createCell(i).setCellValue("S.I.S.");
                    break;
                case 8:
                    subheaderTwo.createCell(i).setCellValue("INTERV. SANIT.");
                    break;
                case 9:
                    subheaderTwo.createCell(i).setCellValue("F.PERDIDA (I. ZOONOSIS)");
                    break;
                case 10:
                    subheaderTwo.createCell(i).setCellValue("DEFENSA NACIONAL");
                    break;
                case 11:
                    subheaderTwo.createCell(i).setCellValue("EXONERACION");
                    break;
                case 12:
                    subheaderTwo.createCell(i).setCellValue("SOAT");
                    break;
                case 13:
                    subheaderTwo.createCell(i).setCellValue("CREDITO HOSP.");
                    break;
                case 14:
                    subheaderTwo.createCell(i).setCellValue("OTROS CONV.");
                    break;
                case 15:
                    subheaderTwo.createCell(i).setCellValue("DEVOLUCION");
                    break;
                case 16:
                    subheaderTwo.createCell(i).setCellValue("DEVOLUCION QUIEBRE STOCK");
                    break;
                case 17:
                    subheaderTwo.createCell(i).setCellValue("VENCIDOS");
                    break;
                case 18:
                    subheaderTwo.createCell(i).setCellValue("MERMA");
                    break;
                case 19:
                    subheaderTwo.createCell(i).setCellValue("SALIDAS SUM(G..Q)");
                    break;
                default:
                    subheaderTwo.createCell(i);
                    break;
            }
        }

        for (int i = 0; i < 23; ++i) {
            if (i == 5) {
                subheaderTwo.getCell(i).setCellStyle(styleRight);
            } else {
                subheaderTwo.getCell(i).setCellStyle(style);
            }
        }

        subheaderTwo.getCell(19).setCellStyle(styleRight);
        subheaderTwo.setHeight((short) 450);

        HSSFRow subheaderThree = sheet.createRow(8);
        for (int i = 0; i < 23; ++i) {
            subheaderThree.createCell(i);
            if (i == 5 || i==19) {
                subheaderThree.getCell(i).setCellStyle(styleRight);
            } else {
                subheaderThree.getCell(i).setCellStyle(style);
            }
        }

        sheet.addMergedRegion(new CellRangeAddress(6, 8, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(6, 8, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(6 ,8, 2, 2));
        
        for (int i = 6; i < 19; ++i) {
            sheet.addMergedRegion(new CellRangeAddress(7, 8, i, i));
            sheet.addMergedRegion(new CellRangeAddress(7, 8, i, i));
            sheet.addMergedRegion(new CellRangeAddress(7, 8, i, i));
            sheet.addMergedRegion(new CellRangeAddress(7, 8, i, i));
        }

        sheet.addMergedRegion(new CellRangeAddress(7, 8, 19, 19));

        HSSFRow subheadeFour = sheet.createRow(9);
        for (int i = 0; i < 23; ++i) {
            switch (i) {
                case 0:
                    subheadeFour.createCell(i).setCellValue("A");
                    break;
                case 1:
                    subheadeFour.createCell(i).setCellValue("B");
                    break;
                case 2:
                    subheadeFour.createCell(i).setCellValue("C");
                    break;
                case 3:
                    subheadeFour.createCell(i).setCellValue("D");
                    break;
                case 4:
                    subheadeFour.createCell(i).setCellValue("E");
                    break;
                case 5:
                    subheadeFour.createCell(i).setCellValue("F");
                    break;
                case 6:
                    subheadeFour.createCell(i).setCellValue("G");
                    break;
                case 7:
                    subheadeFour.createCell(i).setCellValue("H");
                    break;
                case 8:
                    subheadeFour.createCell(i).setCellValue("I");
                    break;
                case 9:
                    subheadeFour.createCell(i).setCellValue("J");
                    break;
                case 10:
                    subheadeFour.createCell(i).setCellValue("K");
                    break;
                case 11:
                    subheadeFour.createCell(i).setCellValue("L");
                    break;
                case 12:
                    subheadeFour.createCell(i).setCellValue("M");
                    break;
                case 13:
                    subheadeFour.createCell(i).setCellValue("N");
                    break;
                case 14:
                    subheadeFour.createCell(i).setCellValue("O");
                    break;
                case 15:
                    subheadeFour.createCell(i).setCellValue("P");
                    break;
                case 16:
                    subheadeFour.createCell(i).setCellValue("Q");
                    break;
                case 17:
                    subheadeFour.createCell(i).setCellValue("R");
                    break;
                case 18:
                    subheadeFour.createCell(i).setCellValue("S");
                    break;
                case 19:
                    subheadeFour.createCell(i).setCellValue("T");
                    break;
                case 20:
                    subheadeFour.createCell(i).setCellValue("U");
                    break;
                case 21:
                    subheadeFour.createCell(i).setCellValue("V");
                    break;
                case 22:
                    subheadeFour.createCell(i).setCellValue("W");
                    break;
                default:
                    break;
            }

            if (i == 5 || i == 19) {
                subheadeFour.getCell(i).setCellStyle(styleRight);
            } else {
                subheadeFour.getCell(i).setCellStyle(style);
            }
        }

        for (int i = 0; i < iciDetalles.size(); ++i) {
            HSSFRow rowDatos = sheet.createRow(i + 10);

            for (int c = 0; c < 23; ++c) {
                switch (c) {
                    case 0:
                        rowDatos.createCell(c).setCellValue(iciDetalles.get(i).getIdProducto());
                        break;
                    case 1:
                        rowDatos.createCell(c).setCellValue(iciDetalles.get(i).getDescripcionProducto());
                        break;
                    case 2:
                        rowDatos.createCell(c).setCellValue(iciDetalles.get(i).getNombreUnidadMedida().substring(0, 4));
                        break;
                    case 3:
                        rowDatos.createCell(c).setCellValue(iciDetalles.get(i).getPrecioOperacion().doubleValue());
                        break;
                    case 4:
                        rowDatos.createCell(c).setCellValue(iciDetalles.get(i).getSaldoAnterior().doubleValue());
                        break;
                    case 5:
                        rowDatos.createCell(c).setCellValue(iciDetalles.get(i).getIngresos().doubleValue());
                        break;
                    case 6:
                        rowDatos.createCell(c).setCellValue(iciDetalles.get(i).getVentas().doubleValue());
                        break;
                    case 7:
                        rowDatos.createCell(c).setCellValue(iciDetalles.get(i).getSis().doubleValue());
                        break;
                    case 8:
                        rowDatos.createCell(c).setCellValue(iciDetalles.get(i).getIntervSanit().doubleValue());
                        break;
                    case 9:
                        rowDatos.createCell(c).setCellValue(iciDetalles.get(i).getFactPerd().doubleValue());
                        break;
                    case 10:
                        rowDatos.createCell(c).setCellValue(iciDetalles.get(i).getDefensaNacional().doubleValue());
                        break;
                    case 11:
                        rowDatos.createCell(c).setCellValue(iciDetalles.get(i).getExoneracion().doubleValue());
                        break;
                    case 12:
                        rowDatos.createCell(c).setCellValue(iciDetalles.get(i).getSoat().doubleValue());
                        break;
                    case 13:
                        rowDatos.createCell(c).setCellValue(iciDetalles.get(i).getCreditoHospitalario().doubleValue());
                        break;
                    case 14:
                        rowDatos.createCell(c).setCellValue(iciDetalles.get(i).getOtrosConvenios().doubleValue());
                        break;
                    case 15:
                        rowDatos.createCell(c).setCellValue(iciDetalles.get(i).getDevolucion().doubleValue());
                        break;
                    case 16:
                        rowDatos.createCell(c).setCellValue(iciDetalles.get(i).getDevolucionQuiebreStock().doubleValue());                        
                        break;
                    case 17:
                        rowDatos.createCell(c).setCellValue(iciDetalles.get(i).getVencido().doubleValue());                        
                        break;
                    case 18:
                        rowDatos.createCell(c).setCellValue(iciDetalles.get(i).getMerma().doubleValue());                        
                        break;
                    case 19:
                        rowDatos.createCell(c).setCellValue(iciDetalles.get(i).getTotalSalidas());                        
                        break;
                    case 20:
                        rowDatos.createCell(c).setCellValue(iciDetalles.get(i).getSaldoFinal().doubleValue());                        
                        break;
                    case 21:
                        Calendar grego = new GregorianCalendar();
                        grego.setTime(iciDetalles.get(i).getVencimiento());
                        rowDatos.createCell(c).setCellValue(dateFormat.format(grego.getTime()));                        
                        break;
                    case 22:
                        rowDatos.createCell(c).setCellValue(iciDetalles.get(i).getRequerimiento().doubleValue());                       
                        break;
                    default:
                        break;
                }

                if (c == 5 || c == 19) {
                    rowDatos.getCell(c).setCellStyle(styleOnlyRightBorder);
                } else {
                    rowDatos.getCell(c).setCellStyle(styleWithoutBorder);
                }
            }
        }

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
