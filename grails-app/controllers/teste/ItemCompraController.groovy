package teste

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class ItemCompraController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [itemCompraList: ItemCompra.list(params), itemCompraTotal: ItemCompra.count()]
    }

    def create() {
        [itemCompra: new ItemCompra(params)]
    }

    def save() {
        def itemCompra = new ItemCompra(params)
        if (!itemCompra.save(flush: true)) {
            render(view: "create", model: [itemCompra: itemCompra])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'itemCompra.label', default: 'ItemCompra'), itemCompra.id])
        redirect(action: "index", id: itemCompra.id)
    }

    def edit(Long id) {
        def itemCompra = ItemCompra.get(id)
        if (!itemCompra) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'itemCompra.label', default: 'ItemCompra'), id])
            redirect(action: "index")
            return
        }

        [itemCompra: itemCompra]
    }

    def update(Long id, Long version) {
        def itemCompra = ItemCompra.get(id)
        if (!itemCompra) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'itemCompra.label', default: 'ItemCompra'), id])
            redirect(action: "index")
            return
        }

        if (version != null) {
            if (itemCompra.version > version) {
                itemCompra.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'itemCompra.label', default: 'ItemCompra')] as Object[],
                          "Another user has updated this ItemCompra while you were editing")
                render(view: "edit", model: [itemCompra: itemCompra])
                return
            }
        }

        itemCompra.properties = params

        if (!itemCompra.save(flush: true)) {
            render(view: "edit", model: [itemCompra: itemCompra])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'itemCompra.label', default: 'ItemCompra'), itemCompra.id])
        redirect(action: "index", id: itemCompra.id)
    }

    def delete(Long id) {
        def itemCompra = ItemCompra.get(id)
        if (!itemCompra) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'itemCompra.label', default: 'ItemCompra'), id])
            redirect(action: "index")
            return
        }

        try {
            itemCompra.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'itemCompra.label', default: 'ItemCompra'), id])
            redirect(action: "index")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'itemCompra.label', default: 'ItemCompra'), id])
            redirect(action: "edit", id: id)
        }
    }

    def getProduto(String search, boolean searchId) {
        params.max = params.max ? params.max : 10
        List dados = Produto.createCriteria().list(params) {
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
