package lv.id.jc.ipv4

import spock.lang.*

@Issue('1')
@Title('IntSet')
@Narrative('Container for storing a set of numbers')
class IntSetTest extends Specification {

    @Subject
    def container = new IntSet()

    def 'should return zero for an empty container'() {
        expect:
        container.size() == 0
    }

    def 'should not thrown an exception for correct ip address'() {
        when:
        numbers.each { container.add it }

        then:
        container.size() == expected

        where:
        numbers   | expected
        []        | 0
        [0]       | 1
        [12]      | 1
        [1, 1, 2] | 2
        [1, 1]    | 1

    }

}
