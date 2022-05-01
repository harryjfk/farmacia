<div class="row">
    <div class="col-sm-12 col-md-12">
        <form id="upper-frm" class="form_validation_reg form-horizontal" autocomplete="off">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="form-group">
                    <label for="almacenSelect" class="col-md-2 col-sm-2 col-lg-2 col-xs-2 control-label">Almacen</label>
                    <div class="col-md-5 col-sm-5 col-lg-5 col-xs-5">
                        <select id="almacenSelect" class="form-control">
                            <option value="0">-- Seleccione un Almacen --</option>
                        </select>                   
                    </div>
                </div>
                <div class="form-group">
                    <label for="startDate" class="col-md-2 col-sm-2 col-lg-2 col-xs-2 control-label">Fecha Inicio</label>
                    <div class="col-md-3 col-sm-3 col-lg-3 col-xs-3">
                        <input id="startDate" name="startDate" class="form-control" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="endDate" class="col-md-2 col-sm-2 col-lg-2 col-xs-2 control-label">Fecha Final</label>
                    <div class="col-md-3 col-sm-3 col-lg-3 col-xs-3">
                        <input id="endDate" name="endDate" class="form-control" />
                    </div>
                </div>
            </div>
        </form>
        <div class="form-group">
        <button id="consultar" class="btn btn-primary" >Consultar</button>
        <button id="inicializar" class="btn btn-warning" >Inicializar</button>
        </div>
    </div>
</div>