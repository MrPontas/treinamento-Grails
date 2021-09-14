<!doctype html>
<html>
<head>
    <%-- <meta name="layout" content="main"/>
    <title>Aplicação teste</title> --%>
    <asset:stylesheet src="index.css"/>
    <asset:stylesheet src="bootstrap.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
</head>
<body style="background: transparent">
    <div id="divContent">
        <g:form action="validarSenha">
            <div id="form-auth">
                <h1>Tela de login</h1>
                <p>Login</p>
                <div class="input-group flex-nowrap">
                    <span class="input-group-text" id="addon-wrapping">
                        <i class="bi bi-person-circle" ></i>
                    </span>
                    <g:textField id="login" name="login" class="form-control" placeholder="Usuário" aria-label="Usuário" aria-describedby="addon-wrapping"/>
                </div>
                <p>Senha</p>
                <div class="input-group flex-nowrap">
                    <span class="input-group-text" id="addon-wrapping">
                        <i class="bi bi-lock-fill"></i>
                    </span>
                <g:passwordField id="senha" name="senha"  class="form-control" placeholder="Senha" aria-label="Senha" aria-describedby="addon-wrapping"/>
                </div>
                <button type="submit" class="btn btn-primary">Iniciar sessão</button>
            </div>
        </g:form>
    </div>
</body>
</html>
