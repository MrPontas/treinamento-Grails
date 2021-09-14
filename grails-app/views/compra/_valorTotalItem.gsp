<div class='input-group-mb-3'>
    <g:textField
        id="vlTot${i}"
        name="itensCompra[${i}].valorTotal"
        value="${formatNumber(locale: (new Locale ("pt", "BR")), number: itemCompra.valorTotal, format: '###,###,##0.00')}"
        onChange="ajaxPost(this, '${createLink(action:"atualizaValorTotalCompra")}', 'divValorTotalCompra', false);"
        readOnly="true"
        class="form-control"
     />
</div>
<g:if test="${refresh}">
    <script>
        ajaxPost(document.getElementById('vlTot${i}'), '${createLink(action:"atualizaValorTotalCompra")}', 'divValorTotalCompra', false);
    </script>
</g:if> 