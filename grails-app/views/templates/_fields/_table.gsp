<head>
    <asset:stylesheet src="table.css"/>
    <%-- <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.css"> --%>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.0/css/jquery.dataTables.css">

</head>
<table class="table">
    <thead>
         <tr class="table-dark table-stripped">
            <g:each in="${domainProperties}" var="p" status="i">
                <g:sortableColumn property="${p.name}" title="${p.name}" />                   
            </g:each>
            <th>Editar</th>
        </tr>
        
    </thead>

    <tbody>
        <g:each in="${collection}" var="bean" status="i">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'} ">
                <g:each in="${domainProperties}" var="p" status="j">
                    <g:if test="${j==0}">
                        <td>
                            ${bean.nome}
                        </td>
                    </g:if>
                    <g:else>
                        <td>
                            <f:display bean="${bean}" property="${p.name}" displayStyle="${displayStyle?:'table'}" />
                        </td>
                    </g:else>
                </g:each>
                <td>
                    <g:link action="edit" id="${bean.id}">
                        <button class= "btn btn-outline-primary">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16">
                                <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
                            </svg>
                        </button>
                    </g:link>
                </td>
            </tr>
        </g:each>
    </tbody>
    <%-- <script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.25/datatables.min.js"></script> --%>
    <%-- <asset:javascript src="table.js"/> --%>

</table>