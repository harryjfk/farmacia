<div class="row" id="mgn-bpp">
    <div class="col-sm-12 col-md-12">
        <form id="bppfrm" class="form_validation_reg form-horizontal" autocomplete="off">
            <div class="col-lg-6 col-md-6 col-sm-6">
                <div class="form-group">
                    <label for="cliente" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Cliente</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <input id="cliente" name="cliente" class="form-control" />
                    </div>
                    <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
                        <div id="seleccionaCli" class="splashy-box_add" 
                             style="height: 16px;
                             margin-top: 10px;
                             margin-left: -22px;
                             cursor: pointer;
                             "></div>
                        <div></div></div>
                </div>
                <div class="form-group">
                    <label for="diagnostico" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Diag&oacute;stico CIE</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <input id="diagnostico" name="diagnostico" class="form-control"/>
                    </div>
                    <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
                        <div id="seleccionaDiag" class="splashy-box_add" 
                             style="height: 16px;
                             margin-top: 10px;
                             margin-left: -22px;
                             cursor: pointer;
                             "></div>
                        <div></div></div>
                </div>
                 <div class="form-group">
                    <label for="turno" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Turno</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <select id="turno" name="turno" class="form-control"></select>
                    </div>
                </div>
            </div>
            <div class="col-lg-6 col-md-6 col-sm-6">
                <div class="form-group">
                    <label for="fechaRegistro" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Fecha de Registro</label>
                    <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                        <input id="fechaRegistro" name="fechaRegistro" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="periodo" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Per&iacute;odo</label>
                    <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                        <input id="periodo" name="periodo" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="numeroDeReceta" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">N&uacute;mero De Receta</label>
                    <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                        <input id="numeroDeReceta" name="numeroDeReceta" class="form-control" />
                    </div>
                </div>
            </div>
            <button id="filtrarbtn" class="btn btn-primary">Consultar</button>
        </form>
    </div>
</div>