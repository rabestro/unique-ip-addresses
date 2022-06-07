package lv.id.jc.ipv4

import spock.lang.*

@Issue('1')
@Title('Application')
@Narrative('Integration test for the console application')
class CounterAppTest extends Specification {

    def 'should throw an exception if no file name specified'() {
        when:
        CounterApp.main()

        then:
        thrown(IndexOutOfBoundsException)
    }

    @PendingFeature
    def 'should count unique IPv4 addresses'() {

    }
}
