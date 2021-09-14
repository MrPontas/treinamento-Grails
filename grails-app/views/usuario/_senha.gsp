    
<asset:stylesheet src="usuario.css" />
<label for="senha" class="senhaLabel">
    <g:message code="senha.label" default="Senha" />
    <span class='required-indicator'>*</span>
    <g:passwordField name="senha" value="${usuario.senha}" />
</label>