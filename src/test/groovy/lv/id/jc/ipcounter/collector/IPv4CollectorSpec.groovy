package lv.id.jc.ipcounter.collector

import lv.id.jc.ipcounter.container.DualBitSetContainer
import lv.id.jc.ipcounter.container.LongArrayContainer
import lv.id.jc.ipcounter.converter.IPv4Converter
import lv.id.jc.ipcounter.converter.IPv4SimpleConverter
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title('IPv4 Collector')
@Narrative('Integration test for IPv4 Collector')
@Subject([IPv4Collector, IPv4Converter, LongArrayContainer])
class IPv4CollectorSpec extends Specification {

    def 'should count unique IPv4 addresses'() {
        given: 'IPv4 collector with standard container and converter'
        def underTest = IPv4Collector.countingUnique()

        when: 'we use collector to count unique ip addresses'
        def unique = ip.stream().collect(underTest)

        then: 'we get a correct number of unique addresses'
        unique == expected

        where:
        ip                                                       | expected
        []                                                       | 0
        ['0.0.0.0']                                              | 1
        ['1.0.1.0']                                              | 1
        ['0.0.0.0', '119.23.56.11', '43.111.0.12']               | 3
        ['1.2.3.4', '4.3.2.1', '1.2.3.4', '4.3.2.1']             | 2
        ['12.71.12.1', '12.71.12.2', '12.71.12.3', '12.71.12.4'] | 4
    }

    def 'should count unique IPv4 addresses with DualBitSetContainer'() {

        given: 'IPv4 collector with custom container and converter'
        def underTest = new IPv4Collector(
                () -> new DualBitSetContainer(),
                new IPv4SimpleConverter())

        when: 'we use collector to count unique ip addresses'
        def unique = ip.stream().collect(underTest)

        then: 'we get a correct number of unique addresses'
        unique == expected

        where:
        ip                                                       | expected
        []                                                       | 0
        ['0.0.0.0']                                              | 1
        ['1.0.1.0']                                              | 1
        ['0.0.0.0', '119.23.56.11', '43.111.0.12']               | 3
        ['1.2.3.4', '4.3.2.1', '1.2.3.4', '4.3.2.1']             | 2
        ['12.71.12.1', '12.71.12.2', '12.71.12.3', '12.71.12.4'] | 4
    }
}
