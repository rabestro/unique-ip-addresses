package com.yourcodereview.jegors.task1.collector

import com.yourcodereview.jegors.task1.container.IntContainer
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import java.util.stream.Collector

@Title('IPv4 Collector')
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
        def ex = thrown(UnsupportedOperationException)
        and:
        ex.message =~ "(is not|isn't) implemented"
    }

    def 'should have proper characteristics'() {
        expect:
        underTest.characteristics() == [Collector.Characteristics.UNORDERED] as Set
    }
}
