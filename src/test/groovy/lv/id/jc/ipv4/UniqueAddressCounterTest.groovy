package lv.id.jc.ipv4

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

@Title('Unique Address Counter')
@Narrative('Unique IPv4 addresses counter')
class UniqueAddressCounterTest extends Specification {
    def container = Mock IntSet
    def converter = Mock Ipv4ToIntConverter

    def "should count unique IPv4 addresses"() {
        given: 'counter with container and converter'
        def counter = new UniqueAddressCounter(container, converter)

        when:
        counter.applyAsLong(ip.stream())

        then: 'for each IP address, the converter was called'
        ip.size() * converter.applyAsInt(_)

        and: 'each number has been added to the container'
        ip.size() * container.add(_)

        and: 'the calculation of unique numbers is called once'
        1 * container.size()

        where:
        ip << [
                [], ['192.198.15.11', '200.0.0.12']
        ]

    }
}
