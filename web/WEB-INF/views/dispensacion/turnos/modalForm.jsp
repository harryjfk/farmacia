
<form id="mdl-form" method="post" class="form_validation_reg" autocomplete="off" action="insertar">
    <div class="row">
        <div class="col-sm-6 col-md-6">
            <label>Descripción<span class="f_req">*</span></label>
            <input type="text" id="descripcion" name="descripcion" class="form-control" data-req="" />
        </div>

        <div class="col-sm-6 col-md-6">
            <div class="form-group">
                <label>Hora Inicio<span class="f_req">*</span></label>
                <div class="input-group" style="width: 100%">
                    <input type="text" id="horaInicio" name="horaInicio"  class="form-control" data-req=""  data-autoclose="true"/>
                </div>
            </div>
        </div>
    </div>  
    <div class="row">
        <div class="col-sm-6 col-md-6">
            <label>Vendedores</label>
            <select id="vendedoresTurno" name="vendedoresTurno" style="width: 100%; height: 30px;" class="select2" multiple ></select>
        </div>
        <div class="col-sm-6 col-md-6">
            <div class="form-group">
                <label>Hora Termino<span class="f_req">*</span></label>
                <div class="input-group" style="width: 100%">
                    <input type="text" id="horaFinal" name="horaFinal"  class="form-control" data-req=""  data-autoclose="true"/>
                </div>
            </div>
        </div>
        <input type="hidden" id="idTurno" class="IDFIELD" name="idTurno" value="0" />
        <input type="hidden" id="idFarmacia"  name="idFarmacia" value="0" />
    </div>
    <div id="no-vendedor-available" style="color:red" ><small>Este turno no tiene vendedores asignados.</small></div>
</form>
<div style="margin-top: 10px" id="verVendedoresTurno" ><a style="background: none !important;" href="javascript:void(0);">Mostrar Vendedores</a></div>

<div class="row">
    <div class="col-sm-12 col-md-12">

        <div id="vendedoresTurno-list-box" class="hidden">
            <div class="row">
                <div class="col-sm-12 col-md-12 col-lg-12 col-xs-12">
                    <table id="tblVendedoresTurno" class="table table-bordered table-striped dTableR">
                        <thead>
                            <tr>
                            <th>Código</th>
                            <th>Nombre</th>
                            <th>Apellido Paterno</th>
                            <th>Apellido Materno</th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>  

