package lv.id.jc.ipcounter.collector

import spock.lang.Specification
import spock.lang.Subject

import java.util.stream.Collector

class IPv4CollectorTest extends Specification {

    @Subject
    def underTest = IPv4Collector.countingUnique()

    def 'should not support parallel processing'() {
        given:
        def a = Mock IntContainer
        def b = Mock IntContainer

        when:
        underTest.combiner().apply(a, b)

        then:
        thrown(UnsupportedOperationException)
    }

    def 'should have proper characteristics'() {
        expect:
        underTest.characteristics() == [Collector.Characteristics.UNORDERED] as Set
    }
}
