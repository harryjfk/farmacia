<div class="row" id="mgn-intervsanitarias">
    <div class="col-sm-12 col-md-12">
        <form id="preventafrm" class="form_validation_reg form-horizontal" autocomplete="off">
            <div class="col-lg-6 col-md-6 col-sm-6">
                <div class="form-group">
                    <label for="nroSalida" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Nro. Salida</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <input id="nroSalida" name="nroSalida" class="form-control" />
                    </div>
                </div>
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
                    <label for="nroHistClinica" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Nro. Hist. Cl&iacute;nica</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <input id="nroHistClinica" name="nroHistClinica" class="form-control" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="prescriptor" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Prescriptor</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <input id="prescriptor" name="prescriptor" class="form-control" />
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
                    <label for="diagnostico" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Diag&oacute;stico CIE</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <input id="diagnostico" name="diagnostico" class="form-control" value="SIN DIAGNOSTICO"/>
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
            </div>

            <div class="col-lg-6 col-md-6 col-sm-6">
                <div class="form-group">
                    <label for="fechaRegistro" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Fecha de Registro</label>
                    <div class="col-md-4 col-sm-4 col-lg-4 col-xs-4">
                        <input id="fechaRegistro" name="fechaRegistro" class="form-control" disabled="true"/>
                    </div>
                    <div class="col-md-4 col-sm-4 col-lg-4 col-xs-4">
                        <input id="guiainterna" name="guiainterna" class="form-control" placeholder="Gu&iacute;a Interna de Salida" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="componente" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Componente/Programa</label>
                    <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                        <select id="componente" name="componente" class="form-control"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="subcomponente" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Sub Componente</label>
                    <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                        <select id="subcomponente" name="subcomponente" class="form-control"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="proceso" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Proceso</label>
                    <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                        <select id="proceso" name="proceso" class="form-control"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="coordinador" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Coordinador</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <input id="coordinador" name="coordinador" class="form-control" />
                    </div>
                    <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
                        <div id="seleccionaCoor" class="splashy-box_add" 
                             style="height: 16px;
                             margin-top: 10px;
                             margin-left: -22px;
                             cursor: pointer;
                             "></div>
                        <div></div></div>
                </div>
                <div class="form-group">
                    <label for="referencia" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Referencia</label>
                    <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                        <input id="referencia" name="referencia" class="form-control" />
                    </div>
                </div>
            </div>
        </form>
        <button id="btnLimpiar" class="btn btn-primary pull-right hidden" style="margin-left: 5px">Limpiar</button>
        <button id="btnAgregar" class="btn btn-primary pull-right hidden">Agregar</button>
    </div>
</div>
