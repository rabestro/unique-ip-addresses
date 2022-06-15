package com.yourcodereview.jegors.task1.container


import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

@Title('BitSetContainer')
@Narrative('Configurable Container for storing a set of int numbers')
class BitSetContainerTest extends Specification {

    def 'should count unique numbers'() {
        given: 'an empty int container with configured level'
        def container = new BitSetContainer(level)

        expect: 'zero for an empty container'
        container.countUnique() == 0

        when: 'we add some int numbers'
        numbers.each { container.add it }

        then: 'it correctly counts unique numbers'
        container.countUnique() == expected

        where:
        level | numbers                                          | expected
        1     | []                                               | 0
        1     | [0]                                              | 1
        1     | [1]                                              | 1
        1     | [1, 1, 2]                                        | 2
        1     | [1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 5, 5, 5, 5]       | 3
        1     | [-1, 0, 1]                                       | 3
        1     | [Integer.MIN_VALUE, 0, Integer.MAX_VALUE]        | 3
        1     | [Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE] | 5
        1     | [-9, -7, -3, -9, 0, 1, -3, 0, 5, 9, 5]           | 7
        1     | [-1, 0, 1, 3, 4, 6, 7, 8, 9, 10, 11, 12]         | 12
        1     | [-128_984, -2_098_321_032, 143, 0, 213_219_872]  | 5
        2     | []                                               | 0
        2     | [0]                                              | 1
        2     | [1]                                              | 1
        2     | [-1, 0, 1]                                       | 3
        2     | [Integer.MIN_VALUE, 0, Integer.MAX_VALUE]        | 3
        2     | [Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE] | 5
        2     | [1, 1, 2]                                        | 2
        2     | [1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 5, 5, 5, 5]       | 3
        2     | [-9, -7, -3, -9, 0, 1, -3, 0, 5, 9, 5]           | 7
        2     | [-1, 0, 1, 3, 4, 6, 7, 8, 9, 10, 11, 12]         | 12
        4     | []                                               | 0
        4     | [0]                                              | 1
        4     | [1]                                              | 1
        4     | [-1, 0, 1]                                       | 3
        4     | [Integer.MIN_VALUE, 0, Integer.MAX_VALUE]        | 3
        4     | [Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE] | 5
        4     | [1, 1, 2]                                        | 2
        4     | [1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 5, 5, 5, 5]       | 3
        4     | [-9, -7, -3, -9, 0, 1, -3, 0, 5, 9, 5]           | 7
        4     | [-1, 0, 1, 3, 4, 6, 7, 8, 9, 10, 11, 12]         | 12
        8     | []                                               | 0
        8     | [0]                                              | 1
        8     | [1]                                              | 1
        8     | [-1, 0, 1]                                       | 3
        8     | [Integer.MIN_VALUE, 0, Integer.MAX_VALUE]        | 3
        8     | [Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE] | 5
        8     | [1, 1, 2]                                        | 2
        8     | [1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 5, 5, 5, 5]       | 3
        8     | [-9, -7, -3, -9, 0, 1, -3, 0, 5, 9, 5]           | 7
        8     | [-1, 0, 1, 3, 4, 6, 7, 8, 9, 10, 11, 12]         | 12
    }
}
