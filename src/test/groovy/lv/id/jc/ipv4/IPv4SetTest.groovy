package lv.id.jc.ipv4

import spock.lang.Issue
import spock.lang.Narrative
import spock.lang.PendingFeature
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Issue('1')
@Title('IPv4Set')
@Narrative('Container for storing a set of IP addresses')
class IPv4SetTest extends Specification {

    @Subject
    def container = new IPv4Set()

    def 'should return zero for an empty container'() {
        expect:
        container.size() == 0
    }

    def 'should not thrown an exception for correct ip address'() {
        when:
        container.add(ipAddress)

        then:
        noExceptionThrown()

        where:
        ipAddress << ['0.0.0.0', '255.255.255.255', '128.21.0.15']
    }

    def 'should return one when add first address'() {
        when:
        container.add('0.0.0.0')

        then:
        container.size() == 1
    }

    @PendingFeature
    def 'should thrown an exception if wrong ip address'() {
        when:
        container.add(ipAddress)

        then:
        thrown(Exception)

        where:
        ipAddress << ['0.0.0.', '256.255.255.255', '-128.21.0.15']
    }

}
