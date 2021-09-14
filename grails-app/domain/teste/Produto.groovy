package teste

class Produto {
    String nome
    BigDecimal valorPadrao
    Date criadoEm

    Produto(){
        this.criadoEm = new Date()
    }

    static constraints = {
        nome(nullable: false)
        valorPadrao(nullable: false)
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'sequence_produto']
    }
}
