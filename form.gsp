<%@ page import="aplicacaoteste.Produto" %>

<div class="container">

    <div class="fieldcontain ${hasErrors(bean: produtoInstance, field: 'nome', 'error')} ">
            <label for="nome">
                    <g:message code="produto.nome.label" default="Nome" />

            </label>
            <g:textField name="nome" value="${produtoInstance?.nome}"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: produtoInstance, field: 'categoria', 'error')} ">
            <label for="categoria">
                    <g:message code="produto.categoria.label" default="Categoria" />

            </label>
            <g:select name="categoria" from="${produtoInstance.constraints.categoria.inList}" value="${produtoInstance?.categoria}" valueMessagePrefix="produto.categoria" noSelection="['': '']"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: produtoInstance, field: 'descricao', 'error')} ">
            <label for="descricao">
                    <g:message code="produto.descricao.label" default="Descricao" />

            </label>
            <g:textField name="descricao" value="${produtoInstance?.descricao}"/>
    </div>
    
    <div class="fieldcontain ${hasErrors(bean: produtoInstance, field: 'valorProduto', 'error')} ">
            <label for="valorProduto">
                    <g:message code="produto.valorProduto.label" default="valorProduto" />

            </label>
            <g:textField name="valorProduto" value="${produtoInstance?.valorProduto}"/>
    </div>
</div>


