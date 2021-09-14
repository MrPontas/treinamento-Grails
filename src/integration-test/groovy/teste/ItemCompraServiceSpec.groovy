package teste

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ItemCompraServiceSpec extends Specification {

    ItemCompraService itemCompraService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ItemCompra(...).save(flush: true, failOnError: true)
        //new ItemCompra(...).save(flush: true, failOnError: true)
        //ItemCompra itemCompra = new ItemCompra(...).save(flush: true, failOnError: true)
        //new ItemCompra(...).save(flush: true, failOnError: true)
        //new ItemCompra(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //itemCompra.id
    }

    void "test get"() {
        setupData()

        expect:
        itemCompraService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ItemCompra> itemCompraList = itemCompraService.list(max: 2, offset: 2)

        then:
        itemCompraList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        itemCompraService.count() == 5
    }

    void "test delete"() {
        Long itemCompraId = setupData()

        expect:
        itemCompraService.count() == 5

        when:
        itemCompraService.delete(itemCompraId)
        sessionFactory.currentSession.flush()

        then:
        itemCompraService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ItemCompra itemCompra = new ItemCompra()
        itemCompraService.save(itemCompra)

        then:
        itemCompra.id != null
    }
}
