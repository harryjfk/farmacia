<script>
    $(document).ready(function () {
        $('#mdl-form').validate({
            rules: {
                nombrePC: {
                    required: true
                },
                serieBoleta: {
                    required: true
                }
            },
            messages: {
                nombrePC: {
                    required: 'Este campo es obligatorio'
                },
                serieBoleta: {
                    required: 'Este campo es obligatorio'
                }
            }
        });
    });


</script>