package teste

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class CompraServiceSpec extends Specification {

    CompraService compraService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Compra(...).save(flush: true, failOnError: true)
        //new Compra(...).save(flush: true, failOnError: true)
        //Compra compra = new Compra(...).save(flush: true, failOnError: true)
        //new Compra(...).save(flush: true, failOnError: true)
        //new Compra(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //compra.id
    }

    void "test get"() {
        setupData()

        expect:
        compraService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Compra> compraList = compraService.list(max: 2, offset: 2)

        then:
        compraList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        compraService.count() == 5
    }

    void "test delete"() {
        Long compraId = setupData()

        expect:
        compraService.count() == 5

        when:
        compraService.delete(compraId)
        sessionFactory.currentSession.flush()

        then:
        compraService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Compra compra = new Compra()
        compraService.save(compra)

        then:
        compra.id != null
    }
}
