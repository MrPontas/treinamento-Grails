<head>
    <asset:stylesheet src="table.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">

</head>

<button type="button" class="btn btn-success" onclick="ajaxPost(this, '${createLink(action:"adicionarProdutos")}', 'divProdutos', false);">Adicionar produto...</button>

<div class="my-table-with-button fieldcontain required ${hasErrors(bean:compra.itensCompra, field:"produto", 'error')}">
    <table class="table">
        <thead>
            <tr class="table-dark table-stripped" >
                <th style="width: 25%"><g:message code="remover.label"/></th>
                <th style="width: 25%"><g:message code="produto.label"/></th>
                <th style="width: 25%"><g:message code="itemCompra.quantidade.label"/></th>
                <th style="width: 25%"><g:message code="itemCompra.valorUnitario.label"/></th>
                <th style="width: 25%"><g:message code="itemCompra.valorTotal.label"/></th>
            </tr>
        </thead>

        <tbody>
            <g:each var="itemCompra" in="${ compra.itensCompra }" status="i">
                <tr>
                    <td>
                        <button class="botaoRemover" type="button" class="btn btn-success" onclick="ajaxPost(this, '${createLink(action:"removeProduto")}?idx=${i}', 'divProdutos', false);" >
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-circle-fill" viewBox="0 0 16 16">
                                <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM5.354 4.646a.5.5 0 1 0-.708.708L7.293 8l-2.647 2.646a.5.5 0 0 0 .708.708L8 8.707l2.646 2.647a.5.5 0 0 0 .708-.708L8.707 8l2.647-2.646a.5.5 0 0 0-.708-.708L8 7.293 5.354 4.646z"/>
                            </svg>
                        </button>
                    </td>
                    <td>
                        <g:hiddenField name="itensCompra[${i}].id" value="${itemCompra?.id}" />
                        <%-- <g:select name="itensCompra[${i}].produto.id"
                            from="${teste.Produto.list()}"
                            value="${itemCompra.produto?.id}"
                            optionKey="id"
                            optionValue="nome"
                            noSelection="['':'']"
                            onChange="ajaxPost(this, '${createLink(action:"atualizaProduto")}?idx=${i}', 'divValorUnitario${i}', false);" /> --%>
                        <g:select id="itensCompra[${i}].produto" name="itensCompra[${i}].produto.id" 
                            from="${teste.Produto.get(itemCompra?.produto?.id)}"
                            value="${itemCompra?.produto?.id}"
                            noSelection="['null':'']" 
                            class="form-control comboboxautocomplete"
                            servidor="${createLink(uri: '/', absolute:true)}"
                            controller="itemCompra"
                            onChange="ajaxPost(this, '${createLink(action:"atualizaProduto")}?idx=${i}', 'divValorUnitario${i}', false);"
                            action="getProduto"
                            optionKey="id"
                            optionValue="nome"
                            camposLabel="nome"
                            campoValor="id"
                            placeholder="Selecione"/>
                    </td>
                    <td>
                        <g:textField
                            name="itensCompra[${i}].quantidade"
                            value="${formatNumber(locale: (new Locale ("pt", "BR")), number: itemCompra.quantidade, format: '###,###,##0.00')}"
                            onChange="ajaxPost(this, '${createLink(action:"atualizaValorTotalItem")}?idx=${i}', 'divValorTotalItem${i}', false);" />

                    </td>
                    <td id="divValorUnitario${i}">
                       <g:render template="valorUnitario" model="['i':i, 'itemCompra': itemCompra]" />
                    </td>
                    <td id="divValorTotalItem${i}">
                        <g:render template="valorTotalItem" model="['itemCompra': itemCompra, 'i':i]" />
                    </td>
                </tr>
            </g:each>    
        </tbody>
    </table>
    <g:if test="${ refreshCB }">
        <script>
                $(".comboboxautocomplete").combobox();
        </script>
    </g:if>
</div>