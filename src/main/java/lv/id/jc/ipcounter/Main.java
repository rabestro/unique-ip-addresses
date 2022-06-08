package lv.id.jc.ipcounter;

import lv.id.jc.ipcounter.impl.SimpleContainer;
import lv.id.jc.ipcounter.impl.SimpleConverter;
import lv.id.jc.ipcounter.impl.SimpleCounter;

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
    public static void main(String[] args) throws IOException {
        var start = Instant.now();

        var counter = new SimpleCounter(new SimpleContainer(), new SimpleConverter());

        try (var addresses = Files.lines(Path.of(args[0]))) {
            var result = counter.applyAsLong(addresses);
            System.out.println(result);
        }

        System.out.println(Duration.between(start, Instant.now()));
    }

}