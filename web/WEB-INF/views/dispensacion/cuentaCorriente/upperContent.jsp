<div id="cuentacorrienteuc" class="row">
    <div class="col-sm-12 col-md-12">
        <form id="cuentacorrientefrm" class="form_validation_reg form-horizontal" autocomplete="off">
            <div class="row">
                <div class="col-lg-6 col-md-6 col-sm-1 col-xs-1"> 
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
                        <label for="formaPago" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Forma de Pago</label>
                        <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                            <select id="formaPago" name="formaPago" class="form-control"></select>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-1 col-xs-1">
                    <div class="form-group">
                        <label for="periodo" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Per&iacute;odo</label>
                        <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                            <input id="periodo" name="periodo" class="form-control" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="fechIni" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Inicio</label>
                        <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                            <input id="fechIni" name="fechIni" class="form-control" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="fechFin" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">T&eacute;rmino</label>
                        <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                            <input id="fechFin" name="fechFin" class="form-control" />
                        </div>
                    </div>
                </div>  
                <div id="estadobox" class="col-lg-3 col-md-3 col-sm-1 col-xs-1">
                    <span class="bold">Situaci&oacute;n Documento</span>
                    <div class="radio">
                        <label>
                            <input type="radio" name="estado" id="xcobrar" value="X_COBRAR" checked>
                            X Cobrar
                        </label>
                    </div>
                    <div class="radio">
                        <label>
                            <input type="radio" name="estado" id="cancelado" value="CANCELADA">
                            Cancelados
                        </label>
                    </div>
                </div>
            </div>
            <button id="btnFilter" class="btn btn-primary">Consultar</button>
        </form>
    </div>
</div>