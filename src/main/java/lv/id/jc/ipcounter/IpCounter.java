package lv.id.jc.ipcounter;

import java.util.function.ToLongFunction;
import java.util.stream.Stream;

/**
 * Counter of unique IPv4 addresses.
 *
 * Counts the number of unique IPv4 addresses in a string stream.
 */
public interface IpCounter extends ToLongFunction<Stream<String>> {
}
