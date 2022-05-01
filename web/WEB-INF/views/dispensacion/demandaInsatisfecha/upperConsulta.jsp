<div class="row" style="padding: 0">
    <div class="col-lg-6 col-md-6 col-sm-6">
        <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7" style="padding: 0">
            <label for="clientesSelect" class="control-label">Cliente</label>
            <input id="clientesSelect" name="clientesSelect" class="form-control" disabled="disabled"/>
        </div>
        <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
            <div id="seleccionaCli" class="splashy-box_add" 
                 style="height: 16px;
                 margin-top: 32px;
                 margin-left: -22px;
                 cursor: pointer;"></div>
            <div></div></div>
    </div>
</div>
<div class='row'>
    <div class="col-lg-3 col-md-3 col-sm-3 form-group">
        <div class="form-group">
            <label>Almacen</label>
            <select id="almacenSelect" class="form-control">
                <option value="0">-- Seleccione un Almacen --</option>
            </select>
        </div>
    </div>
    <div class="col-xs-3">
        <label for="StarDate">Fecha Inicial</label>
        <input type="text" id="StartDate" class="form-control">
    </div>
    <div class="col-xs-3">
        <label for="EndDate">Fecha Final</label>
        <input type="text" id="EndDate" class="form-control">
    </div>
</div>
<div class="row" style="margin-bottom: 23px;margin-top: 0;margin-left: -10px;">
    <div class="col-lg-6 col-md-6 col-sm-6">
        <button class="btn btn-primary" id="consultarDemandas" >Consultar</button>
    </div>
</div>