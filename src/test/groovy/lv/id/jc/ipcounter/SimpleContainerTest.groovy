package lv.id.jc.ipcounter

import lv.id.jc.ipcounter.impl.SimpleContainer
import spock.lang.Issue
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

@Issue('1')
@Title('IntSet')
@Narrative('Container for storing a set of numbers')
class SimpleContainerTest extends Specification {

    def 'should count unique numbers'() {
        given:
        def container = new SimpleContainer()

        expect: 'zero for an empty container'
        container.count() == 0

        when: 'we add some int numbers'
        numbers.each { container.add it }

        then: 'it correctly counts unique numbers'
        container.count() == expected

        where:
        numbers                                    | expected
        []                                         | 0
        [0]                                        | 1
        [12]                                       | 1
        [1, 1, 2]                                  | 2
        [1, 1]                                     | 1
        [-1, 0, 1]                                 | 3
//        [Integer.MIN_VALUE, 0, Integer.MAX_VALUE] | 3
        [-9, -7, -3, -9, 0, 1, -3, 0, 5, 9, 5]     | 7
        [3, 7, 3, 7, 3, 7, 7, 7, 3, 3, 3, 3, 7, 7] | 2

    }

}
