package lv.id.jc.ipcounter.collector

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

@Title('IPv4 Converter')
@Narrative('A simple implementation of IPv4 converter')
class IPv4SimpleConverterTest extends Specification {

    def 'should convert string representation of IPv4 to int'() {
        given:
        def converter = new IPv4SimpleConverter()

        when:
        def actual = converter.applyAsInt(ip)

        then:
        actual == expected

        where:
        ip                | expected
        '0.0.0.8'         | 8
        '0.0.0.255'       | 255
        '0.0.1.0'         | 256
        '0.0.1.44'        | 300
        '0.1.0.0'         | 65536
        '0.1.1.0'         | 65792
        '128.1.1.240'     | -2147417616
        '127.1.1.240'     | 2130772464
        '0.0.0.1'         | 1
        '0.0.0.0.'        | 0
        '255.255.255.255' | -1
        '127.255.255.255' | Integer.MAX_VALUE
        '128.0.0.0'       | Integer.MIN_VALUE
    }
}
