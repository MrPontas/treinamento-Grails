package teste

class Compra {
    Cliente cliente //cliente.nome
    BigDecimal valorTotal
    List itensCompra = []
    Date data

    static hasMany = [itensCompra: ItemCompra]


    Compra(){
        this.valorTotal = 0
        this.data = new Date()
    }
    static constraints = {
        cliente(nullable:false)
        valorTotal(nullable:true)
        itensCompra(nullable:false, validator: {data, obj-> return data?.size()>0 })
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'sequence_compra']
        itensCompra cascade:"all-delete-orphan"
    }
}
