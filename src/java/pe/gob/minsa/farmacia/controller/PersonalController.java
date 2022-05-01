package pe.gob.minsa.farmacia.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Personal;
import pe.gob.minsa.farmacia.services.impl.PersonalService;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
@RequestMapping("/Personal")
public class PersonalController {

    @Autowired
    PersonalService personalService;

    JsonResponse jsonResponse;

    private ManagerDatatables getPersonalDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<Personal> personals = personalService.listar();

        managerDatatables.setiTotalRecords(0);

        for (int i = 0; i <= personals.size() - 1; ++i) {
            Personal c = personals.get(i);
            if (c.getNombreCompleto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || c.getNroDocumento().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                personals.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(personals, new Comparator<Personal>() {
            @Override
            public int compare(Personal o1, Personal o2) {
                switch (sortColumnIndex) {
                    case 1:
                        return o1.getNombreCompleto().toLowerCase().compareTo(o2.getNombreCompleto().toLowerCase()) * sortDirection;
                    case 2:
                        return o1.getTipoDocumento().toLowerCase().compareTo(o2.getTipoDocumento().toLowerCase()) * sortDirection;
                    case 3:
                        return o1.getNroDocumento().toLowerCase().compareTo(o2.getNroDocumento().toLowerCase()) * sortDirection;
                    case 4:
                        return o1.getUnidad().toLowerCase().compareTo(o2.getUnidad().toLowerCase()) * sortDirection;
                    case 5:
                        return o1.getCargo().toLowerCase().compareTo(o2.getCargo().toLowerCase()) * sortDirection;
                    default:
                        return 0;
                }
            }
        });

        managerDatatables.setiTotalDisplayRecords(personals.size());

        if (personals.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            personals = personals.subList(dataTablesParam.iDisplayStart, personals.size());
        } else {
            personals = personals.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(personals);

        return managerDatatables;
    }

    private ManagerDatatables getPersonalUnidad(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<Personal> personals = personalService.listarPorUnidad(request.getParameter("idUnidad"));

        managerDatatables.setiTotalRecords(0);

        for (int i = 0; i <= personals.size() - 1; ++i) {
            Personal c = personals.get(i);
            if (c.getNombreCompleto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || c.getNroDocumento().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                personals.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(personals, new Comparator<Personal>() {
            @Override
            public int compare(Personal o1, Personal o2) {
                switch (sortColumnIndex) {
                    case 0:

                    case 1:
                        return o1.getNombreCompleto().toLowerCase().compareTo(o2.getNombreCompleto().toLowerCase()) * sortDirection;
                    case 2:
                        return o1.getTipoDocumento().toLowerCase().compareTo(o2.getTipoDocumento().toLowerCase()) * sortDirection;
                    case 3:
                        return o1.getNroDocumento().toLowerCase().compareTo(o2.getNroDocumento().toLowerCase()) * sortDirection;
                    case 4:
                        return o1.getUnidad().toLowerCase().compareTo(o2.getUnidad().toLowerCase()) * sortDirection;
                    case 5:
                        return o1.getCargo().toLowerCase().compareTo(o2.getCargo().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(personals.size());

        if (personals.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            personals = personals.subList(dataTablesParam.iDisplayStart, personals.size());
        } else {
            personals = personals.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(personals);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarPersonal() {
        return new ModelAndView("Personal");
    }

    @RequestMapping(value = "/personalJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerPersonalsJSON(HttpServletRequest request, HttpServletResponse response) {
        return getPersonalDatatables(request, response);
    }

    @RequestMapping(value = "/personalPorUnidadJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerPersonalUnidadJSON(HttpServletRequest request, HttpServletResponse response) {
        return getPersonalUnidad(request, response);
    }
}
