package com.yourcodereview.jegors.task1;

import com.yourcodereview.jegors.task1.container.IntContainer;
import com.yourcodereview.jegors.task1.container.LongArrayContainer;
import com.yourcodereview.jegors.task1.converter.IPv4Converter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

import static java.lang.System.Logger.Level.ERROR;
import static java.lang.System.Logger.Level.INFO;

/**
 * Console application for counting unique addresses in a text file
 */
@SuppressWarnings("squid:S106")
public class Main {
    private static final System.Logger LOGGER = System.getLogger("IPv4 Counter");

    /**
     * Application start point
     * <p>
     * The program takes a filename as a command line argument.
     * The file must contain one address per line. The program
     * prints the number of unique addresses in this file.
     *
     * @param args - path to the test file in the first argument
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Please specify one argument - the path to the file with IP addresses");
            LOGGER.log(ERROR, "Invalid number of arguments. One expected, provided: {0}", args.length);
            return;
        }
        var path = Path.of(args[0]);

        var startTime = Instant.now();
        LOGGER.log(INFO, "Execution start time: {0}", startTime);

        try (var ips = Files.lines(path, StandardCharsets.US_ASCII)) {
            System.out.println(countUnique(ips));
        } catch (IOException e) {
            var message = "Error during processing file: " + path;
            System.out.println(message);
            LOGGER.log(ERROR, message);
        }

        var executionTime = Duration.between(startTime, Instant.now());
        LOGGER.log(INFO, "Execution time: {0}", executionTime);
    }

    private static long countUnique(Stream<String> ipAddresses) {
        return ipAddresses
                .mapToInt(new IPv4Converter())
                .collect(LongArrayContainer::new, IntContainer::add, IntContainer::addAll)
                .countUnique();
    }
}
