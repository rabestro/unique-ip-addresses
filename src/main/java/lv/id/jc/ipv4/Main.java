package lv.id.jc.ipv4;

import java.nio.file.Path;

/**
 * Console application for counting unique addresses in a text file
 *
 */
public class Main {
    /**
     * Application start point
     *
     * The program takes a filename as a command line argument.
     * The file must contain one address per line. The program
     * prints the number of unique addresses in this file.
     *
     * @param args - path to the test file in the first argument
     * @throws IndexOutOfBoundsException if no arguments provided
     */
    public static void main(String[] args) {
        var path = Path.of(args[0]);

        System.out.println(path);
    }
}