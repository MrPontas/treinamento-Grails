package teste

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class ClienteController {

    static allowedMethods = [save: "POST", update: "POST", delete:"POST"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [clienteList: Cliente.list(params), clienteTotal: Cliente.count()]
    }

    def create() {
        [cliente: new Cliente(params)]
    }

    def save() {
        def cliente = new Cliente(params)
        if (!cliente.save(flush: true)) {
            render(view: "create", model: [cliente: cliente])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'cliente.label', default: 'Cliente'), cliente.id])
        redirect(action: "index", id: cliente.id)
    }

    def edit(Long id) {
        def cliente = Cliente.get(id)
        if (!cliente) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cliente.label', default: 'Cliente'), id])
            redirect(action: "index")
            return
        }

        [cliente: cliente]
    }


    def update(Long id, Long version) {
        def cliente = Cliente.get(id)
        if (!cliente) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cliente.label', default: 'Cliente'), id])
            redirect(action: "index")
            return
        }

        if (version != null) {
            if (cliente.version > version) {
                cliente.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'cliente.label', default: 'Cliente')] as Object[],
                          "Another user has updated this Cliente while you were editing")
                render(view: "edit", model: [cliente: cliente])
                return
            }
        }

        cliente.properties = params

        if (!cliente.save(flush: true)) {
            render(view: "edit", model: [cliente: cliente])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'cliente.label', default: 'Cliente'), cliente.id])
        redirect(action: "index", id: cliente.id)
    }

    def delete(Long id) {
        def cliente = Cliente.get(id)
        if (!cliente) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cliente.label', default: 'Cliente'), id])
            redirect(action: "index")
            return
        }

        try {
            cliente.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'cliente.label', default: 'Cliente'), id])
            redirect(action: "index")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'cliente.label', default: 'Cliente'), id])
            redirect(action: "edit", id: id)
        }
    }

    def listCliente(int length, int start){
        params.max = length;
        params.offset = start;
        int iCol=0;
        String search = params.getAt("search[value]")?.toString()?.trim(), 
               valColuna = params.getAt("columns["+iCol+"][data]"), 
               orderColumn = params.getAt("order[0][column]");
        
        List dados = Cliente.createCriteria().list(params) {
            
            if (search && !search.equals("")){
                or {
                    // eq("to_char(idade, 'FM999G999G999D00') like ?", [search+"%"])
                    ilike("nome", "%"+search+"%")
                    // ilike("idade", "%"+search+"%")
                    ilike("cpfOuCnpj", "%"+search+"%")

                    while(valColuna!=null){
                        if (valColuna=="idade")
                            sqlRestriction("to_char(idade, 'FM999G999G999D00') like ?", [search+"%"])
                        else if (!valColuna.equals("") && valColuna!="idade")
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
        
        def recordsTotal = Cliente.count();
        def recordsFiltered = dados.totalCount;

        // dados = dados.collect {it -> return [
        //     id : it.id,
        //     nome : it.nome,
        //     modulo : [nome :it.modulo.nome]
        // ]}
        
        render contentType: "text/json", text: ["draw":params.draw,"recordsTotal":recordsTotal,"recordsFiltered":recordsFiltered,"data": dados ] as JSON;

    }
}
