<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-sm-12 col-md-12">
        <form id="intfilterfrm" class="form_validation_reg form-horizontal" autocomplete="off">
            <div class="form-group">
                <label for="paciente" class="col-md-2 col-sm-2 col-lg-2 col-xs-2 control-label">Paciente</label>
                <div class="col-md-4 col-sm-4 col-lg-4 col-xs-4">
                    <input id="paciente" name="paciente" class="form-control" />
                </div>
                <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
                        <div id="seleccionaPac" class="splashy-box_add" 
                             style="height: 16px;
                             margin-top: 10px;
                             margin-left: -22px;
                             cursor: pointer;
                             "></div>
                        <div></div></div>
            </div>
            <div class="form-group">
                <label for="medico" class="col-md-2 col-sm-2 col-lg-2 col-xs-2 control-label">M&eacute;dico</label>
                <div class="col-md-4 col-sm-4 col-lg-4 col-xs-4">
                    <input id="medico" name="medico" class="form-control" />
                </div>
                <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
                        <div id="seleccionaMed" class="splashy-box_add" 
                             style="height: 16px;
                             margin-top: 10px;
                             margin-left: -22px;
                             cursor: pointer;
                             "></div>
                        <div></div></div>
            </div>
            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-10">
                    <button id="intfilterbtn"  type="submit" class="btn btn-primary">Consultar</button>
                    <button id="intsavebtn"  type="submit" class="btn btn-success">Guardar cambios</button>
                </div>
            </div>
        </form>
        <div class="hidden" id="cannot-add-kit-mssg">
            <p>No se encontraron productos en el sistema, por lo que no podr&aacute; crear nuevas intervenciones</p>
        </div>
    </div>
</div>
