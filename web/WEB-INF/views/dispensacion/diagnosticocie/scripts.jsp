<script>
    $(document).ready(function () {
        $('#mdl-form').validate({
            rules: {
                codigo: {
                    required: true
                },
                descripcion: {
                    required: true
                }
            },
            messages: {
                codigo: {
                    required: 'Este campo es obligatorio'
                },
                descripcion: {
                    required: 'Este campo es obligatorio'
                }
            }
        });
        $("#mdl-form").find('#descripcion').change(function (e) {
            e.preventDefault();
            if (window.gp.dataToSend)
                window.gp.dataToSend.descripcion = $(this).val();
        });
        $('#modalData-modal').bind({
            Editar: function (e) {
                e.preventDefault();
                $("#mdl-form").find('#codigo').prop('disabled', true);
                if (!window.gp.dataToSend) {
                    window.gp.dataToSend = {
                        codigo: null,
                        descripcion: null
                    };
                }
                window.gp.dataToSend.codigo = $("#mdl-form").find('#codigo').val();
            },
            Añadir: function (e) {
                e.preventDefault();
                window.gp.dataToSend = null;
            }
        });
        $('#modalData-modal').on('hidden.bs.modal', function (e) {
            e.preventDefault();
            $("#mdl-form").find('#codigo').prop('disabled', false);
        });
    });
</script>