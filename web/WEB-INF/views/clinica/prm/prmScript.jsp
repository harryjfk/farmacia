<script>
    $(document).ready(function () {
        $('#fecha').datepicker({dateFormat:'dd/mm/yy'});
        $('body').addClass('no-change');
        window.gp.dataToSend = {paciente: 0,id: 0,fecha: null,servicio: null,edad: null,historia:null,diagnostico:null,prm:null,intervencion:null,ram:null};
        
        $('#filtrar').click(function(e){
            e.preventDefault();
            var servicio = $('#servicioFiltro').val();
            window.gp.data={servicio:servicio};
            window.gp.getData();
            window.gp.data={};
        });
        
        $('#modalData-modal').bind({
            Añadir: function (event) {
                event.preventDefault();
        window.gp.dataToSend = {paciente: 0,id: 0,fecha: null,servicio: null,edad: null,historia:null,diagnostico:null,prm:null,intervencion:null,ram:null};
            },
            Editar: function (event, id, data) {
                event.preventDefault();
                window.gp.dataToSend = {paciente: 0,id: 0,fecha: null,servicio: null,edad: null,historia:null,diagnostico:null,prm:null,intervencion:null,ram:null};
                $('#mdl-frm #paciente').val(data.paciente.nombres);
                var date = new Date(data.fecha);
                date = date.getMonth() + 1 + '/' + date.getDate() + '/' + date.getFullYear();
                $('#mdl-frm #fecha').val(date);
                window.gp.dataToSend.paciente = data.paciente.paciente;
                window.gp.dataToSend.fecha = date;
                
                window.gp.dataToSend.servicio = data.servicio;
                window.gp.dataToSend.edad = data.edad;
                window.gp.dataToSend.id = data.id;
                window.gp.dataToSend.historia = data.historia;
                window.gp.dataToSend.diagnostico = data.diagnostico;
                window.gp.dataToSend.prm = data.prm;
                window.gp.dataToSend.ram = data.ram;
                window.gp.dataToSend.intervencion = data.intervencion;
                
            }});
        $('#fecha').change(function () {
            window.gp.dataToSend.fecha = $(this).val();
        });
        
        $('#servicio').change(function () {
            window.gp.dataToSend.servicio = $(this).val();
        });
        $('#edad').change(function () {
            window.gp.dataToSend.edad = $(this).val();
        });
        $('#historia').change(function () {
            window.gp.dataToSend.historia = $(this).val();
        });
        $('#intervencion').change(function () {
            window.gp.dataToSend.intervencion = $(this).val();
        });
        $('#diagnostico').change(function () {
            window.gp.dataToSend.diagnostico = $(this).val();
        });
        $('#prm').change(function () {
            window.gp.dataToSend.prm = $(this).val();
        });
        $('#ram').change(function () {
            window.gp.dataToSend.ram = $(this).val();
        });

        var pacienteModal = new PacienteGeneral();
        pacienteModal._table.set('click', _selectPaciente);
        $("#seleccionaPac").click(function () {
            pacienteModal._modal.show();
            var id = '#' + pacienteModal._modal.get('id');
            $(id).find('.modal-dialog').css({width: 'auto'});
        });
        $('#paciente').on('change', function () {
            window.gp.dataToSend.paciente = 0;
        });
        function _selectPaciente(row) {
            if (row.nombre) {
                var nombre = row.nombre + ' ' + row.paterno + ' ' + row.materno,
                        fecha = new Date(row.fechaNacimiento).getTime();
                row.fechaNacimiento = fecha;
                window.gp.dataToSend.paciente = row.paciente;
                $("#paciente").val(nombre);
                $('#historia').attr('value',row.historia);
                window.gp.dataToSend.historia=row.historia;
            }
            pacienteModal._modal.hide();
        }
        $('#mdl-frm').validate({
            rules: {
                servicio: {
                    required: true
                },
                fecha: {
                    required: true
                },
                edad: {
                    required: true
                },
                paciente: {
                    required: true
                },
                historia: {
                    required: true
                },
                diagnostico: {
                    required: true
                },
                prm: {
                    required: true
                },
                intervencion: {
                    required: true
                },
                ram: {
                    required: true
                }
                
            },
            messages: {
                servicio: {
                    required: 'Este campo es obligatorio'
                },
                fecha: {
                    required: 'Este campo es obligatorio'
                },
                edad: {
                    required: 'Este campo es obligatorio'
                },
                paciente: {
                    required: 'Este campo es obligatorio'
                },
                historia: {
                    required: 'Este campo es obligatorio'
                },
                diagnostico: {
                    required: 'Este campo es obligatorio'
                },
                prm: {
                    required: 'Este campo es obligatorio'
                },
                intervencion: {
                    required: 'Este campo es obligatorio'
                },
                ram: {
                    required: 'Este campo es obligatorio'
                }
            }
        });
    });
</script>