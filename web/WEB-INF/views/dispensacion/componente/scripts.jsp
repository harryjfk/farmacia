<script>
    $(document).ready(function () {
        $('#mdl-form').validate({
            rules: {
                descripcion: {
                    required: true
                }
            },
            messages: {
                descripcion: {
                    required: 'Este campo es obligatorio'
                }
            }
        });
    });
</script>