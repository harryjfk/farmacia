<script>
    $(document).ready(function () {
       $("#btnPDF, #btnExcel").remove();
        $('#mdl-form').validate({
            rules: {
                nombre: {
                    required: true
                }
            },
            messages: {
                nombre: {
                    required: 'Este campo es obligatorio'
                }
            }
        });
    });
</script>