package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.viewExcel;

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
import pe.gob.minsa.farmacia.util.UtilExportExcel;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.dtos.TarjetaControlDto;

public class TarjetaControlVisibleExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        List<TarjetaControlDto> tarjetasControlDto = (List<TarjetaControlDto>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Tarjeta de Control Visibe";

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Insumo");
        cabeceras.add("Unidad de Medida");
        cabeceras.add("Ingreso");
        cabeceras.add("Salida");
        cabeceras.add("Saldo");

        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        int rowCount = UtilExportExcel.rowDataCount;

        for (TarjetaControlDto tarjetaControlDto : tarjetasControlDto) {
            HSSFRow aRow = sheet.createRow(rowCount++);

            aRow.createCell(0).setCellValue(tarjetaControlDto.getInsumo().getNombre());
            aRow.createCell(1).setCellValue(tarjetaControlDto.getInsumo().getUnidad().getNombreUnidadMedida());
            aRow.createCell(2).setCellValue(tarjetaControlDto.getIngresos());
            aRow.createCell(3).setCellValue(tarjetaControlDto.getSalidas());
            aRow.createCell(4).setCellValue(tarjetaControlDto.getSaldo());

            for (int i = 0; i < cabeceras.size(); i++) {
                aRow.getCell(i).setCellStyle(rowStyle);
            }
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }
}
