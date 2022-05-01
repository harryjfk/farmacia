<div class="row">
    <div class="form-group">
        <label for="paciente" class="col-md-2 col-sm-2 col-lg-2 col-xs-2 control-label">Paciente</label>
        <div class="col-md-4 col-sm-4 col-lg-4 col-xs-4">
            <input id="pacienteModal" name="pacienteModal" class="form-control" />
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
<div class="row"> 
    <div style="margin-bottom:15px;">
        <button id="consultar"class="btn btn-primary">Consultar</button>
        <label for="consulta" class="col-md-1 col-sm-1 col-lg-1 col-xs-1 control-label">Historia Clinica</label>
        <div class="col-md-2 col-sm-2 col-lg-2 col-xs-2">
            <input id="consulta" name="consulta" class="form-control" />
        </div>
    </div>
    <div class="col-sm-12 col-md-12 col-lg-12 col-xs-12">


    </div>
    <form id="ramForm" class="form_validation_reg form-horizontal" autocomplete="off">


        <div class="col-sm-12 col-md-12 col-lg-12 col-xs-12 form-frame">

            <h4>Datos del Paciente</h4>
            <div class="form-group">
                <label for="paciente" class="col-md-1 col-sm-1 col-lg-1 col-xs-1 control-label">Nombre</label>
                <div class="col-md-11 col-sm-11 col-lg-11 col-xs-11">
                    <input id="paciente" name="paciente" class="form-control" />
                </div>
            </div>
            <div class="form-group">
                <label for="edad" class="col-md-1 col-sm-1 col-lg-1 col-xs-1 control-label">Edad</label>
                <div class="col-md-2 col-sm-2 col-lg-2 col-xs-2">
                    <input id="edad" name="edad" class="form-control"  />
                </div>
                <label for="sexo" class="col-md-1 col-sm-1 col-lg-1 col-xs-1 control-label">Sexo</label>
                <div class="col-md-2 col-sm-2 col-lg-2 col-xs-2">
                    <select name="sexo" class="form-control">
                        <option value="0">M</option>
                        <option value="1">F</option>
                    </select>
                </div>
                <label for="peso" class="col-md-1 col-sm-1 col-lg-1 col-xs-1 control-label">Peso</label>
                <div class="col-md-2 col-sm-2 col-lg-2 col-xs-2">
                    <input id="peso" name="peso" class="form-control"  />
                </div>
                <label for="historia" class="col-md-1 col-sm-1 col-lg-1 col-xs-1 control-label">H.C</label>
                <div class="col-md-2 col-sm-2 col-lg-2 col-xs-2">
                    <input id="historia" name="historia" class="form-control"  />
                </div>
            </div>
            <div class="form-group">
                <label for="establecimiento" class="col-md-1 col-sm-1 col-lg-1 col-xs-1 control-label">Establecimiento de Salud</label>
                <div class="col-md-11 col-sm-11 col-lg-11 col-xs-11">
                    <input id="establecimiento" name="establecimiento" class="form-control" />
                </div>
            </div>
        </div>
        <div class="col-sm-12 col-md-12 col-lg-12 col-xs-12 form-frame" >
            <h4>Persona que Notifica</h4>
            <div class="form-group">
                <label for="categoria" class="col-md-1 col-sm-1 col-lg-1 col-xs-1 control-label">Categoria</label>
                <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                    <select id="categoria" class="form-control">
                        <option value="Medico">Medico</option>
                        <option value="Odontologo">Odontologo</option>
                        <option value="Obstetriz">Obstetriz</option>
                        <option value="Farmaceutico">Farmaceutico</option>
                        <option value="Enfermero">Enfermero</option>
                        <option value="Otro">Otro</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="medico" class="col-md-1 col-sm-1 col-lg-1 col-xs-1 control-label">Nombre</label>
                <div class="col-md-11 col-sm-11 col-lg-11 col-xs-11">
                    <input id="medico" name="medico" class="form-control" />
                </div>
            </div>
            <div class="form-group">
                <label for="direccion" class="col-md-1 col-sm-1 col-lg-1 col-xs-1 control-label">Dirección</label>
                <div class="col-md-11 col-sm-11 col-lg-11 col-xs-11">
                    <input id="direccion" name="direccion" class="form-control" />
                </div>
            </div>
            <div class="form-group">
                <label for="telefono" class="col-md-1 col-sm-1 col-lg-1 col-xs-1 control-label">Telefono</label>
                <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                    <input id="telefono" name="telefono" class="form-control" />
                </div>
                <label for="fecha" class="col-md-1 col-sm-1 col-lg-1 col-xs-1 control-label">Fecha</label>
                <div class="col-md-4 col-sm-4 col-lg-4 col-xs-4">
                    <input id="fecha" name="fecha" class="form-control" />
                </div>
            </div>

        </div>
        <div class="col-sm-12 col-md-12 col-lg-12 col-xs-12 form-frame" >
            <h4>Observaciones Adicionales</h4>
            <div class="form-group">
                <label for="observaciones" class="col-md-1 col-sm-1 col-lg-1 col-xs-1 control-label">Observaciones</label>
                <div class="col-md-11 col-sm-11 col-lg-11 col-xs-11">
                    <textarea id="observaciones" name="observaciones" class="form-control"></textarea>
                </div>
            </div>
        </div>
        <input type="hidden" id="ram" name="ram" value="0">

    </form>
    
    <div class="col-sm-12 col-md-12 col-lg-12 col-xs-12 form-frame" >
        <h4>Medicamentos Sospechosos</h4>
        <table id="medicamentosSospechosos" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                    <th>Nombre Comercial o Generico</th>
                    <th>Laboratorio</th>
                    <th>Lote</th>
                    <th>Dosis Diaria</th>
                    <th>Via de Administracion</th>
                    <th>Fecha Inicio</th>
                    <th>Fecha Final</th>
                </tr>
            </thead>
        </table>
    </div>
    
    <div class="col-sm-12 col-md-12 col-lg-12 col-xs-12 form-frame" >

        <h4>Reacciones Adversas</h4>
        <table id="reaccionesAdversas" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                    <th>Reacción Adversa</th>
                    <th>Fecha Inicio</th>
                    <th>Fecha Final</th>
                    <th>Fecha Final</th>

                </tr>
            </thead>
        </table>
    </div>
    
    <div class="col-sm-12 col-md-12 col-lg-12 col-xs-12 form-frame" >
        <h4>Otros Medicamentos Usados en los Ultimos 3 Meses incluyendo Automedicacion</h4>
        <table id="otrosMedicamentos" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                    <th>Nombre Comercial o Generico</th>
                    <th>Dosis Diaria</th>
                    <th>Via de Administracion</th>
                    <th>Fecha Inicio</th>
                    <th>Fecha Final</th>
                    <th>Indicación Terapéutica</th>
                </tr>
            </thead>
        </table>
    </div>

</div>
