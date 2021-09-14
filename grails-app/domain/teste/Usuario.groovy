package teste

class Usuario {
    String login
    String senha
    String repetirSenha
    static transients = ['repetirSenha']
    static constraints = {
        login(nullable: false, minSize: 6, unique: true, matches: "[a-zA-Z.]+")
        repetirSenha(bindable:true, blank: false, nullable: false)
        senha(nullable: false, minSize: 6, validator: {data, obj, errors ->
            if (data != obj.repetirSenha) 
                errors.rejectValue("senha", "usuario.senha.validator.invalid", [data] as Object[], "")
        })
    }
    static mapping = {
        id generator: 'sequence', params: [sequence: 'sequence_usuario']
    }
}
