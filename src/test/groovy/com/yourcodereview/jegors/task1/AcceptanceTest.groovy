package com.yourcodereview.jegors.task1

import spock.lang.*

import java.nio.file.Files
import java.nio.file.Path

@Issue('1')
@Title('The user gets the number of unique IPv4 addresses')
@Narrative('''As a user 
I want to count the number of unique IPv4 addresses
So that I can analyze internet traffic''')
@See('https://en.wikipedia.org/wiki/IPv4')
class AcceptanceTest extends Specification {
    def stubOut
    PrintStream originalOut

    @TempDir
    Path temp

    void setup() {
        originalOut = System.out
        stubOut = new ByteArrayOutputStream()
        System.out = new PrintStream(stubOut)
    }

    void cleanup() {
        System.out = originalOut
    }

    def 'should check number of arguments'() {

        when: 'we run the program without any arguments'
        Main.main()

        then: 'we got an error message'
        stubOut =~ /specify one argument/

        when: 'we run the program with more then one argument'
        Main.main('one', 'two')

        then: 'we got an error message'
        stubOut =~ /specify one argument/
    }

    def 'should count #expected number of unique IPv4 addresses'() {

        given: 'a text file with IPv4 addresses'
        def sourcePath = temp.resolve 'ip.txt'
        Files.writeString sourcePath, content

        when: 'we run the application with file name of the text file'
        Main.main sourcePath.toString()

        then: 'we get a number of unique IP addresses'
        def actual = stubOut.toString() as int

        and:
        actual == expected

        cleanup:
        Files.deleteIfExists sourcePath

        where: 'content of text file'
        content << [
                '0.0.0.0',
                '1.2.3.4\n4.5.6.7\n9.9.8.8',
                '''
127.7.28.11
1.2.3.4
43.234.255.245
43.234.255.245
1.2.3.4
0.0.0.0
255.255.255.255
127.0.0.1
''',
                ''
        ]
        and: 'expected number of unique IP addresses'
        expected << [1, 3, 6, 0]
    }
}
