package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.viewExcel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.FVenta;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.dtos.CorteCajaDTO;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.dtos.CorteCajaVentaDTO;
import pe.gob.minsa.geniusplex.web.objects.FilterData;

public class CorteCajaExcel extends AbstractExcelView {

    @Override
    @SuppressWarnings("null")
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        List<Cliente> clientes = (List<Cliente>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        CorteCajaDTO cajaDTO = (CorteCajaDTO) model.get("CorteCaja");
        FilterData fData = (FilterData) model.get("fData");
        String nombrePc = "";
        FVenta venta = null;
        if (cajaDTO != null && !cajaDTO.getTblVentas().isEmpty()) {
            venta = cajaDTO.getTblVentas().get(0).getVenta();
            nombrePc = venta.getPuntoDeVenta().getNombrePc();
        }

        String titulo = "Corte de Caja de ";
        if (nombrePc != null && nombrePc.length() > 0) {
            titulo = "Corte de Caja de " + nombrePc;
        }

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        final boolean periodo = fData.getParams().containsKey("periodo");
        final boolean ptoVenta = fData.getParams().containsKey("puntoDeVenta:id");
        final boolean turno = fData.getParams().containsKey("turno:idTurno");
        final boolean vendedor = fData.getParams().containsKey("vendedor:idVendedor");
        final boolean fecha = fData.getParams().containsKey("ventafechaRegistro");
        int rowCount = 0;
        if (periodo || ptoVenta || turno || vendedor || fecha) {

            if (periodo) {
                Object per = fData.getParams().get("periodo");
                HSSFRow aRow = sheet.createRow(rowCount++);
                aRow.createCell(0).setCellValue("Periodo");
                aRow.getCell(0).setCellStyle(rowStyle);
                aRow.createCell(1).setCellValue(per.toString());
                aRow.getCell(1).setCellStyle(rowStyle);
            }
            final boolean ventaNoEsNull = venta != null;
            if (ptoVenta && ventaNoEsNull) {
                HSSFRow aRow = sheet.createRow(rowCount++);
                aRow.createCell(0).setCellValue("Punto de Venta");
                aRow.getCell(0).setCellStyle(rowStyle);
                aRow.createCell(1).setCellValue(nombrePc);
                aRow.getCell(1).setCellStyle(rowStyle);
            }
            if (turno && ventaNoEsNull) {
                HSSFRow aRow = sheet.createRow(rowCount++);
                aRow.createCell(0).setCellValue("Turno");
                aRow.getCell(0).setCellStyle(rowStyle);
                aRow.createCell(1).setCellValue(venta.getTurno().getDescripcion());
                aRow.getCell(1).setCellStyle(rowStyle);
            }
            if (vendedor && ventaNoEsNull) {
                HSSFRow aRow = sheet.createRow(rowCount++);
                aRow.createCell(0).setCellValue("Turno");
                aRow.getCell(0).setCellStyle(rowStyle);
                aRow.createCell(1).setCellValue(venta.getVendedor().getNombreVendedor());
                aRow.getCell(1).setCellStyle(rowStyle);
            }
            if (fecha) {
                HSSFRow aRow = sheet.createRow(rowCount++);
                aRow.createCell(0).setCellValue("Fecha de Registro");
                aRow.getCell(0).setCellStyle(rowStyle);
                aRow.createCell(1).setCellValue(fData.getParams().get("ventafechaRegistro").toString());
                aRow.getCell(1).setCellStyle(rowStyle);
            }
            rowCount = rowCount > 0 ? (rowCount + 1) : 0;
        }

        List<String> cabeceras = new ArrayList<String>();
        cabeceras = Arrays.asList("Anulada", "Nro. Oper.", "Nro. Docum.", "Tipo", "Cliente", "Cajero", "F.P.", "Venta", "IGV", "Total");
        rowCount = addHeaderColumn(sheet, rowCount, workbook, cabeceras);

        for (CorteCajaVentaDTO tblVenta : cajaDTO.getTblVentas()) {
            int cellIdx = 0;
            Boolean anulada = tblVenta.getVenta().isAnulada();
            anulada = anulada == null ? false : anulada;

            HSSFRow aRow = sheet.createRow(rowCount++);

            aRow.createCell(cellIdx).setCellValue(anulada ? "SI" : "NO");
            aRow.getCell(cellIdx++).setCellStyle(rowStyle);
            
            aRow.createCell(cellIdx).setCellValue(tblVenta.getVenta().getNroOperacion());
            aRow.getCell(cellIdx++).setCellStyle(rowStyle);
            
            aRow.createCell(cellIdx).setCellValue(tblVenta.getVenta().getNroVenta());
            aRow.getCell(cellIdx++).setCellStyle(rowStyle);
            
            aRow.createCell(cellIdx).setCellValue(tblVenta.getVenta().getDocTipo());
            aRow.getCell(cellIdx++).setCellStyle(rowStyle);
            
            aRow.createCell(cellIdx).setCellValue(tblVenta.getVenta().getFormaDePago().getDescripcion());
            aRow.getCell(cellIdx++).setCellStyle(rowStyle);
            
            aRow.createCell(cellIdx).setCellValue(tblVenta.getVenta().getCliente().getNombreCliente());
            aRow.getCell(cellIdx++).setCellStyle(rowStyle);
            
            aRow.createCell(cellIdx).setCellValue(tblVenta.getVenta().getVendedor().getNombreVendedor());
            aRow.getCell(cellIdx++).setCellStyle(rowStyle);
            
            aRow.createCell(cellIdx).setCellValue(tblVenta.getTotalVenta().toString());
            aRow.getCell(cellIdx++).setCellStyle(rowStyle);
            
            aRow.createCell(cellIdx).setCellValue(tblVenta.getVenta().getImpuestoPreventa().toString());
            aRow.getCell(cellIdx++).setCellStyle(rowStyle);
            
            aRow.createCell(cellIdx).setCellValue(tblVenta.getVenta().getPreventaNetoAPagar());
            aRow.getCell(cellIdx++).setCellStyle(rowStyle);
        }

        rowCount++;
        //totales
        HSSFRow tRow = sheet.createRow(rowCount++);
        tRow.createCell(6).setCellValue("Total Recaudado");
        tRow.getCell(6).setCellStyle(getBoldCellStyle(workbook));
        tRow.createCell(7).setCellValue(cajaDTO.getTotalVenta().toString() + "(Venta)");
        tRow.getCell(7).setCellStyle(rowStyle);
        tRow.createCell(8).setCellValue(cajaDTO.getTotalImpuestoIGV() + "(IGV)");
        tRow.getCell(8).setCellStyle(rowStyle);
        tRow.createCell(9).setCellValue(cajaDTO.getOverallTotal() + "(IGV)");
        tRow.getCell(9).setCellStyle(rowStyle);
        
        HSSFRow cRow = sheet.createRow(rowCount++);
        cRow.createCell(0).setCellValue("Resumen de Forma de Pago");
        cRow.createCell(0).setCellStyle(getBoldCellStyle(workbook));
        rowCount = addHeaderColumn(sheet, rowCount, workbook, Arrays.asList("Cantidad", "Monto"));
        HashMap<String, String> resumenFormPago = cajaDTO.getResumenFormPago();
        for (Map.Entry<String, String> entrySet : resumenFormPago.entrySet()) {
            String key = entrySet.getKey();
            String value = entrySet.getValue();
            String[] split = value.split("-");

            HSSFRow aRow = sheet.createRow(rowCount++);
            int cellIdx = 0;

            aRow.createCell(cellIdx).setCellValue(key);
            aRow.getCell(cellIdx++).setCellStyle(rowStyle);
            aRow.createCell(cellIdx).setCellValue(split[0]);
            aRow.getCell(cellIdx++).setCellStyle(rowStyle);
            aRow.createCell(cellIdx).setCellValue(split[1]);
            aRow.getCell(cellIdx++).setCellStyle(rowStyle);
        }
        rowCount++;
        HSSFRow aRow = sheet.createRow(rowCount++);
        aRow.createCell(0).setCellValue("Total de Anulados");
        aRow.createCell(0).setCellStyle(getBoldCellStyle(workbook));
        rowCount = addHeaderColumn(sheet, rowCount, workbook, Arrays.asList("Cantidad", "Monto"));
        HSSFRow row2 = sheet.createRow(rowCount++);
        row2.createCell(0).setCellValue(cajaDTO.getCantidadAnulados());
        row2.getCell(0).setCellStyle(rowStyle);
        row2.createCell(1).setCellValue(cajaDTO.getMontoAnulados().toString());
        row2.getCell(1).setCellStyle(rowStyle);

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }

    private int addHeaderColumn(HSSFSheet sheet, int rowCount, HSSFWorkbook workbook, List<String> fpHeadLbls) {
        HSSFRow row1 = sheet.createRow(rowCount);
        CellStyle style = getBoldCellStyle(workbook);
        int index = 0;
        for (String formaPagoLbl : fpHeadLbls) {
            row1.createCell(index).setCellValue(formaPagoLbl);
            row1.getCell(index++).setCellStyle(style);
        }
        return rowCount + 1;
    }

    private CellStyle getBoldCellStyle(HSSFWorkbook workbook) {
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
