<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'cliente.label', default: 'Cliente')}" />
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css" type="text/css" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body id="indexCliente">
        <a href="#list-cliente" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
        </div>
        <div id="list-cliente" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:if test="${clienteList.size() == 0}">
                <p style="margin: 30px"><g:message code="vazio.label" args="[entityName]" /></p>
            </g:if>
            <g:else>
                <%-- <f:table collection="${clienteList}" /> --%>
                <table id="tabelaClientes" class="display">
                    <thead>
                        <tr>
                            <th>Ações</th>
                            <th>
                                <g:message code="cliente.nome.label" default="Nome" />
                            </th>
                            <th>
                                <g:message code="cliente.cpf.label" default="CPF/CNPJ" />
                            </th>
                            <th>
                                <g:message code="cliente.idade.label" default="Idade" />
                            </th>
                        </tr>
                    </thead>
                </table>
            </g:else>

            <div class="pagination">
                <g:paginate total="${clienteCount ?: 0}" />
            </div>
        </div>
        <content tag="jsEspecifico">
            <%-- <asset:javascript src="jquery-3.5.1.js" /> --%>
            <script src="https://cdn.datatables.net/1.10.25/js/jquery.dataTables.min.js"></script>
            <script>
                $('#tabelaClientes').DataTable({
                    "processing": true,
                    "serverSide": true,
                    "ajax": "${createLink(controller:"cliente", action:"listCliente")}",
                    "columns": [
                        {
                            "orderable": false,
                            "data": null,
                            "render": function (data, type, full, meta) {
                                return ('<a style="margin-right:10px" href="${createLink(controller:'cliente', action:'edit')}/'+ data.id +'" ><button class= "btn btn-outline-primary"><i class="bi bi-pencil"></i></button></a>'
                                    // +'<a href="${createLink(controller:'cliente', action:'delete')}/'+ data.id +'" ><button class= "btn btn-outline-danger"><i class="bi bi-x-circle"></i></button></a>'
                                ); }
                        },
                        {
                            "data": "nome"
                        },
                        {
                            "data": "cpfOuCnpj"
                        },
                                                {
                            "data": "idade"
                        },
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