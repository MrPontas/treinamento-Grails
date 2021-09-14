<head>
    <asset:stylesheet src="usuario.css"/>
</head>
<div class="fieldcontain required ">
    <div class="formUsuario ${hasErrors(bean: usuario, field: 'login', 'error')}">
        <g:render template="login"/>
        <g:render template="senha"/>
        <g:render template="repetirSenha"/>
    </div>
</div>

