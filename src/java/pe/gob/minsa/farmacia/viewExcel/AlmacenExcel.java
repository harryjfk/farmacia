package pe.gob.minsa.farmacia.viewExcel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import pe.gob.minsa.farmacia.domain.Almacen;
import pe.gob.minsa.farmacia.services.impl.AlmacenService;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class AlmacenExcel extends AbstractExcelView {

    @Autowired
    AlmacenService almacenService;

    public void setAlmacenService(AlmacenService almacenService) {
        this.almacenService = almacenService;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String idAlmacenPadre = request.getParameter("idAlmacenPadre");

        String titulo = "";
        List<String> cabeceras = new ArrayList<String>();

        if (idAlmacenPadre == null) {
            titulo = "Reporte de Almacenes";

            cabeceras.add("Código");
            cabeceras.add("Almacén");
            cabeceras.add("Subalmacenes");
            cabeceras.add("Estado");
        } else {
            Almacen almacen = almacenService.obtenerPorId(Integer.parseInt(idAlmacenPadre));
            titulo = "Reporte de Subalmacenes de " + almacen.getDescripcion();

            cabeceras.add("Código");
            cabeceras.add("Almacén");            
            cabeceras.add("Estado");
        }

        List<Almacen> almacenes = (List<Almacen>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);

        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        int rowCount = UtilExportExcel.rowDataCount;

        if (idAlmacenPadre == null) {
            for (Almacen almacen : almacenes) {
                HSSFRow aRow = sheet.createRow(rowCount++);

                aRow.createCell(0).setCellValue(almacen.getIdAlmacen());
                aRow.getCell(0).setCellStyle(rowStyle);
                aRow.createCell(1).setCellValue(almacen.getDescripcion());
                aRow.getCell(1).setCellStyle(rowStyle);
                aRow.createCell(2).setCellValue(almacen.getCantidadHijos());
                aRow.getCell(2).setCellStyle(rowStyle);
                aRow.createCell(3).setCellValue(almacen.getActivoTexto());
                aRow.getCell(3).setCellStyle(rowStyle);
            }
        } else {
            for (Almacen almacen : almacenes) {
                HSSFRow aRow = sheet.createRow(rowCount++);

                aRow.createCell(0).setCellValue(almacen.getIdAlmacen());
                aRow.getCell(0).setCellStyle(rowStyle);
                aRow.createCell(1).setCellValue(almacen.getDescripcion());
                aRow.getCell(1).setCellStyle(rowStyle);
                aRow.createCell(2).setCellValue(almacen.getActivoTexto());
                aRow.getCell(2).setCellStyle(rowStyle);
            }
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }
}
