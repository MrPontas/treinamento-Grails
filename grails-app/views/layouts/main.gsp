<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <asset:link rel="icon" href="favicon.png" type="image/x-ico"/>

    <asset:stylesheet src="application.css"/>
    <asset:stylesheet src="bootsrap.min.css"/>
    <asset:stylesheet src="agriwin.css"/>
    <%-- <script src="http://requirejs.org/docs/release/2.1.5/comments/require.js"></script> --%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">

  
    <g:layoutHead/>
</head>

<body>
<%-- <nav class="navbar navbar-dark bg-primary"> --%>
  <!-- Navbar content -->

<header >
    <g:link controller="cliente" class="${((controllerName == "cliente") ? "menuSelecionado" : "")}" >
        <g:message code="cliente.label" />
    </g:link>
    <g:link controller="produto" class="${(((controllerName == "produto") || (controllerName == "relatorioProduto")) ? "menuSelecionado" : "")}" >
        <g:message code="produto.label" />
    </g:link>
    <g:link controller="compra" class="${(((controllerName == "compra") || (controllerName == "relatorioCompra")) ? "menuSelecionado" : "")}" >
        <g:message code="compra.label" />
    </g:link>
        <g:link controller="usuario" class="${((controllerName == "usuario") ? "menuSelecionado" : "")}" >
        <g:message code="usuario.label" />
    </g:link>
    <div class="divExit">
        <g:link controller= "usuario" action="logOut">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-box-arrow-right svgExit" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M10 12.5a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v2a.5.5 0 0 0 1 0v-2A1.5 1.5 0 0 0 9.5 2h-8A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-2a.5.5 0 0 0-1 0v2z"/>
                <path fill-rule="evenodd" d="M15.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L14.293 7.5H5.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3z"/>
            </svg>
            <g:message code="default.exit.label"/>

        </g:link>  
    </div>
    </header>
<%-- </nav> --%>


<g:layoutBody/>

<div id="spinner" class="spinner" style="display:none;">
    <g:message code="spinner.alt" default="Loading&hellip;"/>
</div>

<%-- <g:ifPageProperty name="page.jsEspecifico">
        <g:pageProperty name="page.jsEspecifico"/>
</g:ifPageProperty> --%>
<%-- <asset:javascript src="ajaxPost.js"/> --%>
<%-- <asset:javascript src="jquery-3.5.1.js"/>--%>
<!-- Link to Google CDN's jQuery + jQueryUI; fall back to local -->
<%-- <script>
    if (!window.jQuery) {
        document.write('<script src="${resource(dir: 'js', file: 'libs/jquery-2.1.1.min.js')}"></script>');
    }
</script> --%>

<asset:javascript src="application.js"/>
<%-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script> --%>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<%-- <script>
    if (!window.jQuery.ui) {
        document.write('<script src="${resource(dir: 'js', file: 'libs/jquery-ui-1.10.3.min.js')}"></script>');
    }
</script> --%>
<script src="${resource(dir: 'js', file: 'autocompletecombo.js')}"></script>
<script src="${resource(dir: 'js', file: 'ajaxPost.js')}"></script>
<g:ifPageProperty name="page.jsEspecifico">
        <g:pageProperty name="page.jsEspecifico"/>
    </g:ifPageProperty>

</body>
</html>
