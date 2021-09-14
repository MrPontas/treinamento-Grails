package teste

class ItemCompra {
    BigDecimal valorUnitario
    BigDecimal quantidade
    BigDecimal valorTotal
    Produto produto
    static belongsTo = [compra: Compra]
    
    ItemCompra(){
        this.quantidade = 1
    }

    static constraints = {
        valorUnitario(nullable: true)
        quantidade(nullable: false)
        valorTotal(nullable: false)
        produto(nullable: false)
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'sequence_item_compra']
        itensCompra cascade: "all-delete-orphan"
    }
}
