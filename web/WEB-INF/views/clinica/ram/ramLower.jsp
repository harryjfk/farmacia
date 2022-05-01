<div class="modal fade" id="medicamentosSospechosos-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Agregar Medicamentos Sospechosos </h3>
            </div>
            <div class="modal-body">
                <form id="medicamentoForm" class="form_validation_reg form-horizontal" autocomplete="off" action="insertarMedicamento" method="post">
                    <div class="col-sm-12 col-md-12 col-lg-12 col-xs-12 ">
                        <div class="form-group">
                            <label for="nombreMedicamento" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Nombre Comercial</label>
                            <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                                <input id="nombreMedicamento" name="nombre" class="form-control" />
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="laboratorioMedicamentos" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Laboratorio</label>
                            <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                                <input id="laboratorioMedicamentos" name="laboratorio" class="form-control" />
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="loteMedicamento" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Lote</label>
                            <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                                <input id="loteMedicamento" name="lote" class="form-control" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="dosisMedicamento" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Dosis</label>
                            <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                                <input id="dosisMedicamento" name="dosis" class="form-control" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="viaMedicamento" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Via de Admisión</label>
                            <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                                <input id="viaMedicamento" name="via" class="form-control" />
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="fechaInicioMedicamentos" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Fecha Inicio</label>
                            <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                                <input id="fechaInicioMedicamentos" name="fechaInicio" class="form-control" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="fechaFinalMedicamentos" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Fecha Final</label>
                            <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                                <input id="fechaFinalMedicamentos" name="fechaFinal" class="form-control" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="motivo" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Motivo</label>
                            <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                                <input id="motivo" name="motivo" class="form-control" />
                            </div>
                        </div>
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <button id="btnGuardarmedicamentosSospechosos" class="btn btn-default">Guardar</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="reaccionesAdversas-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Agregar Reacciones Adversas </h3>
            </div>
            <div class="modal-body">
                <form id="reaccionesForm" class="form_validation_reg form-horizontal" autocomplete="off">
                    <div class="col-sm-12 col-md-12 col-lg-12 col-xs-12 ">
                        <div class="form-group">
                            <label for="reaccion" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Reacción Adversa</label>
                            <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                                <textarea id="reaccion" name="reaccion" class="form-control"></textarea>                           
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="fechaInicioReaccion" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Fecha Inicio</label>
                            <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                                <input id="fechaInicioReaccion" name="fechaInicio" class="form-control" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="fechaFinalReaccion" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Fecha Final</label>
                            <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                                <input id="fechaFinalReaccion" name="fechaFinal" class="form-control" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="evolucion" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Evolucion</label>
                            <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                                <select id="evolucion" name="evolucion" class="form-control">
                                    <option value="Mortal">Mortal</option>
                                    <option value="Recuperacion">Recuperación</option>
                                    <option value="Continua">Continua</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <button id="btnGuardarReacciones" class="btn btn-default">Guardar</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="otrosMedicamentos-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Agregar Otros Medicamentos </h3>
            </div>
            <div class="modal-body">
                <form id="otrosForm" class="form_validation_reg form-horizontal" autocomplete="off">
                    <div class="col-sm-12 col-md-12 col-lg-12 col-xs-12 ">
                        <div class="form-group">
                            <label for="nombreOtro" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Nombre Comercial</label>
                            <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                                <input id="nombreOtro" name="nombre" class="form-control" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="dosisOtro" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Dosis</label>
                            <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                                <input id="dosisOtro" name="dosis" class="form-control" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="viaOtro" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Via de Admisión</label>
                            <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                                <input id="viaOtro" name="via" class="form-control" />
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="fechaInicioOtros" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Fecha Inicio</label>
                            <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                                <input id="fechaInicioOtros" name="fechaInicio" class="form-control" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="fechaFinalOtros" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Fecha Final</label>
                            <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                                <input id="fechaFinalOtros" name="fechaFinal" class="form-control" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="indicacion" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Indicación</label>
                            <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                                <input id="indicacion" name="indicacion" class="form-control" />
                            </div>
                        </div>
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <button id="btnGuardarOtros" class="btn btn-default">Guardar</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
            </div>
        </div>
    </div>
</div>
