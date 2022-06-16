package com.yourcodereview.jegors.task1.container


import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

@Title('DualBitSetContainer')
@Narrative('Container for storing a set of int numbers')
class DualBitSetContainerTest extends Specification {

    def 'should count unique numbers'() {
        given: 'an empty int container'
        def container = new DualBitSetContainer()

        expect: 'zero for an empty container'
        container.countUnique() == 0

        when: 'we add some int numbers'
        numbers.each { container.add it }

        then: 'it correctly counts unique numbers'
        container.countUnique() == expected

        where:
        numbers                                         | expected
        []                                              | 0
        [0]                                             | 1
        [12]                                            | 1
        [1, 1, 2]                                       | 2
        [1, 1]                                          | 1
        [-1, 0, 1]                                      | 3
        [-9, -7, -3, -9, 0, 1, -3, 0, 5, 9, 5]          | 7
        [-128_984, -2_098_321_032, 143, 0, 213_219_872] | 5
        [3, 7, 3, 7, 3, 7, 7, 7, 3, 3, 3, 3, 7, 7]      | 2
        [-1, 0, 1, 3, 4, 6, 7, 8, 9, 10, 11, 12]        | 12
        [Integer.MIN_VALUE, 0, Integer.MAX_VALUE]       | 3
    }

}
