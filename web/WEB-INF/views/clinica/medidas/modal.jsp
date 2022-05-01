<div class="row">
    <div class="col-sm-12 col-md-12">
        <form id="mdl-frm" class="form_validation_reg form-horizontal" autocomplete="off">
            <div class="col-lg-8 col-md-8 col-sm-8">
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
                    <label for="tema" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Tema</label>
                    <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                        <input id="tema" name="tema" class="form-control"  />
                    </div>
                </div>
               <div class="form-group">
                <label for="paciente" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Paciente</label>
                <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
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
            </div>
            <div class="col-lg-4 col-md-4 col-sm-4">
                <div class="radio">
                    <label>
                        <input type="radio" name="optionsRadios" id="optionsRadios1" data-value="boleta" checked="checked">
                        Boletin
                    </label>
                </div>
                <div class="radio">
                    <label>
                        <input type="radio" name="optionsRadios" id="optionsRadios2" data-value="diptico">
                        Díptico
                    </label>
                </div>
                <div class="radio">
                    <label>
                        <input type="radio" name="optionsRadios" id="optionsRadios3" data-value="triptico">
                        Tríptico
                    </label>
                </div>
                <div class="radio">
                    <label>
                        <input type="radio" name="optionsRadios" id="optionsRadios4" data-value="afiches">
                        Afiches
                    </label>
                </div>
                <div class="radio">
                    <label>
                        <input type="radio" name="optionsRadios" id="optionsRadios5" data-value="otros">
                        Otros
                    </label>
                </div>
            </div>
                        <input type="hidden" id="id" class="IDFIELD" name="id" value="0" />

        </form>
    </div>
</div>