package lv.id.jc.ipcounter;

import lv.id.jc.ipcounter.collector.IPv4Collector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;

/**
 * Console application for counting unique addresses in a text file
 */
public class Main {

    /**
     * Application start point
     * <p>
     * The program takes a filename as a command line argument.
     * The file must contain one address per line. The program
     * prints the number of unique addresses in this file.
     *
     * @param args - path to the test file in the first argument
     * @throws IndexOutOfBoundsException if no arguments provided
     */
    @SuppressWarnings("squid:S106")
    public static void main(String[] args) {
        var start = Instant.now();

        try (var lines = Files.lines(Path.of(args[0]))) {
            System.out.println(
                    lines.collect(IPv4Collector.countingUnique())
            );
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        System.out.println(Duration.between(start, Instant.now()));
    }

}
