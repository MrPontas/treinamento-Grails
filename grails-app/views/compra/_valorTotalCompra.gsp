
<div class='input-group-mb-3 fieldcontain required ${hasErrors(bean:compra, field:"valorTotal", 'error')}'>
    <g:message code="compra.valorTotal.label" default="Valor Total" />
    <g:textField id="valorTotal"
            name="valorTotal"
            value="${formatNumber(locale: (new Locale ("pt", "BR")), number: compra.valorTotal, format: '###,###,##0.00')}"
            readOnly="true"
            class="form-control"
            />
    
</div>