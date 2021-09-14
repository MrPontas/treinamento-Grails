package teste

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON


class UsuarioController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [usuarioList: Usuario.list(params), usuarioTotal: Usuario.count()]
    }

    def create() {
        [usuario: new Usuario(params)]
    }
                    
    def save() {
        def usuario = new Usuario(params)
        if (!usuario.save(flush: true)) {
            render(view: "create", model: [usuario: usuario])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuario.id])
        redirect(action: "index", id: usuario.id)
    }

    def edit(Long id) {
        def usuario = Usuario.get(id)
        if (!usuario) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), id])
            redirect(action: "index")
            return
        }
        usuario.senha = null

        [usuario: usuario]
    }

    def update(Long id, Long version, String login, String senha, String repetirSenha) {
        def usuario = Usuario.get(id)
        if (!usuario) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), id])
            redirect(action: "index")
            return
        }

        if (version != null) {
            if (usuario.version > version) {
                usuario.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'usuario.label', default: 'Usuario')] as Object[],
                          "Another user has updated this Usuario while you were editing")
                render(view: "edit", model: [usuario: usuario])
                return
            }
        }
        if(!senha && !repetirSenha){
            usuario.login = login
            usuario.repetirSenha = usuario.senha
        } else {
            usuario.properties = params
        }
        if (!usuario.save(flush: true)) {
            render(view: "edit", model: [usuario: usuario])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuario.id])
        redirect(action: "index", id: usuario.id)
    }

    def delete(Long id) {
        def usuario = Usuario.get(id)
        if (!usuario) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), id])
            redirect(action: "index")
            return
        }

        try {
            usuario.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'usuario.label', default: 'Usuario'), id])
            redirect(action: "index")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'usuario.label', default: 'Usuario'), id])
            redirect(action: "edit", id: id)
        }
    }

    def login(){
    }

    def validarSenha(String login, String senha){
        def usuarioInstance = Usuario.findByLoginAndSenha(login,senha);
        if (usuarioInstance){
            session.usuario=usuarioInstance

            flash.message="Bem vindo: "+usuarioInstance.login;

            redirect(controller: "cliente")
            return
        } else {
            flash.message="Usuário ou senha incorretos";
        }

        redirect(action:"login")
        return
    }

    def logOut(){
        session.invalidate()
        redirect(action:"login")
        return
    }

    def listUsuario(int length, int start){
        params.max = length;
        params.offset = start;
        int iCol=0;
        String search = params.getAt("search[value]")?.toString()?.trim(), 
               valColuna = params.getAt("columns["+iCol+"][data]"), 
               orderColumn = params.getAt("order[0][column]");
        
        List dados = Usuario.createCriteria().list(params) {
            
            if (search && !search.equals("")){
                or {
                    ilike("login", "%"+search+"%")

                }
            }
            
            if (orderColumn && params.getAt("columns["+orderColumn+"][data]")?.toString()!="")
                order(params.getAt("columns["+orderColumn+"][data]"),params.getAt("order[0][dir]"))
            else 
                order("id","desc")
        }
        
        def recordsTotal = Usuario.count();
        def recordsFiltered = dados.totalCount;

        // dados = dados.collect {it -> return [
        //     id : it.id,
        //     nome : it.nome,
        //     modulo : [nome :it.modulo.nome]
        // ]}
        
        render contentType: "text/json", text: ["draw":params.draw,"recordsTotal":recordsTotal,"recordsFiltered":recordsFiltered,"data": dados ] as JSON;

    }
}