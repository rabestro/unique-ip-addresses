package com.yourcodereview.jegors.task1;

import com.yourcodereview.jegors.task1.container.BitSetContainer;
import com.yourcodereview.jegors.task1.container.DualBitSetContainer;
import com.yourcodereview.jegors.task1.container.LongArrayContainer;
import com.yourcodereview.jegors.task1.counter.IPStreamCounter;
import com.yourcodereview.jegors.task1.counter.IPv4CollectorCounter;
import com.yourcodereview.jegors.task1.counter.IPv4Counter;
import com.yourcodereview.jegors.task1.counter.NaiveCounter;
import com.yourcodereview.jegors.task1.counter.NaiveIntCounter;
import com.yourcodereview.jegors.task1.counter.SimpleCounter;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

import static java.lang.System.Logger.Level.ERROR;
import static java.lang.System.Logger.Level.INFO;

/**
 * Console application for counting unique addresses in a text file
 */
@SuppressWarnings("squid:S106")
public class Main {
    private static final System.Logger LOGGER = System.getLogger("IPv4 Counter");

    private static final Map<String, IPv4Counter> counters = Map.of(
            "simple", new SimpleCounter(),
            "naive", new NaiveCounter(),
            "naiveInt", new NaiveIntCounter(),
            "longArray", new IPv4CollectorCounter(LongArrayContainer::new),
            "dualBitSet", new IPv4CollectorCounter(DualBitSetContainer::new),
            "level1", new IPv4CollectorCounter(() -> new BitSetContainer(1)),
            "level8", new IPv4CollectorCounter(() -> new BitSetContainer(8)),
            "stream", new IPStreamCounter()
    );

    /**
     * Application start point
     * <p>
     * The program takes a filename as a command line argument.
     * The file must contain one address per line. The program
     * prints the number of unique addresses in this file.
     *
     * @param args - path to the test file in the first argument
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("""
                    Please specify two arguments: the counter name and the path to the file.
                    Available counters are""" + " " + counters.keySet()
            );

            LOGGER.log(ERROR, "Invalid number of arguments. Two expected, provided: {0}", args.length);
            return;
        }
        var counter = counters.get(args[0]);
        var path = Path.of(args[1]);

        var startTime = Instant.now();
        LOGGER.log(INFO, "Execution start time: {0}", startTime);

        System.out.println(counter.countUnique(path));

        var executionTime = Duration.between(startTime, Instant.now());
        LOGGER.log(INFO, "Execution time: {0}", executionTime);
    }

}
