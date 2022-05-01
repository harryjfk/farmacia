<div class="row">
    <div class="col-sm-12 col-md-12">
        <form id="mdl-frm" class="form_validation_reg form-horizontal" autocomplete="off">
            <div class="col-lg-6 col-md-6 col-sm-6">
                <div class="form-group">
                    <label for="servicio" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Servicio</label>
                    <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                        <input id="servicio" name="servicio" class="form-control"  />
                    </div>
                </div>
                <div class="form-group">
                    <label for="fecha" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Fecha</label>
                    <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                        <input id="fecha" name="fecha" class="form-control"  />
                    </div>
                </div>
                <div class="form-group">
                    <label for="edad" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Edad</label>
                    <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                        <input id="edad" name="edad" class="form-control"  />
                    </div>
                </div>
                <div class="form-group">
                    <label for="paciente" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Paciente</label>
                    <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                        <input id="paciente" name="paciente" class="form-control" disabled="disabled" />

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
            </div>
            <div class="col-lg-6 col-md-6 col-sm-6">
                <div class="form-group">
                    <label for="historia" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">H.C</label>
                    <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                        <input id="historia" name="historia" class="form-control"  />
                    </div>
                </div>
                <div class="form-group">
                    <label for="diagnostico" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">DX</label>
                    <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                        <input id="diagnostico" name="diagnostico" class="form-control" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="prm" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">PRM</label>
                    <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                        <input id="prm" name="prm" class="form-control" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="intervencion" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Intervención</label>
                    <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                        <input id="intervencion" name="intervencion" class="form-control"  />
                    </div>
                </div>
                <div class="form-group">
                    <label for="ram" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Ram</label>
                    <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                        <input id="ram" name="ram" class="form-control"  />
                    </div>
                </div>
            </div>
            <input type="hidden" id="id" class="IDFIELD" name="id" value="0" />
            <input type="hidden" id="idPaciente"  name="idPaciente" value="0" />

        </form>
    </div>
</div>