package teste

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class ProdutoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [produtoList: Produto.list(params), produtoTotal: Produto.count()]
    }

    def create() {
        [produto: new Produto(params)]
    }

    def save() {
        def produto = new Produto(params)
        if (!produto.save(flush: true)) {
            render(view: "create", model: [produto: produto])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'produto.label', default: 'Produto'), produto.id])
        redirect(action: "index", id: produto.id)
    }

    def edit(Long id) {
        def produto = Produto.get(id)
        if (!produto) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'produto.label', default: 'Produto'), id])
            redirect(action: "index")
            return
        }

        [produto: produto]
    }

    def update(Long id, Long version) {
        def produto = Produto.get(id)
        if (!produto) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'produto.label', default: 'Produto'), id])
            redirect(action: "index")
            return
        }

        if (version != null) {
            if (produto.version > version) {
                produto.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'produto.label', default: 'Produto')] as Object[],
                          "Another user has updated this Produto while you were editing")
                render(view: "edit", model: [produto: produto])
                return
            }
        }

        produto.properties = params

        if (!produto.save(flush: true)) {
            render(view: "edit", model: [produto: produto])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'produto.label', default: 'Produto'), produto.id])
        redirect(action: "index", id: produto.id)
    }

    def delete(Long id) {
        def produto = Produto.get(id)
        if (!produto) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'produto.label', default: 'Produto'), id])
            redirect(action: "index")
            return
        }

        try {
            produto.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'produto.label', default: 'Produto'), id])
            redirect(action: "index")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'produto.label', default: 'Produto'), id])
            redirect(action: "edit", id: id)
        }
    }

    def listProduto(int length, int start){
        params.max = length;
        params.offset = start;
        int iCol=0;
        String search = params.getAt("search[value]")?.toString()?.trim(), 
               valColuna = params.getAt("columns["+iCol+"][data]"), 
               orderColumn = params.getAt("order[0][column]");
        
        List dados = Produto.createCriteria().list(params) {
            if (search && !search.equals("")){
                or {
                    // eq("to_char(valor_padrao, 'FM999G999G999D00') like ?", [search+"%"])
                    // ilike("nome", "%"+search+"%")

                    while(valColuna!=null){
                        if (valColuna=="valorPadrao")
                            sqlRestriction("to_char(valor_padrao, 'FM999G999G999D00') like ?", [search+"%"])
                        else if (!valColuna.equals("") && valColuna!="valorPadrao")
                            ilike(valColuna, "%"+search+"%")
                        
                        valColuna = params.getAt("columns["+(iCol++)+"][data]");
                    }
                }
            }
            
            if (orderColumn && params.getAt("columns["+orderColumn+"][data]")?.toString()!="")
                order(params.getAt("columns["+orderColumn+"][data]"),params.getAt("order[0][dir]"))
            else 
                order("id","desc")
        }
        
        def recordsTotal = Produto.count();
        def recordsFiltered = dados.totalCount;
        def mascara;

        dados = dados.collect {it -> 
            return [
                id : it.id,
                nome : it.nome,
                valorPadrao : formatNumber(locale: (new Locale ("pt", "BR")), number: it.valorPadrao, format:'###,###,##0.00')
            ]
        }
        
        render contentType: "text/json", text: ["draw":params.draw,"recordsTotal":recordsTotal,"recordsFiltered":recordsFiltered,"data": dados ] as JSON;

    }
}
