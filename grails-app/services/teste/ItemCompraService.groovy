package teste

import grails.gorm.services.Service

@Service(ItemCompra)
interface ItemCompraService {

    ItemCompra get(Serializable id)

    List<ItemCompra> list(Map args)

    Long count()

    void delete(Serializable id)

    ItemCompra save(ItemCompra itemCompra)

}