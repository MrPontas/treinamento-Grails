<head>
    <asset:stylesheet src="table.css"/>
</head>
<div class="fieldcontain required ${hasErrors(bean:compra, field:"produto", 'error')}">
    <table class="table">
        <thead>
            <tr class="table-dark table-stripped">
                <g:sortableColumn property="Cliente" title="Cliente" />                   
                <g:sortableColumn property="valorTotal" title="Valor total" />                   
                
                <th>Editar</th>
            </tr>
            
        </thead>

        <tbody>
            <g:each in="${teste.Compra.list()}" var="compra" status="i">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'} ">
                    <td>
                        ${compra.cliente.nome}
                    </td>
                    <td>
                        R$${formatNumber(locale: (new Locale ("pt", "BR")), number: compra.valorTotal, format: '###,###,##0.00')}
                    </td>
                    <td>
                        <g:link action="edit" id="${compra.id}">
                            <button class= "btn btn-outline-primary">
                                <i class="bi bi-pencil"></i>
                            </button>                            
                        </g:link>
                    </td>
                </tr>
            </g:each>
        </tbody>
    </table>
</div>