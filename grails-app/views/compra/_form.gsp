<head>
    <asset:stylesheet src="compraForm.css"/>
</head>
    <div class="divClienteData">
        <div class="select-div fieldcontain required ${hasErrors(bean: compra, field: 'cliente', 'error')} ">
            <label for="cliente.id" style="width: 120px">
                <g:message code="cliente.label" default="Cliente" />
                <span class='required-indicator'>*</span>
            </label>
            <%-- <g:select
                from="${teste.Cliente.list()}"
                name="cliente.id"
                value="${compra?.cliente?.id}"
                optionKey="id"
                optionValue="nome"
                noSelection="['':'']" /> --%>
            <g:select id="cliente" name="cliente.id" 
                from="${teste.Cliente.get(compra.cliente?.id)}"
                value="${compra.cliente?.id}"
                noSelection="['null':'']" 
                class="form-control comboboxautocomplete"
                servidor="${createLink(uri: '/', absolute:true)}"
                controller="compra"
                action="getCliente"
                optionKey="id"
                optionValue="nome"
                camposLabel="nome"
                campoValor="id"
                placeholder="Selecione"/>
        </div>
        <div class="divData fieldcontain required ${hasErrors(bean: compra, field: 'data', 'error')}" >
            <label for="data" style="width: 200px">
                <g:message code="compra.data.label" default="Data" />
                <span class='required-indicator'>*</span>
            </label>
            <input name="data" class="form-control" type="datetime-local" value="${formatDate(date:(compra?.data ? compra?.data : new Date()), format: "yyyy-MM-dd'T'HH:mm")}" />
        </div>
    </div>
    
    <div id="divProdutos">
        <g:render template="itensCompra"/>
    </div>

    <div class="divValorTotal" id="divValorTotalCompra" >
        <g:render template="valorTotalCompra" model="['compra':compra]" />
    </div>

