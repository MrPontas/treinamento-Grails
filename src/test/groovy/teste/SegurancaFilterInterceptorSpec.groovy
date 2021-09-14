package teste

import grails.testing.web.interceptor.InterceptorUnitTest
import spock.lang.Specification

class SegurancaFilterInterceptorSpec extends Specification implements InterceptorUnitTest<SegurancaFilterInterceptor> {

    def setup() {
    }

    def cleanup() {

    }

    void "Test segurancaFilter interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"segurancaFilter")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}
