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
                <label for="codPaciente" class="col-md-2 col-sm-2 col-lg-2 col-xs-2 control-label">Código del Paciente</label>
                <div class="col-md-4 col-sm-4 col-lg-4 col-xs-4S">
                    <input id="codPaciente" name="codPaciente" class="form-control" disabled="disabled" />
                </div>
            </div>
            <div class="form-group">
                <label for="historia" class="col-md-2 col-sm-2 col-lg-2 col-xs-2 control-label">Historia Clinica</label>
                <div class="col-md-4 col-sm-4 col-lg-4 col-xs-4">
                    <input id="historia" name="historia" class="form-control" disabled="disabled" />
                </div>
            </div>
            <div class="form-group">
                <label for="sexo" class="col-md-2 col-sm-2 col-lg-2 col-xs-2 control-label">Sexo</label>
                <div class="col-md-4 col-sm-4 col-lg-4 col-xs-4">
                    <input id="sexo" name="sexo" class="form-control" disabled="disabled" />
                </div>
            </div>
            <div class="form-group">
                <label for="fechaNacimiento" class="col-md-2 col-sm-2 col-lg-2 col-xs-2 control-label">Fecha de Nacimiento</label>
                <div class="col-md-4 col-sm-4 col-lg-4 col-xs-4">
                    <input id="fechaNacimiento" name="fechaNacimiento" class="form-control" disabled="disabled" />
                </div>
            </div>
            <div class="form-group">
                <label for="fechaIni" class="col-md-2 col-sm-2 col-lg-2 col-xs-2 control-label">Fecha de Inicio</label>
                <div class="col-md-4 col-sm-4 col-lg-4 col-xs-4">
                    <input id="fechaIni" name="fechaIni" class="form-control" />
                </div>
            </div>
            <div class="form-group">
                <label for="fechaFin" class="col-md-2 col-sm-2 col-lg-2 col-xs-2 control-label">Fecha de Fin</label>
                <div class="col-md-4 col-sm-4 col-lg-4 col-xs-4">
                    <input id="fechaFin" name="fechaFin" class="form-control" />
                </div>
            </div>
            <div class="form-group">
                <div class="col-md-4 col-sm-4 col-lg-4 col-xs-4">
                    <button id="consultar" class="btn btn-primary">Consultar</button>
                </div>
            </div>
        </form>
    </div>
</div>