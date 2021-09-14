package teste

class Cliente {
    String nome
    String cpfOuCnpj
    Integer idade

    static constraints = {
        nome(nullable: false, blank: false)
        cpfOuCnpj(nullable: true, size: 11..14)
        idade(nullable: false, min: 0, max: 130)
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'sequence_cliente']
    }
}
