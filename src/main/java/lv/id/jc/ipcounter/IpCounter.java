package lv.id.jc.ipcounter;

import java.util.function.ToLongFunction;
import java.util.stream.Stream;

/**
 * Counter of unique IPv4 addresses.
 *
 * Processes a stream of strings representing IPv4 addresses and counting unique ones.
 */
public interface IpCounter extends ToLongFunction<Stream<String>> {
}
