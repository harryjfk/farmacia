package pe.gob.minsa.farmacia.viewExcel;

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
import pe.gob.minsa.farmacia.domain.dto.TarjetaControlDto;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class TarjetaControlVisibleExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        List<TarjetaControlDto> tarjetasControlDto = (List<TarjetaControlDto>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Tarjeta de Control Visibe (Kardex)";

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("F. Registro");
        cabeceras.add("Tipo");
        cabeceras.add("N°  Mov.");
        cabeceras.add("Producto");
        cabeceras.add("Concepto");
        cabeceras.add("Lote");
        cabeceras.add("Fecha Vcto.");
        cabeceras.add("Ingresos");
        cabeceras.add("Salidas");
        cabeceras.add("Saldo");

        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        int rowCount = UtilExportExcel.rowDataCount;

        SimpleDateFormat dateFormat = new SimpleDateFormat(utilExportExcel.getFormatoFecha());
        Calendar grego = new GregorianCalendar();

        for (TarjetaControlDto tarjetaControlDto : tarjetasControlDto) {
            HSSFRow aRow = sheet.createRow(rowCount++);

            grego.setTimeInMillis(tarjetaControlDto.getFechaRegistro().getTime());
            aRow.createCell(0).setCellValue(dateFormat.format(grego.getTime()));
            aRow.createCell(1).setCellValue(tarjetaControlDto.getTipoMovimiento().toString());
            aRow.createCell(2).setCellValue(tarjetaControlDto.getNumeroMovimiento());
            aRow.createCell(3).setCellValue(tarjetaControlDto.getProducto());
            aRow.createCell(4).setCellValue(tarjetaControlDto.getNombreConcepto());
            aRow.createCell(5).setCellValue(tarjetaControlDto.getLote());

            if (tarjetaControlDto.getFechaVencimiento() == null) {
                aRow.createCell(6).setCellValue("");
            } else {
                grego.setTimeInMillis(tarjetaControlDto.getFechaVencimiento().getTime());
                aRow.createCell(6).setCellValue(dateFormat.format(grego.getTime()));
            }

            aRow.createCell(7).setCellValue(tarjetaControlDto.getIngresos());
            aRow.createCell(8).setCellValue(tarjetaControlDto.getSalidas());
            aRow.createCell(9).setCellValue(tarjetaControlDto.getSaldo());

            for (int i = 0; i < cabeceras.size(); ++i) {
                aRow.getCell(i).setCellStyle(rowStyle);
            }
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }
}
