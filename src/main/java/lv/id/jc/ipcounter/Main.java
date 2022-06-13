package lv.id.jc.ipcounter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static java.lang.System.Logger.Level.ERROR;
import static java.lang.System.Logger.Level.INFO;
import static lv.id.jc.ipcounter.collector.IPv4Collector.countingUnique;

/**
 * Console application for counting unique addresses in a text file
 */
@SuppressWarnings("squid:S106")
public class Main {
    private static final System.Logger LOGGER = System.getLogger("IPv4 Counter");

    private static final Predicate<String> IPv4_VALIDATOR = Pattern
            .compile("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)(\\.(?!$)|$)){4}$")
            .asMatchPredicate();

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

        process(path);

        var executionTime = Duration.between(startTime, Instant.now());
        LOGGER.log(INFO, "Execution time: {0}", executionTime);
    }

    private static void process(Path path) {
        try (var lines = Files.lines(path)) {

            var unique = lines
                    .filter(IPv4_VALIDATOR) // if no validation required this line should be deleted
                    .collect(countingUnique());

            System.out.println(unique);

        } catch (IOException e) {
            LOGGER.log(ERROR, "Error during processing file: {0}", path);
        }
    }
}
