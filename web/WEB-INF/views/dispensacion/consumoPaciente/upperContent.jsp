<div class="row">
    <div class="col-sm-12 col-md-12">
        <form class="form-horizontal">

            <div class="col-sm-6 col-md-6">

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
                    <label for="medico" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Prescriptor</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
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
                <div  class="form-group">
                    <label for="tipoSelect" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Tipo</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <select id="tipoSelect" class="form-control">
                            <option value="0"> -- TODOS--</option>
                            <option value="1">MEDICAMENTO</option>
                            <option value="2">INSUMO</option>
                        </select>
                    </div>
                    <button id="consumoPacienteConsultar" class="btn btn-primary">Consultar</button>

                </div>
            </div>

            <div class="col-sm-3 col-md-3">
                <div  class="form-group">
                    <label for="startDate" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Fecha de Inicio</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <input class="form-control" id="startDate" />
                    </div>
                </div>
                <div  class="form-group">
                    <label for="endDate" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Fecha Final</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <input class="form-control" id="endDate" />
                    </div>
                </div>    
                <div  class="form-group">
                    <select id="formaPago" class="form-control"> </select>  
                </div>     
            </div>
        </form>

    </div>

</div>