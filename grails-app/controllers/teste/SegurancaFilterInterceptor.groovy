package teste


class SegurancaFilterInterceptor {
    
    SegurancaFilterInterceptor() {
        matchAll().excludes(controller:"usuario", action:"login").excludes(controller:"usuario", action:"validarSenha")

    }

    boolean before() { 
        if (!session.usuario){
            redirect(controller:"usuario", action:"login")
            flash.message="Faca o seu login sem vergonhaaa"
            false
        }

        true 
     }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
