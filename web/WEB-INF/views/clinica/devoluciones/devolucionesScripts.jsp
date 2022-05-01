<script>
    $(document).ready(function () {
        window.gp.ajaxList='getDevoluciones';
        $('body').addClass('no-change no-edit');
        var dataConsulta = {paciente: 0};
        $('#btnAgregar').hide();
        var pacienteModal = new PacienteGeneral();
        pacienteModal._table.set('click', _selectPaciente);
        $("#seleccionaPac").click(function () {
            pacienteModal._modal.show();
            var id = '#' + pacienteModal._modal.get('id');
            $('#pacienteModal').val(dataConsulta.paciente);
            $(id).find('.modal-dialog').css({width: 'auto'});
        });
        $('#paciente').on('change', function () {
            dataConsulta.paciente = 0;
            $('#btnAgregar').hide();
            $('#pacienteModal').val(0);
            $('#btnAgregar').hide();
        });
        function _selectPaciente(row) {
            if (row.nombre) {
                var nombre = row.nombre + ' ' + row.paterno + ' ' + row.materno,
                        fecha = new Date(row.fechaNacimiento).getTime();
                row.fechaNacimiento = fecha;
                dataConsulta.paciente = row.paciente;
                $("#paciente").val(nombre);
                $('#btnAgregar').show();
                $('#pacienteModal').val(row.paciente);
                window.gp.data = {paciente: row.paciente};
                window.gp.reportData = window.gp.data;
                window.gp.getData();
            }
            pacienteModal._modal.hide();
        }
        _ProductoSelectFill('#producto');
        $('#cantidad').change(function () {
            $('#pacienteModal').val(dataConsulta.paciente);
        });
         $('#mdl-frm').validate({
            rules: {
                producto: {
                    required: true
                },
                cantidad: {
                    required: true
                },
                valorUnitario:{
                    required:true,
                    digits:true
                },
                servicio:{required:true},
                tipo:{required:true}
                
               
                
            },
            messages: {
                producto: {
                    required: 'Este campo es obligatorio'
                },
                cantidad: {
                    required: 'Este campo es obligatorio'
                },
                valorUnitario:{
                    required:'Este campo es obligatorio',
                    digits:'El valor debe ser un número'
                },
                servicio:{required:'Este campo es obligatorio'},
                tipo:{required:'Este campo es obligatorio'}
                
            }
        });
         window.gp.reportCondition=function(){
            if(!dataConsulta.paciente){
                alertify.error("Debe escoger un Paciente para realizar esta acción.");
                return false;
            }
            window.gp.reportData={paciente:dataConsulta.paciente};
            return true;
        };
    });
</script>    