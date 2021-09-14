package teste

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class CompraController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [compraList: Compra.list(params), compraTotal: Compra.count()]
    }

    def create() {
        [compra: new Compra(params)]
    }

    def save() {
        def compra = new Compra(params)
        println params
        if (!compra.save(flush: true)) {
            render(view: "create", model: [compra: compra])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'compra.label', default: 'Compra'), compra.id])
        redirect(action: "index", id: compra.id)
    }

    def edit(Long id) {
        def compra = Compra.get(id)
        if (!compra) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'compra.label', default: 'Compra'), id])
            redirect(action: "index")
            return
        }

        [compra: compra]
    }

    def update(Long id, Long version) {
        def compra = Compra.get(id)
        println compra.data.properties
        if (!compra) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'compra.label', default: 'Compra'), id])
            redirect(action: "index")
            return
        }

        if (version != null) {
            if (compra.version > version) {
                compra.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'compra.label', default: 'Compra')] as Object[],
                          "Another user has updated this Compra while you were editing")
                render(view: "edit", model: [compra: compra])
                return
            }
        }
        compra.itensCompra.clear()
        compra.properties = params

        if (!compra.save(flush: true)) {
            render(view: "edit", model: [compra: compra])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'compra.label', default: 'Compra'), compra.id])
        redirect(action: "index", id: compra.id)
    }

    def delete(Long id) {
        def compra = Compra.get(id)
        if (!compra) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'compra.label', default: 'Compra'), id])
            redirect(action: "index")
            return
        }

        try {
            compra.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'compra.label', default: 'Compra'), id])
            redirect(action: "index")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'compra.label', default: 'Compra'), id])
            redirect(action: "edit", id: id)
        }
    }
    def adicionarProdutos(){
        def compra = new Compra(params)

        compra.itensCompra.add(new ItemCompra())

        render(template:"itensCompra", model:[compra:compra, refreshCB:true])
    }
    def removeProduto(Integer idx){
        def compra = new Compra(params)

        compra.itensCompra.remove(idx)
        
        render(template:"itensCompra", model:[compra:compra])
    }

    def atualizaProduto(Integer idx){
        def compra = new Compra(params)
        def itemCompra = compra.itensCompra[idx]
        
        itemCompra.valorUnitario = itemCompra.produto.valorPadrao

        render(template:"valorUnitario", model:[i: idx, itemCompra: itemCompra, refresh:true])
    }

    
    def atualizaValorTotalItem(Integer idx){
        def compra = new Compra(params)
        def itemCompra
        if(compra.itensCompra.size() > idx){
            itemCompra = compra.itensCompra[idx];
            // println "``````````````"+itemCompra+"``````````````"
            itemCompra.valorTotal = itemCompra.valorUnitario * itemCompra.quantidade

        }
        render(template:"valorTotalItem", model:[i: idx, itemCompra: itemCompra, refresh: true])

    }

    def atualizaValorTotalCompra(){
        def compra = new Compra(params)
        println compra
        compra.valorTotal = 0
        for(item in compra.itensCompra) {
            if(item.valorTotal)
                compra.valorTotal += item.valorTotal
        }

        render(template:"valorTotalCompra", model:[compra:compra, refresh:true])
    }

    def listCompra(int length, int start){
        params.max = length;
        params.offset = start;
        int iCol=0;
        String search = params.getAt("search[value]")?.toString()?.trim(), 
               valColuna = params.getAt("columns["+iCol+"][data]"), 
               orderColumn = params.getAt("order[0][column]");
        
        List dados = Compra.createCriteria().list(params) {
            if (search && !search.equals("")){
                or {
                    while(valColuna!=null){
                        if (valColuna=="valorTotal")
                            sqlRestriction("to_char(valor_total, 'FM999G999G999D00') like ?", [search+"%"])
                        else if (valColuna == "cliente.nome")
                            cliente{
                                ilike("nome", "%"+search+"%")
                            }
                        else if (!valColuna.equals(""))
                            ilike(valColuna, "%"+search+"%")
                        
                        valColuna = params.getAt("columns["+(iCol++)+"][data]");
                    }
                }
            }
            
            if (orderColumn && params.getAt("columns["+orderColumn+"][data]")?.toString()!="")
                if(params.getAt("columns["+orderColumn+"][data]").toString() == "cliente.nome"){
                    cliente {
                        order("nome", params.getAt("order[0][dir]"))
                    }
                }
                else
                    order(params.getAt("columns["+orderColumn+"][data]"),params.getAt("order[0][dir]"))
            else order("id","desc")
        
        }
        def recordsTotal = Compra.count();
        def recordsFiltered = dados.totalCount;
        dados = dados.collect {it -> return [
            id: it.id,
            cliente: [nome: it.cliente.nome], //cliente.nome
            valorTotal: formatNumber(locale: (new Locale ("pt", "BR")), number: it.valorTotal, format: '###,###,##0.00')
        ]}
        
        render contentType: "text/json", text: ["draw":params.draw,"recordsTotal":recordsTotal,"recordsFiltered":recordsFiltered,"data": dados ] as JSON;
    }

    def getCliente(String search, boolean searchId){
        params.max = params.max ? params.max : 10
        List dados = Cliente.createCriteria().list(params) {
            if(searchId)
                eq("id", search.toLong())
            else
                ilike("nome", "%"+search+"%")
        }

        dados = dados.collect {it -> return [
            id: it.id,
            nome: it.nome
        ]};

        render dados as JSON;
    }
}
