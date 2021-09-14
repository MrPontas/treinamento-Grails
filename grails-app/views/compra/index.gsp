<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'compra.label', default: 'Compra')}" />
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css" type="text/css" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <asset:stylesheet src="table.css"/>
    </head>
    <body>
        <a href="#list-compra" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
            <g:link controller="relatorioCompra">
                <i class="bi bi-file-earmark-bar-graph"></i>
                <g:message code="relatorio.label" args="[entityName]" />
            </g:link>
        </div>
        <div id="list-compra" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:if test="${compraList.size() == 0}">
                <p style="margin: 30px"><g:message code="vazio.label" args="[entityName]" /></p>
            </g:if>
            <g:else>
                <%-- <g:render template="table2"/> --%>
                <table id="tabelaCompras" class="display">
                    <thead>
                        <tr>
                            <th>Ações</th>
                            <th>
                                <g:message code="compra.cliente.label" default="Cliente" />
                            </th>
                            <th>
                                <g:message code="compra.valorTotal.label" default="Valor Total" />
                            </th>
                        </tr>
                    </thead>
                </table>
            </g:else>

            <div class="pagination">
                <g:paginate total="${produtoCount ?: 0}" />
            </div>
        </div>
        <content tag="jsEspecifico">
            <script src="https://cdn.datatables.net/1.10.25/js/jquery.dataTables.min.js"></script>
            <script>
                $('#tabelaCompras').DataTable({
                    "processing": true,
                    "serverSide": true,
                    "ajax": "${createLink(controller:"compra", action:"listCompra")}",
                    "columns": [
                        {
                            "orderable": false,
                            "data": null,
                            "render": function (data, type, full, meta) {
                                return ('<a style="margin-right:10px" href="${createLink(controller:'compra', action:'edit')}/'+ data.id +'" ><button class= "btn btn-outline-primary" title="Editar"><i class="bi bi-pencil"></i></button></a>'
                                    // +'<a href="${createLink(controller:'produto', action:'delete')}/'+ data.id +'" ><button class= "btn btn-outline-danger"><i class="bi bi-x-circle"></i></button></a>'
                                ); }
                        },
                        {
                            "data": "cliente.nome",
                        },
                        {
                            "data": "valorTotal"
                        }
                    ],
                    "oLanguage": {
                        "sEmptyTable": "Nenhum registro encontrado",
                        "sInfo": "Mostrando de _START_ a _END_ de _TOTAL_ registros",
                        "sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
                        "sInfoFiltered": "(Filtrando de _MAX_ registros)",
                        "sInfoPostFix": "",
                        "sInfoThousands": ".",
                        "sLengthMenu": "Mostrar _MENU_ registros por página",
                        "sLoadingRecords": "Carregando...",
                        "sProcessing": "<div class=\"progress\"><div class=\"progress-bar progress-bar-striped progress-bar-animated\" role=\"progressbar\" aria-valuenow=\"100\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: 100%\"></div></div>",
                        "sZeroRecords": "Nenhum registro encontrado",
                        "sSearch": "Pesquisar:",
                        "oPaginate": {
                            "sNext": "Próximo",
                            "sPrevious": "Anterior",
                            "sFirst": "Primeiro",
                            "sLast": "Último"
                        },
                        "oAria": {
                            "sSortAscending": ": Ordenar colunas de forma ascendente",
                            "sSortDescending": ": Ordenar colunas de forma descendente"
                        }
                    },
                });
            </script>
        </content>
    </body>
</html>