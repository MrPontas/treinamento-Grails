 <g:textField
        id="vlUnt${i}"
        name="itensCompra[${i}].valorUnitario"
        value="${formatNumber(locale: (new Locale ("pt", "BR")), number: itemCompra.valorUnitario, format: '###,###,##0.00')}"
        onChange="ajaxPost(this, '${createLink(action:"atualizaValorTotalItem")}?idx=${i}', 'divValorTotalItem${i}', false);" />

<g:if test="${refresh}">
    <script>
        ajaxPost(document.getElementById('vlUnt${i}'), '${createLink(action:"atualizaValorTotalItem")}?idx=${i}', 'divValorTotalItem${i}', false);
    </script>
</g:if>