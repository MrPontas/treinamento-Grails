<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'produto.label', default: 'Produto')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>

        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css" type="text/css" />
    </head>
    <body>
        <div class="nav" role="navigation">
			<g:link class="list" controller="produto" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link>
		</div>

        <div id="list-produto" class="content scaffold-list" role="main">
            <h1><g:message code="relatorio.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            
            <g:form action="criarRelatorio">
				<fieldset class="form" style="border-top: 1px solid black">
                    <h2 style="margin-left: 20px; margin-top:10px">Filtros: </h2>
					<div class='fieldcontain required'>
                        <label for='nome' style="width: 150px">
                            <g:message code="produto.label" default="Produto" />
                        </label>
                        <g:textField name="nome" value="${nome}" />
                        <div style="margin-top: 20px">
                            <label for='dIni' style="width: 150px">
                                <g:message code="dIni.label" default="Criado entre" />
                            </label>
                            <input name="dIni" type="date" value="${formatDate(date: new Date(), format: "yyyy-MM-dd")}" />
                            <label for='dFim' style="width: 35px">
                                <g:message code="dFim.label" default="e" />
                            </label>
                            <input name="dFim" type="date" style="margin-left: 20px" value="${formatDate(date: new Date(), format: "yyyy-MM-dd")}" />
                        </div>

                    </div>
				</fieldset>
				<fieldset class="buttons">
					    <button name="_action_criarRelatorio" class="btn btn-danger btn-relatorio" type="submit" value="${message(code: 'default.button.criarRelatorio.label', default: 'Relatório PDF')}" onclick="desbloquearTelaCookie();">
                            <i class="ace-icon glyphicon glyphicon-print bigger-110"></i>
                            ${message(code: 'default.button.criarRelatorio.label', default: 'Relatório PDF')}
                        </button>
                        <button name="_action_criarRelatorio" class="btn btn-success btn-relatorio" type="submit" value="${message(code: 'default.button.criarRelatorio.label', default: 'Relatório EXCEL')}" onclick="desbloquearTelaCookie();">
                            <i class="ace-icon fa fa-download bigger-110"></i>
                            ${message(code: 'default.button.criarRelatorio.label', default: 'Relatório EXCEL')}
                        </button>
				</fieldset>
			</g:form>
        </div>
    </body>
</html>