package lv.id.jc.ipv4

import spock.lang.PendingFeature
import spock.lang.Specification

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
