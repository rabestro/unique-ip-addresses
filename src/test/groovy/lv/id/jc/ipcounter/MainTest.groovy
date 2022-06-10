package lv.id.jc.ipcounter

import spock.lang.*

import java.nio.file.Files
import java.nio.file.Path

import static java.lang.System.lineSeparator

@Issue('1')
@Title('Application')
@Narrative('Functional test of the application')
class MainTest extends Specification {
    def out = new ByteArrayOutputStream()
    def originalOut = System.out

    @TempDir
    Path temp

    void setup() {
        System.out = new PrintStream(out)
    }

    void cleanup() {
        System.out = originalOut
    }

    def 'should check number of arguments'() {
        when: 'we run the program without any arguments'
        Main.main()

        then: 'we got an error message'
        out =~ /Please specify one argument/

        when: 'we run the program with more then one argument'
        Main.main('one', 'two')

        then: 'we got an error message'
        out =~ /Please specify one argument/
    }

    def 'should count unique IPv4 addresses'() {
        given: 'a text file with IPv4 addresses'
        def sourcePath = temp.resolve 'ip.txt'
        Files.writeString sourcePath, content

        when: 'we run the application with file name of the text file'
        Main.main sourcePath.toString()

        then:
        new Scanner(out.toString()).nextInt() == expected

        where:
        content << [
                '0.0.0.0',
                '1.2.3.4\n4.5.6.7\n9.9.8.8',
                [
                        '127.7.28.11',
                        '1.2.3.4',
                        '43.234.255.245',
                        '1.2.3.4',
                        '127.0.0.1'
                ].join(lineSeparator()),
                ''
        ]

        and:
        expected << [1, 3, 4, 0]
    }

}
