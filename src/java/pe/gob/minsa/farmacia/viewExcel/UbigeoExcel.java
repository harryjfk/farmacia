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
import pe.gob.minsa.farmacia.domain.Ubigeo;
import pe.gob.minsa.farmacia.services.impl.UbigeoService;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class UbigeoExcel extends AbstractExcelView {

    @Autowired
    UbigeoService ubigeoService;

    public void setUbigeoService(UbigeoService ubigeoService) {
        this.ubigeoService = ubigeoService;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String idUbigeo = request.getParameter("idUbigeo");

        String titulo = "";
        List<String> cabeceras = new ArrayList<String>();

        if (idUbigeo == null) {
            titulo = "Reporte de Departamentos";
        } else {
            Ubigeo ubigeo = ubigeoService.obtenerPorId(idUbigeo);

            if (idUbigeo.length() == 2) {
                titulo = "Reporte de Provincias de " + ubigeo.getNombreUbigeo();
            } else if (idUbigeo.length() == 4) {
                titulo = "Reporte de Distritos de " + ubigeo.getNombreUbigeo();
            }
        }

        cabeceras.add("Código");
        cabeceras.add("Ubigeo");
        cabeceras.add("Estado");

        List<Ubigeo> ubigeos = (List<Ubigeo>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);

        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        int rowCount = UtilExportExcel.rowDataCount;

        for (Ubigeo ubigeo : ubigeos) {
            HSSFRow aRow = sheet.createRow(rowCount++);

            aRow.createCell(0).setCellValue(ubigeo.getIdUbigeo());            
            aRow.createCell(1).setCellValue(ubigeo.getNombreUbigeo());            
            aRow.createCell(2).setCellValue(ubigeo.getActivoTexto());
            
            for(int i = 0; i<cabeceras.size(); ++i){
                aRow.getCell(i).setCellStyle(rowStyle);
            }
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }
}
