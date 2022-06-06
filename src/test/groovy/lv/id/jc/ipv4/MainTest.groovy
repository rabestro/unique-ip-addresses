package lv.id.jc.ipv4

import spock.lang.Issue
import spock.lang.Narrative
import spock.lang.PendingFeature
import spock.lang.Specification
import spock.lang.Title

@Issue('1')
@Title('Application')
@Narrative('Integration test for the console application')
class MainTest extends Specification {

    def 'should throw an exception if no file name specified'() {
        when:
        Main.main()

        then:
        thrown(IndexOutOfBoundsException)
    }

    @PendingFeature
    def 'should count unique IPv4 addresses'() {

    }
}
