<div class="row" id="mgm-cortecaja">
    <div class="col-sm-12 col-md-12">
        <form id="cortecajafrm" class="form_validation_reg form-horizontal" autocomplete="off">
            <div class="col-lg-6 col-md-6 col-sm-6">
                <div class="form-group">
                    <label for="puntoVenta" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Punto de Venta</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <input id="puntoVenta" name="puntoVenta" class="form-control" />
                    </div>
                    <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
                        <div id="seleccionaPtoVen" class="splashy-box_add" 
                             style="height: 16px;
                             margin-top: 10px;
                             margin-left: -22px;
                             cursor: pointer;
                             "></div>
                        <div></div></div>
                </div>
                <div class="form-group">
                    <label for="vendedor" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Cajero</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <input id="vendedor" name="vendedor" class="form-control" />
                    </div>
                    <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
                        <div id="seleccionaVen" class="splashy-box_add" 
                             style="height: 16px;
                             margin-top: 10px;
                             margin-left: -22px;
                             cursor: pointer;
                             "></div>
                        <div></div></div>
                </div>
            </div>
            <div class="col-lg-3 col-md-3 col-sm-3">
                <div class="form-group">
                    <label for="periodo" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Per&iacute;odo</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <input id="periodo" name="periodo" class="form-control" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="turno" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Turno</label>
                    <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                        <select id="turno" name="turno" class="form-control"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="ventafechaRegistro" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Fecha</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <input id="ventafechaRegistro" name="ventafechaRegistro" class="form-control" />
                    </div>
                </div>
            </div>
            <button id="btnFiltrar" class="btn btn-primary">Consultar</button>
        </form>
    </div>
</div>